package dev.abeatriz.athena_os.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abeatriz.athena_os.dto.category.CategoryCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.category.CategoryDetailDTO;
import dev.abeatriz.athena_os.dto.category.CategoryWithCountProduct;
import dev.abeatriz.athena_os.entity.Category;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import dev.abeatriz.athena_os.mapper.CategoryMapper;
import dev.abeatriz.athena_os.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @MockitoBean
    private CategoryService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper ObjMapper;

    private CategoryMapper mapper = CategoryMapper.INSTANCE;

    private Category categoryEntity;
    private CategoryCreateUpdateDTO categoryCreateDTO;
    private CategoryDetailDTO categoryDetailDTO;

    private Category categoryEntity2;
    private CategoryCreateUpdateDTO categoryCreateDTO2;
    private CategoryDetailDTO categoryDetailDTO2;

    private CategoryWithCountProduct categoryWithCountProduct1;
    private CategoryWithCountProduct categoryWithCountProduct2;

    private List<CategoryDetailDTO> categories;
    private List<CategoryWithCountProduct> categoriesActives;

    @BeforeEach
    void setup() {
        categoryCreateDTO = new CategoryCreateUpdateDTO("Categoria 1", mapper.toString(CategoryStatus.ATIVO));
        categoryEntity = mapper.toEntity(categoryCreateDTO);
        categoryEntity.setCategoryId(1L);
        categoryDetailDTO = mapper.toDTO(categoryEntity);

        categoryCreateDTO2 = new CategoryCreateUpdateDTO("Categoria 2", mapper.toString(CategoryStatus.INATIVO));
        categoryEntity2 = mapper.toEntity(categoryCreateDTO2);
        categoryEntity2.setCategoryId(1L);
        categoryDetailDTO2 = mapper.toDTO(categoryEntity2);

        categoryWithCountProduct1 = new CategoryWithCountProduct(1L, "Categoria 3", CategoryStatus.ATIVO, 5L);
        categoryWithCountProduct2 = new CategoryWithCountProduct(2L, "Categoria 3", CategoryStatus.ATIVO, 10L);

        categories = new ArrayList<>(Arrays.asList(categoryDetailDTO, categoryDetailDTO2));

        categoriesActives = new ArrayList<>(Arrays.asList(categoryWithCountProduct1, categoryWithCountProduct2));
    }

    @AfterEach
    void tearDown() {
        reset(service);
    }

    @Test
    @DisplayName("Create Success")
    void givenCategory_whenCreate_thenReturnDetailCategory() throws Exception {
        //Given
        var url = "/categories";
        given(service.create(any(CategoryCreateUpdateDTO.class))).willReturn(categoryDetailDTO);

        // When
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjMapper.writeValueAsString(categoryCreateDTO)));

        //Then
        response
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(categoryDetailDTO.name())))
            .andExpect(jsonPath("$.status", is(categoryDetailDTO.status().name())));
    }

    @Test
    @DisplayName("ListAll Success")
    void givenCategories_whenListAll_thenReturnCategorysWithCountProductList() throws Exception {
        //Given
        var url = "/categories/products";
        given(service.listAllWithProductCount()).willReturn(categoriesActives);

        // When
        var response = mockMvc.perform(get(url));

        //Then
        response
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$[0].categoryId", is(categoriesActives.getFirst().categoryId().intValue())))
            .andExpect(jsonPath("$[0].name", is(categoriesActives.getFirst().name())))
            .andExpect(jsonPath("$[0].productCount", is(categoriesActives.getFirst().productCount().intValue())))
            .andExpect(jsonPath("$[0].status", is(categoriesActives.getFirst().status().toString())))
            .andExpect(jsonPath("$.size()", is(categoriesActives.size())));
    }

    @Test
    @DisplayName("ListAll Success")
    void givenCategories_whenListAll_thenReturnDetailCategoryList() throws Exception {
        //Given
        var url = "/categories";
        given(service.listAll()).willReturn(categories);

        // When
        var response = mockMvc.perform(get(url));


        //Then
        response
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$[0].categoryId", is(categories.getFirst().categoryId().intValue())))
            .andExpect(jsonPath("$[0].name", is(categories.getFirst().name())))
            .andExpect(jsonPath("$[0].status", is(categories.getFirst().status().toString())))
            .andExpect(jsonPath("$.size()", is(categories.size())));
    }

    @Test
    @DisplayName("FindById Success")
    void givenCategoryId_whenListById_thenReturnDetailCategory() throws Exception {
        //Given
        var id = 1L;
        var url = "/categories/{id}";
        given(service.listById(id)).willReturn(categoryDetailDTO);

        // When
        var response = mockMvc.perform(get(url, id));

        //Then
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(categoryDetailDTO.name())))
            .andExpect(jsonPath("$.status", is(categoryDetailDTO.status().name())));

    }

    @Test
    @DisplayName("Update Success")
    void givenCategoryAndCategoryId_whenUpdate_thenReturnDetailCategoryUpdated() throws Exception {
        //Given
        var id = 1L;
        var url = "/categories/{id}";
        categoryEntity2.setCategoryId(id);
        categoryDetailDTO2 = mapper.toDTO(categoryEntity2);
        given(service.update(categoryCreateDTO, id)).willReturn(categoryDetailDTO2);

        // When
        var response = mockMvc.perform(put(url, id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjMapper.writeValueAsString(categoryCreateDTO)));

        //Then
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryId", is((int) id)))
            .andExpect(jsonPath("$.name", is(categoryDetailDTO2.name())))
            .andExpect(jsonPath("$.status", is(categoryDetailDTO2.status().name())));
    }


    @Test
    @DisplayName("Disable Success")
    void givenCategoryId_whenDisable_thenReturnDetailCategoryDisabled() throws Exception {
        //Given
        var id = 1L;
        var url = "/categories/{id}/disable";
        given(service.disable(id)).willReturn(categoryDetailDTO2);

        // When
        var response = mockMvc.perform(put(url, id));

        // Then
        response
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryId", is((int) id)))
            .andExpect(jsonPath("$.status", is(CategoryStatus.INATIVO.name())));
    }


    @Test
    @DisplayName("Delete Success")
    void givenCategory_whenDelete_thenCategoryIsDeleted() throws Exception {
        //Given
        var id = 1L;
        var url = "/categories/{id}";
        willDoNothing().given(service).delete(id);

        // When
        var response = mockMvc.perform(delete(url, id));

        // Then
        response.andDo(print()).andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Missing Required Fields Return ErrorMessage")
    void givenMissingRequiredFields_whenCreateCategory_thenReturnBadRequestWithErrorMessage() throws Exception {
        //Given
        var url = "/categories";
        var json = """
                {
                    "name": "",
                    "status": ""
                }""";

        // When
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        System.out.println(json);

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Valores inválidos")))
            .andExpect(jsonPath("$.fields.name").exists())
            .andExpect(jsonPath("$.fields.status").exists())
            .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid input sizes return ErrorMessage")
    void givenInvalidInputSizes_whenCreateCategory_thenReturnBadRequestWithErrorMessage() throws Exception {
        //Given -> Name, CategoryStatus, WhatsApp
        var url = "/categories";
        var json = """
                {
                    "name": "1",
                    "status": ""
                }""";

        // When
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Valores inválidos")))
            .andExpect(jsonPath("$.fields.name").exists())
            .andExpect(jsonPath("$.fields.status").exists())
            .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid Enum value return ErrorMessage")
    void givenInvalidEnumValue_whenCreateCategory_thenReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        var url = "/categories";
        var json = """
                {
                    "name": "Categoria ABC",
                    "status": "ADAD"
                }""";
        // When
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Valores inválidos")))
            .andExpect(jsonPath("$.fields.status").exists())
            .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid JSON return ErrorMessage")
    void givenInvalidJson_whenCreateCategory_thenReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        var url = "/categories";
        var json = """
                {
                    "name": ""
                    "status": "",
                }""";
        // When
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is(400)))
            .andExpect(jsonPath("$.message", containsString("JSON")));
    }

    @Test
    @DisplayName("Non existence CategoryId return ErrorMessage")
    void givenNonExistenceCategoryId_whenListById_thenReturnNotFoundWithErrorMessage() throws Exception {
        //Given
        var invalidId = 20L;
        var url = "/categories/{id}";
        given(service.listById(invalidId)).willThrow(new EntityNotFoundException());

        // When
        var response = mockMvc.perform(get(url, invalidId));

        //Then
        response
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("Entidade não foi encontrada")))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status", is(404)));

    }


    @Test
    @DisplayName("Invalid resource return ErrorMessage ")
    void givenInvalidResource_whenRequest_thenReturnNotFoundWithErrorMessage() throws Exception {
        //Given
        var url = "/invalid_resource";

        // When
        var response = mockMvc.perform(get(url));

        //Then
        response
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("Recurso não encontrado")))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status", is(404)));

    }

    @Test
    @DisplayName("Invalid HTTP Method")
    void givenUnsupportedHttpMethod_whenRequest_thenReturnsMethodNotAllowed() throws Exception {
        // Given
        var url = "/categories";

        // When
        var response = mockMvc.perform(put(url));

        // Then
        response
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            .andExpect(jsonPath("$.status", is(405)))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.timestamp").exists());
    }
}

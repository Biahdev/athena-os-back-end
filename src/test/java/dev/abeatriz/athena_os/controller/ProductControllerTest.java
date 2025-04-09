package dev.abeatriz.athena_os.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abeatriz.athena_os.dto.product.OptionDTO;
import dev.abeatriz.athena_os.dto.product.OptionValueDTO;
import dev.abeatriz.athena_os.dto.product.ProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.product.ProductDetailDTO;
import dev.abeatriz.athena_os.entity.Product;
import dev.abeatriz.athena_os.entity.enums.OptionType;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import dev.abeatriz.athena_os.mapper.ProductMapper;
import dev.abeatriz.athena_os.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockitoBean
    private ProductService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductMapper mapper = ProductMapper.INSTANCE;

    private OptionValueDTO optionValue;
    private OptionDTO options;
    private ProductCreateUpdateDTO productCreatUpdateDTO;
    private ProductDetailDTO productDetail;
    private Product productEntity;

    private OptionValueDTO optionValue2;
    private OptionDTO options2;
    private ProductCreateUpdateDTO productCreatUpdateDTO2;
    private ProductDetailDTO productDetail2;
    private Product productEntity2;

    @BeforeEach
    void setup() {
        optionValue = new OptionValueDTO("Azul", new BigDecimal("5.0"));
        options = new OptionDTO("Cor", OptionType.MULTI_SELECT, List.of(optionValue));
        productCreatUpdateDTO = new ProductCreateUpdateDTO(1L, "Camisa", "delicata consetetur dolorem conubia signiferumque", mapper.toString(ProductStatus.REGULAR), new BigDecimal("20.0"), new BigDecimal("30.0"), List.of(options));
        productEntity = mapper.toEntity(productCreatUpdateDTO);
        productDetail = mapper.toDTO(productEntity);

        optionValue2 = new OptionValueDTO("Branco", new BigDecimal("1.0"));
        options2 = new OptionDTO("Cor", OptionType.MULTI_SELECT, List.of(optionValue2));
        productCreatUpdateDTO2 = new ProductCreateUpdateDTO(1L, "Calça", "conubia signiferumque", mapper.toString(ProductStatus.PROMOCAO), new BigDecimal("10.0"), new BigDecimal("20.0"), List.of(options2));
        productEntity2 = mapper.toEntity(productCreatUpdateDTO2);
        productDetail2 = mapper.toDTO(productEntity2);
    }

    @Test
    @DisplayName("Create Success")
    void givenProduct_whenCreate_thenReturnDetailProduct() throws Exception {
        //Given
        var url = "/products";
        given(service.create(any(ProductCreateUpdateDTO.class))).willReturn(productDetail);

        // When
        var response = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCreatUpdateDTO)));

        //Then
        response
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.categoryName", is(productDetail.categoryName())),
                        jsonPath("$.name", is(productDetail.name())),
                        jsonPath("$.description", is(productDetail.description())),
                        jsonPath("$.status", is(productDetail.status().name())),
                        jsonPath("$.costValue", is(productDetail.costValue().doubleValue())),
                        jsonPath("$.salesValue", is(productDetail.salesValue().doubleValue())),
                        jsonPath("$.options[0].title", is(options.title())),
                        jsonPath("$.options[0].type", is(options.type().toString())),
                        jsonPath("$.options[0].values[0].name", is(optionValue.name())),
                        jsonPath("$.options[0].values[0].price", is(optionValue.price().doubleValue()))
                );
    }

    @Test
    @DisplayName("List Sucess")
    void givenGetRequest_whenListAll_thenReturnDetailProduct() throws Exception {
        // Given
        var url = "/products";
        given(service.listAll()).willReturn(List.of(productDetail));

        // When
        var response = mockMvc.perform(get(url));

        // Then
        response
                .andDo(print())
                .andExpectAll(status().isOk(),
                        jsonPath("$[0].categoryName", is(productDetail.categoryName())),
                        jsonPath("$[0].name", is(productDetail.name())),
                        jsonPath("$[0].description", is(productDetail.description())),
                        jsonPath("$[0].status", is(productDetail.status().name())),
                        jsonPath("$[0].costValue", is(productDetail.costValue().doubleValue())),
                        jsonPath("$[0].salesValue", is(productDetail.salesValue().doubleValue())),
                        jsonPath("$[0].options[0].title", is(options.title())),
                        jsonPath("$[0].options[0].type", is(options.type().toString())),
                        jsonPath("$[0].options[0].values[0].name", is(optionValue.name())),
                        jsonPath("$[0].options[0].values[0].price", is(optionValue.price().doubleValue()))
                );
    }

    @Test
    @DisplayName("Get By Id Sucess")
    void givenProductId_whenGetById_thenReturnDetailProduct() throws Exception {
        // Given
        var id = 1L;
        var url = "/products/{id}";
        given(service.listById(id)).willReturn(productDetail);

        // When
        var response = mockMvc.perform(get(url, id));

        // Then
        response
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.categoryName", is(productDetail.categoryName())),
                        jsonPath("$.name", is(productDetail.name())),
                        jsonPath("$.description", is(productDetail.description())),
                        jsonPath("$.status", is(productDetail.status().name())),
                        jsonPath("$.costValue", is(productDetail.costValue().doubleValue())),
                        jsonPath("$.salesValue", is(productDetail.salesValue().doubleValue())),
                        jsonPath("$.options[0].title", is(options.title())),
                        jsonPath("$.options[0].type", is(options.type().toString())),
                        jsonPath("$.options[0].values[0].name", is(optionValue.name())),
                        jsonPath("$.options[0].values[0].price", is(optionValue.price().doubleValue()))
                );

    }

    @Test
    @DisplayName("Update Sucess")
    void givenProductCreateUpdateDTOAndProductId_whenUpdate_thenReturnDetailProductUpdated() throws Exception {
        // Given
        var id = 1L;
        var url = "/products/{id}";
        given(service.update(productCreatUpdateDTO, id)).willReturn(productDetail2);

        // When
        var response = mockMvc.perform(put(url, id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCreatUpdateDTO)));

        // Then
        response
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.categoryName", is(productDetail2.categoryName())),
                        jsonPath("$.name", is(productDetail2.name())),
                        jsonPath("$.description", is(productDetail2.description())),
                        jsonPath("$.status", is(productDetail2.status().name())),
                        jsonPath("$.costValue", is(productDetail2.costValue().doubleValue())),
                        jsonPath("$.salesValue", is(productDetail2.salesValue().doubleValue())),
                        jsonPath("$.options[0].title", is(options2.title())),
                        jsonPath("$.options[0].type", is(options2.type().toString())),
                        jsonPath("$.options[0].values[0].name", is(optionValue2.name())),
                        jsonPath("$.options[0].values[0].price", is(optionValue2.price().doubleValue()))
                );

    }


    @Test
    @DisplayName("Delete Success")
    void givenProductId_whenDelete_thenProductIsDeleted() throws Exception {
        //Given
        var id = 1L;
        var url = "/products/{id}";
        willDoNothing().given(service).delete(id);

        // When
        var response = mockMvc.perform(delete(url, id));

        // Then
        response.andDo(print()).andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Missing Required Fields Return ErrorMessage")
    void givenMissingRequiredFields_whenCreateProduct_thenReturnBadRequestWithErrorMessage() throws Exception {
        var url = "/products";
        var json = """
                {
                   "categoryId": "",
                   "name": "",
                   "description": "Propriae eum graece accusata massa elit dicit appetere saepe aliquip ",
                   "status": "INVALIDO",
                   "costValue": 10.5,
                   "salesValue": 0,
                   "options": [
                     {
                       "title": "Cor",
                       "type": "SINGLE_SELECT",
                       "values": [
                         {
                           "name": "Azul",
                           "price": 5
                         }
                       ]
                     }
                   ]
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
                .andExpect(jsonPath("$.fields.categoryId").exists())
                .andExpect(jsonPath("$.fields.name").exists())
                .andExpect(jsonPath("$.fields.salesValue").exists())
                .andExpect(jsonPath("$.fields.status").exists())
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid input sizes return ErrorMessage")
    void givenInvalidInputSizes_whenCreateProduct_thenReturnBadRequestWithErrorMessage() throws Exception {
        //Given -> Name, ProductStatus, WhatsApp
        var url = "/products";
        var json = """
                {
                   "categoryId": "0",
                   "name": "A",
                   "description": "A",
                   "status": "REGULAR",
                   "costValue": 10.5,
                   "salesValue": 0,
                   "options": [
                     {
                       "title": "Cor",
                       "type": "SINGLE_SELECT",
                       "values": [
                         {
                           "name": "Azul",
                           "price": 5
                         }
                       ]
                     }
                   ]
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
                .andExpect(jsonPath("$.fields.categoryId").exists())
                .andExpect(jsonPath("$.fields.name").exists())
                .andExpect(jsonPath("$.fields.salesValue").exists())
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid Enum value return ErrorMessage")
    void givenInvalidEnumValue_whenCreateProduct_thenReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        var url = "/products";
        var json = """
                {
                   "categoryId": "1",
                   "name": "Aasd",
                   "description": "asdasaA",
                   "status": "INVALIDO",
                   "costValue": 10.5,
                   "salesValue": 10.1,
                   "options": [
                     {
                       "title": "Cor",
                       "type": "SINGLE_SELECT",
                       "values": [
                         {
                           "name": "Azul",
                           "price": 5
                         }
                       ]
                     }
                   ]
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
    void givenInvalidJson_whenCreateProduct_thenReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        var url = "/products";
        var json = """
                {
                   "categoryId": "1"
                   "name": "Aasd"
                   "description": "asdasaA",
                   "status": "INVALIDO",
                   "costValue": 10.5,
                   "salesValue": 10.1,
                   "options": [
                     {
                       "title": "Cor",
                       "type": "SINGLE_SELECT",
                       "values": [
                         {
                           "name": "Azul",
                           "price": 5
                         }
                       ]
                     }
                   ]
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
                .andExpect(jsonPath("$.message", containsString("JSON com o formato inválido")));
    }

    // TODO: Passar testes de entidade não encontrada para a service
    @Test
    @DisplayName("Non existence ProductId return ErrorMessage")
    void givenNonExistenceProductId_whenListById_thenReturnNotFoundWithErrorMessage() throws Exception {
        //Given
        var invalidId = 200L;
        var url = "/products/{id}";
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
        //TODO: Buscar uma forma de melhorar isso, não faz sentido ser testada em todos os endpoints/entidades
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
        var url = "/products";

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

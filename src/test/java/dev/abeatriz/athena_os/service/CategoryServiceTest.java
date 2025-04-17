package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.category.CategoryCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.category.CategoryDetailDTO;
import dev.abeatriz.athena_os.dto.category.CategoryWithCountProduct;
import dev.abeatriz.athena_os.entity.Category;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import dev.abeatriz.athena_os.mapper.CategoryMapper;
import dev.abeatriz.athena_os.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapperMock;
    private CategoryMapper mapper = CategoryMapper.INSTANCE;

    @InjectMocks
    private CategoryService service;

    private Category categoryCategory;
    private CategoryCreateUpdateDTO categoryCreateDTO;
    private CategoryDetailDTO categoryDetailDTO;

    private Category categoryCategory2;
    private CategoryCreateUpdateDTO categoryCreateDTO2;
    private CategoryDetailDTO categoryDetailDTO2;

    @BeforeEach
    void setUp() {
        categoryCreateDTO = new CategoryCreateUpdateDTO("Categoria 1", mapper.toString(CategoryStatus.ATIVO));
        categoryCategory = mapper.toEntity(categoryCreateDTO);
        categoryCategory.setCategoryId(1L);
        categoryDetailDTO = mapper.toDTO(categoryCategory);

        categoryCreateDTO2 = new CategoryCreateUpdateDTO("Categoria 2", mapper.toString(CategoryStatus.INATIVO));
        categoryCategory2 = mapper.toEntity(categoryCreateDTO2);
        categoryCategory2.setCategoryId(1L);
        categoryDetailDTO2 = mapper.toDTO(categoryCategory2);
    }

    @AfterEach
    void tearDown() {
        reset(repository, mapperMock);
    }

    @Test
    @DisplayName("Create Success")
    void givenCategory_whenCreate_thenReturnDetailCategory() {
        // Given
        given(mapperMock.toEntity(any(CategoryCreateUpdateDTO.class))).willReturn(categoryCategory);
        given(repository.save(any(Category.class))).willReturn(categoryCategory);
        given(mapperMock.toDTO(any(Category.class))).willReturn(categoryDetailDTO);

        // When
        var newCategory = service.create(categoryCreateDTO);

        // Then
        assertNotNull(newCategory);
        assertEquals(newCategory, categoryDetailDTO);
        assertEquals(1L, 3L);
        verify(mapperMock, times(1)).toEntity(any(CategoryCreateUpdateDTO.class));
        verify(mapperMock, times(1)).toDTO(any(Category.class));
        verify(repository, times(1)).save(categoryCategory);

    }

    @Test
    @DisplayName("ListAll Success")
    void givenCategorys_whenListAll_thenReturnDetailCategoryList() {
        // Given
        var categoryCategoryList = List.of(categoryCategory, categoryCategory2);
        var categoryDetailDTOList = List.of(categoryDetailDTO, categoryDetailDTO2);

        given(repository.findAll()).willReturn(categoryCategoryList);
        given(mapperMock.toDTO(categoryCategoryList)).willReturn(categoryDetailDTOList);

        // When
        var newCategoryList = service.listAll();

        // Then
        assertNotNull(newCategoryList);
        assertEquals(newCategoryList.size(), categoryDetailDTOList.size());
        assertEquals(newCategoryList, categoryDetailDTOList);
        assertAll("categoryDetailDTOList",
                () -> newCategoryList.forEach(category -> assertInstanceOf(CategoryDetailDTO.class, category))
        );
        verify(repository, times(1)).findAll();
        verify(mapperMock, times(1)).toDTO(anyList());
    }

    @Test
    @DisplayName("WithCountProductList Success")
    void givenCategorys_whenListAll_thenReturnCategorysWithCountProductList() {
        // Given
        var category1 = new CategoryWithCountProduct(1L, "Festa", CategoryStatus.ATIVO, 10L);
        var category2 = new CategoryWithCountProduct(2L, "Batizado", CategoryStatus.ATIVO, 3L);
        var categoryWithProductList = List.of(category1, category2);

        given(repository.findAllWithProductCount()).willReturn(categoryWithProductList);

        // When
        var newCategoryList = service.listAllWithProductCount();

        // Then
        assertNotNull(newCategoryList);
        assertEquals(newCategoryList.size(), categoryWithProductList.size());
        assertEquals(newCategoryList, categoryWithProductList);
        assertAll("CategoryWithCountProduct",
                () -> newCategoryList.forEach(category -> assertInstanceOf(CategoryWithCountProduct.class, category))
        );
        verify(repository, times(1)).findAllWithProductCount();
    }

    @Test
    @DisplayName("ListById Success")
    void givenCategoryId_whenListById_thenReturnDetailCategory() {
        // Given
        given(repository.findById(1L)).willReturn(Optional.ofNullable(categoryCategory));
        given(mapperMock.toDTO(categoryCategory)).willReturn(categoryDetailDTO);

        // When
        var categoryById = service.listById(1L);

        // Then
        assertNotNull(categoryById);
        assertEquals(categoryDetailDTO, categoryById);
        assertEquals(1, categoryById.categoryId());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(categoryCategory);
    }

    @Test
    @DisplayName("Update Success")
    void givenCategoryId_whenUpdate_thenReturnDetailCategoryUpdated() {
        // Given
        given(repository.findById(1L)).willReturn(Optional.ofNullable(categoryCategory));
        given(mapperMock.toDTO(categoryCategory)).willReturn(categoryDetailDTO);

        // When
        var categoryUpdate = service.update(categoryCreateDTO, 1L);

        // Then
        assertNotNull(categoryUpdate);
        assertEquals(categoryUpdate.name(), categoryDetailDTO.name());
        assertEquals(categoryUpdate.status(), categoryDetailDTO.status());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(categoryCategory);
    }

    @Test
    @DisplayName("Disable Success")
    void givenCategoryId_whenDisable_thenReturnDetailCategoryDisabled() {
        // Given
        categoryCategory.setStatus(CategoryStatus.INATIVO);
        var categoryDetailDisable = mapper.toDTO(categoryCategory);

        given(repository.findById(1L)).willReturn(Optional.ofNullable(categoryCategory));
        given(mapperMock.toDTO(categoryCategory)).willReturn(categoryDetailDisable);

        // When
        var newCategoryDisable = service.disable(1L);

        // Then
        assertNotNull(newCategoryDisable);
        assertEquals(newCategoryDisable, categoryDetailDisable);
        assertEquals(CategoryStatus.INATIVO, newCategoryDisable.status());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(categoryCategory);
    }


    @Test
    @DisplayName("Delete Success")
    void givenCategoryId_whenDelete_thenReturnDetailCategoryDeleted() {
        // Given
        given(repository.findById(1L)).willReturn(Optional.ofNullable(categoryCategory));
        willDoNothing().given(repository).delete(categoryCategory);

        // When
        service.delete(1L);

        // Then
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(categoryCategory);
    }


}

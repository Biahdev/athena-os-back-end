package dev.abeatriz.athena_os.service;


import dev.abeatriz.athena_os.dto.product.OptionDTO;
import dev.abeatriz.athena_os.dto.product.OptionValueDTO;
import dev.abeatriz.athena_os.dto.product.ProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.product.ProductDetailDTO;
import dev.abeatriz.athena_os.entity.Category;
import dev.abeatriz.athena_os.entity.Product;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import dev.abeatriz.athena_os.entity.enums.OptionType;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import dev.abeatriz.athena_os.mapper.ProductMapper;
import dev.abeatriz.athena_os.repository.CategoryRepository;
import dev.abeatriz.athena_os.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    private ProductMapper mapperMock;
    private ProductMapper mapper = ProductMapper.INSTANCE;

    @InjectMocks
    private ProductService productService;

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

    private Category category;

    //TODO: Definir email como UNIQUE no banco


    @BeforeEach
    void setup() {
        category = new Category("Feminino", CategoryStatus.ATIVO);
        category.setCategoryId(1L);

        optionValue = new OptionValueDTO("Azul", new BigDecimal("5.0"));
        options = new OptionDTO("Cor", OptionType.MULTI_SELECT, List.of(optionValue));
        productCreatUpdateDTO = new ProductCreateUpdateDTO(1L, "Camisa", "delicata consetetur dolorem conubia signiferumque", mapper.toString(ProductStatus.REGULAR), new BigDecimal("20.0"), new BigDecimal("30.0"), List.of(options));
        productEntity = mapper.toEntity(productCreatUpdateDTO);
        productEntity.setCategory(category);
        productEntity.setProductId(1L);
        productDetail = mapper.toDTO(productEntity);

        optionValue2 = new OptionValueDTO("Branco", new BigDecimal("1.0"));
        options2 = new OptionDTO("Cor", OptionType.MULTI_SELECT, List.of(optionValue2));
        productCreatUpdateDTO2 = new ProductCreateUpdateDTO(1L, "Calça", "conubia signiferumque", mapper.toString(ProductStatus.PROMOCAO), new BigDecimal("10.0"), new BigDecimal("20.0"), List.of(options2));
        productEntity2 = mapper.toEntity(productCreatUpdateDTO2);
        productEntity2.setCategory(category);
        productEntity2.setProductId(2L);
        productDetail2 = mapper.toDTO(productEntity2);
    }

    @Test
    @DisplayName("Create Success")
    void givenProduct_whenCreate_thenReturnProductDetail() {
        // Given
        given(mapperMock.toEntity(any(ProductCreateUpdateDTO.class))).willReturn(productEntity);
        given(productRepository.save(any(Product.class))).willReturn(productEntity);
        given(categoryRepository.findByCategoryIdAndStatus(1L, CategoryStatus.ATIVO)).willReturn(Optional.ofNullable(category));
        given(mapperMock.toDTO(any(Product.class))).willReturn(productDetail);


        // When
        var newProduct = productService.create(productCreatUpdateDTO);

        // Then
        assertNotNull(newProduct);
        assertEquals(newProduct, productDetail);
        assertEquals(1, newProduct.productId());
        assertEquals("Camisa", newProduct.name());
        assertEquals(ProductStatus.REGULAR, newProduct.status());
        assertEquals(new BigDecimal("30.0"), newProduct.salesValue());
        assertEquals(new BigDecimal("20.0"), newProduct.costValue());
        assertEquals("delicata consetetur dolorem conubia signiferumque", newProduct.description());
        assertEquals("Feminino", newProduct.categoryName());
        verify(mapperMock, times(1)).toEntity(any(ProductCreateUpdateDTO.class));
        verify(productRepository, times(1)).save(productEntity);
        verify(categoryRepository, times(1)).findByCategoryIdAndStatus(1L, CategoryStatus.ATIVO);
        verify(mapperMock, times(1)).toDTO(productEntity);
    }

    @Test
    @DisplayName("ListById Success")
    void givenProductId_whenListById_thenReturnDetailProduct() {
        // Given
        given(productRepository.findById(1L)).willReturn(Optional.ofNullable(productEntity));
        given(mapperMock.toDTO(productEntity)).willReturn(productDetail);

        // When
        var productById = productService.listById(1L);

        // Then
        assertNotNull(productById);
        assertEquals(productDetail, productById);
        assertEquals(productById.productId(), 1);
        verify(productRepository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(productEntity);
    }


    @Test
    @DisplayName("Update Success")
    void givenProductId_whenUpdate_thenReturnDetailProductUpdated() {
        // Given
        given(mapperMock.toEntity(any(ProductCreateUpdateDTO.class))).willReturn(productEntity2);
        given(productRepository.save(any(Product.class))).willReturn(productEntity2);
        given(categoryRepository.findByCategoryIdAndStatus(1L, CategoryStatus.ATIVO)).willReturn(Optional.ofNullable(category));
        given(productRepository.findById(1L)).willReturn(Optional.ofNullable(productEntity2));
        given(mapperMock.toDTO(any(Product.class))).willReturn(productDetail2);


        // When
        var productUpdate = productService.update(productCreatUpdateDTO, 1L);

        // Then
        assertNotNull(productUpdate);
        assertEquals(productUpdate.productId(), productDetail2.productId());
        assertEquals(productUpdate.name(), productDetail2.name());
        assertEquals(productUpdate.status(), productDetail2.status());
        assertEquals(productUpdate.salesValue(), productDetail2.salesValue());
        assertEquals(productUpdate.costValue(), productDetail2.costValue());
        assertEquals(productUpdate.description(), productDetail2.description());
        assertEquals(productUpdate.categoryName(), productDetail2.categoryName());
        verify(mapperMock, times(1)).toEntity(any(ProductCreateUpdateDTO.class));
        verify(productRepository, times(1)).save(productEntity2);
        verify(productRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findByCategoryIdAndStatus(1L, CategoryStatus.ATIVO);
        verify(mapperMock, times(1)).toDTO(productEntity2);
    }


    @Test
    @DisplayName("ListAll Success")
    void givenProducts_whenListAll_thenReturnDetailProductList() {
        // Given
        var productEntityList = List.of(productEntity, productEntity2);
        var productDetailList = List.of(productDetail, productDetail2);

        given(productRepository.findAll()).willReturn(productEntityList);
        given(mapperMock.toDTO(productEntityList)).willReturn(productDetailList);

        // When
        var newProductList = productService.listAll();

        // Then
        assertNotNull(newProductList);
        assertEquals(newProductList.size(), productDetailList.size());
        assertEquals(newProductList, productDetailList);
        assertAll("productDetailList",
            () -> newProductList.forEach(employee -> assertInstanceOf(ProductDetailDTO.class, employee))
        );
        verify(productRepository, times(1)).findAll();
        verify(mapperMock, times(1)).toDTO(anyList());
    }


    @Test
    @DisplayName("Disable Success")
    void givenProductId_whenDisable_thenReturnDetailProductDisabled() {
        // Given
        productEntity.setStatus(ProductStatus.INATIVO);
        var productDetailDisable = mapper.toDTO(productEntity);

        given(productRepository.findById(1L)).willReturn(Optional.ofNullable(productEntity));
        given(mapperMock.toDTO(productEntity)).willReturn(productDetailDisable);

        // When
        var newProductDisable = productService.disable(1L);

        // Then
        assertNotNull(newProductDisable);
        assertEquals(newProductDisable, productDetailDisable);
        assertEquals(newProductDisable.status(), ProductStatus.INATIVO);
        verify(productRepository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(productEntity);
    }


    @Test
    @DisplayName("Delete Success")
    void givenProductId_whenDelete_thenReturnDetailProductDeleted() {
        // Given
        given(productRepository.findById(1L)).willReturn(Optional.ofNullable(productEntity));
        willDoNothing().given(productRepository).delete(productEntity);

        // When
        productService.delete(1L);

        // Then
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(productEntity);
    }


}

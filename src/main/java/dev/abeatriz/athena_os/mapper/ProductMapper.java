package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.product.ProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.product.ProductDetailDTO;
import dev.abeatriz.athena_os.entity.Product;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "category.categoryId", source = "categoryId")
    Product toEntity(ProductCreateUpdateDTO product);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "category.categoryId", target = "categoryId")
    ProductDetailDTO toDTO(Product productDetailDTO);

    List<ProductDetailDTO> toDTO(List<Product> clientEntity);

    String toString(ProductStatus status);

    ProductStatus toEnumStatus(String status);
}

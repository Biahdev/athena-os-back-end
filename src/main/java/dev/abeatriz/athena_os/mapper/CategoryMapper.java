package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.category.CategoryCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.category.CategoryDetailDTO;
import dev.abeatriz.athena_os.entity.Category;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "categoryId", ignore = true)
    Category toEntity(CategoryCreateUpdateDTO category);

    CategoryDetailDTO toDTO(Category clientEntity);

    List<CategoryDetailDTO> toDTO(List<Category> clientEntity);

    String toString(CategoryStatus status);

    CategoryStatus toEnumStatus(String status);

}

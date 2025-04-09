package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.category.CategoryCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.category.CategoryDetailDTO;
import dev.abeatriz.athena_os.entity.Category;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-30T18:34:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryCreateUpdateDTO category) {
        if ( category == null ) {
            return null;
        }

        Category category1 = new Category();

        category1.setName( category.name() );
        category1.setStatus( toEnumStatus( category.status() ) );

        return category1;
    }

    @Override
    public CategoryDetailDTO toDTO(Category clientEntity) {
        if ( clientEntity == null ) {
            return null;
        }

        Long categoryId = null;
        String name = null;
        CategoryStatus status = null;

        categoryId = clientEntity.getCategoryId();
        name = clientEntity.getName();
        status = clientEntity.getStatus();

        CategoryDetailDTO categoryDetailDTO = new CategoryDetailDTO( categoryId, name, status );

        return categoryDetailDTO;
    }

    @Override
    public List<CategoryDetailDTO> toDTO(List<Category> clientEntity) {
        if ( clientEntity == null ) {
            return null;
        }

        List<CategoryDetailDTO> list = new ArrayList<CategoryDetailDTO>( clientEntity.size() );
        for ( Category category : clientEntity ) {
            list.add( toDTO( category ) );
        }

        return list;
    }

    @Override
    public String toString(CategoryStatus status) {
        if ( status == null ) {
            return null;
        }

        String string;

        switch ( status ) {
            case ATIVO: string = "ATIVO";
            break;
            case INATIVO: string = "INATIVO";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return string;
    }

    @Override
    public CategoryStatus toEnumStatus(String status) {
        if ( status == null ) {
            return null;
        }

        CategoryStatus categoryStatus;

        switch ( status ) {
            case "ATIVO": categoryStatus = CategoryStatus.ATIVO;
            break;
            case "INATIVO": categoryStatus = CategoryStatus.INATIVO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return categoryStatus;
    }
}

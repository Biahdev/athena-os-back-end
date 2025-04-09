package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.product.OptionDTO;
import dev.abeatriz.athena_os.dto.product.OptionValueDTO;
import dev.abeatriz.athena_os.dto.product.ProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.product.ProductDetailDTO;
import dev.abeatriz.athena_os.entity.Category;
import dev.abeatriz.athena_os.entity.Option;
import dev.abeatriz.athena_os.entity.OptionValue;
import dev.abeatriz.athena_os.entity.Product;
import dev.abeatriz.athena_os.entity.enums.OptionType;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import java.math.BigDecimal;
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
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductCreateUpdateDTO product) {
        if ( product == null ) {
            return null;
        }

        Product product1 = new Product();

        product1.setCategory( productCreateUpdateDTOToCategory( product ) );
        product1.setStatus( toEnumStatus( product.status() ) );
        product1.setName( product.name() );
        product1.setDescription( product.description() );
        product1.setCostValue( product.costValue() );
        product1.setSalesValue( product.salesValue() );
        product1.setOptions( optionDTOListToOptionList( product.options() ) );

        return product1;
    }

    @Override
    public ProductDetailDTO toDTO(Product productDetailDTO) {
        if ( productDetailDTO == null ) {
            return null;
        }

        String categoryName = null;
        String categoryId = null;
        Long productId = null;
        String name = null;
        String description = null;
        ProductStatus status = null;
        BigDecimal costValue = null;
        BigDecimal salesValue = null;
        List<OptionDTO> options = null;

        categoryName = productDetailDTOCategoryName( productDetailDTO );
        Long categoryId1 = productDetailDTOCategoryCategoryId( productDetailDTO );
        if ( categoryId1 != null ) {
            categoryId = String.valueOf( categoryId1 );
        }
        productId = productDetailDTO.getProductId();
        name = productDetailDTO.getName();
        description = productDetailDTO.getDescription();
        status = productDetailDTO.getStatus();
        costValue = productDetailDTO.getCostValue();
        salesValue = productDetailDTO.getSalesValue();
        options = optionListToOptionDTOList( productDetailDTO.getOptions() );

        ProductDetailDTO productDetailDTO1 = new ProductDetailDTO( productId, categoryName, categoryId, name, description, status, costValue, salesValue, options );

        return productDetailDTO1;
    }

    @Override
    public List<ProductDetailDTO> toDTO(List<Product> clientEntity) {
        if ( clientEntity == null ) {
            return null;
        }

        List<ProductDetailDTO> list = new ArrayList<ProductDetailDTO>( clientEntity.size() );
        for ( Product product : clientEntity ) {
            list.add( toDTO( product ) );
        }

        return list;
    }

    @Override
    public String toString(ProductStatus status) {
        if ( status == null ) {
            return null;
        }

        String string;

        switch ( status ) {
            case REGULAR: string = "REGULAR";
            break;
            case INATIVO: string = "INATIVO";
            break;
            case PROMOCAO: string = "PROMOCAO";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return string;
    }

    @Override
    public ProductStatus toEnumStatus(String status) {
        if ( status == null ) {
            return null;
        }

        ProductStatus productStatus;

        switch ( status ) {
            case "REGULAR": productStatus = ProductStatus.REGULAR;
            break;
            case "INATIVO": productStatus = ProductStatus.INATIVO;
            break;
            case "PROMOCAO": productStatus = ProductStatus.PROMOCAO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return productStatus;
    }

    protected Category productCreateUpdateDTOToCategory(ProductCreateUpdateDTO productCreateUpdateDTO) {
        if ( productCreateUpdateDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryId( productCreateUpdateDTO.categoryId() );

        return category;
    }

    protected OptionValue optionValueDTOToOptionValue(OptionValueDTO optionValueDTO) {
        if ( optionValueDTO == null ) {
            return null;
        }

        OptionValue optionValue = new OptionValue();

        optionValue.setName( optionValueDTO.name() );
        optionValue.setPrice( optionValueDTO.price() );

        return optionValue;
    }

    protected List<OptionValue> optionValueDTOListToOptionValueList(List<OptionValueDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OptionValue> list1 = new ArrayList<OptionValue>( list.size() );
        for ( OptionValueDTO optionValueDTO : list ) {
            list1.add( optionValueDTOToOptionValue( optionValueDTO ) );
        }

        return list1;
    }

    protected Option optionDTOToOption(OptionDTO optionDTO) {
        if ( optionDTO == null ) {
            return null;
        }

        Option option = new Option();

        option.setTitle( optionDTO.title() );
        option.setType( optionDTO.type() );
        option.setValues( optionValueDTOListToOptionValueList( optionDTO.values() ) );

        return option;
    }

    protected List<Option> optionDTOListToOptionList(List<OptionDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Option> list1 = new ArrayList<Option>( list.size() );
        for ( OptionDTO optionDTO : list ) {
            list1.add( optionDTOToOption( optionDTO ) );
        }

        return list1;
    }

    private String productDetailDTOCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long productDetailDTOCategoryCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Long categoryId = category.getCategoryId();
        if ( categoryId == null ) {
            return null;
        }
        return categoryId;
    }

    protected OptionValueDTO optionValueToOptionValueDTO(OptionValue optionValue) {
        if ( optionValue == null ) {
            return null;
        }

        String name = null;
        BigDecimal price = null;

        name = optionValue.getName();
        price = optionValue.getPrice();

        OptionValueDTO optionValueDTO = new OptionValueDTO( name, price );

        return optionValueDTO;
    }

    protected List<OptionValueDTO> optionValueListToOptionValueDTOList(List<OptionValue> list) {
        if ( list == null ) {
            return null;
        }

        List<OptionValueDTO> list1 = new ArrayList<OptionValueDTO>( list.size() );
        for ( OptionValue optionValue : list ) {
            list1.add( optionValueToOptionValueDTO( optionValue ) );
        }

        return list1;
    }

    protected OptionDTO optionToOptionDTO(Option option) {
        if ( option == null ) {
            return null;
        }

        String title = null;
        OptionType type = null;
        List<OptionValueDTO> values = null;

        title = option.getTitle();
        type = option.getType();
        values = optionValueListToOptionValueDTOList( option.getValues() );

        OptionDTO optionDTO = new OptionDTO( title, type, values );

        return optionDTO;
    }

    protected List<OptionDTO> optionListToOptionDTOList(List<Option> list) {
        if ( list == null ) {
            return null;
        }

        List<OptionDTO> list1 = new ArrayList<OptionDTO>( list.size() );
        for ( Option option : list ) {
            list1.add( optionToOptionDTO( option ) );
        }

        return list1;
    }
}

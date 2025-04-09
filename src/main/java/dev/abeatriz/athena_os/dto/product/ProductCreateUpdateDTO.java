package dev.abeatriz.athena_os.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import dev.abeatriz.athena_os.exception.validators.enums.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Schema(title = "Produto criação ou atualização")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductCreateUpdateDTO(

    @Min(1)
    @NotNull
    @Schema(title = "Category ID", example = "2", type = "long", minimum = "1")
    Long categoryId,

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(title = "Nome", example = "Camisa", type = "string", minLength = 3, maxLength = 50)
    String name,

    @Schema(title = "Descrição", example = "Propriae eum graece accusata massa elit dicit appetere saepe aliquip ", type = "string")
    String description,

    @NotNull
    @ValueOfEnum(enumClass = ProductStatus.class)
    @Schema(title = "Status", example = "REGULAR", type = "string", defaultValue = "REGULAR", allowableValues = {"REGULAR", "INATIVO", "PROMOCAO"})
    String status,

    @Schema(title = "Preço de custo", example = "10.50", type = "double")
    BigDecimal costValue,

    @Min(1)
    @NotNull
    @Schema(title = "Preço de venda", example = "15", type = "double",  minimum = "1")
    BigDecimal salesValue,

    @Schema(title = "Opções")
    List<OptionDTO> options
) {
}
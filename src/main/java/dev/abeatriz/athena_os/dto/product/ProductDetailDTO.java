package dev.abeatriz.athena_os.dto.product;

import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.List;

@Schema(title = "Produto detalhe")
public record ProductDetailDTO(

    @Min(1)
    @Schema(title = "Produto ID", example = "1", type = "long", minimum = "1")
    Long productId,

    @Schema(title = "Categoria", example = "Casamento", type = "string")
    String categoryName,

    @Schema(title = "CategoriaId", example = "1", type = "string")
    String categoryId,

    @Schema(title = "Nome", example = "Camisa", type = "string", minLength = 1)
    String name,

    @Schema(title = "Descrição", example = "Propriae eum graece accusata massa elit dicit appetere saepe aliquip ", type = "string")
    String description,

    @Schema(title = "Status", example = "REGULAR", type = "string", defaultValue = "REGULAR", allowableValues = {"REGULAR", "INATIVO", "PROMOCAO"})
    ProductStatus status,

    @Schema(title = "Preço de custo", example = "10.50", type = "double")
    BigDecimal costValue,

    @Min(1)
    @Schema(title = "Preço de venda", example = "15", type = "double")
    BigDecimal salesValue,

    @Schema(title = "Opções")
    List<OptionDTO> options
) {
}

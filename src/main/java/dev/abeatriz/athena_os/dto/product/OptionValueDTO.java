package dev.abeatriz.athena_os.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(title = "Valores das opções do Produto")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OptionValueDTO(

    @Schema(title = "Nome", example = "Azul", type = "string")
    String name,

    @Schema(title = "Preço", example = "5.0", type = "double")
    BigDecimal price
) {
}

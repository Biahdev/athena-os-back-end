package dev.abeatriz.athena_os.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abeatriz.athena_os.entity.enums.OptionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(title = "Opções do Produto")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OptionDTO(
    @Schema(title = "Titulo", example = "Cor", type = "string")
    String title,

    @Schema(title = "Tipo", example = "SINGLE_SELECT", type = "string", defaultValue = "SINGLE_SELECT", allowableValues = {"SINGLE_SELECT", "MULTI_SELECT"})
    OptionType type,

    @Schema(title = "Valores das opções")
    List<OptionValueDTO> values
) {

}

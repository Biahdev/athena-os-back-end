package dev.abeatriz.athena_os.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import dev.abeatriz.athena_os.exception.validators.enums.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(title = "Categoria criação ou atualização as informações")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryCreateUpdateDTO(

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(title = "Nome da categoria", example = "Esporte", type = "string", minLength = 3, maxLength = 50)
    String name,

    @NotBlank
    @ValueOfEnum(enumClass = CategoryStatus.class)
    @Schema(title = "Status da categoria", example = "INATIVO", type = "string", defaultValue = "REGULAR", allowableValues = {"REGULAR", "INATIVO"})
    String status
) {


}

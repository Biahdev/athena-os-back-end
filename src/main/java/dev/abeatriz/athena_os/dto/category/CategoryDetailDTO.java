package dev.abeatriz.athena_os.dto.category;

import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Categoria detalhe")
public record CategoryDetailDTO(
    @Schema(title = "ID da categoria", minimum = "1", example = "1")
    Long categoryId,

    @Schema(title = "Nome da categoria", example = "Casamento", type = "string", minLength = 3, maxLength = 50)
    String name,

    @Schema(title = "Status da categoria", example = "ATIVO", type = "string", defaultValue = "ATIVO", allowableValues = {"ATIVO", "INATIVO"})
    CategoryStatus status
) {
}

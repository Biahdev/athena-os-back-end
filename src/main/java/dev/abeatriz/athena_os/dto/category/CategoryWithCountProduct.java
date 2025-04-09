package dev.abeatriz.athena_os.dto.category;

import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Categoria com contagem de produtos")
public record CategoryWithCountProduct(

        @Schema(title = "ID da categoria", minimum = "1", example = "1")
        Long categoryId,

        @Schema(title = "Nome da categoria", example = "Esporte", type = "string", minLength = 3, maxLength = 50)
        String name,

        @Schema(title = "Status da categoria", example = "ATIVO", type = "string", defaultValue = "ATIVO", allowableValues = {"ATIVO", "INATIVO"})
        CategoryStatus status,

        @Schema(title = "Quantidade de produtos que relacionados a essa categoria", minimum = "0", example = "8")
        Long productCount
) {
}

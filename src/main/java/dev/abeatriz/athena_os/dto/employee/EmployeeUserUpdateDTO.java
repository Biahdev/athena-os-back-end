package dev.abeatriz.athena_os.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.exception.validators.enums.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(title = "Funcionário criação ou atualização")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeUserUpdateDTO(

        @NotBlank
        @Size(min = 3, max = 50)
        @Schema(title = "Nome", example = "André", type = "string", minLength = 3, maxLength = 50)
        String name,

        @NotBlank
        @Size(min = 3, max = 100)
        @Schema(title = "Email", example = "admin@admin.com", type = "string", minLength = 3, maxLength = 100)
        String email,

        @NotBlank
        @ValueOfEnum(enumClass = UserRole.class)
        @Schema(title = "Role", example = "ATIVO", type = "string", defaultValue = "ATIVO", allowableValues = {"ATIVO", "INATIVO"})
        String userRole,

        @NotBlank
        @ValueOfEnum(enumClass = EmployeePosition.class)
        @Schema(title = "Cargo do funcionário", example = "PROPRIETARIO", type = "string", defaultValue = "PROPRIETARIO", allowableValues = {"PROPRIETARIO", "VENDEDOR", "ASSISTENTE"})
        String position,

        @Size(min = 2, max = 250)
        @Schema(title = "Notas", example = "O pix dele é XXXXX", type = "string", minLength = 3, maxLength = 250)
        String note
) {
}

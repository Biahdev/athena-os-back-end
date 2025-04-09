package dev.abeatriz.athena_os.dto.employee;

import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.entity.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(title = "Funcionário detalhe")
public record EmployeeUserDetailDTO(

        @Min(1)
        @Schema(title = "ID do funcionário", minimum = "1", example = "1")
        Long employeeId,

        @Schema(title = "Nome", example = "André", type = "string", minLength = 3, maxLength = 50)
        String name,

        @Schema(title = "Status do funcionário", example = "REGULAR", type = "string", defaultValue = "REGULAR", allowableValues = {"REGULAR", "INATIVO"})
        EmployeeStatus status,

        @Schema(title = "Cargo do funcionário", example = "PROPRIETARIO", type = "string", defaultValue = "REGULAR", allowableValues = {"PROPRIETARIO", "VENDEDOR", "ASSISTENTE"})
        EmployeePosition position,

        @Schema(title = "Notas", example = "O pix dele é XXXXX", type = "string", minLength = 3, maxLength = 250)
        String note,

        String email,

        UserStatus userStatus,

        UserRole userRole
) {
}

package dev.abeatriz.athena_os.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.entity.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Usuario criação ou atualização de dados")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDetailDTO(
        String email,
        UserStatus status,
        UserRole role
) {
}

package dev.abeatriz.athena_os.dto.user;


import dev.abeatriz.athena_os.entity.enums.UserRole;

public record MeResponse(
        String name,
        String email,
        String phone,
        UserRole userRole
) {
}
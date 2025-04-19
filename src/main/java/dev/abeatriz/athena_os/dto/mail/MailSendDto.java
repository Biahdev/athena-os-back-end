package dev.abeatriz.athena_os.dto.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MailSendDto(

        @NotBlank
        @Schema(title = "Destino", minimum = "1", example = "email@email.com.br")
        String to,

        @NotBlank
        @Schema(title = "Assunto", example = "Email de teste")
        String subject,

        @NotBlank
        @Schema(title = "Conteúdo", example = "Esse é um email de teste")
        String content
) {
}

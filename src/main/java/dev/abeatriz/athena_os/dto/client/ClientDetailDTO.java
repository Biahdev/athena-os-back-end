package dev.abeatriz.athena_os.dto.client;

import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(title = "Cliente detalhe")
public record ClientDetailDTO(

    @Min(1)
    @Schema(title = "ID do Cliente", minimum = "1", example = "1")
    Long clientId,

    @Schema(title = "Nome", example = "Ana Beatriz", type = "string", minLength = 3, maxLength = 50)
    String name,

    @Schema(title = "Status do Cliente", example = "REGULAR", type = "string", allowableValues = {"REGULAR", "INADIMPLENTE", "INATIVO"})
    ClientStatus status,

    @Schema(title = "Endereço", example = "Rua ali do lado, 141, Bairro 2, Belo Horizonte - MG", type = "string", minLength = 3, maxLength = 100)
    String address,

    @Schema(title = "Telefone", example = "5531999474747", type = "string", minLength = 3, maxLength = 13)
    String phone,

    @Schema(title = "WhatsApp", example = "true", type = "boolean")
    Boolean whatsapp,

    @Schema(title = "Instagram", example = "abc", type = "string", minLength = 3, maxLength = 50)
    String instagram

) {
}

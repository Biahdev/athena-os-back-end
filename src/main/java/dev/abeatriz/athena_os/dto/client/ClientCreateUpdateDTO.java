package dev.abeatriz.athena_os.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import dev.abeatriz.athena_os.exception.validators.enums.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(title = "Cliente criação ou atualização")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ClientCreateUpdateDTO(

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(title = "Nome", example = "Ana Beatriz", type = "string", minLength = 3, maxLength = 50)
    String name,

    @ValueOfEnum(enumClass = ClientStatus.class)
    @Schema(title = "Status do Cliente", example = "REGULAR", type = "string", defaultValue = "REGULAR", allowableValues = {"REGULAR", "INADIMPLENTE", "INATIVO"})
    String status,

    @Size(min = 3, max = 100)
    @Schema(title = "Endereço", example = "Rua ali do lado, 141, Bairro 2, Belo Horizonte - MG", type = "string", minLength = 3, maxLength = 100)
    String address,

    @Size(min = 12, max = 13)
    @Schema(title = "Telefone", example = "5531999474747", type = "string", minLength = 3, maxLength = 13)
    String phone,

    @NotNull
    @Schema(title = "WhatsApp", example = "true", type = "boolean", defaultValue = "true")
    Boolean whatsapp,

    @Size(min = 3, max = 50)
    @Schema(title = "Instagram", example = "abc", type = "string", minLength = 3, maxLength = 50)
    String instagram

) {
}

package dev.abeatriz.athena_os.controller;

import dev.abeatriz.athena_os.dto.client.ClientCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import dev.abeatriz.athena_os.exception.ErrorMessage;
import dev.abeatriz.athena_os.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Cliente", description = "Operações relacionadas entidade cliente")
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(
            summary = "Cadastrar",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cadastro concluída com sucesso",
                            content = {@Content(schema = @Schema(implementation = ClientDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Valores inválidos ou mal formatados",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<ClientDetailDTO> create(@RequestBody @Valid ClientCreateUpdateDTO json) {
        var newClient = clientService.create(json);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }


    @Operation(
            summary = "Listagem",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = ClientDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<List<ClientDetailDTO>> listAll() {
        var allClients = clientService.listAll();
        return ResponseEntity.ok(allClients);
    }


    @Operation(
            summary = "Detalhe",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalhe com sucesso",
                            content = {@Content(schema = @Schema(implementation = ClientDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailDTO> listById(@PathVariable Long id) {
        var client = clientService.listById(id);
        return ResponseEntity.ok(client);
    }


    @Operation(
            summary = "Update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update com sucesso",
                            content = {@Content(schema = @Schema(implementation = ClientDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Valores inválidos",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClientDetailDTO> update(@RequestBody @Valid ClientCreateUpdateDTO json, @PathVariable Long id) {
        var client = clientService.update(json, id);
        return ResponseEntity.ok(client);
    }


    @Operation(
            summary = "Desativar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Desativado com sucesso",
                            content = {@Content(schema = @Schema(implementation = ClientDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @PutMapping("/{id}/disable")
    public ResponseEntity<ClientDetailDTO> disable(@PathVariable Long id) {
        var client = clientService.disable(id);
        return ResponseEntity.ok(client);
    }


    @Operation(
            summary = "Deletar",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Desativado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDetailDTO> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Listagem dos Status",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = ClientStatus.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/status")
    public ResponseEntity<List<String>> listClientStatus() {
        var status = clientService.listClientStatus();
        return ResponseEntity.ok(status);
    }


}

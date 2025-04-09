package dev.abeatriz.athena_os.controller;

import dev.abeatriz.athena_os.dto.order.OrderListDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.detail.OrderDetailDTO;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;
import dev.abeatriz.athena_os.exception.ErrorMessage;
import dev.abeatriz.athena_os.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedido", description = "Operações relacionadas entidade Pedidos")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Operation(
        summary = "Cadastrar",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Cadastro concluída com sucesso",
                content = {@Content(schema = @Schema(implementation = OrderDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<OrderDetailDTO> create(@RequestBody @Valid OrderCreateUpdateDTO json) {
        var newOrder = service.create(json);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @Operation(
        summary = "Listagem",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Listagem com sucesso",
                content = {@Content(schema = @Schema(implementation = OrderListDTO.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso ou Entidade não foi encontrado",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
            ),
        }
    )
    @GetMapping
    public ResponseEntity<List<OrderListDTO>> listAll() {
        var orders = service.listAll();
        return ResponseEntity.ok(orders);
    }


    @Operation(
        summary = "Detalhe",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Detalhe com sucesso",
                content = {@Content(schema = @Schema(implementation = OrderDetailDTO.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso ou Entidade não foi encontrado",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
            ),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> findById(@PathVariable @NotNull Long id) {
        var order = service.listById(id);
        return ResponseEntity.ok(order);
    }


    @Operation(
        summary = "Listagem de status",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Listagem com sucesso",
                content = {@Content(schema = @Schema(implementation = OrderStatus.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso ou Entidade não foi encontrado",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
            ),
        }
    )
    @GetMapping("/status")
    public ResponseEntity<List<String>> listStatus() {
        var status = service.listStatus();
        return ResponseEntity.ok(status);
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
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    /*
    @Operation(
        summary = "Update",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Update com sucesso",
                content = {@Content(schema = @Schema(implementation = OrderDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<OrderDetailDTO> update(@RequestBody @Valid OrderCreateUpdateDTO json, @PathVariable Long id) {
        var order = service.update(json, id);
        return ResponseEntity.ok(order);
    }







     */


}

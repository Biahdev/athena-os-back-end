package dev.abeatriz.athena_os.controller;


import dev.abeatriz.athena_os.dto.product.ProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.product.ProductDetailDTO;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import dev.abeatriz.athena_os.exception.ErrorMessage;
import dev.abeatriz.athena_os.service.ProductService;
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

@Tag(name = "Produto", description = "Operações relacionadas entidade Produto")
@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductService service;

    @Operation(
        summary = "Cadastrar",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Cadastro concluída com sucesso",
                content = {@Content(schema = @Schema(implementation = ProductDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<ProductDetailDTO> create(@RequestBody @Valid ProductCreateUpdateDTO json) {
        var newProduct = service.create(json);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @Operation(
        summary = "Listagem",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Listagem com sucesso",
                content = {@Content(schema = @Schema(implementation = ProductDetailDTO.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso ou Entidade não foi encontrado",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
            ),
        }
    )
    @GetMapping
    public ResponseEntity<List<ProductDetailDTO>> listAll() {
        var products = service.listAll();
        return ResponseEntity.ok(products);
    }


    @Operation(
        summary = "Detalhe",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Detalhe com sucesso",
                content = {@Content(schema = @Schema(implementation = ProductDetailDTO.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso ou Entidade não foi encontrado",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
            ),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> findById(@PathVariable Long id) {
        var products = service.listById(id);
        return ResponseEntity.ok(products);
    }


    @Operation(
        summary = "Update",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Update com sucesso",
                content = {@Content(schema = @Schema(implementation = ProductDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<ProductDetailDTO> update(@RequestBody @Valid ProductCreateUpdateDTO json, @PathVariable Long id) {
        var products = service.update(json, id);
        return ResponseEntity.ok(products);
    }

    @Operation(
        summary = "Listagem dos Status",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Listagem com sucesso",
                content = {@Content(schema = @Schema(implementation = ProductStatus.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso ou Entidade não foi encontrado",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
            ),
        }
    )
    @GetMapping("/status")
    public ResponseEntity<List<String>> listProductStatus() {
        var status = service.listProductStatus();
        return ResponseEntity.ok(status);
    }

    @Operation(
        summary = "Desativar",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Desativado com sucesso",
                content = {@Content(schema = @Schema(implementation = ProductDetailDTO.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso ou Entidade não foi encontrado",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
            ),
        }
    )
    @PutMapping("/{id}/disable")
    public ResponseEntity<ProductDetailDTO> disable(@PathVariable Long id) {
        var employee = service.disable(id);
        return ResponseEntity.ok(employee);
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


}

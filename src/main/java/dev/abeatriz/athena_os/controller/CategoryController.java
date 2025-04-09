package dev.abeatriz.athena_os.controller;


import dev.abeatriz.athena_os.dto.category.CategoryCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.category.CategoryDetailDTO;
import dev.abeatriz.athena_os.dto.category.CategoryWithCountProduct;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import dev.abeatriz.athena_os.exception.ErrorMessage;
import dev.abeatriz.athena_os.service.CategoryService;
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

@Tag(name = "Categoria", description = "Operações relacionadas entidade categoria")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Cadastrar",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cadastro concluída com sucesso",
                            content = {@Content(schema = @Schema(implementation = CategoryDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<CategoryDetailDTO> create(@RequestBody @Valid CategoryCreateUpdateDTO json) {
        var newCategory = categoryService.create(json);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }


    @Operation(
            summary = "Listagem todas as categorias com a quantidade de produtos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = CategoryWithCountProduct.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/products")
    public ResponseEntity<List<CategoryWithCountProduct>> listAllWithProductCount() {
        var categories = categoryService.listAllWithProductCount();
        return ResponseEntity.ok(categories);
    }


    @Operation(
            summary = "Listagem todas as categorias com a quantidade de produtos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = CategoryWithCountProduct.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping()
    public ResponseEntity<List<CategoryDetailDTO>> listAll() {
        var categories = categoryService.listAll();
        return ResponseEntity.ok(categories);
    }


    @Operation(
            summary = "Detalhe",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalhe com sucesso",
                            content = {@Content(schema = @Schema(implementation = CategoryDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetailDTO> listById(@PathVariable Long id) {
        var category = categoryService.listById(id);
        return ResponseEntity.ok(category);
    }


    @Operation(
            summary = "Update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update com sucesso",
                            content = {@Content(schema = @Schema(implementation = CategoryDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<CategoryDetailDTO> update(@RequestBody @Valid CategoryCreateUpdateDTO json, @PathVariable Long id) {
        var category = categoryService.update(json, id);
        return ResponseEntity.ok(category);
    }


    @Operation(
            summary = "Desativar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Desativado com sucesso",
                            content = {@Content(schema = @Schema(implementation = CategoryDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @PutMapping("/{id}/disable")
    public ResponseEntity<CategoryDetailDTO> disable(@PathVariable Long id) {
        var category = categoryService.disable(id);
        return ResponseEntity.ok(category);
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
    public ResponseEntity<CategoryDetailDTO> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Listagem do Status",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = CategoryStatus.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/status")
    public ResponseEntity<List<String>> listCategoryStatus() {
        var status = categoryService.listCategoryStatus();
        return ResponseEntity.ok(status);
    }
}

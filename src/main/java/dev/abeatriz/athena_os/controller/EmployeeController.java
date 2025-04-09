package dev.abeatriz.athena_os.controller;


import dev.abeatriz.athena_os.dto.employee.EmployeeUserDetailDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserCreateDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserUpdateDTO;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import dev.abeatriz.athena_os.exception.ErrorMessage;
import dev.abeatriz.athena_os.service.EmployeeService;
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

@Tag(name = "Funcionário", description = "Operações relacionadas entidade funcionários")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(
            summary = "Cadastrar",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cadastro concluída com sucesso",
                            content = {@Content(schema = @Schema(implementation = EmployeeUserDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<EmployeeUserDetailDTO> create(@RequestBody @Valid EmployeeUserCreateDTO json) {
        var newEmployee = employeeService.create(json);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }


    @Operation(
            summary = "Listagem",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = EmployeeUserDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<List<EmployeeUserDetailDTO>> listAll() {
        var employees = employeeService.listAll();
        return ResponseEntity.ok(employees);
    }


    @Operation(
            summary = "Detalhe",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detalhe com sucesso",
                            content = {@Content(schema = @Schema(implementation = EmployeeUserDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeUserDetailDTO> listById(@PathVariable Long id) {
        var employee = employeeService.listById(id);
        return ResponseEntity.ok(employee);
    }


    @Operation(
            summary = "Update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update com sucesso",
                            content = {@Content(schema = @Schema(implementation = EmployeeUserDetailDTO.class), mediaType = "application/json")}
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
    public ResponseEntity<EmployeeUserDetailDTO> update(@RequestBody @Valid EmployeeUserUpdateDTO json, @PathVariable Long id) {
        var employee = employeeService.update(json, id);
        return ResponseEntity.ok(employee);
    }

    @Operation(
            summary = "Desativar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Desativado com sucesso",
                            content = {@Content(schema = @Schema(implementation = EmployeeUserDetailDTO.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @PutMapping("/{id}/disable")
    public ResponseEntity<EmployeeUserDetailDTO> disable(@PathVariable Long id) {
        var employee = employeeService.disable(id);
        return ResponseEntity.ok(employee);
    }

    @Operation(
            summary = "Listagem dos Status",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = EmployeeStatus.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/status")
    public ResponseEntity<List<String>> listEmployeeStatus() {
        var status = employeeService.listEmployeeStatus();
        return ResponseEntity.ok(status);
    }


    @Operation(
            summary = "Listagem de cargos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listagem com sucesso",
                            content = {@Content(schema = @Schema(implementation = EmployeePosition.class), mediaType = "application/json")}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso ou Entidade não foi encontrado",
                            content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}
                    ),
            }
    )
    @GetMapping("/positions")
    public ResponseEntity<List<String>> listPositions() {
        var status = employeeService.listPositions();
        return ResponseEntity.ok(status);
    }

}

package com.example.controller;

import com.example.dto.EmpresaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller REST de exemplo demonstrando o uso da validação de CNPJ.
 * 
 * <p>Este controller mostra como a anotação {@link com.example.validation.cnpj.ValidCNPJ}
 * é automaticamente aplicada quando usado com @Valid em endpoints REST.</p>
 * 
 * @author Sistema de Validação
 * @since 1.0
 */
@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "API para gerenciamento de empresas com validação de CNPJ alfanumérico")
public class EmpresaController {

    /**
     * Endpoint para cadastrar uma nova empresa.
     * 
     * <p>A validação do CNPJ ocorre automaticamente através da anotação @Valid.</p>
     * 
     * @param empresaDTO dados da empresa a ser cadastrada
     * @return resposta com os dados da empresa cadastrada
     */
    @PostMapping
    @Operation(
        summary = "Cadastrar nova empresa",
        description = """
            Cadastra uma nova empresa no sistema com validação automática de CNPJ.
            
            O CNPJ pode ser informado em qualquer um dos formatos:
            - Alfanumérico sem formatação: 12ABC34501DE35
            - Numérico com formatação: 90.021.382/0001-22
            - Alfanumérico com formatação: 12.ABC.345/01DE-35
            
            A validação verifica:
            - Formato correto (12 caracteres base + 2 DV)
            - Apenas letras MAIÚSCULAS nos caracteres base
            - Apenas números nos dígitos verificadores
            - Cálculo correto dos dígitos verificadores
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Empresa cadastrada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Sucesso",
                    value = """
                        {
                          "mensagem": "Empresa cadastrada com sucesso!",
                          "empresa": {
                            "razaoSocial": "Tech Inovação Ltda",
                            "cnpj": "12ABC34501DE35",
                            "nomeFantasia": "Tech Inova"
                          }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos (CNPJ inválido, campos obrigatórios vazios, etc.)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "CNPJ Inválido",
                    value = """
                        {
                          "cnpj": "CNPJ inválido ou com dígitos verificadores incorretos"
                        }
                        """
                )
            )
        )
    })
    public ResponseEntity<Map<String, Object>> cadastrarEmpresa(
            @Parameter(
                description = "Dados da empresa a ser cadastrada",
                required = true,
                schema = @Schema(implementation = EmpresaDTO.class)
            )
            @Valid @RequestBody EmpresaDTO empresaDTO) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Empresa cadastrada com sucesso!");
        response.put("empresa", empresaDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para atualizar dados de uma empresa.
     * 
     * @param id identificador da empresa
     * @param empresaDTO novos dados da empresa
     * @return resposta com os dados atualizados
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar empresa",
        description = "Atualiza os dados de uma empresa existente, incluindo validação de CNPJ"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Empresa atualizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "mensagem": "Empresa atualizada com sucesso!",
                          "id": 1,
                          "empresa": {
                            "razaoSocial": "Tech Inovação Ltda - Atualizada",
                            "cnpj": "R55231B3000757",
                            "nomeFantasia": "Tech Inova Plus"
                          }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content(mediaType = "application/json")
        )
    })
    public ResponseEntity<Map<String, Object>> atualizarEmpresa(
            @Parameter(description = "ID da empresa", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novos dados da empresa", required = true)
            @Valid @RequestBody EmpresaDTO empresaDTO) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Empresa atualizada com sucesso!");
        response.put("id", id);
        response.put("empresa", empresaDTO);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Tratamento global de exceções de validação.
     * 
     * <p>Captura erros de validação e retorna um mapa com os campos inválidos
     * e suas respectivas mensagens de erro.</p>
     * 
     * @param ex exceção de validação
     * @return mapa com os erros de validação
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return errors;
    }
}

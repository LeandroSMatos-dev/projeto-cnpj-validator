package com.example.validation.cnpj;

import com.example.dto.EmpresaDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para validação de CNPJ em DTOs usando Bean Validation.
 * 
 * <p>Valida o comportamento da anotação {@link ValidCNPJ} quando aplicada
 * em campos de DTOs, simulando o comportamento em um contexto Spring.</p>
 * 
 * @author Leandro Santos Matos
 * @since 1.0
 */
@DisplayName("Testes de Integração - Validação de CNPJ em DTOs")
class CNPJValidationIntegrationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        validatorFactory.close();
    }

    @Nested
    @DisplayName("Validação em EmpresaDTO")
    class ValidacaoEmpresaDTO {

        @Test
        @DisplayName("Deve aceitar DTO com CNPJ válido")
        void deveAceitarDTOComCNPJValido() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "12ABC34501DE35",
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertTrue(violations.isEmpty(),
                "Não deveria haver violações para CNPJ válido");
        }

        @Test
        @DisplayName("Deve aceitar DTO com CNPJ formatado válido")
        void deveAceitarDTOComCNPJFormatadoValido() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "90.021.382/0001-22",
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertTrue(violations.isEmpty(),
                "Não deveria haver violações para CNPJ formatado válido");
        }

        @Test
        @DisplayName("Deve rejeitar DTO com CNPJ inválido")
        void deveRejeitarDTOComCNPJInvalido() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "12ABC34501DE00",  // DV incorreto
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertEquals(1, violations.size(),
                "Deveria haver exatamente 1 violação");

            ConstraintViolation<EmpresaDTO> violation = violations.iterator().next();
            assertEquals("cnpj", violation.getPropertyPath().toString());
            assertEquals("CNPJ inválido ou com dígitos verificadores incorretos", 
                violation.getMessage());
        }

        @Test
        @DisplayName("Deve rejeitar DTO com CNPJ nulo quando @NotBlank presente")
        void deveRejeitarDTOComCNPJNulo() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                null,
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            // @NotBlank deve capturar primeiro
            assertFalse(violations.isEmpty(),
                "Deveria haver violações para CNPJ nulo");
            
            boolean temViolacaoCNPJ = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cnpj"));
            
            assertTrue(temViolacaoCNPJ,
                "Deveria ter violação no campo CNPJ");
        }

        @Test
        @DisplayName("Deve rejeitar DTO com CNPJ vazio")
        void deveRejeitarDTOComCNPJVazio() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "",
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertFalse(violations.isEmpty(),
                "Deveria haver violações para CNPJ vazio");
        }

        @Test
        @DisplayName("Deve validar múltiplos campos simultaneamente")
        void deveValidarMultiplosCamposSimultaneamente() {
            EmpresaDTO empresa = new EmpresaDTO(
                "",  // Razão social inválida
                "12ABC34501DE00",  // CNPJ inválido
                "AB"  // Nome fantasia muito curto (se houver validação)
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertTrue(violations.size() >= 2,
                "Deveria haver pelo menos 2 violações (razão social e CNPJ)");

            boolean temViolacaoRazaoSocial = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("razaoSocial"));
            boolean temViolacaoCNPJ = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cnpj"));

            assertTrue(temViolacaoRazaoSocial, "Deveria ter violação em razaoSocial");
            assertTrue(temViolacaoCNPJ, "Deveria ter violação em cnpj");
        }
    }

    @Nested
    @DisplayName("Testes de Casos Especiais")
    class CasosEspeciais {

        @Test
        @DisplayName("Deve validar CNPJ com caracteres especiais de formatação")
        void deveValidarCNPJComCaracteresEspeciais() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "12.ABC.345/01DE-35",
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertTrue(violations.isEmpty(),
                "Deveria aceitar CNPJ com formatação completa");
        }

        @Test
        @DisplayName("Deve rejeitar CNPJ com letras minúsculas")
        void deveRejeitarCNPJComLetrasMinusculas() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "12abc34501de35",
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertEquals(1, violations.size(),
                "Deveria rejeitar CNPJ com letras minúsculas");
        }

        @Test
        @DisplayName("Deve rejeitar CNPJ zerado")
        void deveRejeitarCNPJZerado() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "00000000000000",
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertEquals(1, violations.size(),
                "Deveria rejeitar CNPJ zerado");
        }

        @Test
        @DisplayName("Deve rejeitar CNPJ com tamanho incorreto")
        void deveRejeitarCNPJComTamanhoIncorreto() {
            EmpresaDTO empresa = new EmpresaDTO(
                "Empresa Exemplo Ltda",
                "12ABC345",
                "Exemplo"
            );

            Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
            
            assertEquals(1, violations.size(),
                "Deveria rejeitar CNPJ com tamanho incorreto");
        }
    }

    @Nested
    @DisplayName("Testes com CNPJs Reais (Numéricos)")
    class CNPJsReais {

        @Test
        @DisplayName("Deve validar CNPJs numéricos reais válidos")
        void deveValidarCNPJsNumericosValidos() {
            String[] cnpjsValidos = {
                "90.021.382/0001-22",
                "90.024.778/0001-23",
                "90.025.108/0001-21",
                "90.025.255/0001-00",
                "90.024.420/0001-09"
            };

            for (String cnpj : cnpjsValidos) {
                EmpresaDTO empresa = new EmpresaDTO(
                    "Empresa Teste",
                    cnpj,
                    "Teste"
                );

                Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresa);
                
                assertTrue(violations.isEmpty(),
                    "CNPJ " + cnpj + " deveria ser válido");
            }
        }
    }
}

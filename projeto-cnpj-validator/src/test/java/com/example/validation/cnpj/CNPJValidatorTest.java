package com.example.validation.cnpj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o {@link CNPJValidator}.
 * 
 * <p>Valida todos os cenários de validação de CNPJ alfanumérico,
 * incluindo casos válidos, inválidos e edge cases.</p>
 * 
 * @author Leandro Santos Matos
 * @since 1.0
 */
@DisplayName("CNPJValidator - Testes de Validação")
class CNPJValidatorTest {

    private CNPJValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CNPJValidator();
    }

    @Nested
    @DisplayName("Testes de CNPJs Válidos")
    class CNPJsValidos {

        @ParameterizedTest
        @DisplayName("Deve validar CNPJs alfanuméricos sem formatação")
        @ValueSource(strings = {
            "12ABC34501DE35",
            "1345C3A5000106",
            "R55231B3000757"
        })
        void deveValidarCNPJsAlfanumericosSemFormatacao(String cnpj) {
            assertTrue(validator.isValid(cnpj, null),
                "CNPJ " + cnpj + " deveria ser válido");
        }

        @ParameterizedTest
        @DisplayName("Deve validar CNPJs numéricos sem formatação")
        @ValueSource(strings = {
            "90021382000122",
            "90024778000123",
            "90025108000121",
            "90025255000100",
            "90024420000109"
        })
        void deveValidarCNPJsNumericosSemFormatacao(String cnpj) {
            assertTrue(validator.isValid(cnpj, null),
                "CNPJ " + cnpj + " deveria ser válido");
        }

        @ParameterizedTest
        @DisplayName("Deve validar CNPJs com formatação")
        @ValueSource(strings = {
            "12.ABC.345/01DE-35",
            "13.45C.3A5/0001-06",
            "90.021.382/0001-22",
            "90.024.778/0001-23"
        })
        void deveValidarCNPJsComFormatacao(String cnpj) {
            assertTrue(validator.isValid(cnpj, null),
                "CNPJ " + cnpj + " deveria ser válido");
        }

        @Test
        @DisplayName("Deve validar CNPJ com espaços")
        void deveValidarCNPJComEspacos() {
            String cnpj = "  12ABC34501DE35  ";
            assertTrue(validator.isValid(cnpj, null),
                "CNPJ com espaços deveria ser válido");
        }
    }

    @Nested
    @DisplayName("Testes de CNPJs Inválidos")
    class CNPJsInvalidos {

        @ParameterizedTest
        @DisplayName("Deve rejeitar CNPJs com dígitos verificadores incorretos")
        @ValueSource(strings = {
            "R55231B3000700",  // DV incorreto
            "90025108000101",  // DV incorreto
            "12ABC34501DE00"   // DV incorreto
        })
        void deveRejeitarCNPJsComDVIncorreto(String cnpj) {
            assertFalse(validator.isValid(cnpj, null),
                "CNPJ " + cnpj + " deveria ser inválido (DV incorreto)");
        }

        @ParameterizedTest
        @DisplayName("Deve rejeitar CNPJs com letras minúsculas")
        @ValueSource(strings = {
            "1345c3A5000106",
            "12abc34501DE35"
        })
        void deveRejeitarCNPJsComLetrasMinusculas(String cnpj) {
            assertFalse(validator.isValid(cnpj, null),
                "CNPJ " + cnpj + " deveria ser inválido (letras minúsculas)");
        }

        @ParameterizedTest
        @DisplayName("Deve rejeitar CNPJs com letras no DV")
        @ValueSource(strings = {
            "90024420/0001A2",
            "12ABC34501DEAA"
        })
        void deveRejeitarCNPJsComLetrasNoDV(String cnpj) {
            assertFalse(validator.isValid(cnpj, null),
                "CNPJ " + cnpj + " deveria ser inválido (letras no DV)");
        }

        @ParameterizedTest
        @DisplayName("Deve rejeitar CNPJs com tamanho inválido")
        @ValueSource(strings = {
            "90025255/0001",      // Muito curto (12 caracteres)
            "12ABC34501DE3500",   // Muito longo (16 caracteres)
            "123",                // Muito curto (3 caracteres)
            "90.025.255/000"      // Muito curto (11 caracteres)
        })
        void deveRejeitarCNPJsComTamanhoInvalido(String cnpj) {
            assertFalse(validator.isValid(cnpj, null),
                "CNPJ " + cnpj + " deveria ser inválido (tamanho incorreto)");
        }

        @Test
        @DisplayName("Deve rejeitar CNPJ zerado")
        void deveRejeitarCNPJZerado() {
            String cnpj = "00000000000000";
            assertFalse(validator.isValid(cnpj, null),
                "CNPJ zerado deveria ser inválido");
        }

        @Test
        @DisplayName("Deve rejeitar string vazia")
        void deveRejeitarStringVazia() {
            assertFalse(validator.isValid("", null),
                "String vazia deveria ser inválida");
        }

        @Test
        @DisplayName("Deve rejeitar string apenas com espaços")
        void deveRejeitarStringApenasComEspacos() {
            assertFalse(validator.isValid("   ", null),
                "String apenas com espaços deveria ser inválida");
        }
    }

    @Nested
    @DisplayName("Testes de Valores Nulos")
    class ValoresNulos {

        @ParameterizedTest
        @NullSource
        @DisplayName("Deve aceitar null quando acceptNull = true")
        void deveAceitarNullQuandoConfigurado(String cnpj) {
            ValidCNPJ annotation = createAnnotation(true);
            validator.initialize(annotation);
            
            assertTrue(validator.isValid(cnpj, null),
                "Null deveria ser aceito quando acceptNull = true");
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Deve rejeitar null quando acceptNull = false")
        void deveRejeitarNullQuandoNaoConfigurado(String cnpj) {
            ValidCNPJ annotation = createAnnotation(false);
            validator.initialize(annotation);
            
            assertFalse(validator.isValid(cnpj, null),
                "Null deveria ser rejeitado quando acceptNull = false");
        }

        private ValidCNPJ createAnnotation(boolean acceptNull) {
            return new ValidCNPJ() {
                @Override
                public Class<ValidCNPJ> annotationType() {
                    return ValidCNPJ.class;
                }

                @Override
                public String message() {
                    return "CNPJ inválido";
                }

                @Override
                public Class<?>[] groups() {
                    return new Class[0];
                }

                @Override
                public Class[] payload() {
                    return new Class[0];
                }

                @Override
                public boolean acceptNull() {
                    return acceptNull;
                }
            };
        }
    }

    @Nested
    @DisplayName("Testes de Cálculo de DV")
    class CalculoDV {

        @Test
        @DisplayName("Deve calcular DV corretamente para CNPJ alfanumérico")
        void deveCalcularDVParaCNPJAlfanumerico() {
            assertEquals("35", CNPJValidator.calculaDV("12ABC34501DE"));
            assertEquals("06", CNPJValidator.calculaDV("1345C3A50001"));
            assertEquals("57", CNPJValidator.calculaDV("R55231B30007"));
        }

        @Test
        @DisplayName("Deve calcular DV corretamente para CNPJ numérico")
        void deveCalcularDVParaCNPJNumerico() {
            assertEquals("22", CNPJValidator.calculaDV("900213820001"));
            assertEquals("23", CNPJValidator.calculaDV("900247780001"));
            assertEquals("21", CNPJValidator.calculaDV("900251080001"));
            assertEquals("00", CNPJValidator.calculaDV("900252550001"));
            assertEquals("09", CNPJValidator.calculaDV("900244200001"));
        }

        @Test
        @DisplayName("Deve calcular DV removendo formatação")
        void deveCalcularDVRemovendoFormatacao() {
            assertEquals("22", CNPJValidator.calculaDV("90.021.382/0001"));
            assertEquals("35", CNPJValidator.calculaDV("12.ABC.345/01DE"));
        }

        @Test
        @DisplayName("Deve lançar exceção para base de CNPJ nula")
        void deveLancarExcecaoParaBaseNula() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CNPJValidator.calculaDV(null)
            );
            assertEquals("Base do CNPJ não pode ser nula", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção para base de CNPJ inválida")
        void deveLancarExcecaoParaBaseInvalida() {
            assertThrows(
                IllegalArgumentException.class,
                () -> CNPJValidator.calculaDV("123"),
                "Deveria lançar exceção para base muito curta"
            );

            assertThrows(
                IllegalArgumentException.class,
                () -> CNPJValidator.calculaDV("000000000000"),
                "Deveria lançar exceção para base zerada"
            );

            assertThrows(
                IllegalArgumentException.class,
                () -> CNPJValidator.calculaDV("12abc3450001"),
                "Deveria lançar exceção para base com letras minúsculas"
            );
        }
    }
}

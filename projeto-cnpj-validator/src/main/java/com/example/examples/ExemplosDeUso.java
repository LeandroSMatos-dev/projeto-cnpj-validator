package com.example.examples;

import com.example.dto.EmpresaDTO;
import com.example.validation.cnpj.CNPJValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * Classe com exemplos práticos de uso da validação de CNPJ.
 * 
 * <p>Demonstra diferentes cenários de uso da anotação {@link com.example.validation.cnpj.ValidCNPJ}
 * e do validador {@link CNPJValidator}.</p>
 * 
 * @author Sistema de Validação
 * @since 1.0
 */
public class ExemplosDeUso {

    public static void main(String[] args) {
        System.out.println("=== EXEMPLOS DE USO - VALIDAÇÃO DE CNPJ ===\n");

        exemplo1_CalcularDigitosVerificadores();
        exemplo2_ValidarCNPJProgramaticamente();
        exemplo3_ValidarComBeanValidation();
        exemplo4_ValidarMultiplosCNPJs();
        exemplo5_TratarErros();
    }

    /**
     * Exemplo 1: Calcular dígitos verificadores de um CNPJ.
     */
    private static void exemplo1_CalcularDigitosVerificadores() {
        System.out.println("--- Exemplo 1: Calcular Dígitos Verificadores ---");

        String[] basesCNPJ = {
            "12ABC34501DE",
            "90.021.382/0001",
            "R55231B30007"
        };

        for (String base : basesCNPJ) {
            try {
                String dv = CNPJValidator.calculaDV(base);
                System.out.printf("Base: %-20s → DV: %s%n", base, dv);
            } catch (IllegalArgumentException e) {
                System.out.printf("Base: %-20s → Erro: %s%n", base, e.getMessage());
            }
        }

        System.out.println();
    }

    /**
     * Exemplo 2: Validar CNPJ programaticamente.
     */
    private static void exemplo2_ValidarCNPJProgramaticamente() {
        System.out.println("--- Exemplo 2: Validar CNPJ Programaticamente ---");

        CNPJValidator validator = new CNPJValidator();

        String[] cnpjs = {
            "12ABC34501DE35",           // Válido
            "90.021.382/0001-22",       // Válido
            "R55231B3000700",           // Inválido (DV errado)
            "1345c3A5000106",           // Inválido (letras minúsculas)
            null                        // Null (configurável)
        };

        for (String cnpj : cnpjs) {
            boolean valido = validator.isValid(cnpj, null);
            System.out.printf("CNPJ: %-25s → %s%n", 
                cnpj == null ? "null" : cnpj, 
                valido ? "✓ VÁLIDO" : "✗ INVÁLIDO");
        }

        System.out.println();
    }

    /**
     * Exemplo 3: Validar usando Bean Validation em um DTO.
     */
    private static void exemplo3_ValidarComBeanValidation() {
        System.out.println("--- Exemplo 3: Validar com Bean Validation ---");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Exemplo com CNPJ válido
        EmpresaDTO empresaValida = new EmpresaDTO(
            "Empresa Exemplo Ltda",
            "12ABC34501DE35",
            "Exemplo"
        );

        Set<ConstraintViolation<EmpresaDTO>> violations = validator.validate(empresaValida);
        
        if (violations.isEmpty()) {
            System.out.println("✓ Empresa válida: " + empresaValida);
        } else {
            System.out.println("✗ Erros de validação:");
            violations.forEach(v -> 
                System.out.printf("  - %s: %s%n", v.getPropertyPath(), v.getMessage())
            );
        }

        System.out.println();

        // Exemplo com CNPJ inválido
        EmpresaDTO empresaInvalida = new EmpresaDTO(
            "Empresa Teste",
            "12ABC34501DE00",  // DV incorreto
            "Teste"
        );

        violations = validator.validate(empresaInvalida);
        
        if (violations.isEmpty()) {
            System.out.println("✓ Empresa válida: " + empresaInvalida);
        } else {
            System.out.println("✗ Erros de validação para empresa inválida:");
            violations.forEach(v -> 
                System.out.printf("  - %s: %s%n", v.getPropertyPath(), v.getMessage())
            );
        }

        factory.close();
        System.out.println();
    }

    /**
     * Exemplo 4: Validar múltiplos CNPJs em lote.
     */
    private static void exemplo4_ValidarMultiplosCNPJs() {
        System.out.println("--- Exemplo 4: Validar Múltiplos CNPJs ---");

        CNPJValidator validator = new CNPJValidator();

        String[][] cnpjsComStatus = {
            {"12ABC34501DE35", "Alfanumérico sem formatação"},
            {"90.021.382/0001-22", "Numérico com formatação"},
            {"1345C3A5000106", "Alfanumérico sem formatação"},
            {"R55231B3000757", "Alfanumérico sem formatação"},
            {"90.025.255/0001-00", "Com DV zerados"},
            {"12ABC34501DE00", "DV incorreto"},
            {"90.024.420/0001A2", "Letra no DV"}
        };

        int validos = 0;
        int invalidos = 0;

        for (String[] item : cnpjsComStatus) {
            String cnpj = item[0];
            String descricao = item[1];
            boolean valido = validator.isValid(cnpj, null);

            if (valido) {
                validos++;
            } else {
                invalidos++;
            }

            System.out.printf("%-25s | %-35s | %s%n", 
                cnpj, 
                descricao,
                valido ? "✓" : "✗");
        }

        System.out.printf("%nResumo: %d válidos, %d inválidos%n", validos, invalidos);
        System.out.println();
    }

    /**
     * Exemplo 5: Tratar erros de validação.
     */
    private static void exemplo5_TratarErros() {
        System.out.println("--- Exemplo 5: Tratamento de Erros ---");

        // Tentando calcular DV com base inválida
        String[] basesInvalidas = {
            null,
            "123",
            "000000000000",
            "12abc34501de"  // minúsculas
        };

        for (String base : basesInvalidas) {
            try {
                String dv = CNPJValidator.calculaDV(base);
                System.out.printf("Base: %-20s → DV: %s%n", base, dv);
            } catch (IllegalArgumentException e) {
                System.out.printf("Base: %-20s → Erro capturado: %s%n", 
                    base == null ? "null" : base, 
                    e.getMessage());
            }
        }

        System.out.println();
    }
}

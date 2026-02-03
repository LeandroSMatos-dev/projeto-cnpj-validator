package com.example.validation.cnpj;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para a anotação {@link ValidCNPJ}.
 * 
 * <p>Implementa a lógica de validação de CNPJ alfanumérico conforme as regras
 * da Receita Federal do Brasil para o novo formato de CNPJ.</p>
 * 
 * <p>Regras de validação:</p>
 * <ul>
 *   <li>12 caracteres base: letras maiúsculas (A-Z) ou dígitos (0-9)</li>
 *   <li>2 dígitos verificadores: apenas números (0-9)</li>
 *   <li>Não aceita CNPJ com todos os caracteres zerados</li>
 *   <li>Remove automaticamente caracteres de formatação (., /, -)</li>
 *   <li>Valida os dígitos verificadores através de algoritmo específico</li>
 * </ul>
 * 
 * @author Sistema de Validação
 * @since 1.0
 */
public class CNPJValidator implements ConstraintValidator<ValidCNPJ, String> {

    private static final int TAMANHO_CNPJ_SEM_DV = 12;
    private static final String REGEX_CARACTERES_FORMATACAO = "[./-]";
    private static final String REGEX_FORMACAO_BASE_CNPJ = "[A-Z\\d]{12}";
    private static final String REGEX_FORMACAO_DV = "[\\d]{2}";
    private static final String REGEX_VALOR_ZERADO = "^[0]+$";
    
    private static final int VALOR_BASE = (int) '0';
    private static final int[] PESOS_DV = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

    private boolean acceptNull;

    /**
     * Inicializa o validador com os parâmetros da anotação.
     * 
     * @param constraintAnnotation anotação com os parâmetros de configuração
     */
    @Override
    public void initialize(ValidCNPJ constraintAnnotation) {
        this.acceptNull = constraintAnnotation.acceptNull();
    }

    /**
     * Valida se o CNPJ informado é válido.
     * 
     * @param cnpj o CNPJ a ser validado
     * @param context contexto de validação
     * @return true se o CNPJ é válido, false caso contrário
     */
    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        // Trata valores nulos conforme configuração
        if (cnpj == null) {
            return acceptNull;
        }

        // Remove espaços e caracteres de formatação
        cnpj = removeCaracteresFormatacao(cnpj);

        // Valida formato e estrutura
        if (!isCnpjFormacaoValidaComDV(cnpj)) {
            return false;
        }

        // Extrai e valida os dígitos verificadores
        String dvInformado = cnpj.substring(TAMANHO_CNPJ_SEM_DV);
        String dvCalculado = calculaDV(cnpj.substring(0, TAMANHO_CNPJ_SEM_DV));
        
        return dvCalculado.equals(dvInformado);
    }

    /**
     * Calcula os dígitos verificadores (DV) de um CNPJ.
     * 
     * @param baseCnpj os 12 primeiros caracteres do CNPJ (sem DV)
     * @return os 2 dígitos verificadores calculados
     * @throws IllegalArgumentException se a base do CNPJ for inválida
     */
    public static String calculaDV(String baseCnpj) {
        if (baseCnpj == null) {
            throw new IllegalArgumentException("Base do CNPJ não pode ser nula");
        }

        baseCnpj = removeCaracteresFormatacao(baseCnpj);

        if (!isCnpjFormacaoValidaSemDV(baseCnpj)) {
            throw new IllegalArgumentException(
                String.format("CNPJ '%s' não é válido para o cálculo do DV", baseCnpj)
            );
        }

        String dv1 = String.format("%d", calculaDigito(baseCnpj));
        String dv2 = String.format("%d", calculaDigito(baseCnpj.concat(dv1)));
        
        return dv1.concat(dv2);
    }

    /**
     * Calcula um dígito verificador individual.
     * 
     * @param cnpj CNPJ parcial para cálculo do dígito
     * @return dígito verificador calculado (0-9)
     */
    private static int calculaDigito(String cnpj) {
        int soma = 0;
        
        for (int indice = cnpj.length() - 1; indice >= 0; indice--) {
            int valorCaracter = (int) cnpj.charAt(indice) - VALOR_BASE;
            soma += valorCaracter * PESOS_DV[PESOS_DV.length - cnpj.length() + indice];
        }
        
        return soma % 11 < 2 ? 0 : 11 - (soma % 11);
    }

    /**
     * Remove caracteres de formatação do CNPJ.
     * 
     * @param cnpj CNPJ com ou sem formatação
     * @return CNPJ sem caracteres de formatação
     */
    private static String removeCaracteresFormatacao(String cnpj) {
        return cnpj.trim().replaceAll(REGEX_CARACTERES_FORMATACAO, "");
    }

    /**
     * Valida se o CNPJ tem formação válida sem os dígitos verificadores.
     * 
     * @param cnpj CNPJ sem DV (12 caracteres)
     * @return true se a formação é válida
     */
    private static boolean isCnpjFormacaoValidaSemDV(String cnpj) {
        return cnpj.matches(REGEX_FORMACAO_BASE_CNPJ) && !cnpj.matches(REGEX_VALOR_ZERADO);
    }

    /**
     * Valida se o CNPJ tem formação válida com os dígitos verificadores.
     * 
     * @param cnpj CNPJ completo (14 caracteres)
     * @return true se a formação é válida
     */
    private static boolean isCnpjFormacaoValidaComDV(String cnpj) {
        return cnpj.matches(REGEX_FORMACAO_BASE_CNPJ.concat(REGEX_FORMACAO_DV)) 
            && !cnpj.matches(REGEX_VALOR_ZERADO);
    }
}

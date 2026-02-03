package com.example.validation.cnpj;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validação de CNPJ (Cadastro Nacional da Pessoa Jurídica) brasileiro.
 * 
 * <p>Esta anotação suporta o novo formato alfanumérico de CNPJ, composto por 12 caracteres
 * base (letras maiúsculas A-Z e dígitos 0-9) mais 2 dígitos verificadores numéricos.</p>
 * 
 * <p>Formatos aceitos:</p>
 * <ul>
 *   <li>Sem formatação: 12ABC34501DE35</li>
 *   <li>Com formatação: 12.ABC.345/01DE-35</li>
 * </ul>
 * 
 * <p>Exemplo de uso:</p>
 * <pre>
 * public class EmpresaDTO {
 *     {@literal @}ValidCNPJ(message = "CNPJ inválido")
 *     private String cnpj;
 * }
 * </pre>
 * 
 * @author Sistema de Validação
 * @since 1.0
 */
@Documented
@Constraint(validatedBy = CNPJValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCNPJ {

    /**
     * Mensagem de erro padrão quando a validação falha.
     * 
     * @return mensagem de erro
     */
    String message() default "CNPJ inválido";

    /**
     * Grupos de validação aos quais esta constraint pertence.
     * 
     * @return grupos de validação
     */
    Class<?>[] groups() default {};

    /**
     * Payload para clientes da API Bean Validation.
     * 
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Define se valores nulos devem ser considerados válidos.
     * Por padrão, valores nulos são considerados válidos (use @NotNull para validar presença).
     * 
     * @return true se null é considerado válido, false caso contrário
     */
    boolean acceptNull() default true;
}

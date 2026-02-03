package com.example.dto;

import com.example.validation.cnpj.ValidCNPJ;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de exemplo demonstrando o uso da anotação {@link ValidCNPJ}.
 * 
 * <p>Esta classe representa os dados de uma empresa e utiliza a validação
 * customizada de CNPJ através da anotação @ValidCNPJ.</p>
 * 
 * @author Sistema de Validação
 * @since 1.0
 */
@Schema(description = "Dados de uma empresa para cadastro ou atualização")
public class EmpresaDTO {

    @NotBlank(message = "Razão social é obrigatória")
    @Size(min = 3, max = 200, message = "Razão social deve ter entre 3 e 200 caracteres")
    @Schema(
        description = "Razão social da empresa",
        example = "Tech Inovação Ltda",
        required = true,
        minLength = 3,
        maxLength = 200
    )
    private String razaoSocial;

    @NotBlank(message = "CNPJ é obrigatório")
    @ValidCNPJ(message = "CNPJ inválido ou com dígitos verificadores incorretos")
    @Schema(
        description = """
            CNPJ da empresa em formato alfanumérico ou numérico.
            
            Formatos aceitos:
            - Alfanumérico: 12ABC34501DE35 ou 12.ABC.345/01DE-35
            - Numérico: 90021382000122 ou 90.021.382/0001-22
            
            Regras:
            - 12 caracteres base (A-Z maiúsculo ou 0-9)
            - 2 dígitos verificadores (apenas 0-9)
            - Formatação opcional (., /, -)
            """,
        example = "12ABC34501DE35",
        required = true,
        pattern = "[A-Z0-9]{12}[0-9]{2}"
    )
    private String cnpj;

    @Size(max = 100, message = "Nome fantasia não pode exceder 100 caracteres")
    @Schema(
        description = "Nome fantasia da empresa (opcional)",
        example = "Tech Inova",
        maxLength = 100
    )
    private String nomeFantasia;

    // Construtor padrão
    public EmpresaDTO() {
    }

    // Construtor com parâmetros
    public EmpresaDTO(String razaoSocial, String cnpj, String nomeFantasia) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    // Getters e Setters
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public String toString() {
        return "EmpresaDTO{" +
                "razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                '}';
    }
}

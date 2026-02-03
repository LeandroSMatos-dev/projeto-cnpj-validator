package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do Swagger/OpenAPI para documentação automática da API.
 * 
 * <p>Esta classe configura o SpringDoc OpenAPI para gerar documentação
 * interativa da API REST de validação de CNPJ.</p>
 * 
 * <p>Acesse a documentação em:</p>
 * <ul>
 *   <li>Swagger UI: http://localhost:8080/swagger-ui.html</li>
 *   <li>API Docs (JSON): http://localhost:8080/api-docs</li>
 *   <li>API Docs (YAML): http://localhost:8080/api-docs.yaml</li>
 * </ul>
 * 
 * @author Sistema de Validação
 * @since 1.0
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Configuração customizada da documentação OpenAPI.
     * 
     * @return objeto OpenAPI configurado com informações da API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Validação de CNPJ Alfanumérico")
                        .version("1.0.0")
                        .description("""
                                # API de Validação de CNPJ Alfanumérico
                                
                                Esta API permite cadastrar e validar empresas usando o novo formato 
                                alfanumérico de CNPJ brasileiro (válido a partir de 2026).
                                
                                ## Formato do CNPJ
                                
                                O CNPJ alfanumérico é composto por:
                                - **12 caracteres base**: letras maiúsculas (A-Z) e/ou dígitos (0-9)
                                - **2 dígitos verificadores**: apenas números (0-9)
                                
                                ### Exemplos Válidos:
                                - `12ABC34501DE35` (alfanumérico sem formatação)
                                - `90.021.382/0001-22` (numérico com formatação)
                                - `12.ABC.345/01DE-35` (alfanumérico com formatação)
                                
                                ### Regras de Validação:
                                - ✅ Aceita letras maiúsculas (A-Z) nos 12 primeiros caracteres
                                - ✅ Aceita números (0-9) em qualquer posição
                                - ✅ Remove automaticamente formatação (., /, -)
                                - ❌ Rejeita letras minúsculas
                                - ❌ Rejeita letras nos dígitos verificadores
                                - ❌ Rejeita CNPJs zerados
                                - ❌ Valida os dígitos verificadores usando algoritmo oficial da Receita Federal
                                
                                ## Código Fonte
                                
                                Este validador foi desenvolvido com base no código oficial fornecido pela
                                Receita Federal do Brasil para o novo formato de CNPJ alfanumérico.
                                
                                ## Tecnologias
                                
                                - Spring Boot 3.2.0
                                - Bean Validation (JSR 380)
                                - Hibernate Validator
                                - SpringDoc OpenAPI 3
                                """)
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("suporte@example.com")
                                .url("https://github.com/example/cnpj-validator"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("https://api.example.com")
                                .description("Servidor de Produção (exemplo)")
                ));
    }
}

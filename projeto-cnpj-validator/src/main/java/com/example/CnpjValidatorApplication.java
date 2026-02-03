package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * 
 * <p>Esta classe inicializa a aplicação Spring Boot com validação de CNPJ alfanumérico.</p>
 * 
 * <p>Recursos disponíveis:</p>
 * <ul>
 *   <li>REST API para cadastro de empresas com validação de CNPJ</li>
 *   <li>Validação automática usando Bean Validation (JSR 380)</li>
 *   <li>Suporte a CNPJ alfanumérico e numérico</li>
 *   <li>Tratamento global de exceções de validação</li>
 * </ul>
 * 
 * <p>Endpoints disponíveis:</p>
 * <ul>
 *   <li>POST /api/empresas - Cadastrar empresa</li>
 *   <li>PUT /api/empresas/{id} - Atualizar empresa</li>
 * </ul>
 * 
 * @author Sistema de Validação
 * @since 1.0
 */
@SpringBootApplication
public class CnpjValidatorApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     * 
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(CnpjValidatorApplication.class, args);
        
        System.out.println("\n" +
            "╔══════════════════════════════════════════════════════════════╗\n" +
            "║                                                              ║\n" +
            "║     VALIDADOR DE CNPJ ALFANUMÉRICO - SPRING BOOT             ║\n" +
            "║                                                              ║\n" +
            "║     Servidor iniciado com sucesso!                           ║\n" +
            "║                                                              ║\n" +
            "║     Acesse: http://localhost:8080                            ║\n" +
            "║                                                              ║\n" +
            "║     Endpoints disponíveis:                                   ║\n" +
            "║     POST   /api/empresas                                     ║\n" +
            "║     PUT    /api/empresas/{id}                                ║\n" +
            "║                                                              ║\n" +
            "╚══════════════════════════════════════════════════════════════╝\n");
    }
}

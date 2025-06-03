package com.example.solicitacao.micro.model.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança para o microserviço de solicitações.
 * Define políticas de autenticação e autorização usando OAuth2 com JWT.
 * 
 * Esta configuração implementa um modelo de segurança stateless,
 * adequado para arquiteturas de microserviços.
 */
@Configuration
public class SecurityConfig {
    
    /**
     * Configura a cadeia de filtros de segurança do Spring Security.
     * 
     * Implementa as seguintes políticas de segurança:
     * - Desabilita CSRF (adequado para APIs REST stateless)
     * - Exige autenticação para todas as requisições
     * - Utiliza JWT como mecanismo de autenticação via OAuth2
     * 
     * @param http Objeto HttpSecurity para configuração de segurança
     * @return SecurityFilterChain configurada para o microserviço
     * @throws Exception Se houver erro na configuração de segurança
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita proteção CSRF - desnecessária para APIs REST stateless
            // que utilizam tokens JWT para autenticação
            .csrf().disable()
            
            // Configura autorização de requisições HTTP
            .authorizeHttpRequests(auth -> auth
                // Exige autenticação para qualquer endpoint
                // Autorização específica por role é controlada via @PreAuthorize nos controllers
                .anyRequest().authenticated()
            )
            
            // Configura o microserviço como Resource Server OAuth2
            // Valida tokens JWT recebidos nas requisições
            .oauth2ResourceServer().jwt();
            
        return http.build();
    }
}
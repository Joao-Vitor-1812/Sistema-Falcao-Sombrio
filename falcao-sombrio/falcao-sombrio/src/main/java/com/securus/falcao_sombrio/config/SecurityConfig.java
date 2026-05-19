package com.securus.falcao_sombrio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.securus.falcao_sombrio.OperadorRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OperadorRepository operadorRepository;

    // Construtor que injeta dinamicamente o repositório vinculado ao Supabase
    public SecurityConfig(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    // Configuração do UserDetailsService usando a Lambda Expression ligada ao findByLogin
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> operadorRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Operador não encontrado no Supabase: " + username));
    }

    // Define que a validação de senhas será feita em texto puro temporariamente
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Filtros de segurança e interceptações de URL
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Mantido desativado para permitir requisições via fetch do JavaScript
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/login.html", "/css/**", "/js/**", "/api/logs/**", "/api/operador/dados", "/api/frotas/**", "/api/missoes/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html", true)
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
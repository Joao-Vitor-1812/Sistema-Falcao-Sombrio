package com.securus.falcao_sombrio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

    public SecurityConfig(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> operadorRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Operador não encontrado no Supabase: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desativa o CSRF usando a sintaxe atualizada do Spring Security 6+
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/login", "/login.html", "/css/**", "/js/**", "/api/logs/**", "/api/operador/dados", "/api/frotas/**", "/api/missoes/**", "/api/auth/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
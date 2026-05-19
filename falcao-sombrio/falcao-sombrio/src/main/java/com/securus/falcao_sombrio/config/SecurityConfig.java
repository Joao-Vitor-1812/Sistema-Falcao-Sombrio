package com.securus.falcao_sombrio.config;

import com.securus.falcao_sombrio.OperadorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OperadorRepository operadorRepository;

    public SecurityConfig(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    // Bean essencial para o Spring saber descriptografar o BCrypt do seu Supabase
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/login.html", "/css/**", "/js/**", "/api/telemetria/**", "/api/logs/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            return operadorRepository.findByLogin(username)
                .map(operador -> User.builder()
                    .username(operador.getLogin())
                    .password("{noop}" + operador.getSenha()) // REMOVIDO o "{noop}". Agora usa BCrypt puro!
                    .roles("USER")
                    .build())
                .orElseThrow(() -> new UsernameNotFoundException("Operador não encontrado: " + username));
        };
    }
}
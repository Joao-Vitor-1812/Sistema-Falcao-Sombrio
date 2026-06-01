package com.securus.falcao_sombrio.config;

// === SEUS IMPORTS ORIGINAIS ===
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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

    // === SEU ATRIBUTO E CONSTRUTOR ORIGINAL ===
    private final OperadorRepository operadorRepository;

    public SecurityConfig(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    // === NOVAS VARIÁVEIS ADICIONADAS PARA O PROTOCOLO AES-128 ===
    private static final String SECRET_KEY = "FalcaoSombrioKey";  // Chave de 16 bytes
    private static final String INIT_VECTOR = "RandomVector16B#"; // Vetor de 16 bytes

    // === NOVO MÉTODO ESTRUTURADO PARA O RF-05 ===
    // Ele lê a string cifrada que o javascript gera e devolve o texto plano
    public static String decrypt(String encryptedData) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(original);
        } catch (Exception ex) {
            System.out.println("ERRO PROTOCOLO RF-05: Falha crítica ao quebrar ofuscação de payload tático.");
            return null;
        }
    }

    // === SEU USERDETAILS SERVICE ORIGINAL ===
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> operadorRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Operador não encontrado no Supabase: " + username));
    }

    // === SEU PASSWORD ENCODER ORIGINAL ===
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // === SEU SECURITY FILTER CHAIN ORIGINAL ===
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
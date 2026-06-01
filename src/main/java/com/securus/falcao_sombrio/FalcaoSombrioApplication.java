package com.securus.falcao_sombrio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching // Mantém a ativação do RNF-02
public class FalcaoSombrioApplication {

    public static void main(String[] args) {
        SpringApplication.run(FalcaoSombrioApplication.class, args);
    }

    // REGISTRO OBRIGATÓRIO PARA CORREÇÃO DO ERRO DO BEAN 'CacheManager'
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("missoesCache");
    }
}
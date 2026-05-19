package com.securus.falcao_sombrio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
    // O Spring cria automaticamente os métodos de salvar (save) aqui
}
package com.securus.falcao_sombrio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Integer> {
    
    // Método JPA que faz a busca automática baseada na String da coluna 'login'
    Optional<Operador> findByLogin(String login);
}
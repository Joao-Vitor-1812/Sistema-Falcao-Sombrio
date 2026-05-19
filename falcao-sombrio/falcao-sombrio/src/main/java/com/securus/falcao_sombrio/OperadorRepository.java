package com.securus.falcao_sombrio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Integer> {
    // Este método faz o Spring criar automaticamente a query: 
    // SELECT * FROM operador WHERE login = ?
    Optional<Operador> findByLogin(String login);
}
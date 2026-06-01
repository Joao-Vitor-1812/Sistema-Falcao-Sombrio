package com.securus.falcao_sombrio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrotaRepository extends JpaRepository<Frota, Integer> {
    
    // Método para buscar a frota usando o nome exato do esquadrão
    Optional<Frota> findByNomeEsquadrao(String nomeEsquadrao);
}
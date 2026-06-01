package com.securus.falcao_sombrio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    // Pronto! O Spring Data JPA assume o controle usando o Supabase
}
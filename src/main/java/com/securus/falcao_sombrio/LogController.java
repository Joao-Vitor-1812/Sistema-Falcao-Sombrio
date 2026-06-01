package com.securus.falcao_sombrio;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogRepository logRepository;

    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    // Endpoint que recebe o rastro da missão normal ou da evasão
    @PostMapping("/gravar")
    public ResponseEntity<Log> gravarLog(@RequestBody Log novoLog, Principal principal) {
        // 1. Primeiro, verifica se o front-end enviou o operador dentro do payload
        if (novoLog.getIdOperador() != null && !novoLog.getIdOperador().trim().isEmpty()) {
            // Mantém o operador que veio do front-end (ex: "WINDOWS" ou "OP001")
            novoLog.setIdOperador(novoLog.getIdOperador());
        } 
        // 2. Se não veio no payload, tenta pegar do Spring Security (Principal)
        else if (principal != null) {
            novoLog.setIdOperador(principal.getName());
        } 
        // 3. Se ambos falharem, usa o fallback padrão
        else {
            novoLog.setIdOperador("SISTEMA_AUTONOMO");
        }

        // Salva direto no MongoDB Atlas / Supabase
        Log salvo = logRepository.save(novoLog);
        return ResponseEntity.ok(salvo);
    }
}
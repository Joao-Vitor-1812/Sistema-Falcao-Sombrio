package com.securus.falcao_sombrio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

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
        // Se o operador estiver logado no Spring Security, pegamos o ID dele automaticamente
        if (principal != null) {
            novoLog.setIdOperador(principal.getName());
        } else {
            novoLog.setIdOperador("SISTEMA_AUTONOMO");
        }

        // Salva direto no MongoDB Atlas
        Log salvo = logRepository.save(novoLog);
        return ResponseEntity.ok(salvo);
    }
}
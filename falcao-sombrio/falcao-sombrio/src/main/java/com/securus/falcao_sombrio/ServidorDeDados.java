package com.securus.falcao_sombrio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/telemetria")
public class ServidorDeDados {

    @Autowired
    private LogRepository logRepository; // Ponte para o MongoDB local

    @PostMapping("/salvar")
    public Log criarLogAuditoria(@RequestBody Log dados) {
        // Recebe o rastro do drone do HTML e grava no MongoDB
        return logRepository.save(dados);
    }

    @GetMapping("/historico")
    public List<Log> listarDadosMissao() {
        // Busca todos os logs gravados no MongoDB
        return logRepository.findAll();
    }
}
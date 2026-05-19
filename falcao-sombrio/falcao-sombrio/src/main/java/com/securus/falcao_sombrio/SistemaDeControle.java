package com.securus.falcao_sombrio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SistemaDeControle {
    private String versaoSoftware = "v1.0-MIL-SPEC";
    private String ipServidor = "127.0.0.1";

    @Autowired
    private LogRepository logRepository; // Para salvar alertas no MongoDB

    public String emitirAlerta() {
        String msg = "ALERTA: Atividade suspeita detectada!";
        // Exemplo: Salvar o alerta automaticamente no NoSQL
        Log logAlerta = new Log();
        logAlerta.setAcao("SISTEMA_ALERTA");
        logAlerta.setDadosJson("{\"msg\": \"" + msg + "\"}");
        logRepository.save(logAlerta);
        return msg;
    }

    public boolean iniciarMissao() {
        return true; 
    }

    public int gerarIdMissao() {
        return (int) (Math.random() * 10000);
    }
}
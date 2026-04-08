package com.securus.falcao_sombrio;
public class SistemaDeControle {
    private String versaoSoftware;
    private String ipServidor;

    public String emitirAlerta() {
        return "ALERTA: Atividade suspeita ou falha detectada!";
    }

    public boolean iniciarMissao() {
        
        return true; 
    }

    public int gerarIdMissao() {
        return (int) (Math.random() * 10000);
    }

    public void distribuirParametros() {
        
    }
}
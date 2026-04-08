package com.securus.falcao_sombrio;
import java.util.Date;

public class Log {
    private int idLog;
    private Date dataHora;
    private String acaoRealizada;
    private int idOperador;

    
    
    public String registroLog() {
        return "Log [" + idLog + "] - " + acaoRealizada + " por Operador: " + idOperador;
    }
}
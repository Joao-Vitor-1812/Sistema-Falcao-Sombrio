package com.securus.falcao_sombrio;
public class Missao {
    private int idMissao; 
    private String objetivos; 
    private String restricoes; 
    private StatusMissao status; 

    public void finalizarMissao() { 
        this.status = StatusMissao.CONCLUIDA; 
    }
}
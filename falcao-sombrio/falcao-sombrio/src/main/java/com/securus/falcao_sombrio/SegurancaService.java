package com.securus.falcao_sombrio;
public class SegurancaService {
    private String chavePublica; 
    public boolean autenticarLogin(String user, String senha) { 
        return true; 
    }

    public boolean validarMFA(String codigo) { 
        return true; 
    }
}
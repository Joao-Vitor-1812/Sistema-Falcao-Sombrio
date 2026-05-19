package com.securus.falcao_sombrio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegurancaService {

    @Autowired
    private OperadorRepository operadorRepository;

    private String chavePublica = "SEC-DYNAMICS-2026-X99";

    /**
     * Autenticação Real via Banco de Dados (Supabase)
     */
    public boolean autenticarLogin(String user, String senha) {
        // Busca o operador no banco pelo login
        Optional<Operador> operadorOpt = operadorRepository.findByLogin(user);

        if (operadorOpt.isPresent()) {
            Operador op = operadorOpt.get();
            // Verifica se a senha bate (Em produção, usaríamos BCrypt aqui)
            if (op.getSenha().equals(senha)) {
                System.out.println("LOG: Autenticação bem-sucedida para: " + user);
                return true;
            }
        }
        
        System.out.println("LOG: Falha de login para o usuário: " + user);
        return false;
    }

    public boolean validarMFA(String codigo) {
        return "123456".equals(codigo);
    }

    public String getChavePublica() { return chavePublica; }
}
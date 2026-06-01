package com.securus.falcao_sombrio;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operador")
public class OperadorController {

    // Endpoint que devolve as informações do operador autenticado na sessão
    @GetMapping("/dados")
    public Map<String, String> getDadosOperadorLogado(@AuthenticationPrincipal Operador operador) {
        Map<String, String> dados = new HashMap<>();
        if (operador != null) {
            dados.put("login", operador.getLogin());
            dados.put("nome", operador.getNome());
            dados.put("nivelAcesso", operador.getNivelAcesso()); // Traz o dado real da coluna do Supabase
        } else {
            dados.put("login", "Anônimo");
            dados.put("nivelAcesso", "NENHUM");
        }
        return dados;
    }
}
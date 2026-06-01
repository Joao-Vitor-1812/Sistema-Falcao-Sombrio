package com.securus.falcao_sombrio;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frotas")
public class FrotaController {

    private final FrotaRepository frotaRepository;

    public FrotaController(FrotaRepository frotaRepository) {
        this.frotaRepository = frotaRepository;
    }

    // Endpoint para popular o <select> de frotas de todos os usuários
    @GetMapping
    public List<Frota> listarTodas() {
        return frotaRepository.findAll();
    }

    // Endpoint de gravação acionado pelo prompt do operador WINDOWS
    @PostMapping("/criar")
    public ResponseEntity<String> criarFrota(@RequestBody Frota novaFrota) {
        if (novaFrota.getNomeEsquadrao() == null || novaFrota.getNomeEsquadrao().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Nome do esquadrão inválido.");
        }
        
        // Salva na tabela 'frota' do Supabase
        frotaRepository.save(novaFrota);
        return ResponseEntity.ok("Nova frota integrada à base militar com sucesso!");
    }

    // Endpoint de remoção acionado pelo select do operador WINDOWS
    @DeleteMapping("/remover")
    public ResponseEntity<String> removerFrota(@RequestParam("nome") String nomeEsquadrao) {
        return frotaRepository.findByNomeEsquadrao(nomeEsquadrao)
                .map(frota -> {
                    frotaRepository.delete(frota);
                    return ResponseEntity.ok("Frota removida do sistema tático!");
                })
                .orElse(ResponseEntity.badRequest().body("Erro: Esquadrão não encontrado na base."));
    }
}
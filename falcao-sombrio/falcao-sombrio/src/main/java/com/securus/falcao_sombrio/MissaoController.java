package com.securus.falcao_sombrio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Repository
interface MissaoRepository extends JpaRepository<Missao, Integer> {
}

@RestController
@RequestMapping("/api/missoes")
@CrossOrigin(origins = "*") // Garante que o JavaScript consiga acessar a rota sem bloqueios
public class MissaoController {

    private final MissaoRepository missaoRepository;

    public MissaoController(MissaoRepository missaoRepository) {
        this.missaoRepository = missaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Missao>> listarMissoes() {
        try {
            List<Missao> missoes = missaoRepository.findAll();
            return ResponseEntity.ok(missoes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/gravar")
    public ResponseEntity<String> gravarMissao(@RequestBody Missao novaMissao) {
        if (novaMissao.getObjetivos() == null || novaMissao.getObjetivos().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Campo objetivos vazio.");
        }
        missaoRepository.save(novaMissao);
        return ResponseEntity.ok("Gravado!");
    }
}

@Entity
@Table(name = "missao")
class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missao")
    private int idMissao;

    // Mudamos para Integer objeto para aceitar valores NULL vindos do Supabase sem estourar erro
    @Column(name = "fk_id_operador", nullable = true)
    private Integer fkIdOperador;

    @Column(name = "objetivos", nullable = false)
    private String objetivos;

    @Column(name = "restricoes", nullable = true)
    private String restricoes;

    @Column(name = "status_missao", nullable = true)
    private String statusMissao;

    @Column(name = "resultado_final", nullable = true)
    private String resultadoFinal;

    @Column(name = "status", nullable = true)
    private String status;

    // --- GETTERS E SETTERS COMPLETOS ---
    public int getIdMissao() { return idMissao; }
    public void setIdMissao(int idMissao) { this.idMissao = idMissao; }

    public Integer getFkIdOperador() { return fkIdOperador; }
    public void setFkIdOperador(Integer fkIdOperador) { this.fkIdOperador = fkIdOperador; }

    public String getObjetivos() { return objetivos; }
    public void setObjetivos(String objetivos) { this.objetivos = objetivos; }

    public String getRestricoes() { return restricoes; }
    public void setRestricoes(String restricoes) { this.restricoes = restricoes; }

    public String getStatusMissao() { return statusMissao; }
    public void setStatusMissao(String statusMissao) { this.statusMissao = statusMissao; }

    public String getResultadoFinal() { return resultadoFinal; }
    public void setResultadoFinal(String resultadoFinal) { this.resultadoFinal = resultadoFinal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
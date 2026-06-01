package com.securus.falcao_sombrio;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; // Import do Cache ativo (RNF-02)

import com.securus.falcao_sombrio.config.SecurityConfig; // Importa a SecurityConfig para usar o decrypt (RF-05)

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

    // =========================================================================
    // REQUISITO RNF-02 ATUALIZADO: RETORNO EM MEMÓRIA CACHE (LATÊNCIA < 50ms)
    // =========================================================================
    @GetMapping
    @Cacheable(value = "missoesCache") 
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

    // =========================================================================
    // PROTOCOLO REQUISITO RF-05: INTERCEPTAÇÃO E DESCRIPTOGRAFIA DE ROTA
    // =========================================================================
    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciarOperacaoSegura(@RequestBody MissaoRequestDto request) {
        
        // 1. Captura a string cifrada em AES que veio do JavaScript (ex: U2FsdGVkX1...)
        String textoCifrado = request.getCoordenadasCriptografadas();
        
        if (textoCifrado == null || textoCifrado.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Erro de integridade: Dados de geolocalização ausentes.");
        }

        // 2. Executa a descriptografia militar centralizada na SecurityConfig
        String coordenadasDecifradas = SecurityConfig.decrypt(textoCifrado);
        
        if (coordenadasDecifradas == null) {
            return ResponseEntity.badRequest().body("Erro de decodificação: Assinatura ou payload corrompido.");
        }
        
        // 3. Printa no terminal para comprovar que o RF-05 descriptografou com sucesso
        System.out.println("\n--- [PROTOCOLO REQUISITO RF-05 ATIVO] ---");
        System.out.println("SISTEMA: Criptograma de Rede recebido com sucesso.");
        System.out.println("SISTEMA: Coordenadas Reais Decifradas no Servidor: " + coordenadasDecifradas);
        System.out.println("-----------------------------------------\n");
        
        // Separação caso precise salvar a Latitude e Longitude individualmente
        String[] partes = coordenadasDecifradas.split(",");
        String latitude = partes[0];
        String longitude = partes[1];

        return ResponseEntity.ok("Canal tático estabelecido. Rota operando sob o critério RF-05.");
    }

    // DTO auxiliar para mapear o JSON composto enviado pelo fetch do index.html
    public static class MissaoRequestDto {
        private String idFrota;
        private String comando;
        private String coordenadasCriptografadas;

        public String getIdFrota() { return idFrota; }
        public void setIdFrota(String idFrota) { this.idFrota = idFrota; }

        public String getComando() { return comando; }
        public void setComando(String comando) { this.comando = comando; }

        public String getCoordenadasCriptografadas() { return coordenadasCriptografadas; }
        public void setCoordenadasCriptografadas(String coordenadasCriptografadas) { this.coordenadasCriptografadas = coordenadasCriptografadas; }
    }
}

@Entity
@Table(name = "missao")
class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missao")
    private int idMissao;

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
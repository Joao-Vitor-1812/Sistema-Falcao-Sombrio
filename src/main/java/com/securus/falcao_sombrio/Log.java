package com.securus.falcao_sombrio;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auditoria_telemetria") // Define a coleção no Mongo
public class Log {
    @Id
    private String id;
    private String idOperador;
    private String acao;
    private String dadosJson; // Aqui salvamos o rastro do drone
    private LocalDateTime timestamp = LocalDateTime.now();

    // Getters e Setters (ou use o @Data se tiver Lombok)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdOperador() { return idOperador; }
    public void setIdOperador(String idOperador) { this.idOperador = idOperador; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getDadosJson() { return dadosJson; }
    public void setDadosJson(String dadosJson) { this.dadosJson = dadosJson; }
}
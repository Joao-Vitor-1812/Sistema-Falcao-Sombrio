package com.securus.falcao_sombrio;

import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auditoria_telemetria")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Mudamos para Long porque SERIAL no Postgres usa número

    @Column(name = "id_operador")
    private String idOperador;

    private String acao;

    @Column(name = "dados_json", columnDefinition = "TEXT")
    private String dadosJson;

    private LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIdOperador() { return idOperador; }
    public void setIdOperador(String idOperador) { this.idOperador = idOperador; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getDadosJson() { return dadosJson; }
    public void setDadosJson(String dadosJson) { this.dadosJson = dadosJson; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
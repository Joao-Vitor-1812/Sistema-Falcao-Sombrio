package com.securus.falcao_sombrio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "missao")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMissao;

    @Column(nullable = false)
    private String objetivos;

    private String restricoes;

    @Enumerated(EnumType.STRING)
    private StatusMissao status;

    // Construtor padrão exigido pelo JPA
    public Missao() {
    }

    public Missao(String objetivos, String restricoes, StatusMissao status) {
        this.objetivos = objetivos;
        this.restricoes = restricoes;
        this.status = status;
    }

    // Método de negócio
    public void finalizarMissao() { 
        this.status = StatusMissao.CONCLUIDA; 
    }

    // Getters e Setters
    public int getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(int idMissao) {
        this.idMissao = idMissao;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(String restricoes) {
        this.restricoes = restricoes;
    }

    public StatusMissao getStatus() {
        return status;
    }

    public void setStatus(StatusMissao status) {
        this.status = status;
    }
}
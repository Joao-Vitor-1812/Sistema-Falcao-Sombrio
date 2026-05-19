package com.securus.falcao_sombrio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "frota")
public class Frota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_frota")
    private int idFrota;

    @Column(name = "nome_esquadrao", nullable = false)
    private String nomeEsquadrao;

    @Column(name = "rota_padrao")
    private String rotaPadrao;

    // --- GETTERS E SETTERS COMPLETOS ---
    public int getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(int idFrota) {
        this.idFrota = idFrota;
    }

    public String getNomeEsquadrao() {
        return nomeEsquadrao;
    }

    public void setNomeEsquadrao(String nomeEsquadrao) {
        this.nomeEsquadrao = nomeEsquadrao;
    }

    public String getRotaPadrao() {
        return rotaPadrao;
    }

    public void setRotaPadrao(String rotaPadrao) {
        this.rotaPadrao = rotaPadrao;
    }
}
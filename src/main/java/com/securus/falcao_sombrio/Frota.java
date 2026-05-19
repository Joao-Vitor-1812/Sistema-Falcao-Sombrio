package com.securus.falcao_sombrio;

import jakarta.persistence.*;
import java.util.List;

@Entity 
@Table(name = "frota")
public class Frota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFrota;

    private String nomeEsquadrao;

    @OneToMany(mappedBy = "frota")
    private List<Drone> drones;

    public int getIdFrota() { return idFrota; }
    public void setIdFrota(int idFrota) { this.idFrota = idFrota; }
    public String getNomeEsquadrao() { return nomeEsquadrao; }
    public void setNomeEsquadrao(String nomeEsquadrao) { this.nomeEsquadrao = nomeEsquadrao; }
    public List<Drone> getDrones() { return drones; }
    public void setDrones(List<Drone> drones) { this.drones = drones; }
}
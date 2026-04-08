package com.securus.falcao_sombrio;
import jakarta.persistence.*;

@Entity
@Table(name = "drone")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDrone;
    private String modelo;
    private double bateria;
    private double latitude;
    private double longitude;
    private double altitude;
    
    @Enumerated(EnumType.STRING)
    private StatusDrone status;

    @ManyToOne
    @JoinColumn(name = "fk_id_frota") 
    private Frota frota;

    public void executarMissao() {
        System.out.println("Executando missão...");
    }

    public void analisarAmbiente() {
        System.out.println("Analisando sensores...");
    }

    public void ativarEvasao() {
        System.out.println("Protocolo de evasão ativado!");
    }

    public void transmitirTelemetria() {
        System.out.println("Enviando dados para o NoSQL...");
    }

    public int getIdDrone() { return idDrone; }
    public void setIdDrone(int idDrone) { this.idDrone = idDrone; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getBateria() { return bateria; }
    public void setBateria(double bateria) { this.bateria = bateria; }

    public StatusDrone getStatus() { return status; }
    public void setStatus(StatusDrone status) { this.status = status; }

    public Frota getFrota() { return frota; }
    public void setFrota(Frota frota) { this.frota = frota; }
}
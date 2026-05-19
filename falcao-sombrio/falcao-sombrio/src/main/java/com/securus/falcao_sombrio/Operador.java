package com.securus.falcao_sombrio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operador")
public class Operador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOperador;

    private String nome;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    private String nivelAcesso;

    // Métodos de negócio
    public void acessarSistema() { 
        System.out.println("Operador " + nome + " acessando o sistema...");
    }

    public void planejarMissao(Missao m) { 
        System.out.println("Planejando missão: " + m.getObjetivos());
    }

    // Getters e Setters
    public int getIdOperador() { return idOperador; }
    public void setIdOperador(int idOperador) { this.idOperador = idOperador; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNivelAcesso() { 
        return this.nivelAcesso; 
    }

    public void setNivelAcesso(String nivelAcesso) { 
        this.nivelAcesso = nivelAcesso; 
    }
}
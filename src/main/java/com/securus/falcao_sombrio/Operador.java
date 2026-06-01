package com.securus.falcao_sombrio;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operador")
public class Operador implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOperador;
    private String nome;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;
    private String nivelAcesso;

    // A COLUNA DO PENDRIVE QUE ESTAVA FALTANDO
    @Column(name = "hardware_key")
    private String hardwareKey;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.nivelAcesso));
    }

    @Override public String getPassword() { return this.senha; }
    @Override public String getUsername() { return this.login; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // --- GETTERS E SETTERS COMPLETOS ---
    public int getIdOperador() { return idOperador; }
    public void setIdOperador(int idOperador) { this.idOperador = idOperador; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNivelAcesso() { return this.nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    // O GETTER E SETTER DA CHAVE DO PENDRIVE
    public String getHardwareKey() { return this.hardwareKey; }
    public void setHardwareKey(String hardwareKey) { this.hardwareKey = hardwareKey; }
}
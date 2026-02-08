package com.test.neoris.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String clienteid;
    private String contrasena;
    private boolean estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClienteid() {
        return clienteid;
    }

    public void setClienteid(String clienteid) {
        this.clienteid = clienteid;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

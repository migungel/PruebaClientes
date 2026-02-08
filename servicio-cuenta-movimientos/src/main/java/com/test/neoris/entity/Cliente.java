package com.test.neoris.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(nullable = false)
    private boolean estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

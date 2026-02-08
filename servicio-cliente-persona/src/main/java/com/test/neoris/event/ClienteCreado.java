package com.test.neoris.event;

import java.io.Serializable;

public class ClienteCreado implements Serializable {
    private Long clienteId;
    private String nombre;
    private String clienteid;
    private boolean estado;

    public ClienteCreado() {}

    public ClienteCreado(Long clienteId, String nombre, String clienteid, boolean estado) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.clienteid = clienteid;
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClienteid() {
        return clienteid;
    }

    public void setClienteid(String clienteid) {
        this.clienteid = clienteid;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

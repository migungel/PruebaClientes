package com.test.neoris.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//@Data
@Entity
@Table(name = "movimiento")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_id", nullable = false, foreignKey = @ForeignKey(name = "FK_MOVIMIENTO_CUENTA"))
    private Cuenta cuenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}

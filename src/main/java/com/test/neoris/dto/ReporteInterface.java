package com.test.neoris.dto;

import java.time.LocalDate;

public interface ReporteInterface {
    LocalDate getFecha();
    String getCliente();
    String getNumeroCuenta();
    String getTipo();
    Double getSaldoInicial();
    Boolean getEstado();
    Double getMovimiento();
    Double getSaldoDisponible();
}

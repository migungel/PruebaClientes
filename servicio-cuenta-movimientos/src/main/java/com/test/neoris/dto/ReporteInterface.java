package com.test.neoris.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReporteInterface {
    LocalDate getFecha();

    String getCliente();

    String getNumeroCuenta();

    String getTipo();

    BigDecimal getSaldoInicial();

    Boolean getEstado();

    BigDecimal getMovimiento();

    BigDecimal getSaldoDisponible();
}

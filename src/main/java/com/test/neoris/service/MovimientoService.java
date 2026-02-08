package com.test.neoris.service;

import com.test.neoris.dto.ReporteEstadoCuentaDTO;
import com.test.neoris.dto.ReporteInterface;
import com.test.neoris.entity.Movimiento;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoService {
    List<Movimiento> listarTodos();
    Movimiento guardar(Movimiento movimiento);
    Movimiento actualizar(Long id, Movimiento movimiento);
    void eliminar(Long id);
    Movimiento buscarPorId(Long id);
    List<Movimiento> obtenerReporte(Long clienteId, LocalDateTime inicio, LocalDateTime fin);
    List<ReporteEstadoCuentaDTO> obtenerReporteSp(Long clienteId, LocalDateTime inicio, LocalDateTime fin);
}

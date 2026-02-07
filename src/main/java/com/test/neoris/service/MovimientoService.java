package com.test.neoris.service;

import com.test.neoris.entity.Movimiento;

import java.util.List;

public interface MovimientoService {
    List<Movimiento> listarTodos();
    Movimiento guardar(Movimiento movimiento);
    Movimiento actualizar(Long id, Movimiento movimiento);
    void eliminar(Long id);
    Movimiento buscarPorId(Long id);
}

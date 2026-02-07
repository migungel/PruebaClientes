package com.test.neoris.service;

import com.test.neoris.entity.Cuenta;

import java.util.List;

public interface CuentaService {
    List<Cuenta> listarTodas();
    Cuenta guardar(Cuenta cuenta);
    Cuenta actualizar(Long id, Cuenta cuenta);
    void eliminar(Long id);
    Cuenta buscarPorId(Long id);
    Cuenta buscarPorNumero(String numeroCuenta);
}

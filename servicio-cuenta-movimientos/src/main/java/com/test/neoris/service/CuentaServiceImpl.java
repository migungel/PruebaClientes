package com.test.neoris.service;

import com.test.neoris.entity.Cuenta;
import com.test.neoris.exception.BusinessException;
import com.test.neoris.exception.ResourceNotFoundException;
import com.test.neoris.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<Cuenta> listarTodas() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta guardar(Cuenta cuenta) {
        if (cuenta.getCliente() == null || cuenta.getCliente().getId() == null) {
            throw new BusinessException("La cuenta debe tener un cliente asignado");
        }
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta buscarPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));
    }

    @Override
    public Cuenta actualizar(Long id, Cuenta cuentaDetalles) {
        Cuenta cuenta = buscarPorId(id);

        cuenta.setNumeroCuenta(cuentaDetalles.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDetalles.getTipoCuenta());
        cuenta.setEstado(cuentaDetalles.getEstado());
        cuenta.setSaldoInicial(cuentaDetalles.getSaldoInicial());

        if (cuentaDetalles.getCliente() != null) {
            cuenta.setCliente(cuentaDetalles.getCliente());
        }

        return cuentaRepository.save(cuenta);
    }

    @Override
    public void eliminar(Long id) {
        Cuenta cuenta = buscarPorId(id);
        cuentaRepository.delete(cuenta);
    }

    @Override
    public Cuenta buscarPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }
}

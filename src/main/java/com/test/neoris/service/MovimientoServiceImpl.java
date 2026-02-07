package com.test.neoris.service;

import com.test.neoris.entity.Cuenta;
import com.test.neoris.entity.Movimiento;
import com.test.neoris.repository.CuentaRepository;
import com.test.neoris.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService{
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<Movimiento> listarTodos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Movimiento buscarPorId(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Override
    @Transactional
    public Movimiento guardar(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new RuntimeException("La cuenta asociada no existe"));

        double saldoActual = cuenta.getSaldoInicial();
        double valorMovimiento = movimiento.getValor();

        if (valorMovimiento < 0 && Math.abs(valorMovimiento) > saldoActual) {
            throw new RuntimeException("Saldo no disponible");
        }

        double nuevoSaldo = saldoActual + valorMovimiento;

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        // 6. Guardar el registro del movimiento
        return movimientoRepository.save(movimiento);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Movimiento movimiento = buscarPorId(id);

        // Opcional: Revertir el saldo en la cuenta antes de borrar el movimiento
        Cuenta cuenta = movimiento.getCuenta();
        cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimiento.getValor());
        cuentaRepository.save(cuenta);

        movimientoRepository.delete(movimiento);
    }
}

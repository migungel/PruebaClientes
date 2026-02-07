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

        return movimientoRepository.save(movimiento);
    }

    @Override
    @Transactional
    public Movimiento actualizar(Long id, Movimiento movimientoDetalles) {
        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        Cuenta cuenta = movimientoExistente.getCuenta();

        double saldoSinMovimientoOriginal = cuenta.getSaldoInicial() - movimientoExistente.getValor();

        double nuevoValor = movimientoDetalles.getValor();
        if (nuevoValor < 0 && Math.abs(nuevoValor) > saldoSinMovimientoOriginal) {
            throw new RuntimeException("Saldo no disponible para esta actualización");
        }

        double nuevoSaldoFinal = saldoSinMovimientoOriginal + nuevoValor;
        cuenta.setSaldoInicial(nuevoSaldoFinal);
        cuentaRepository.save(cuenta);

        movimientoExistente.setTipoMovimiento(movimientoDetalles.getTipoMovimiento());
        movimientoExistente.setValor(nuevoValor);
        movimientoExistente.setSaldo(nuevoSaldoFinal);
        // La fecha se mantiene o se puede actualizar a LocalDateTime.now() según prefieras.

        return movimientoRepository.save(movimientoExistente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Movimiento movimiento = buscarPorId(id);

        Cuenta cuenta = movimiento.getCuenta();
        cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimiento.getValor());
        cuentaRepository.save(cuenta);

        movimientoRepository.delete(movimiento);
    }
}

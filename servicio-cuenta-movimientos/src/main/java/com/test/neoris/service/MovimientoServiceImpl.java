package com.test.neoris.service;

import com.test.neoris.dto.ReporteEstadoCuentaDTO;
import com.test.neoris.dto.ReporteInterface;
import com.test.neoris.entity.Cuenta;
import com.test.neoris.entity.Movimiento;
import com.test.neoris.exception.BusinessException;
import com.test.neoris.exception.ResourceNotFoundException;
import com.test.neoris.repository.CuentaRepository;
import com.test.neoris.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {
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
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Movimiento guardar(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new ResourceNotFoundException("La cuenta asociada no existe"));

        double saldoActual = calcularSaldoActual(cuenta.getId(), cuenta.getSaldoInicial());
        double valorMovimiento = movimiento.getValor();
        double nuevoSaldo = saldoActual + valorMovimiento;

        if (nuevoSaldo < 0) {
            throw new BusinessException("Saldo no disponible");
        }

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        return movimientoRepository.save(movimiento);
    }

    @Override
    @Transactional
    public Movimiento actualizar(Long id, Movimiento movimientoDetalles) {
        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con ID: " + id));

        Cuenta cuenta = movimientoExistente.getCuenta();
        double saldoSinMovimiento = calcularSaldoActualExcluyendo(cuenta.getId(), cuenta.getSaldoInicial(), id);
        double nuevoValor = movimientoDetalles.getValor();
        double nuevoSaldoFinal = saldoSinMovimiento + nuevoValor;

        if (nuevoSaldoFinal < 0) {
            throw new BusinessException("Saldo no disponible para esta actualización");
        }

        movimientoExistente.setTipoMovimiento(movimientoDetalles.getTipoMovimiento());
        movimientoExistente.setValor(nuevoValor);
        movimientoExistente.setSaldo(nuevoSaldoFinal);
        movimientoExistente.setFecha(movimientoDetalles.getFecha());
        return movimientoRepository.save(movimientoExistente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Movimiento movimiento = buscarPorId(id);
        movimientoRepository.delete(movimiento);
    }

    @Override
    @Transactional
    public List<Movimiento> obtenerReporte(Long clienteId, LocalDateTime inicio, LocalDateTime fin) {
        return movimientoRepository.generarReporte(clienteId, inicio, fin);
    }

    @Override
    @Transactional
    public List<ReporteEstadoCuentaDTO> obtenerReporteSp(Long clienteId, LocalDateTime inicio, LocalDateTime fin) {
        List<ReporteInterface> proyecciones = movimientoRepository.generarReporteSp(clienteId, inicio, fin);

        return proyecciones.stream().map(p -> new ReporteEstadoCuentaDTO(
                p.getFecha(),
                p.getCliente(),
                p.getNumeroCuenta(),
                p.getTipo(),
                p.getSaldoInicial(),
                p.getEstado(),
                p.getMovimiento(),
                p.getSaldoDisponible())).collect(Collectors.toList());
    }

    private double calcularSaldoActual(Long cuentaId, double saldoInicial) {
        List<Movimiento> movimientos = movimientoRepository.findAll().stream()
                .filter(m -> m.getCuenta().getId().equals(cuentaId))
                .toList();
        return saldoInicial + movimientos.stream().mapToDouble(Movimiento::getValor).sum();
    }

    private double calcularSaldoActualExcluyendo(Long cuentaId, double saldoInicial, Long movimientoExcluido) {
        List<Movimiento> movimientos = movimientoRepository.findAll().stream()
                .filter(m -> m.getCuenta().getId().equals(cuentaId) && !m.getId().equals(movimientoExcluido))
                .toList();
        return saldoInicial + movimientos.stream().mapToDouble(Movimiento::getValor).sum();
    }
}

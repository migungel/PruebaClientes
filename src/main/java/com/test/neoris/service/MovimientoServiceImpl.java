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
    public Movimiento guardar(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new ResourceNotFoundException("La cuenta asociada no existe"));

        List<Movimiento> movimientosPrevios = movimientoRepository.findAll().stream()
                .filter(m -> m.getCuenta().getId().equals(cuenta.getId()))
                .toList();

        double saldoActual = cuenta.getSaldoInicial();
        for (Movimiento m : movimientosPrevios) {
            saldoActual += m.getValor();
        }

        double valorMovimiento = movimiento.getValor();
        double nuevoSaldo = saldoActual + valorMovimiento;

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        if (valorMovimiento < 0 && Math.abs(valorMovimiento) > saldoActual) {
            throw new BusinessException("Saldo no disponible");
        }

        return movimientoGuardado;
    }

    @Override
    @Transactional
    public Movimiento actualizar(Long id, Movimiento movimientoDetalles) {
        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con ID: " + id));

        Cuenta cuenta = movimientoExistente.getCuenta();

        List<Movimiento> movimientosPrevios = movimientoRepository.findAll().stream()
                .filter(m -> m.getCuenta().getId().equals(cuenta.getId()) && !m.getId().equals(id))
                .toList();

        double saldoSinMovimiento = cuenta.getSaldoInicial();
        for (Movimiento m : movimientosPrevios) {
            saldoSinMovimiento += m.getValor();
        }

        double nuevoValor = movimientoDetalles.getValor();

        if (nuevoValor < 0 && Math.abs(nuevoValor) > saldoSinMovimiento) {
            throw new BusinessException("Saldo no disponible para esta actualización");
        }

        double nuevoSaldoFinal = saldoSinMovimiento + nuevoValor;

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
}

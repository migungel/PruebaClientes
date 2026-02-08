package com.test.neoris.controller;

import com.test.neoris.dto.ReporteEstadoCuentaDTO;
import com.test.neoris.dto.ReporteInterface;
import com.test.neoris.entity.Movimiento;
import com.test.neoris.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> getReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaFin,
            @RequestParam Long clienteId) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        return movimientoService.obtenerReporte(clienteId, inicio, fin);
    }

    @GetMapping("/sp")
    public ResponseEntity<List<ReporteEstadoCuentaDTO>> getReporteSp(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaFin,
            @RequestParam Long clienteId) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
        List<ReporteEstadoCuentaDTO> reporte = movimientoService.obtenerReporteSp(clienteId, inicio, fin);
        return ResponseEntity.ok(reporte);
    }
}

package com.test.neoris.controller;

import com.test.neoris.dto.ApiResponse;
import com.test.neoris.dto.ReporteEstadoCuentaDTO;
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
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Movimiento>>> getReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaFin,
            @RequestParam Long clienteId) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Movimiento> movimientos = movimientoService.obtenerReporte(clienteId, inicio, fin);
        if (movimientos.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(
                    "No se encontraron movimientos para el cliente y rango de fechas especificado", movimientos));
        }
        return ResponseEntity.ok(new ApiResponse<>(movimientos));
    }

    @GetMapping("/sp")
    public ResponseEntity<ApiResponse<List<ReporteEstadoCuentaDTO>>> getReporteSp(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fechaFin,
            @RequestParam Long clienteId) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
        List<ReporteEstadoCuentaDTO> reporte = movimientoService.obtenerReporteSp(clienteId, inicio, fin);
        if (reporte.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(
                    "No se encontraron movimientos para el cliente y rango de fechas especificado", reporte));
        }
        return ResponseEntity.ok(new ApiResponse<>(reporte));
    }
}

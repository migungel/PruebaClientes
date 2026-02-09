package com.test.neoris.controller;

import com.test.neoris.dto.ApiResponse;
import com.test.neoris.entity.Movimiento;
import com.test.neoris.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Movimiento>>> listar() {
        List<Movimiento> movimientos = movimientoService.listarTodos();
        if (movimientos.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>("No se encontraron movimientos registrados", movimientos));
        }
        return ResponseEntity.ok(new ApiResponse<>(movimientos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Movimiento>> crear(@RequestBody Movimiento movimiento) {
        Movimiento nuevoMovimiento = movimientoService.guardar(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Movimiento registrado exitosamente", nuevoMovimiento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Movimiento>> actualizar(@PathVariable Long id,
            @RequestBody Movimiento movimiento) {
        Movimiento movimientoActualizado = movimientoService.actualizar(id, movimiento);
        return ResponseEntity.ok(new ApiResponse<>("Movimiento actualizado exitosamente", movimientoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>("Movimiento eliminado exitosamente", null));
    }
}

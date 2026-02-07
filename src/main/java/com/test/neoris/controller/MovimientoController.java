package com.test.neoris.controller;

import com.test.neoris.entity.Movimiento;
import com.test.neoris.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> listar() { return movimientoService.listarTodos(); }

    @PostMapping
    public Movimiento crear(@RequestBody Movimiento movimiento) {
        return movimientoService.guardar(movimiento);
    }

    @PutMapping("/{id}")
    public Movimiento actualizar(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        return movimientoService.actualizar(id, movimiento);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { movimientoService.eliminar(id); }
}

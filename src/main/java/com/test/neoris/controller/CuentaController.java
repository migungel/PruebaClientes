package com.test.neoris.controller;

import com.test.neoris.dto.ApiResponse;
import com.test.neoris.entity.Cuenta;
import com.test.neoris.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cuenta>>> listar() {
        List<Cuenta> cuentas = cuentaService.listarTodas();
        if (cuentas.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>("No se encontraron cuentas registradas", cuentas));
        }
        return ResponseEntity.ok(new ApiResponse<>(cuentas));
    }

    @PostMapping
    public Cuenta crear(@RequestBody Cuenta cuenta) { return cuentaService.guardar(cuenta); }

    @PutMapping("/{id}")
    public Cuenta actualizar(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return cuentaService.actualizar(id, cuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { cuentaService.eliminar(id); }
}

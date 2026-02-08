package com.test.neoris.controller;

import com.test.neoris.dto.ApiResponse;
import com.test.neoris.entity.Cuenta;
import com.test.neoris.exception.ResourceNotFoundException;
import com.test.neoris.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(params = "numeroCuenta")
    public ResponseEntity<ApiResponse<Cuenta>> buscarPorNumero(@RequestParam String numeroCuenta) {
        Cuenta cuenta = cuentaService.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            throw new ResourceNotFoundException("Cuenta no encontrada con número: " + numeroCuenta);
        }
        return ResponseEntity.ok(new ApiResponse<>(cuenta));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cuenta>> crear(@RequestBody Cuenta cuenta) {
        Cuenta nuevaCuenta = cuentaService.guardar(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Cuenta creada exitosamente", nuevaCuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cuenta>> actualizar(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta cuentaActualizada = cuentaService.actualizar(id, cuenta);
        return ResponseEntity.ok(new ApiResponse<>("Cuenta actualizada exitosamente", cuentaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>("Cuenta eliminada exitosamente", null));
    }
}

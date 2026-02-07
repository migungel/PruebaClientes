package com.test.neoris.controller;

import com.test.neoris.entity.Cuenta;
import com.test.neoris.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> listar() { return cuentaService.listarTodas(); }

    @PostMapping
    public Cuenta crear(@RequestBody Cuenta cuenta) { return cuentaService.guardar(cuenta); }

    @PutMapping("/{id}")
    public Cuenta actualizar(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return cuentaService.actualizar(id, cuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { cuentaService.eliminar(id); }
}

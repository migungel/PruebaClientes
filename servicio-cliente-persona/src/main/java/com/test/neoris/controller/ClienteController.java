package com.test.neoris.controller;

import com.test.neoris.dto.ApiResponse;
import com.test.neoris.entity.Cliente;
import com.test.neoris.exception.ResourceNotFoundException;
import com.test.neoris.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> listar() {
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>("No se encontraron clientes registrados", clientes));
        }
        return ResponseEntity.ok(new ApiResponse<>(clientes));
    }

    @GetMapping(params = "clienteid")
    public ResponseEntity<ApiResponse<Cliente>> buscarPorClienteid(@RequestParam String clienteid) {
        Cliente cliente = clienteService.buscarPorClienteid(clienteid);
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente no encontrado con clienteid: " + clienteid);
        }
        return ResponseEntity.ok(new ApiResponse<>(cliente));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> crear(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Cliente creado exitosamente", nuevoCliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.actualizar(id, cliente);
        return ResponseEntity.ok(new ApiResponse<>("Cliente actualizado exitosamente", clienteActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>("Cliente eliminado exitosamente", null));
    }
}

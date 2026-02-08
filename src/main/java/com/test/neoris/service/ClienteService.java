package com.test.neoris.service;

import com.test.neoris.entity.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> listarTodos();
    Cliente guardar(Cliente cliente);
    Cliente actualizar(Long id, Cliente cliente);
    void eliminar(Long id);
    Cliente buscarPorId(Long id);
    Cliente buscarPorClienteid(String clienteid);
}

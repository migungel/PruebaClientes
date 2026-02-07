package com.test.neoris.service;

import com.test.neoris.entity.Cliente;
import com.test.neoris.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarTodos() { return clienteRepository.findAll(); }

    @Override
    public Cliente guardar(Cliente cliente) { return clienteRepository.save(cliente); }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public Cliente actualizar(Long id, Cliente clienteDetalles) {
        Cliente cliente = buscarPorId(id);
        cliente.setNombre(clienteDetalles.getNombre());
        cliente.setGenero(clienteDetalles.getGenero());
        cliente.setEdad(clienteDetalles.getEdad());
        cliente.setDireccion(clienteDetalles.getDireccion());
        cliente.setTelefono(clienteDetalles.getTelefono());
        cliente.setContrasena(clienteDetalles.getContrasena());
        cliente.setEstado(clienteDetalles.isEstado());
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(Long id) { clienteRepository.deleteById(id); }
}

package com.test.neoris.service;

import com.test.neoris.entity.Cliente;
import com.test.neoris.event.ClienteCreado;
import com.test.neoris.exception.ResourceNotFoundException;
import com.test.neoris.messaging.ClienteEventPublisher;
import com.test.neoris.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteEventPublisher eventPublisher;

    @Override
    public List<Cliente> listarTodos() { return clienteRepository.findAll(); }

    @Override
    public Cliente guardar(Cliente cliente) {
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        ClienteCreado evento = new ClienteCreado(
            clienteGuardado.getId(),
            clienteGuardado.getNombre(),
            clienteGuardado.getClienteid(),
            clienteGuardado.isEstado()
        );
        eventPublisher.publishClienteCreado(evento);
        
        return clienteGuardado;
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
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
    public void eliminar(Long id) {
        buscarPorId(id);
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente buscarPorClienteid(String clienteid) {
        return clienteRepository.findByClienteid(clienteid);
    }
}

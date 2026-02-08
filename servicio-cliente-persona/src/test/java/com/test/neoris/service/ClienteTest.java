package com.test.neoris.service;

import com.test.neoris.entity.Cliente;
import com.test.neoris.exception.ResourceNotFoundException;
import com.test.neoris.messaging.ClienteEventPublisher;
import com.test.neoris.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteEventPublisher eventPublisher;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setClienteid("JLEMA123");
        cliente.setNombre("Jose Lema");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("Otavalo sn y principal");
        cliente.setTelefono("098254785");
        cliente.setContrasena("1234");
        cliente.setEstado(true);
    }

    @Test
    void testListarTodos() {
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setClienteid("CLI002");
        cliente2.setNombre("María López");

        List<Cliente> clientes = Arrays.asList(cliente, cliente2);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("JLEMA123", resultado.get(0).getClienteid());
        assertEquals("CLI002", resultado.get(1).getClienteid());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testGuardarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.guardar(cliente);

        assertNotNull(resultado);
        assertEquals("JLEMA123", resultado.getClienteid());
        assertEquals("Jose Lema", resultado.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testBuscarPorIdExitoso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("JLEMA123", resultado.getClienteid());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> clienteService.buscarPorId(999L));

        assertEquals("Cliente no encontrado con ID: 999", exception.getMessage());
        verify(clienteRepository, times(1)).findById(999L);
    }

    @Test
    void testActualizarCliente() {
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Jose Lema Actualizado");
        clienteActualizado.setGenero("Masculino");
        clienteActualizado.setEdad(31);
        clienteActualizado.setDireccion("Otavalo sn y secundaria");
        clienteActualizado.setTelefono("098254786");
        clienteActualizado.setContrasena("5678");
        clienteActualizado.setEstado(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.actualizar(1L, clienteActualizado);

        assertNotNull(resultado);
        assertEquals("Jose Lema Actualizado", resultado.getNombre());
        assertEquals(31, resultado.getEdad());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testEliminarCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.eliminar(1L);

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarPorClienteid() {
        when(clienteRepository.findByClienteid("JLEMA123")).thenReturn(cliente);

        Cliente resultado = clienteService.buscarPorClienteid("JLEMA123");

        assertNotNull(resultado);
        assertEquals("JLEMA123", resultado.getClienteid());
        assertEquals("Jose Lema", resultado.getNombre());
        verify(clienteRepository, times(1)).findByClienteid("JLEMA123");
    }
}

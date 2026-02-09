package com.test.neoris.listener;

import com.test.neoris.config.RabbitMQConfig;
import com.test.neoris.event.ClienteCreado;
import com.test.neoris.repository.CuentaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventListener {

    @Autowired
    private CuentaRepository cuentaRepository;

    @RabbitListener(queues = RabbitMQConfig.CLIENTE_QUEUE)
    public void handleClienteCreado(ClienteCreado evento) {
        System.out.println("Evento recibido: Cliente creado con ID: " + evento.getClienteId());
        System.out.println("Nombre: " + evento.getNombre());
        System.out.println("ClienteID: " + evento.getClienteid());
    }
}

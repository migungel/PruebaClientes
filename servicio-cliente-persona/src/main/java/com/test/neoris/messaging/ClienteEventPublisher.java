package com.test.neoris.messaging;

import com.test.neoris.config.RabbitMQConfig;
import com.test.neoris.event.ClienteCreado;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishClienteCreado(ClienteCreado evento) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CLIENTE_QUEUE, evento);
    }
}

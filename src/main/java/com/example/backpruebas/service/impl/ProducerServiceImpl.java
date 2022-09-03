package com.example.backpruebas.service.impl;

import com.example.backpruebas.entity.Prueba;
import com.example.backpruebas.service.ProducerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProducerServiceImpl implements ProducerService {
     @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMsg(Prueba object) {
        amqpTemplate.convertSendAndReceive("salud.pruebas.exchange","salud.pruebas.routingkey",object);
    }
}

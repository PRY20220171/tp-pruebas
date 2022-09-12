package com.example.backpruebas.service.impl;

import com.example.backpruebas.entity.Prueba;
import com.example.backpruebas.service.ConsumerService;
import com.example.backpruebas.service.PruebaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private PruebaService pruebaService;

    @Override
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.rabbitmq.queue}",durable = "true"),
                    exchange = @Exchange(value = "${spring.rabbitmq.exchange}"),
                    key = "${spring.rabbitmq.routingkey}")
    )
    public Object consumerMessage(String objId) throws AmqpIOException {
        System.out.println("=============== Message ==================");
        System.out.println(objId);
        System.out.println("==========================================");
        UUID pruebaId;
        try{
            pruebaId= UUID.fromString(objId);
        } catch (Exception e) {
            ObjectMapper obj = new ObjectMapper();
            try {
                return obj.writeValueAsString("Error: El id de la prueba no es un UUID válido");
            }catch(JsonProcessingException ex){
                return null;
            }
        }
        Prueba objinst= pruebaService.getPrueba(pruebaId);
        if(objinst==null){
            return null;
        }
        else{
            ObjectMapper obj = new ObjectMapper();
            try {
                return obj.writeValueAsString(objinst);
            }catch(JsonProcessingException e){
                return null;
            }
        }
    }
}
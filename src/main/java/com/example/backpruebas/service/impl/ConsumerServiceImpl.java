package com.example.backpruebas.service.impl;

import com.example.backpruebas.entity.Prueba;
import com.example.backpruebas.service.ConsumerService;
import com.example.backpruebas.service.PruebaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    /*@RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.rabbitmq.queue}", durable = "true"),
                    exchange = @Exchange(value = "${spring.rabbitmq.exchange}"),
                    key = "${spring.rabbitmq.routingkey}")
    )*/
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "pruebas.rpc.requests", durable = "true"),
                    exchange = @Exchange(value = "pruebas.rpc"),
                    key = "${spring.rabbitmq.routingkey}")
    )

    public Object consumerMessage(String objId) throws AmqpIOException {

        System.out.println("=============== Message ==================");
        System.out.println(objId);
        System.out.println("==========================================");

        UUID pruebaId;

        try {
            pruebaId = UUID.fromString(objId);
        } catch (Exception e) {
            ObjectMapper obj = new ObjectMapper();
            try {
                return obj.writeValueAsString("Error: El id de la prueba no es un UUID válido");
            } catch (JsonProcessingException ex) {
                return null;
            }
        }

        Prueba product = pruebaService.getPrueba(pruebaId);

        if (product == null) {
            System.out.println("No existe una prueba con ese ID");
            return null;
        } else {

            System.out.println("Existe la prueba");
            System.out.println(product.toString());

            ObjectMapper obj = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();

            try {
                return obj.writeValueAsString(product).replaceAll("^\\{\\Xid\\X:\\d+,", "{"); //test
            } catch (JsonProcessingException e) {
                System.out.println(e.toString());
                return null;
            }
        }

    }
}
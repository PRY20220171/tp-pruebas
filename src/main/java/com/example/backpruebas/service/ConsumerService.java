package com.example.backpruebas.service;

import com.example.backpruebas.entity.Prueba;

import java.util.UUID;

public interface ConsumerService {
    Object consumerMessage(UUID proId) throws Exception;
}

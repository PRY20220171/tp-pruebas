package com.example.backpruebas.service;

import com.example.backpruebas.entity.Prueba;

import java.util.UUID;

public interface ConsumerService {
    Object consumerMessage(String objId) throws Exception;
}
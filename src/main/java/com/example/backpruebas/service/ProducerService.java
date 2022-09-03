package com.example.backpruebas.service;

import com.example.backpruebas.entity.Prueba;

public interface ProducerService {
    //Object sendMsg(Long proId) throws Exception;

    void sendMsg(Prueba object);
}

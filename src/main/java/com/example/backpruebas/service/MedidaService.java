package com.example.backpruebas.service;

import com.example.backpruebas.entity.Medida;

import java.util.List;
import java.util.UUID;

public interface MedidaService {
    List<Medida> findMedidaAll();
    Medida getMedida(UUID id);
    Medida createMedida(Medida medida);
    Medida updateMedida(Medida medida);
    String deleteMedida(UUID id);
}

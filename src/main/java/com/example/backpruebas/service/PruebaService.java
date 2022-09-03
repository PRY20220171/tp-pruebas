package com.example.backpruebas.service;

import com.example.backpruebas.entity.Prueba;

import java.util.List;
import java.util.UUID;

public interface PruebaService {
    List<Prueba> findPruebaAll();
    Prueba getPrueba(UUID id);
    Prueba createPrueba(Prueba prueba);
    Prueba updatePrueba(Prueba prueba);
    String deletePrueba(UUID id);
}

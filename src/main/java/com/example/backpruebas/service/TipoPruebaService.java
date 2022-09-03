package com.example.backpruebas.service;

import com.example.backpruebas.entity.TipoPrueba;

import java.util.List;
import java.util.UUID;

public interface TipoPruebaService {
    List<TipoPrueba> findTipoPruebaAll();
    TipoPrueba getTipoPrueba(UUID id);
    TipoPrueba createTipoPrueba(TipoPrueba tipoPrueba);
    TipoPrueba updateTipoPrueba(TipoPrueba tipoPrueba);
    String deleteTipoPrueba(UUID id);
}

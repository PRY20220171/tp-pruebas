package com.example.backpruebas.service;

import com.example.backpruebas.entity.CategoriaPrueba;

import java.util.List;
import java.util.UUID;

public interface CategoriaPruebaService {
    List<CategoriaPrueba> findCategoriaPruebaAll();
    CategoriaPrueba getCategoriaPrueba(UUID id);
    CategoriaPrueba createCategoriaPrueba(CategoriaPrueba categoriaPrueba);
    CategoriaPrueba updateCategoriaPrueba(CategoriaPrueba categoriaPrueba);
    String deleteCategoriaPrueba(UUID id);
}

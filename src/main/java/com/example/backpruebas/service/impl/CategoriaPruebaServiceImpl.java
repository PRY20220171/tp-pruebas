package com.example.backpruebas.service.impl;

import com.example.backpruebas.entity.CategoriaPrueba;
import com.example.backpruebas.repository.CategoriaPruebaRepository;
import com.example.backpruebas.service.CategoriaPruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaPruebaServiceImpl implements CategoriaPruebaService {
    @Autowired
    private CategoriaPruebaRepository categoriaPruebaRepository;

    @Override
    public List<CategoriaPrueba> findCategoriaPruebaAll() {
        return (List<CategoriaPrueba>) categoriaPruebaRepository.findAll();
    }

    @Override
    public CategoriaPrueba getCategoriaPrueba(UUID id) {
        return categoriaPruebaRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriaPrueba createCategoriaPrueba(CategoriaPrueba categoriaPrueba) {
        //Aquí irian las validaciones al crear el categoriaPrueba de ser necesario
        return categoriaPruebaRepository.save(categoriaPrueba);
    }

    @Override
    public CategoriaPrueba updateCategoriaPrueba(CategoriaPrueba categoriaPrueba) {
        CategoriaPrueba categoriaPruebaDB = this.getCategoriaPrueba(categoriaPrueba.getId());
        if (categoriaPruebaDB == null) {
            return null;
        }
        //Actualizamos los valores del categoriaPrueba:
        categoriaPruebaDB.setFecregistro(categoriaPrueba.getFecregistro());
        categoriaPruebaDB.setDescripcion(categoriaPrueba.getDescripcion());
        categoriaPruebaDB.setEstado(categoriaPrueba.getEstado());
        categoriaPruebaDB.setTipo(categoriaPrueba.getTipo());
        return categoriaPruebaRepository.save(categoriaPrueba);
    }

    @Override
    public String deleteCategoriaPrueba(UUID id) {
        CategoriaPrueba categoriaPruebaDB = this.getCategoriaPrueba(id);
        if (categoriaPruebaDB == null) {
            return null;
        }
        try{
            categoriaPruebaRepository.delete(categoriaPruebaDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }
        return "ELIMINADO CON EXITO";
    }
/*
    @Override
    public CategoriaPrueba getByDni(Long dni) {
        return categoriaPruebaRepository.findAllByTipoAndEstado("DNI", dni.toString());
    }

    @Override
    public CategoriaPrueba getByDocExtranjeria(Long estado) {
        return categoriaPruebaRepository.findAllByTipoAndEstado("DOCUMENTO EXTRANJERIA", estado.toString());
    }
 */
}

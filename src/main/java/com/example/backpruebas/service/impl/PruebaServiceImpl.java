package com.example.backpruebas.service.impl;

import com.example.backpruebas.entity.Prueba;
import com.example.backpruebas.repository.PruebaRepository;
import com.example.backpruebas.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PruebaServiceImpl implements PruebaService {
    @Autowired
    private PruebaRepository pruebaRepository;

    @Override
    public List<Prueba> findPruebaAll() {
        return (List<Prueba>) pruebaRepository.findAll();
    }

    @Override
    public Prueba getPrueba(UUID id) {
        return pruebaRepository.findById(id).orElse(null);
    }

    @Override
    public Prueba createPrueba(Prueba prueba) {
        //Aquí irian las validaciones al crear el prueba de ser necesario
        return pruebaRepository.save(prueba);
    }

    @Override
    public Prueba updatePrueba(Prueba prueba) {
        Prueba pruebaDB = this.getPrueba(prueba.getId());
        if (pruebaDB == null) {
            return null;
        }
        //Actualizamos los valores del prueba:
        pruebaDB.setIdtipoprueba(prueba.getIdtipoprueba());
        pruebaDB.setIdpaciente(prueba.getIdpaciente());
        pruebaDB.setFecprueba(prueba.getFecprueba());
        pruebaDB.setFecresultado(prueba.getFecresultado());
        pruebaDB.setResultado(prueba.getResultado());
        pruebaDB.setObservacion(prueba.getObservacion());
        return pruebaRepository.save(prueba);
    }

    @Override
    public String deletePrueba(UUID id) {
        Prueba pruebaDB = this.getPrueba(id);
        if (pruebaDB == null) {
            return null;
        }
        try{
            pruebaRepository.delete(pruebaDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }
        return "ELIMINADO CON EXITO";
    }
}

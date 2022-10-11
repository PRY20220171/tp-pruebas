package com.example.backpruebas.service.impl;

import com.example.backpruebas.entity.Prueba;
import com.example.backpruebas.entity.TipoPrueba;
import com.example.backpruebas.repository.PruebaRepository;
import com.example.backpruebas.repository.TipoPruebaRepository;
import com.example.backpruebas.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PruebaServiceImpl implements PruebaService {
    @Autowired
    private PruebaRepository pruebaRepository;

    @Autowired
    private TipoPruebaRepository tipoPruebaRepository;

    @Override
    public List<Prueba> findPruebaAll() {
        return (List<Prueba>) pruebaRepository.findAll();
    }

    @Override
    public Prueba getPrueba(UUID id) {
        Prueba prueba = pruebaRepository.findById(id).orElse(null);

        if(prueba != null){
            prueba.setTipoPrueba(tipoPruebaRepository.findById(prueba.getIdtipoprueba()).orElse(null));
        }

        return prueba;

    }

    @Override
    public Prueba createPrueba(Prueba prueba) {
        //Aquí irian las validaciones al crear el prueba de ser necesario

        prueba.setIdtipoprueba(tipoPruebaRepository.save(prueba.getTipoPrueba()).getId());

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

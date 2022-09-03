package com.example.backmedidas.service.impl;

import com.example.backmedidas.entity.Medida;
import com.example.backmedidas.repository.MedidaRepository;
import com.example.backmedidas.service.MedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MedidaServiceImpl implements MedidaService {
    @Autowired
    private MedidaRepository medidaRepository;

    @Override
    public List<Medida> findMedidaAll() {
        return (List<Medida>) medidaRepository.findAll();
    }

    @Override
    public Medida getMedida(UUID id) {
        return medidaRepository.findById(id).orElse(null);
    }

    @Override
    public Medida createMedida(Medida medida) {
        //Aquí irian las validaciones al crear el medida de ser necesario
        return medidaRepository.save(medida);
    }

    @Override
    public Medida updateMedida(Medida medida) {
        Medida medidaDB = this.getMedida(medida.getId());
        if (medidaDB == null) {
            return null;
        }
        //Actualizamos los valores del medida:
        medidaDB.setFecregistro(medida.getFecregistro());
        medidaDB.setDescripcion(medida.getDescripcion());
        medidaDB.setEstado(medida.getEstado());
        medidaDB.setTipo(medida.getTipo());
        return medidaRepository.save(medida);
    }

    @Override
    public String deleteMedida(UUID id) {
        Medida medidaDB = this.getMedida(id);
        if (medidaDB == null) {
            return null;
        }
        try{
            medidaRepository.delete(medidaDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }
        return "ELIMINADO CON EXITO";
    }
/*
    @Override
    public Medida getByDni(Long dni) {
        return medidaRepository.findAllByTipoAndEstado("DNI", dni.toString());
    }

    @Override
    public Medida getByDocExtranjeria(Long estado) {
        return medidaRepository.findAllByTipoAndEstado("DOCUMENTO EXTRANJERIA", estado.toString());
    }
 */
}

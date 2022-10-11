package com.example.backpruebas.service.impl;

import com.example.backpruebas.entity.TipoPrueba;
import com.example.backpruebas.repository.CategoriaPruebaRepository;
import com.example.backpruebas.repository.MedidaRepository;
import com.example.backpruebas.repository.TipoPruebaRepository;
import com.example.backpruebas.service.TipoPruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoPruebaServiceImpl implements TipoPruebaService {
    @Autowired
    private TipoPruebaRepository tipoPruebaRepository;

    @Autowired
    private MedidaRepository medidaRepository;

    @Autowired
    private CategoriaPruebaRepository categoriaPruebaRepository;

    @Override
    public List<TipoPrueba> findTipoPruebaAll() {
        return (List<TipoPrueba>) tipoPruebaRepository.findAll();
    }

    @Override
    public TipoPrueba getTipoPrueba(UUID id) {
        TipoPrueba tipoPrueba = tipoPruebaRepository.findById(id).orElse(null);

        if(tipoPrueba != null){
            tipoPrueba.setMedida(medidaRepository.findById(tipoPrueba.getIdmedida()).orElse(null));
            tipoPrueba.setCategoriaPrueba(categoriaPruebaRepository.findById(tipoPrueba.getIdcategoriaprueba()).orElse(null));
        }

        return tipoPrueba;

    }

    @Override
    public TipoPrueba createTipoPrueba(TipoPrueba tipoPrueba) {
        //Aquí irian las validaciones al crear el tipoPrueba de ser necesario

        tipoPrueba.setIdmedida(medidaRepository.save(tipoPrueba.getMedida()).getId());
        tipoPrueba.setIdcategoriaprueba(categoriaPruebaRepository.save(tipoPrueba.getCategoriaPrueba()).getId());

        return tipoPruebaRepository.save(tipoPrueba);
    }

    @Override
    public TipoPrueba updateTipoPrueba(TipoPrueba tipoPrueba) {

        TipoPrueba tipoPruebaDB = this.getTipoPrueba(tipoPrueba.getId());
        if (tipoPruebaDB == null) {
            return null;
        }
        //Actualizamos los valores del tipoPrueba:
        //tipoPruebaDB.setFecregistro(tipoPrueba.getFecregistro());
        tipoPruebaDB.setNombre(tipoPrueba.getNombre());
        tipoPruebaDB.setDescripcion(tipoPrueba.getDescripcion());
        //tipoPruebaDB.setEstado(tipoPrueba.getEstado());
        //tipoPruebaDB.setTipo(tipoPrueba.getTipo());
        return tipoPruebaRepository.save(tipoPrueba);

    }

    @Override
    public String deleteTipoPrueba(UUID id) {
        TipoPrueba tipoPruebaDB = this.getTipoPrueba(id);
        if (tipoPruebaDB == null) {
            return null;
        }
        try{
            tipoPruebaRepository.delete(tipoPruebaDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }
        return "ELIMINADO CON EXITO";
    }
/*
    @Override
    public TipoPrueba getByDni(Long dni) {
        return tipoPruebaRepository.findAllByTipoAndEstado("DNI", dni.toString());
    }

    @Override
    public TipoPrueba getByDocExtranjeria(Long estado) {
        return tipoPruebaRepository.findAllByTipoAndEstado("DOCUMENTO EXTRANJERIA", estado.toString());
    }
 */
}

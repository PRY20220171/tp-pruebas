package com.example.backpruebas.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backpruebas.entity.CategoriaPrueba;
import com.example.backpruebas.service.CategoriaPruebaService;
import com.example.backpruebas.service.ProducerService;
import com.example.backpruebas.util.ErrorMessage;
import com.example.backpruebas.util.Message;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categoriapruebas")
public class CategoriaPruebaController {
    @Autowired
    private CategoriaPruebaService categoriapruebaService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos de la categoria de la prueba del paciente por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response= CategoriaPrueba.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<CategoriaPrueba>> listCategoriaPrueba(@RequestParam(name="idcategoriaprueba",required = false) String idCategoriaPrueba){
        List<CategoriaPrueba> categoriapruebas=new ArrayList<>();
        if(null==idCategoriaPrueba){
            categoriapruebas=categoriapruebaService.findCategoriaPruebaAll();
            if(categoriapruebas.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            categoriapruebas = Collections.singletonList(categoriapruebaService.getCategoriaPrueba(UUID.fromString(idCategoriaPrueba)));
        }
        return ResponseEntity.ok(categoriapruebas);
    }

    @PostMapping
    public ResponseEntity<CategoriaPrueba> createCategoriaPrueba(@Valid @RequestBody CategoriaPrueba categoriaprueba, BindingResult result){
        categoriaprueba.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        CategoriaPrueba categoriapruebacreate = categoriapruebaService.createCategoriaPrueba(categoriaprueba);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriapruebacreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaPrueba> updateCategoriaPrueba(@PathVariable("id") String id, @RequestBody CategoriaPrueba categoriaprueba){
        categoriaprueba.setId(UUID.fromString(id));
        CategoriaPrueba categoriapruebaDB=categoriapruebaService.updateCategoriaPrueba(categoriaprueba);
        if(categoriapruebaDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriapruebaDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoriaPrueba(@PathVariable("id") String id){
        String categoriapruebaDelete=categoriapruebaService.deleteCategoriaPrueba(UUID.fromString(id));
        if(categoriapruebaDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriapruebaDelete);
    }
/*
    @Autowired
    ProducerService rabbitMQSender;
    @GetMapping(value = "/test/{id}")
    public ResponseEntity<CategoriaPrueba> producer(@PathVariable("id") String id) {
        CategoriaPrueba paciente = rabbitMQSender.sendMsg(id);
        if(paciente==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }
*/


}

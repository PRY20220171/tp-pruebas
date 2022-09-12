package com.example.backpruebas.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backpruebas.entity.TipoPrueba;
import com.example.backpruebas.service.ProducerService;
import com.example.backpruebas.service.TipoPruebaService;
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
@RequestMapping("/tipospruebas")
public class TipoPruebaController {
    @Autowired
    private TipoPruebaService tipopruebaService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos del tipo de prueba por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response= TipoPrueba.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<TipoPrueba>> listTipoprueba(@RequestParam(name="idtipoprueba",required = false) String idTipoprueba){
        List<TipoPrueba> tipopruebas=new ArrayList<>();
        if(null==idTipoprueba){
            tipopruebas=tipopruebaService.findTipoPruebaAll();
            if(tipopruebas.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            tipopruebas = Collections.singletonList(tipopruebaService.getTipoPrueba(UUID.fromString(idTipoprueba)));
        }
        return ResponseEntity.ok(tipopruebas);
    }

    @PostMapping
    public ResponseEntity<TipoPrueba> createTipoprueba(@Valid @RequestBody TipoPrueba tipoprueba, BindingResult result){
        tipoprueba.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        TipoPrueba tipopruebacreate = tipopruebaService.createTipoPrueba(tipoprueba);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipopruebacreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoPrueba> updateTipoprueba(@PathVariable("id") String id, @RequestBody TipoPrueba tipoprueba){
        tipoprueba.setId(UUID.fromString(id));
        TipoPrueba tipopruebaDB=tipopruebaService.updateTipoPrueba(tipoprueba);
        if(tipopruebaDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipopruebaDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTipoprueba(@PathVariable("id") String id){
        String tipopruebaDelete=tipopruebaService.deleteTipoPrueba(UUID.fromString(id));
        if(tipopruebaDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipopruebaDelete);
    }
/*
    @Autowired
    ProducerService rabbitMQSender;

    @GetMapping(value = "/test")
    public String producer() {
        rabbitMQSender.sendMsg(new TipoPrueba());
        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }
*/


}

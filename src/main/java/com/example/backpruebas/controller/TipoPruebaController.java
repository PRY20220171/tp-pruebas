package com.example.backtipopruebas.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backtipopruebas.entity.Tipoprueba;
import com.example.backtipopruebas.service.TipopruebaService;
import com.example.backtipopruebas.service.ProducerService;
import com.example.backtipopruebas.util.ErrorMessage;
import com.example.backtipopruebas.util.Message;
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
public class TipopruebaController {
    @Autowired
    private TipopruebaService tipopruebaService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos del tipo de prueba por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=Tipoprueba.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<Tipoprueba>> listTipoprueba(@RequestParam(name="idtipoprueba",required = false) String idTipoprueba){
        List<Tipoprueba> tipopruebas=new ArrayList<>();
        if(null==idTipoprueba){
            tipopruebas=tipopruebaService.findTipopruebaAll();
            if(tipopruebas.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            tipopruebas = Collections.singletonList(tipopruebaService.getTipoprueba(UUID.fromString(idTipoprueba)));
        }
        return ResponseEntity.ok(tipopruebas);
    }

    @PostMapping
    public ResponseEntity<Tipoprueba> createTipoprueba(@Valid @RequestBody Tipoprueba tipoprueba, BindingResult result){
        tipoprueba.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        Tipoprueba tipopruebacreate = tipopruebaService.createTipoprueba(tipoprueba);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipopruebacreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipoprueba> updateTipoprueba(@PathVariable("id") String id, @RequestBody Tipoprueba tipoprueba){
        tipoprueba.setId(UUID.fromString(id));
        Tipoprueba tipopruebaDB=tipopruebaService.updateTipoprueba(tipoprueba);
        if(tipopruebaDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipopruebaDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTipoprueba(@PathVariable("id") String id){
        String tipopruebaDelete=tipopruebaService.deleteTipoprueba(UUID.fromString(id));
        if(tipopruebaDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipopruebaDelete);
    }

    @Autowired
    ProducerService rabbitMQSender;

    @GetMapping(value = "/test")
    public String producer() {
        rabbitMQSender.sendMsg(new Tipoprueba());
        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }



}

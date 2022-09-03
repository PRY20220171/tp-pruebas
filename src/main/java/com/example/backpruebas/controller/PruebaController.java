package com.example.backpruebas.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backpruebas.entity.Prueba;
import com.example.backpruebas.service.PruebaService;
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
@RequestMapping("/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos de la prueba del paciente por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=Prueba.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<Prueba>> listPrueba(@RequestParam(name="idprueba",required = false) String idPrueba){
        List<Prueba> pruebas=new ArrayList<>();
        if(null==idPrueba){
            pruebas=pruebaService.findPruebaAll();
            if(pruebas.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            pruebas = Collections.singletonList(pruebaService.getPrueba(UUID.fromString(idPrueba)));
        }
        return ResponseEntity.ok(pruebas);
    }

    @PostMapping
    public ResponseEntity<Prueba> createPrueba(@Valid @RequestBody Prueba prueba, BindingResult result){
        prueba.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        Prueba pruebacreate = pruebaService.createPrueba(prueba);
        return ResponseEntity.status(HttpStatus.CREATED).body(pruebacreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prueba> updatePrueba(@PathVariable("id") String id, @RequestBody Prueba prueba){
        prueba.setId(UUID.fromString(id));
        Prueba pruebaDB=pruebaService.updatePrueba(prueba);
        if(pruebaDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pruebaDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrueba(@PathVariable("id") String id){
        String pruebaDelete=pruebaService.deletePrueba(UUID.fromString(id));
        if(pruebaDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pruebaDelete);
    }

    @Autowired
    ProducerService rabbitMQSender;

    @GetMapping(value = "/test")
    public String producer() {
        rabbitMQSender.sendMsg(new Prueba());
        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }



}

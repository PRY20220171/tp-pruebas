package com.example.backpruebas.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backpruebas.entity.Medida;
import com.example.backpruebas.service.MedidaService;
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
@RequestMapping("/medidas")
public class MedidaController {
    @Autowired
    private MedidaService medidaService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos de la medida de la prueba por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=Medida.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<Medida>> listMedida(@RequestParam(name="idmedida",required = false) String idMedida){
        List<Medida> medidas=new ArrayList<>();
        if(null==idMedida){
            medidas=medidaService.findMedidaAll();
            if(medidas.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            medidas = Collections.singletonList(medidaService.getMedida(UUID.fromString(idMedida)));
        }
        return ResponseEntity.ok(medidas);
    }

    @PostMapping
    public ResponseEntity<Medida> createMedida(@Valid @RequestBody Medida medida, BindingResult result){
        medida.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        Medida medidacreate = medidaService.createMedida(medida);
        return ResponseEntity.status(HttpStatus.CREATED).body(medidacreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medida> updateMedida(@PathVariable("id") String id, @RequestBody Medida medida){
        medida.setId(UUID.fromString(id));
        Medida medidaDB=medidaService.updateMedida(medida);
        if(medidaDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medidaDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedida(@PathVariable("id") String id){
        String medidaDelete=medidaService.deleteMedida(UUID.fromString(id));
        if(medidaDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medidaDelete);
    }
    /*

    @Autowired
    ProducerService rabbitMQSender;

    @GetMapping(value = "/test")
    public String producer() {
        rabbitMQSender.sendMsg(new Medida());
        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }
    */



}

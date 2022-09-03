package com.example.backpruebas.repository;

import com.example.backpruebas.entity.CategoriaPrueba;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//import org.springframework.data.cassandra.repository.Query;
//import org.springframework.data.repository.Repository;
@Repository
public interface CategoriaPruebaRepository extends CrudRepository<CategoriaPrueba, UUID> {
    //CategoriaPrueba findOneById(String id);
    //CategoriaPrueba findAllByDoctipoAndDocnum(String doctipo, String docnum);
    //@Query("SELECT * from pizza_orders WHERE orderdate = ?0 and zoneid = ?1 ALLOW FILTERING")
    //Order findOrderByOrderDateAndZoneId(LocalDate orderDate, ZoneId zoneId);
}

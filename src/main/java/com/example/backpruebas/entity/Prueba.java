package com.example.backpruebas.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

//import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Prueba.class)
public class Prueba  implements Serializable {
    
    @ApiModelProperty(value="ID de la prueba medica brindada al paciente", dataType="uuid", position=1)
    @Id
    @Column("idprueba")
    @CassandraType(type = CassandraType.Name.UUID)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKey
    private UUID id;

    @ApiModelProperty(value="Es el ID del tipo de prueba medica", dataType="uuid", position=2)
    @NotNull(message = "El idtipoprueba no puede ser nulo")
    @Column("idtipoprueba")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID idtipoprueba;
    @Transient
    private TipoPrueba tipoPrueba;

    @ApiModelProperty(value="Es el ID del paciente", dataType="uuid", position=3)
    @NotNull(message = "La idpaciente no puede ser nulo")
    @Column("idpaciente")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID idpaciente;

    @ApiModelProperty(value="Es la fecha en la que se obtiene el resultado de la prueba", dataType="datetime", position=4)
    @NotNull(message = "La fecresultado no puede ser nulo")
    @Column( "fecresultado")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Date fecresultado;

    @ApiModelProperty(value="Es la fecha en la que se realiza la prueba", dataType="datetime", position=5)
    @NotNull(message = "El fecprueba no puede ser nulo")
    @Column("fecprueba")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Date fecprueba;

    @ApiModelProperty(value="Es el resultado de la prueba", dataType="text", position=6)
    @NotEmpty(message = "La resultado no puede ser vacio")
    @NotNull(message = "La resultado no puede ser nulo")
    @Column("resultado")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String resultado;

    @ApiModelProperty(value="Es la obervacion de la prueba", dataType="text", position=3)
    @NotNull(message = "La observacion no puede ser nulo")
    @Column("observacion")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String observacion;

}

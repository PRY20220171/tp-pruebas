package com.example.backpruebas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paciente {

    private UUID id;

    private String nombres;

    private String apellidos;

    private String docnum;

    private String doctipo;

    private String sexo;

    private String gruposang;

    private String rh;

    private String telefono;

    private String gradoinstruccion;

    private String ocupacion;

    private String estadocivil;

    private LocalDate fecnac;

    private UUID idlugarnac;

    private UUID iddomicilioact;

    private UUID idantecedenteperi;

    private UUID idantecedentepsico;

    private UUID idantecedentefam;

    private UUID idantecedentepato;

}

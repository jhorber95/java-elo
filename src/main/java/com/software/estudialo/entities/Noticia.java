package com.software.estudialo.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "noticia")
public class Noticia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen_principal")
    private String imagenPrincipal;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @NotNull
    private Long autor;

}

package com.software.estudialo.entities;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "test_history")
public class TestHistory {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "id_fase_item")
    private Integer idFaseItem;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "created_at")
    private Timestamp created_at;
}

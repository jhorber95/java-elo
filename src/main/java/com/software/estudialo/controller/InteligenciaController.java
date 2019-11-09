package com.software.estudialo.controller;


import com.software.estudialo.entities.Inteligencia;
import com.software.estudialo.service.InteligenciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InteligenciaController {

    private final Logger log = LoggerFactory.getLogger(InteligenciaController.class);

    private final InteligenciaService service;

    public InteligenciaController(InteligenciaService service) {
        this.service = service;
    }


    @GetMapping("/inteligencias")
    public ResponseEntity<List<Inteligencia>> getAll() {
        List<Inteligencia> list = service.getAllIntelligences();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/inteligencias/{id}")
    public ResponseEntity<Inteligencia> getById(@PathVariable int id) {
        log.debug("REST request to get Inteligencia: {}", id);
        Inteligencia entity = service.findOne(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

}

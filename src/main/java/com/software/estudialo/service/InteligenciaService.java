package com.software.estudialo.service;

import com.software.estudialo.entities.Inteligencia;
import com.software.estudialo.repository.InteligenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteligenciaService {

    private final InteligenceRepository repository;

    public InteligenciaService(InteligenceRepository repository) {
        this.repository = repository;
    }

    public List<Inteligencia> getAllIntelligences(){
        return repository.findAllByOrderByIdAsc();
    }

    public Inteligencia findOne(int id) {
        return repository.findOne(id);
    }
}

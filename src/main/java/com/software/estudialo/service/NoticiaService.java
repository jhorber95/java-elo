package com.software.estudialo.service;

import com.software.estudialo.entities.Noticia;
import com.software.estudialo.repository.NoticiaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticiaService {

    private final NoticiaRepository repository;

    public NoticiaService(NoticiaRepository repository) {
        this.repository = repository;
    }

    public Noticia save(Noticia testHistory) {
        return repository.save(testHistory);
    }

    public Page<Noticia> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Noticia findOne(Long id) {
        return repository.findOne(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}

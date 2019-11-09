package com.software.estudialo.repository;

import com.software.estudialo.entities.Inteligencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteligenceRepository extends JpaRepository<Inteligencia, Integer> {

    List<Inteligencia> findAllByOrderByIdAsc();
}

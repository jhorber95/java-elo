package com.software.estudialo.repository;

import com.software.estudialo.entities.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
}

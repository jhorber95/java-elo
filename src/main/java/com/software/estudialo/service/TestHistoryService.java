package com.software.estudialo.service;

import com.software.estudialo.entities.TestHistory;
import com.software.estudialo.repository.TestHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestHistoryService {

    private final TestHistoryRepository testHistoryRepository;

    public TestHistoryService(TestHistoryRepository testHistoryRepository) {
        this.testHistoryRepository = testHistoryRepository;
    }

    public TestHistory save(TestHistory testHistory) {
        return testHistoryRepository.save(testHistory);
    }

    public Page<TestHistory> findAll(Pageable pageable) {
        return testHistoryRepository.findAll(pageable);
    }

    public TestHistory findOne(Long id) {
        return testHistoryRepository.findOne(id);
    }

    public void delete(Long id) {
        testHistoryRepository.delete(id);
    }
}

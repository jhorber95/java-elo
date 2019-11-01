package com.software.estudialo.service;

import com.software.estudialo.entities.Fase;
import com.software.estudialo.entities.FaseItem;
import com.software.estudialo.entities.TestHistory;
import com.software.estudialo.entities.UserTest;
import com.software.estudialo.repository.TestHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TestHistoryService {
    private static Logger log = LoggerFactory.getLogger(TestHistoryService.class);

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

    public List<TestHistory> setupUserData(UserTest userTest) {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        List<TestHistory> list = new ArrayList<>();
        for (Fase fase : userTest.getTest().getFases()) {
            for (FaseItem faseItem : fase.getItems()) {
                if (faseItem.isSeleccionado()) {
                    TestHistory testHistory = new TestHistory();
                    testHistory.setIdUser(userTest.getUser().getId());
                    testHistory.setIdFaseItem(faseItem.getId());
                    testHistory.setCreated_at(ts);

                    save(testHistory);
                    list.add(save(testHistory));

                    log.debug("Save test user {}", testHistory);

                }
            }
        }
        return list;
    }
}

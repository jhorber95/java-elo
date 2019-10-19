package com.software.estudialo.controller;

import com.software.estudialo.controller.error.BadRequestAlertException;
import com.software.estudialo.entities.TestHistory;
import com.software.estudialo.service.TestHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class TestHistoryController {

    private final TestHistoryService service;

    private final Logger log = LoggerFactory.getLogger(TestHistoryController.class);
    private static final String ENTITY_NAME = "test history";


    public TestHistoryController(TestHistoryService service) {
        this.service = service;
    }

    @PostMapping("/test-history")
    public ResponseEntity<TestHistory> createTestHistory(@Valid @RequestBody TestHistory testHistory) throws URISyntaxException {
        log.debug("REST request to save TestHistory : {}", testHistory);
        if (testHistory.getId() != null) {
            throw new BadRequestAlertException("A new department cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestHistory result = service.save(testHistory);
        return ResponseEntity.created(new URI("/api/test-history/" + result.getId())).body(result);
    }

    @PutMapping("/test-history")
    public ResponseEntity<TestHistory> updateTestHistory(@Valid @RequestBody TestHistory testHistory) throws URISyntaxException {
        log.debug("REST request to update TestHistory : {}", testHistory);
        if (testHistory.getId() != null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idnull");
        }
        TestHistory result = service.save(testHistory);
        return ResponseEntity.created(new URI("/api/test-history/" + result.getId())).body(result);
    }

    @GetMapping("/test-history")
    public ResponseEntity<Page<TestHistory>> getAllDepartments(Pageable pageable) {
        log.debug("REST request to get a page of TestHistory");
        Page<TestHistory> page = service.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/test-history/{id}")
    public ResponseEntity<TestHistory> getTestHistory(@PathVariable Long id) {
        log.debug("REST request to get TestHistory : {}", id);
        TestHistory entity = service.findOne(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteTestHistory(@PathVariable Long id) {
        log.debug("REST request to delete TestHistory : {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

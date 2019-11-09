package com.software.estudialo.controller;

import com.software.estudialo.controller.error.BadRequestAlertException;
import com.software.estudialo.entities.Noticia;
import com.software.estudialo.service.NoticiaService;
import com.software.estudialo.util.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class NoticiaController {


    private final NoticiaService service;

    private final Logger log = LoggerFactory.getLogger(NoticiaController.class);
    private static final String ENTITY_NAME = "test history";

    public NoticiaController(NoticiaService service) {
        this.service = service;
    }

    @PostMapping("/noticia")
    public ResponseEntity<Noticia> createNoticia(@Valid @RequestBody Noticia noticia) throws URISyntaxException {
        log.debug("REST request to save Noticia : {}", noticia);
        if (noticia.getId() != null) {
            throw new BadRequestAlertException("A new department cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Noticia result = service.save(noticia);
        return ResponseEntity.created(new URI("/api/noticia/" + result.getId())).body(result);
    }

    @PutMapping("/noticia")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Noticia> updateNoticia(@Valid @RequestBody Noticia noticia) throws URISyntaxException {
        log.debug("REST request to update Noticia : {}", noticia);
        if (noticia.getId() != null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idnull");
        }
        Noticia result = service.save(noticia);
        return ResponseEntity.created(new URI("/api/noticia/" + result.getId())).body(result);
    }

    @GetMapping("/noticia")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Page<Noticia>> getAllDepartments(Pageable pageable) {
        log.debug("REST request to get a page of Noticia");
        Page<Noticia> page = service.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/noticia/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Noticia> getNoticia(@PathVariable Long id) {
        log.debug("REST request to get Noticia : {}", id);
        Noticia entity = service.findOne(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/noticia/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteNoticia(@PathVariable Long id) {
        log.debug("REST request to delete Noticia : {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

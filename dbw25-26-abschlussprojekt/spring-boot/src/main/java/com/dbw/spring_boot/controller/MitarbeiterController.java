package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Mitarbeiter;
import com.dbw.spring_boot.respositories.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mitarbeiter")
public class MitarbeiterController {

    private final MitarbeiterRepository repository;

    @Autowired
    public MitarbeiterController(MitarbeiterRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Mitarbeiter>> getAllMitarbeiter() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<Mitarbeiter> getMitarbeiterById(@RequestParam Integer id) {
        Mitarbeiter mitarbeiter = repository.findById(id);
        if (mitarbeiter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mitarbeiter);
    }

    @PostMapping
    public ResponseEntity<Mitarbeiter> create(@RequestBody Mitarbeiter mitarbeiter) {
        try {
            Mitarbeiter created = repository.save(mitarbeiter);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        Mitarbeiter mitarbeiter = repository.findById(id);
        if (mitarbeiter == null) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Kunde;
import com.dbw.spring_boot.respositories.KundeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kunden")
public class KundeController {

    private final KundeRepository repository;

    @Autowired
    public KundeController(KundeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Kunde>> getAllKunden() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<Kunde> getKundeById(@RequestParam Integer id) {
        Kunde kunde = repository.findById(id);
        if (kunde == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(kunde);
    }

    @GetMapping(params = "email")
    public ResponseEntity<Kunde> getKundeByEmail(@RequestParam String email) {
        Kunde kunde = repository.findByEmail(email);
        if (kunde == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(kunde);
    }

    @PostMapping
    public ResponseEntity<Kunde> create(@RequestBody Kunde kunde) {
        try {
            Kunde created = repository.save(kunde);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @PutMapping(params = "id")
    public ResponseEntity<Void> update(@RequestParam Integer id, @RequestBody Kunde kunde) {
        Kunde existing = repository.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        kunde.setKundeId(id);
        repository.update(kunde);
        return ResponseEntity.noContent().build();
    }
}

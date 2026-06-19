package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Bestellung;
import com.dbw.spring_boot.respositories.BestellungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bestellungen")
public class BestellungController {

    private final BestellungRepository repository;

    @Autowired
    public BestellungController(BestellungRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Bestellung>> getAllBestellungen() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<Bestellung> getBestellungById(@RequestParam Integer id) {
        Bestellung bestellung = repository.findById(id);
        if (bestellung == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bestellung);
    }

    @PostMapping
    public ResponseEntity<Bestellung> create(@RequestBody Bestellung bestellung) {
        try {
            Bestellung created = repository.save(bestellung);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @DeleteMapping(params = "id")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        Bestellung bestellung = repository.findById(id);
        if (bestellung == null) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

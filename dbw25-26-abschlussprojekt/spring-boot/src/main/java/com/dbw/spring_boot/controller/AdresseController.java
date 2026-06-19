package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Adresse;
import com.dbw.spring_boot.respositories.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adressen")
public class AdresseController {

    private final AdresseRepository repository;

    @Autowired
    public AdresseController(AdresseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Adresse>> getAllAdressen() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<Adresse> getAdresseById(@RequestParam Integer id) {
        Adresse adresse = repository.findById(id);
        if (adresse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adresse);
    }

    @PostMapping
    public ResponseEntity<Adresse> create(@RequestBody Adresse adresse) {
        try {
            Adresse created = repository.save(adresse);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @PutMapping(params = "id")
    public ResponseEntity<Void> update(@RequestParam Integer id, @RequestBody Adresse adresse) {
        Adresse existing = repository.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        adresse.setAdresseId(id);
        repository.update(adresse);
        return ResponseEntity.noContent().build();
    }
}

package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Produkt;
import com.dbw.spring_boot.respositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produkte")
public class ProduktController {

    private final ProduktRepository repository;

    @Autowired
    public ProduktController(ProduktRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Produkt>> getAllProdukte() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = "sku")
    public ResponseEntity<Produkt> getProduktBySku(@RequestParam String sku) {
        Produkt produkt = repository.findBySku(sku);
        if (produkt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produkt);
    }

    @PostMapping
    public ResponseEntity<Produkt> create(@RequestBody Produkt produkt) {
        try {
            Produkt created = repository.save(produkt);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @DeleteMapping(params = "sku")
    public ResponseEntity<Void> delete(@RequestParam String sku) {
        Produkt produkt = repository.findBySku(sku);
        if (produkt == null) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteBySku(sku);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(params = "sku")
    public ResponseEntity<Void> updateLagerbestand(
            @RequestParam String sku,
            @RequestBody Map<String, Integer> update) {
        Produkt produkt = repository.findBySku(sku);
        if (produkt == null) {
            return ResponseEntity.notFound().build();
        }
        repository.updateLagerbestand(sku, update.get("lagerbestand"));
        return ResponseEntity.noContent().build();
    }
}

package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Bestellposition;
import com.dbw.spring_boot.respositories.BestellpositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bestellpositionen")
public class BestellpositionController {

    private final BestellpositionRepository repository;

    @Autowired
    public BestellpositionController(BestellpositionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Bestellposition>> getAllBestellpositionen() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<Bestellposition> getBestellpositionById(@RequestParam Integer id) {
        Bestellposition position = repository.findById(id);
        if (position == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(position);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Bestellposition position) {
        try {
            Bestellposition created = repository.save(position);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Constraint-Verletzung: " + e.getMessage());
        } catch (org.springframework.dao.DataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datenbankfehler: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Fehler beim Erstellen: " + e.getMessage());
        }
    }



    @DeleteMapping(params = "id")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        Bestellposition position = repository.findById(id);
        if (position == null) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

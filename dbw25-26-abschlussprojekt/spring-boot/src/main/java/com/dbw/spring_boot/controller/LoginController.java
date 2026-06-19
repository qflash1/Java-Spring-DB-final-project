package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Kunde;
import com.dbw.spring_boot.model.Mitarbeiter;
import com.dbw.spring_boot.respositories.KundeRepository;
import com.dbw.spring_boot.respositories.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final MitarbeiterRepository mitarbeiterRepository;
    private final KundeRepository kundeRepository;

    @Autowired
    public LoginController(MitarbeiterRepository mitarbeiterRepository, KundeRepository kundeRepository) {
        this.mitarbeiterRepository = mitarbeiterRepository;
        this.kundeRepository = kundeRepository;
    }

    @PostMapping("/mitarbeiter")
    public ResponseEntity<Mitarbeiter> loginMitarbeiter(@RequestBody Mitarbeiter loginData) {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findByPersonalNrAndPasswort(
                loginData.getPersonalNr(),
                loginData.getPasswort()
        );

        if (mitarbeiter != null) {
            mitarbeiter.setPasswort(null);
            return ResponseEntity.ok(mitarbeiter);
        }

        Mitarbeiter emptyResponse = new Mitarbeiter(null, null, null, null, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyResponse);
    }

    @PostMapping("/kunde")
    public ResponseEntity<Kunde> loginKunde(@RequestBody Kunde loginData) {
        Kunde kunde = kundeRepository.findByEmailAndPasswort(
                loginData.getEmail(),
                loginData.getPasswort()
        );

        if (kunde != null) {
            kunde.setPasswort(null);
            kunde.setAdressen(new ArrayList<>());
            return ResponseEntity.ok(kunde);
        }

        Kunde emptyResponse = new Kunde(null, null, null, null, null, new ArrayList<>());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyResponse);
    }
}

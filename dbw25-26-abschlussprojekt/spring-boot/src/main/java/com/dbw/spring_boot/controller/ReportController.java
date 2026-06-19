package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.KundeSummeAnzahlBestellungen;
import com.dbw.spring_boot.model.MitarbeiterBestellstatusUebersicht;
import com.dbw.spring_boot.model.MitarbeiterUebersicht;
import com.dbw.spring_boot.model.ProduktVerkaufszahlen;
import com.dbw.spring_boot.respositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/kunde/summe-anzahl-bestellungen")
    public ResponseEntity<List<KundeSummeAnzahlBestellungen>> getKundeSummeAnzahlBestellungen() {
        return ResponseEntity.ok(reportRepository.getKundeSummeAnzahlBestellungen());
    }

    @GetMapping("/produkt/verkaufszahlen")
    public ResponseEntity<List<ProduktVerkaufszahlen>> getProduktVerkaufszahlen() {
        return ResponseEntity.ok(reportRepository.getProduktVerkaufszahlen());
    }

    @GetMapping("/mitarbeiter/uebersicht")
    public ResponseEntity<List<MitarbeiterUebersicht>> getMitarbeiterUebersicht() {
        return ResponseEntity.ok(reportRepository.getMitarbeiterUebersicht());
    }

    @GetMapping("/mitarbeiter/bestellstatus-uebersicht")
    public ResponseEntity<List<MitarbeiterBestellstatusUebersicht>> getMitarbeiterBestellstatusUebersicht() {
        return ResponseEntity.ok(reportRepository.getMitarbeiterBestellstatusUebersicht());
    }

}

package com.dbw.spring_boot.respositories;

import com.dbw.spring_boot.model.KundeSummeAnzahlBestellungen;
import com.dbw.spring_boot.model.MitarbeiterBestellstatusUebersicht;
import com.dbw.spring_boot.model.MitarbeiterUebersicht;
import com.dbw.spring_boot.model.ProduktVerkaufszahlen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<KundeSummeAnzahlBestellungen> getKundeSummeAnzahlBestellungen() {
        String sql = "SELECT * FROM v_kunde_summe_anzahl_bestellungen";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            KundeSummeAnzahlBestellungen result = new KundeSummeAnzahlBestellungen();
            result.setKundeId(rs.getInt("kunde_id"));
            result.setEmail(rs.getString("email"));
            result.setAnzahlBestellungen(rs.getLong("anzahl_bestellungen"));
            result.setGesamtsumme(rs.getDouble("gesamtsumme"));
            return result;
        });
    }

    public List<ProduktVerkaufszahlen> getProduktVerkaufszahlen() {
        String sql = "SELECT * FROM v_produkt_verkaufszahlen";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProduktVerkaufszahlen result = new ProduktVerkaufszahlen();
            result.setSku(rs.getString("sku"));
            result.setName(rs.getString("name"));
            result.setGesamtVerkaufteMenge(rs.getLong("gesamt_verkaufte_menge"));
            result.setUmsatz(rs.getDouble("umsatz"));
            result.setAnzahlBestellungen(rs.getLong("anzahl_bestellungen"));
            return result;
        });
    }

    public List<MitarbeiterUebersicht> getMitarbeiterUebersicht() {
        String sql = "SELECT * FROM v_mitarbeiter_uebersicht";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MitarbeiterUebersicht result = new MitarbeiterUebersicht();
            result.setPersonalNr(rs.getInt("personal_nr"));
            result.setAnzahlVerwalteterBestellungen(rs.getLong("anzahl_verwalteter_bestellungen"));
            result.setAnzahlAngelegterProdukte(rs.getLong("anzahl_angelegter_produkte"));
            return result;
        });
    }

    public List<MitarbeiterBestellstatusUebersicht> getMitarbeiterBestellstatusUebersicht() {
        String sql = "SELECT * FROM v_mitarbeiter_bestellstatus_uebersicht";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MitarbeiterBestellstatusUebersicht result = new MitarbeiterBestellstatusUebersicht();
            result.setPersonalNr(rs.getInt("personal_nr"));
            result.setStatus(rs.getString("status"));
            result.setAnzahlBestellungen(rs.getLong("anzahl_bestellungen"));
            return result;
        });
    }


}
package com.dbw.spring_boot.model;

import java.time.ZonedDateTime;
import java.util.List;

public class Bestellung {
    private Integer bestellungId;
    private Integer kundeId;
    private Integer personalnr;
    private ZonedDateTime datum;
    private String status;
    private List<BestellpositionMitProdukt> positionen;

    public Bestellung() {}

    public Bestellung(Integer bestellungId, Integer kundeId, Integer personalnr, ZonedDateTime datum, String status, List<BestellpositionMitProdukt> positionen) {
        this.bestellungId = bestellungId;
        this.kundeId = kundeId;
        this.personalnr = personalnr;
        this.datum = datum;
        this.status = status;
        this.positionen = positionen;
    }

    public Integer getBestellungId() { return bestellungId; }
    public void setBestellungId(Integer bestellungId) { this.bestellungId = bestellungId; }

    public Integer getKundeId() { return kundeId; }
    public void setKundeId(Integer kundeId) { this.kundeId = kundeId; }

    public Integer getPersonalnr() { return personalnr; }
    public void setPersonalnr(Integer personalnr) { this.personalnr = personalnr; }

    public ZonedDateTime getDatum() { return datum; }
    public void setDatum(ZonedDateTime datum) { this.datum = datum; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<BestellpositionMitProdukt> getPositionen() { return positionen; }
    public void setPositionen(List<BestellpositionMitProdukt> positionen) { this.positionen = positionen; }
}

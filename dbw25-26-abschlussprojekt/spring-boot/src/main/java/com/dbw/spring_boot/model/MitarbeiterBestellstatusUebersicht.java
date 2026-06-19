package com.dbw.spring_boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MitarbeiterBestellstatusUebersicht {
    @JsonProperty("personalNr")
    private Integer personalNr;

    @JsonProperty("status")
    private String status;

    @JsonProperty("anzahlBestellungen")
    private Long anzahlBestellungen;

    public MitarbeiterBestellstatusUebersicht() {}

    public MitarbeiterBestellstatusUebersicht(Integer personalNr, String status, Long anzahlBestellungen) {
        this.personalNr = personalNr;
        this.status = status;
        this.anzahlBestellungen = anzahlBestellungen;
    }

    // Getter und Setter
    public Integer getPersonalNr() { return personalNr; }
    public void setPersonalNr(Integer personalNr) { this.personalNr = personalNr; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getAnzahlBestellungen() { return anzahlBestellungen; }
    public void setAnzahlBestellungen(Long anzahlBestellungen) { this.anzahlBestellungen = anzahlBestellungen; }
}

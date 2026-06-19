package com.dbw.spring_boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MitarbeiterUebersicht {
    @JsonProperty("personalNr")
    private Integer personalNr;

    @JsonProperty("anzahlVerwalteterBestellungen")
    private Long anzahlVerwalteterBestellungen;

    @JsonProperty("anzahlAngelegterProdukte")
    private Long anzahlAngelegterProdukte;

    public MitarbeiterUebersicht() {}

    public MitarbeiterUebersicht(Integer personalNr, Long anzahlVerwalteterBestellungen, Long anzahlAngelegterProdukte) {
        this.personalNr = personalNr;
        this.anzahlVerwalteterBestellungen = anzahlVerwalteterBestellungen;
        this.anzahlAngelegterProdukte = anzahlAngelegterProdukte;
    }

    // Getter und Setter
    public Integer getPersonalNr() { return personalNr; }
    public void setPersonalNr(Integer personalNr) { this.personalNr = personalNr; }

    public Long getAnzahlVerwalteterBestellungen() { return anzahlVerwalteterBestellungen; }
    public void setAnzahlVerwalteterBestellungen(Long anzahlVerwalteterBestellungen) { this.anzahlVerwalteterBestellungen = anzahlVerwalteterBestellungen; }

    public Long getAnzahlAngelegterProdukte() { return anzahlAngelegterProdukte; }
    public void setAnzahlAngelegterProdukte(Long anzahlAngelegterProdukte) { this.anzahlAngelegterProdukte = anzahlAngelegterProdukte; }
}

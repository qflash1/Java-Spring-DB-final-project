package com.dbw.spring_boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KundeSummeAnzahlBestellungen {
    @JsonProperty("kundeId")
    private Integer kundeId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("anzahlBestellungen")
    private Long anzahlBestellungen;

    @JsonProperty("gesamtsumme")
    private Double gesamtsumme;

    public KundeSummeAnzahlBestellungen() {}

    public KundeSummeAnzahlBestellungen(Integer kundeId, String email, Long anzahlBestellungen, Double gesamtsumme) {
        this.kundeId = kundeId;
        this.email = email;
        this.anzahlBestellungen = anzahlBestellungen;
        this.gesamtsumme = gesamtsumme;
    }

    // Getter und Setter
    public Integer getKundeId() { return kundeId; }
    public void setKundeId(Integer kundeId) { this.kundeId = kundeId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getAnzahlBestellungen() { return anzahlBestellungen; }
    public void setAnzahlBestellungen(Long anzahlBestellungen) { this.anzahlBestellungen = anzahlBestellungen; }

    public Double getGesamtsumme() { return gesamtsumme; }
    public void setGesamtsumme(Double gesamtsumme) { this.gesamtsumme = gesamtsumme; }
}



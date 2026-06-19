package com.dbw.spring_boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProduktVerkaufszahlen {
    @JsonProperty("sku")
    private String sku;

    @JsonProperty("name")
    private String name;

    @JsonProperty("gesamtVerkaufteMenge")
    private Long gesamtVerkaufteMenge;

    @JsonProperty("umsatz")
    private Double umsatz;

    @JsonProperty("anzahlBestellungen")
    private Long anzahlBestellungen;

    public ProduktVerkaufszahlen() {}

    public ProduktVerkaufszahlen(String sku, String name, Long gesamtVerkaufteMenge, Double umsatz, Long anzahlBestellungen) {
        this.sku = sku;
        this.name = name;
        this.gesamtVerkaufteMenge = gesamtVerkaufteMenge;
        this.umsatz = umsatz;
        this.anzahlBestellungen = anzahlBestellungen;
    }

    // Getter und Setter
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getGesamtVerkaufteMenge() { return gesamtVerkaufteMenge; }
    public void setGesamtVerkaufteMenge(Long gesamtVerkaufteMenge) { this.gesamtVerkaufteMenge = gesamtVerkaufteMenge; }

    public Double getUmsatz() { return umsatz; }
    public void setUmsatz(Double umsatz) { this.umsatz = umsatz; }

    public Long getAnzahlBestellungen() { return anzahlBestellungen; }
    public void setAnzahlBestellungen(Long anzahlBestellungen) { this.anzahlBestellungen = anzahlBestellungen; }
}

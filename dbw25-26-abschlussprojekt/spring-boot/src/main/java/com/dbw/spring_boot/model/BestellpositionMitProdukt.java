package com.dbw.spring_boot.model;

import java.math.BigDecimal;

public class BestellpositionMitProdukt {
    private Integer positionsId;
    private Integer bestellungId;
    private Produkt produkt;
    private Integer menge;
    private BigDecimal gesamtpreis;

    public BestellpositionMitProdukt() {}

    public BestellpositionMitProdukt(Integer positionsId, Integer bestellungId, Produkt produkt, Integer menge, BigDecimal gesamtpreis) {
        this.positionsId = positionsId;
        this.bestellungId = bestellungId;
        this.produkt = produkt;
        this.menge = menge;
        this.gesamtpreis = gesamtpreis;
    }

    public Integer getPositionsId() { return positionsId; }
    public void setPositionsId(Integer positionsId) { this.positionsId = positionsId; }

    public Integer getBestellungId() { return bestellungId; }
    public void setBestellungId(Integer bestellungId) { this.bestellungId = bestellungId; }

    public Produkt getProdukt() { return produkt; }
    public void setProdukt(Produkt produkt) { this.produkt = produkt; }

    public Integer getMenge() { return menge; }
    public void setMenge(Integer menge) { this.menge = menge; }

    public BigDecimal getGesamtpreis() { return gesamtpreis; }
    public void setGesamtpreis(BigDecimal gesamtpreis) { this.gesamtpreis = gesamtpreis; }
}

package com.dbw.spring_boot.model;

import java.math.BigDecimal;

public class Bestellposition {
    private Integer positionsId;
    private Integer bestellungId;
    private String produktSku;
    private Integer menge;
    private BigDecimal gesamtpreis;

    public Bestellposition() {}

    public Bestellposition(Integer positionsId, Integer bestellungId, String produktSku, Integer menge, BigDecimal gesamtpreis) {
        this.positionsId = positionsId;
        this.bestellungId = bestellungId;
        this.produktSku = produktSku;
        this.menge = menge;
        this.gesamtpreis = gesamtpreis;
    }

    public Integer getPositionsId() { return positionsId; }
    public void setPositionsId(Integer positionsId) { this.positionsId = positionsId; }

    public Integer getBestellungId() { return bestellungId; }
    public void setBestellungId(Integer bestellungId) { this.bestellungId = bestellungId; }

    public String getProduktSku() { return produktSku; }
    public void setProduktSku(String produktSku) { this.produktSku = produktSku; }

    public Integer getMenge() { return menge; }
    public void setMenge(Integer menge) { this.menge = menge; }

    public BigDecimal getGesamtpreis() { return gesamtpreis; }
    public void setGesamtpreis(BigDecimal gesamtpreis) { this.gesamtpreis = gesamtpreis; }
}

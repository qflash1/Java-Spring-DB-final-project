package com.dbw.spring_boot.model;

public class Adresse {
    private Integer adresseId;
    private Boolean aktiv;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String ort;
    private String land;

    public Adresse() {}

    public Adresse(Integer adresseId, Boolean aktiv, String strasse, String hausnummer, String plz, String ort, String land) {
        this.adresseId = adresseId;
        this.aktiv = aktiv;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.ort = ort;
        this.land = land;
    }

    public Integer getAdresseId() { return adresseId; }
    public void setAdresseId(Integer adresseId) { this.adresseId = adresseId; }

    public Boolean getAktiv() { return aktiv; }
    public void setAktiv(Boolean aktiv) { this.aktiv = aktiv; }

    public String getStrasse() { return strasse; }
    public void setStrasse(String strasse) { this.strasse = strasse; }

    public String getHausnummer() { return hausnummer; }
    public void setHausnummer(String hausnummer) { this.hausnummer = hausnummer; }

    public String getPlz() { return plz; }
    public void setPlz(String plz) { this.plz = plz; }

    public String getOrt() { return ort; }
    public void setOrt(String ort) { this.ort = ort; }

    public String getLand() { return land; }
    public void setLand(String land) { this.land = land; }
}

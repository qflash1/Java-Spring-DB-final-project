package com.dbw.spring_boot.model;

public class KundeHatAdresse {
    private Adresse adresse;
    private String typ;

    public KundeHatAdresse() {}

    public KundeHatAdresse(Adresse adresse, String typ) {
        this.adresse = adresse;
        this.typ = typ;
    }

    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }

    public String getTyp() { return typ; }
    public void setTyp(String typ) { this.typ = typ; }
}

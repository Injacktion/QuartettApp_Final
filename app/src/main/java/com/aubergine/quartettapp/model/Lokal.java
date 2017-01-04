package com.aubergine.quartettapp.model;

/**
 * Created by Daniel on 12.12.2016.
 */

public class Lokal {

    private final String id;
    private final String name;
    private final String art;
    private final String email;
    private final String webadresse;
    private final String strasse;
    private final String haltestelle;
    private final String guennstigstesBier;
    private final String schritte;
    private final String fassungsvermoegen;
    private final Specials special;
    private final String gruendung;
    private final String prozentWeiblichesPersonal;

    public Lokal(String id, String name, String art, String email, String webadresse, String strasse, String haltestelle, String guennstigstesBier, String schritte, String fassungsvermoegen, Specials special, String gruendung, String prozentWeiblichesPersonal) {
        this.id = id;
        this.name = name;
        this.art = art;
        this.email = email;
        this.webadresse = webadresse;
        this.strasse = strasse;
        this.haltestelle = haltestelle;
        this.guennstigstesBier = guennstigstesBier;
        this.schritte = schritte;
        this.fassungsvermoegen = fassungsvermoegen;
        this.special = special;
        this.gruendung = gruendung;
        this.prozentWeiblichesPersonal = prozentWeiblichesPersonal;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArt() {
        return art;
    }

    public String getEmail() {
        return email;
    }

    public String getWebadresse() {
        return webadresse;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getHaltestelle() {
        return haltestelle;
    }

    public String getGuennstigstesBier() {
        return guennstigstesBier;
    }

    public String getSchritte() {
        return schritte;
    }

    public String getFassungsvermoegen() {
        return fassungsvermoegen;
    }

    public Specials getSpecial() {
        return special;
    }

    public String getGruendung() {
        return gruendung;
    }

    public String getProzentWeiblichesPersonal() {
        return prozentWeiblichesPersonal;
    }

    @Override
    public String toString() {
        return "Lokal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", art='" + art + '\'' +
                ", email='" + email + '\'' +
                ", webadresse='" + webadresse + '\'' +
                ", strasse='" + strasse + '\'' +
                ", haltestelle='" + haltestelle + '\'' +
                ", guennstigstesBier='" + guennstigstesBier + '\'' +
                ", schritte='" + schritte + '\'' +
                ", fassungsvermoegen='" + fassungsvermoegen + '\'' +
                ", special=" + special +
                ", gruendung='" + gruendung + '\'' +
                ", prozentWeiblichesPersonal='" + prozentWeiblichesPersonal + '\'' +
                '}';
    }
}

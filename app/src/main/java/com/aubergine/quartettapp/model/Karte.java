package com.aubergine.quartettapp.model;

/**
 * Created by Daniel on 13.12.2016.
 */

public class Karte {

    private final String id;
    private final Lokal lokal;
    private final Gutschein gutschein;

    public Karte(String id, Lokal lokal, Gutschein gutschein) {
        this.id = id;
        this.lokal = lokal;
        this.gutschein = gutschein;
    }

    public String getId() {
        return id;
    }

    public Lokal getLokal() {
        return lokal;
    }

    public Gutschein getGutschein() {
        return gutschein;
    }

    @Override
    public String toString() {
        return "Karte{" +
                "id='" + id + '\'' +
                ", lokal=" + lokal +
                ", gutschein=" + gutschein +
                '}';
    }
}

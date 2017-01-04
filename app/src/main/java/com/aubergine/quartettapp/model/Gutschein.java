package com.aubergine.quartettapp.model;

import java.sql.Date;

/**
 * Created by Daniel on 13.12.2016.
 */

public class Gutschein {

    private final String id;
    private final String beschreibung;
    private final Date gueltigkeitsdatum;
    private boolean eingeloest;
    private Date eineloestDatum;

    public Gutschein(String id, String beschreibung, java.sql.Date gueltigkeitsdatum, boolean eingeloest, Date eingeloestdatum) {
        this.id = id;
        this.beschreibung = beschreibung;
        this.gueltigkeitsdatum = gueltigkeitsdatum;
        this.eingeloest = eingeloest;
        this.eineloestDatum = eingeloestdatum;
    }

    public boolean isEingeloest() {
        return eingeloest;
    }
    public String getId() {
        return id;
    }
    public String getBeschreibung() {
        return beschreibung;
    }
    public Date getGueltigkeitsdatum() {
        return gueltigkeitsdatum;
    }
    public Date getEineloestDatum() {
        return eineloestDatum;
    }

    public void setEineloestDatum(Date datum){
        this.eineloestDatum = datum;
    }

    public void setEingeloest(boolean eingeloest){
        this.eingeloest = eingeloest;
    }

    @Override
    public String toString() {
        return "Gutschein{" +
                "id='" + id + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", gueltigkeitsdatum=" + gueltigkeitsdatum +
                ", eingeloest=" + eingeloest +
                ", eineloestDatum=" + eineloestDatum +
                '}';
    }




}

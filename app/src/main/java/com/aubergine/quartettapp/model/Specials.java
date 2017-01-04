package com.aubergine.quartettapp.model;

/**
 * Created by Daniel on 18.12.2016.
 */

public class Specials {

    private final boolean tv;
    private final boolean essen;
    private final boolean dj;
    private final boolean rauchen;

    public Specials(boolean tv, boolean essen, boolean dj, boolean rauchen) {
        this.tv = tv;
        this.essen = essen;
        this.dj = dj;
        this.rauchen = rauchen;
    }

    public boolean isTv() {
        return tv;
    }

    public boolean isEssen() {
        return essen;
    }

    public boolean isDj() {
        return dj;
    }

    public boolean isRauchen() {
        return rauchen;
    }

    @Override
    public String toString() {
        return "Specials{" +
                "tv=" + tv +
                ", essen=" + essen +
                ", dj=" + dj +
                ", rauchen=" + rauchen +
                '}';
    }
}

package com.aubergine.quartettapp.activities;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import com.aubergine.quartettapp.database.DataBaseHelper;
import com.aubergine.quartettapp.model.Gutschein;
import com.aubergine.quartettapp.model.Karte;
import com.aubergine.quartettapp.model.Lokal;
import com.aubergine.quartettapp.model.Specials;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Olsson on 12.12.2016.
 */

public class MainApplication extends Application {
    private static Context context;
    public static List<Karte> karten = new ArrayList<>();
    public static Cursor cursor = null;
    public static DataBaseHelper dbHelper = null;

    public void onCreate() {
        super.onCreate();
        loadDatabase();
    }

    public static List<Karte> suche(String query) {
        List<Karte> suchKarten = new ArrayList<>();
        for (Karte k : karten) {
            if (k.getLokal().getName().toLowerCase().contains(query.toLowerCase())) {
                suchKarten.add(k);
            }
        }
        return suchKarten;
    }

    //Setzt das Einlösedatum und den Boolean Eingelöst
    public static void updateKarteEingeloest(String index) {
        Date date = new Date(System.currentTimeMillis());
        for (Karte k : karten) {
            if (k.getId().equals(index)) {
                k.getGutschein().setEingeloest(true);
                k.getGutschein().setEineloestDatum(date);
            }
        }
    }

    //Läd die Werte aus der Datenbank
    private void loadDatabase() {
        MainApplication.context = getApplicationContext();

        //Start database object initializing
        dbHelper = new DataBaseHelper(MainApplication.this);
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        dbHelper.openDataBase();
        cursor = dbHelper.queryAllInformation();
        if (cursor.moveToFirst()) {
            do {
                //19 Spalten
                //Pos 0 ID
                //Pos 1-9 Lokal
                //Pos 10-13 Specials
                //Pos 14, 15 Lokal
                //Pos 16-19 Gutschein
                Specials specials = new Specials((cursor.getInt(10) > 0), (cursor.getInt(11) > 0), (cursor.getInt(12) > 0), (cursor.getInt(13) > 0));
                Lokal lokal = new Lokal(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), specials, cursor.getString(14), cursor.getString(15));
                Gutschein gutschein = new Gutschein(cursor.getString(0), cursor.getString(16), new Date(Long.parseLong(cursor.getString(17))), (cursor.getInt(18) > 0), new Date(Long.parseLong(cursor.getString(19))));
                Karte karte = new Karte(cursor.getString(0), lokal, gutschein);
                MainApplication.karten.add(karte);
            } while (cursor.moveToNext());
        }

    }

    //Holt eine Karte anhand ihres Index aus der Karten Liste
    public static Karte getKartebIndex(String index) {
        for (Karte k : karten) {
            if (k.getId().equals(index)) {
                return k;
            }
        }
        return null;
    }

    public static Context getAppContext() {
        return MainApplication.context;
    }
}

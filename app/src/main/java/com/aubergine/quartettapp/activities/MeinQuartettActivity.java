package com.aubergine.quartettapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aubergine.quartettapp.R;
import com.aubergine.quartettapp.fragments.EinloesenFragment;
import com.aubergine.quartettapp.fragments.KartenDetailFragment;
import com.aubergine.quartettapp.fragments.MeinQuartettFragment;

import static com.aubergine.quartettapp.R.layout.activity_meinquartett;

/**
 * Created by Chris on 15.12.2016.
 */

//Haupt Activity für die Fragmente MeinQuartettFragment, KartenDetailFragment und EinloesenFragment
public class MeinQuartettActivity extends AppCompatActivity
        implements MeinQuartettFragment.OnKarteSelectListner,
        KartenDetailFragment.OnEinloesenListner,
        EinloesenFragment.OnEntsperrenListner {
    //transaction beschreibt eine Tranasction die beim Austauschen von Fragmenten verwendet wird
    public FragmentTransaction transaction;
    //Der FragmentManager verwaltet die Fragmente und Transactions
    public FragmentManager fragmentManager;
    //Sobald eine Karte entsperrt ist soll es nicht mehr möglich sein auf die Vorherige Ansicht zu wechseln
    private boolean entsperrt = false;
    //Sobald eine Suche gestartet ist soll beim Zurück Klicken die aktivität beendet werden
    private boolean suche = false;

    public MeinQuartettActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleSuchIntent(getIntent());
        //Beim Start der Aktivity wird MeinQuartett angezeigt
        MeinQuartettFragment meinQuartettFragment = new MeinQuartettFragment();
        //Der Fragmentmanager fügt das neue Fragment dem fragmeent_container hinzu.
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, meinQuartettFragment);
        transaction.commit();

        setContentView(activity_meinquartett);

        //Die Custom ActionBar wird aktiviert
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbarlayout);
        View view = getSupportActionBar().getCustomView();


        //Zurück Button
        ImageButton backward = (ImageButton) view.findViewById(R.id.action_bar_back);
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entsperrt | suche | fragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                } else {
                    fragmentManager.popBackStack();
                }
            }
        });

        //Wenn die Activity über die Suche aufgerufen wurde enhält extras die Werte für die Suche
        boolean suche = false;
        String query = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            suche = extras.getBoolean("suche");
            query = extras.getString("query");
        }

        //Die Werte müssen an das neue Fragment übergeben werden
        Bundle args = new Bundle();
        args.putBoolean("suche", suche);
        meinQuartettFragment.setArguments(args);

        //Wenn eine Suche aufgerufen wurde dann wird der Titel angepasst.
        if (suche) {
            args.putString("query", query);
            setCustomTitle("Suchergebnisse");
        } else {
            setCustomTitle("MeinQuartett");
        }
    }

    //Wird die Suchfunktion aus dieser Activity aufgerufen
    private void handleSuchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            suche = true;
            Intent meinQuartettIntent = new Intent(this, MeinQuartettActivity.class);
            meinQuartettIntent.putExtra("suche", true);
            meinQuartettIntent.putExtra("query", query);
            startActivity(meinQuartettIntent);
        }
    }

    // Konfiguration für die Suche
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_searchicon).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleSuchIntent(intent);
    }

    //Interface Methode für MeinQuartett Fragment
    @Override
    public void onKarteSelected(View view) {

        KartenDetailFragment kartenDetailFragment = (KartenDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_kartendetail);
        String kartenIndex = ((TextView) view.findViewById(R.id.karte_list_item_id)).getText().toString();

        if (kartenDetailFragment != null) {
            kartenDetailFragment.updateKartenView(kartenIndex);
        } else {
            KartenDetailFragment newKartenDetailFragment = new KartenDetailFragment();
            Bundle args = new Bundle();
            args.putString("index", kartenIndex);
            newKartenDetailFragment.setArguments(args);
            transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, newKartenDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    //Interface Methode für EinloesenFragment
    @Override
    public void onEntsperrenSelected(String index, View view) {
        MainApplication.updateKarteEingeloest(index);
        EinloesenFragment newEinloesenFragment = new EinloesenFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        newEinloesenFragment.setArguments(args);
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, newEinloesenFragment);
        transaction.commit();
        entsperrt = true;
        Toast.makeText(getApplicationContext(), "Karte wurde eingelöst", Toast.LENGTH_SHORT).show();
    }

    //Interface Methode für KartenDetailFragment
    @Override
    public void onEinloesenSelected(String index, View view) {
        EinloesenFragment newEinloesenFragment = new EinloesenFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        newEinloesenFragment.setArguments(args);
        entsperrt = false;
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, newEinloesenFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //Setzt den Title für die Activity. Kann aus jedem Fragment aufgerufen werden.
    public void setCustomTitle(String title) {
        View view = getSupportActionBar().getCustomView();
        TextView titleText = (TextView) view.findViewById(R.id.action_title);
        titleText.setText(title);
    }


}

package com.aubergine.quartettapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

import com.aubergine.quartettapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleSuchIntent(getIntent());

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbarlayout);
        View view = getSupportActionBar().getCustomView();
        ImageButton backward = (ImageButton) view.findViewById(R.id.action_bar_back);

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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

    private void handleSuchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Intent meinQuartettIntent = new Intent(this, MeinQuartettActivity.class);
            meinQuartettIntent.putExtra("suche", true);
            meinQuartettIntent.putExtra("query", query);
            startActivity(meinQuartettIntent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleSuchIntent(intent);
    }

    //Methode die vom MeinQuartett Button ausgeführt wird um die MeinQuartettActivity zu starten.
    public void meinQuartett(View v) {
        Intent meinQuartettIntent = new Intent(this, MeinQuartettActivity.class);
        startActivity(meinQuartettIntent);
    }

    public void naechstesLokal(View v) {
        Intent neachstesLokalIntent = new Intent(this, NaechstesLokalActivity.class);
        startActivity(neachstesLokalIntent);
    }

    public void quartettKaufen(View v) {
        Intent quartettKaufenlIntent = new Intent(this, QuartettKaufenActivity.class);
        startActivity(quartettKaufenlIntent);
    }
}

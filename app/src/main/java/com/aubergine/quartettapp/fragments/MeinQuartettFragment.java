package com.aubergine.quartettapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.aubergine.quartettapp.R;
import com.aubergine.quartettapp.activities.CustomList;
import com.aubergine.quartettapp.activities.MainApplication;
import com.aubergine.quartettapp.model.Karte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 15.12.2016.
 */

public class MeinQuartettFragment extends ListFragment {
    OnKarteSelectListner mCallback;

    public MeinQuartettFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        // Dies Stellt sicher, dass die übergeordnete Activity das Interface implmentiert hat.
        try {
            mCallback = (OnKarteSelectListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnKarteSelectListner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getArguments();
        boolean isSucheActive = b.getBoolean("suche");
        String query = b.getString("query");
        List<Karte> karten;
        // Wenn eine Suche aktiviert wurde dann werden nur die karten die der query entsperchen geladen.
        if (isSucheActive) {
            karten = MainApplication.suche(query);
        } else {
            karten = MainApplication.karten;
        }
        if (karten.size() == 0) {
            Toast.makeText(getActivity(), "Keine Lokale gefunden",
                    Toast.LENGTH_LONG).show();
        }
        List<String> lokale = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<Integer> imageId = new ArrayList<>();
        List<Boolean> isEingeloest = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_meinquartett, container, false);

        // Füllt alle Listen mit den Informationen für den CustomList kartenAdapter
        for (Karte k : karten) {
            lokale.add(k.getLokal().getName().toString());
            ids.add(k.getId().toString());
            int image = getResources().getIdentifier((k.getId() + "0").toLowerCase(), "drawable", "com.aubergine.quartettapp");
            imageId.add(image);
            isEingeloest.add(k.getGutschein().isEingeloest());
        }

        // Der Adapter erhält die gefüllten Listen
        CustomList kartenAdapter = new
                CustomList(getActivity(), ids, lokale, imageId, isEingeloest);
        setListAdapter(kartenAdapter);

        return rootView;
    }

    // Da die Klasse ein ListFragement ist hat sie ein OnListItemClick dieser
    // ruft die onKarteSelectet auf, die in der MeinQuartettActivity implementiert ist auf,
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mCallback.onKarteSelected(v);
    }

    // Die Übergeordnete Activity muss dieses Interface implementieren
    public interface OnKarteSelectListner {
        public void onKarteSelected(View view);
    }


}

package com.aubergine.quartettapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aubergine.quartettapp.R;
import com.aubergine.quartettapp.activities.MainApplication;
import com.aubergine.quartettapp.activities.MeinQuartettActivity;
import com.aubergine.quartettapp.model.Karte;

/**
 * Created by Chris on 27.12.2016.
 */

// Zeigt nochmal alle Informationen an die zum Einlösen eines Gutscheins notwendig sind.
public class EinloesenFragment extends Fragment implements View.OnClickListener {
    private static Karte karte;
    EinloesenFragment.OnEntsperrenListner mCallback;
    private String index;

    public EinloesenFragment() {
    }

    // Wenn ein Gutschein bereits eingelöst ist, werden die jeweiligen Elemente ausgeblendet.
    private static View isKarteEingeloest(View rootView) {
        Button entsperrenButton = (Button) rootView.findViewById(R.id.entsperren_button);
        if (karte.getGutschein().isEingeloest()) {
            rootView.findViewById(R.id.table_entwertet).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.einloesen_entwertetwert)).setText(karte.getGutschein().getEineloestDatum().toString());
            rootView.findViewById(R.id.einloesen_layout).setVisibility(View.GONE);
            entsperrenButton.setEnabled(false);
        } else {
            rootView.findViewById(R.id.table_entwertet).setVisibility(View.GONE);
        }
        return rootView;
    }

    // Hilfsmethode zum holen einer Karte
    private static void findKartebyIndex(String index) {
        karte = MainApplication.getKartebIndex(index);
    }

    @Override
    public void onClick(View view) {
        mCallback.onEntsperrenSelected(index, view);
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
            mCallback = (EinloesenFragment.OnEntsperrenListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEntsperrenListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_einloesen, null);
        // Werte die aus der Activity stammen auslesen
        Bundle b = getArguments();
        this.index = b.getString("index");
        findKartebyIndex(index);

        // Setzt den Titel der App auf den Namen des Lokals
        String titleText = karte.getLokal().getName();
        ((MeinQuartettActivity) getActivity()).setCustomTitle(titleText);
        // Der Index der Karte + 1 = Name des Bildes in groß
        String imageIndex = (index + "1").toLowerCase();

        // Die Informationen werden gesetzt
        int res = getResources().getIdentifier(imageIndex, "drawable", "com.aubergine.quartettapp");
        ((ImageView) rootView.findViewById(R.id.einloesen_lokalimg)).setImageResource(res);
        ((TextView) rootView.findViewById(R.id.einloesen_id)).setText(karte.getId());
        ((TextView) rootView.findViewById(R.id.einloesen_name)).setText(karte.getLokal().getName());
        ((TextView) rootView.findViewById(R.id.einloesen_gutscheinwert)).setText(karte.getGutschein().getBeschreibung());
        ((TextView) rootView.findViewById(R.id.einloesen_gueltigbiswert)).setText(karte.getGutschein().getGueltigkeitsdatum().toString());
        Button entsperrenButton = (Button) rootView.findViewById(R.id.entsperren_button);
        entsperrenButton.setOnClickListener(this);
        rootView = isKarteEingeloest(rootView);
        return rootView;
    }

    // Interface das von der Activity implementiert wird.
    public interface OnEntsperrenListner {
        public void onEntsperrenSelected(String index, View view);
    }
}





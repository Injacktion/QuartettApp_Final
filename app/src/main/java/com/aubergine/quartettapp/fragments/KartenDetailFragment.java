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
 * Created by Chris on 15.12.2016.
 */

// Zeigt eine Karte mit allen Informationen an
public class KartenDetailFragment extends Fragment implements View.OnClickListener {
    public String index;

    OnEinloesenListner mCallback;

    public KartenDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kartendetail, null);
        // Werte die aus der Activity stammen auslesen
        Bundle b = getArguments();
        this.index = b.getString("index");

        Karte karte = MainApplication.getKartebIndex(index);
        String titleText = karte.getLokal().getName();
        ((MeinQuartettActivity) getActivity()).setCustomTitle(titleText);
        String imageIndex = (index + "1").toLowerCase();
        int res = getResources().getIdentifier(imageIndex, "drawable", "com.aubergine.quartettapp");

        // Füllt die Karte mit allen Informationen
        ((ImageView) rootView.findViewById(R.id.img)).setImageResource(res);
        ((TextView) rootView.findViewById(R.id.kartendetail_id)).setText(karte.getId());
        ((TextView) rootView.findViewById(R.id.kartendetail_name)).setText(karte.getLokal().getName());
        ((TextView) rootView.findViewById(R.id.kartendetail_haltestelle)).setText(karte.getLokal().getHaltestelle());
        ((TextView) rootView.findViewById(R.id.kartendetail_adresse)).setText(karte.getLokal().getStrasse());
        ((TextView) rootView.findViewById(R.id.kartendetail_gruendungsjahrwert)).setText(karte.getLokal().getGruendung());
        ((TextView) rootView.findViewById(R.id.kartendetail_guenstigstesbierwert)).setText(karte.getLokal().getGuennstigstesBier());
        ((TextView) rootView.findViewById(R.id.kartendetail_schrittewert)).setText(karte.getLokal().getSchritte());
        ((TextView) rootView.findViewById(R.id.kartendetail_fassungsvermoegenwert)).setText(karte.getLokal().getFassungsvermoegen());
        ((TextView) rootView.findViewById(R.id.kartendetail_weiblichewert)).setText(karte.getLokal().getProzentWeiblichesPersonal());
        ((TextView) rootView.findViewById(R.id.kartendetail_gutscheinwert)).setText(karte.getGutschein().getBeschreibung());
        ((TextView) rootView.findViewById(R.id.kartendetail_gueltigbiswert)).setText(karte.getGutschein().getGueltigkeitsdatum().toString());
        Button einloesenButton = (Button) rootView.findViewById(R.id.kartendetail_einlösen);
        // Setzt den Einlösen Butten auf Eingelöst (Rot) wenn der Gutschein bereits eingelöst ist.
        if (karte.getGutschein().isEingeloest()) {
            einloesenButton.setBackground(getResources().getDrawable(R.drawable.colorbutton2));
            einloesenButton.setText("Eingelöst");
        }
        // Setzt den OnClickListener, der von dieser Klasse implementiert wird, für den einloesenButton
        einloesenButton.setOnClickListener(this);

        return rootView;
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
            mCallback = (KartenDetailFragment.OnEinloesenListner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEinloesenListner");
        }
    }

    // Wen der Button geklickt wird, ruft die Methode onEinloesenSelected aus der Activity auf.
    @Override
    public void onClick(View view) {
        mCallback.onEinloesenSelected(this.index, view);
    }

    public void updateKartenView(String index) {
        this.index = index;
    }

    public interface OnEinloesenListner {
        public void onEinloesenSelected(String Id, View view);
    }

}

package com.aubergine.quartettapp.activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aubergine.quartettapp.R;

import java.util.List;

// Erzeugt die Liste mit Bildern Namen und Eingelöst Status unter Verwendung der geerbten ArrayAdapter Klasse
public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> ids;
    private final List<String> lokale;
    private final List<Integer> imageId;
    private final List<Boolean> isEingeloest;


    public CustomList(Activity context,
                      List<String> ids, List<String> lokale, List<Integer> imageId, List<Boolean> isEingeloest) {
        super(context, R.layout.karte_list_item, ids);
        this.context = context;
        this.ids = ids;
        this.imageId = imageId;
        this.lokale = lokale;
        this.isEingeloest = isEingeloest;
    }

    /* Jedes Listen Element wird anhand der getView Methode gesetzt.
       Dabei erhält jedes Element die Werte an der Stelle in der sie in der Liste vorkommen.
       GetView gibt jeweils eine Zeile der Liste zurück.*/
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.karte_list_item, null, true);
        TextView idTitle = (TextView) rowView.findViewById(R.id.karte_list_item_id);
        TextView lokalTitle = (TextView) rowView.findViewById(R.id.karte_list_item_lokal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        ImageView imageView2 = (ImageView) rowView.findViewById(R.id.karte_list_item_eingeloest);

        idTitle.setText(ids.get(position));
        lokalTitle.setText(lokale.get(position));
        imageView.setImageResource(imageId.get(position));
        if (isEingeloest.get(position)) {
            imageView2.setImageResource(R.drawable.checked);
        }
        return rowView;
    }
}
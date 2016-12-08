package com.app.mobi.horus;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rosario on 07/12/2016.
 */
public class OpcionList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] integers;

    public OpcionList(Activity context, String[] itemname, Integer[] integers) {
        super(context, R.layout.activity_detalles_lista, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.integers=integers;
    }

    public View getView(int posicion,View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_detalles_lista,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        //etxDescripcion.setText("Description "+itemname[posicion]);

        return rowView;
    }


}

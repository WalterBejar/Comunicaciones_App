package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.Notificacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andre on 06/09/2017.
 */

public class NotificAdapter extends ArrayAdapter<Notificacion> {
    private Context context;
    private List<Notificacion> data = new ArrayList<Notificacion>();
    DateFormat formatoInicial = new SimpleDateFormat("yyyy-mm-dd'T'00:00:00", new Locale("es", "ES"));
    DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
    public NotificAdapter(Context context, List<Notificacion> data) {
        super(context, R.layout.public_notifica,data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.public_notifica, null,true);

        ImageView icono = (ImageView) rowView.findViewById(R.id.notific_icon);
        TextView ntTitulo = (TextView)  rowView.findViewById(R.id.notific_titulo);
        TextView ntFecha = (TextView)  rowView.findViewById(R.id.notific_fecha);


        //asignando a las variables los valores obtenidos
        final String tempTitulo=data.get(position).getTitulo();
        final String tempFecha=data.get(position).getFecha();


        //cargar la data al layout//////
        icono.setImageResource(R.drawable.ic_menu_notificacion);
        ntTitulo.setText(tempTitulo);
        //ntFecha.setText(tempFecha);
        try {
            ntFecha.setText(formatoRender.format(formatoInicial.parse(tempFecha)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rowView;
    }





}

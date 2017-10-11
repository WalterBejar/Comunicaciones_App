package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.ActImgNot;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.Comunicado;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andre on 26/09/2017.
 */

public class ComAdapter extends ArrayAdapter<Comunicado> {

    private Context context;
    private List<Comunicado> data = new ArrayList<Comunicado>();
    DateFormat formatoInicial = new SimpleDateFormat("yyyy-mm-dd'T'00:00:00", new Locale("es", "ES"));
    DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

    public ComAdapter(Context context, List<Comunicado> data) {
        super(context, R.layout.public_com,data);
        this.data = data;
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.public_com, null,true);

        ImageView icono = (ImageView) rowView.findViewById(R.id.com_icon);

        //TextView cNom_publicador = (TextView)  rowView.findViewById(R.id.com_txpub);
        TextView cFecha = (TextView)  rowView.findViewById(R.id.com_txfecha);
        TextView cTitulo = (TextView)  rowView.findViewById(R.id.com_titulo);

        ImageView cImagNot = (ImageView)  rowView.findViewById(R.id.com_imagen);
        TextView cDescripcion = (TextView) rowView.findViewById(R.id.com_desc);
        //TextView cDescs= (TextView) rowView.findViewById(R.id.com_desc_inv);


        //asignando a las variables los valores obtenidos
        final String tempNombre=data.get(position).getNom_publicador();
        final String tempFecha=data.get(position).getFecha();
        final String tempTitulo=data.get(position).getTitulo();
        final String tempDescripcion=data.get(position).getDescripcion();


        //cargar la data al layout//////
        icono.setImageResource(R.drawable.ic_menu_publicaciones);
        //cNom_publicador.setText(tempNombre);
        //cFecha.setText(tempFecha);

        try {
            cFecha.setText(formatoRender.format(formatoInicial.parse(tempFecha)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cTitulo.setText(tempTitulo);

        int ds=data.get(position).getFiledata().size();
        if(ds==0) {
            //cDescs.setVisibility(View.VISIBLE);
            //cDescripcion.setVisibility(View.GONE);
            cImagNot.setVisibility(View.GONE);
            //cDescs.setText(tempDescripcion);
        }else {

            cDescripcion.setText(tempDescripcion);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + data.get(position).getFiledata().get(2).replaceAll("\\s", "%20"))
                    .into(cImagNot);
        }


        cImagNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   ((ListView) parent).performItemClick(convertView, position, 0);
                Intent intent=new Intent(v.getContext(), ActImgNot.class);

                intent.putExtra("url_img", GlobalVariables.Urlbase + data.get(position).getFiledata().get(1).replaceAll("\\s", "%20"));
                // intent.putExtra("val",0);

                //intent.putExtra(ActVidDet.EXTRA_PARAM_ID, item.getId());
                v.getContext().startActivity(intent);

            }
        });




        return rowView;
    }





}



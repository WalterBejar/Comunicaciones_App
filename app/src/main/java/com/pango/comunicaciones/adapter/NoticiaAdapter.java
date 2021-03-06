package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.ActImgNot;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.MainActivity;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.model.Noticias;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andre on 25/09/2017.
 */

public class NoticiaAdapter extends ArrayAdapter<Noticias> {
    private Context context;
    private List<Noticias> data = new ArrayList<Noticias>();

    DateFormat formatoInicial = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
    DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");

    public NoticiaAdapter(Context context, List<Noticias> data) {
        super(context, R.layout.public_noticias,data);
        this.data = data;
        this.context = context;
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        //ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.public_noticias, null,true);

        ImageView icono = (ImageView) rowView.findViewById(R.id.icon_inicio);
        //TextView nNom_publicador = (TextView)  rowView.findViewById(R.id.tx_publicador);
        TextView nFecha = (TextView)  rowView.findViewById(R.id.txfecha);
        TextView nTitulo = (TextView)  rowView.findViewById(R.id.titulo_noticia);

        ImageView nImagNot = (ImageView)  rowView.findViewById(R.id.imag_not);
        TextView nDescripcion = (TextView) rowView.findViewById(R.id.txdesc);
        //TextView nDescs2= (TextView) rowView.findViewById(R.id.desc_inv);


        //asignando a las variables los valores obtenidos
        //final String tempNombre=data.get(position).getNom_publicador();
        final String tempFecha=data.get(position).getFecha();
        final String tempTitulo=data.get(position).getTitulo();

        final String tempDescripcion=data.get(position).getDescripcion();


        //cargar la data al layout//////
        icono.setImageResource(R.drawable.ic_noticia3);
        //nNom_publicador.setText(tempNombre);
        //nFecha.setText(tempFecha);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaThn.ttf");

        try {
            nFecha.setTypeface(face);
            nFecha.setText(formatoRender.format(formatoInicial.parse(tempFecha)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Typeface face1=Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaMed.ttf");
        nTitulo.setTypeface(face1);
        nTitulo.setText(tempTitulo);




        nDescripcion.setTypeface(face);
        nDescripcion.setText(tempDescripcion);



        //comunicadoModel.setFecha(formatoRender.format(formatoInicial.parse(comunicado.getFecha())));

       //nImagNot.setVisibility(View.GONE);


        int ds=data.get(position).getUrlmin().length();

        if(ds==0) {
            //nDescs.setVisibility(View.VISIBLE);
            //nDescripcion.setVisibility(View.GONE);
            nImagNot.setVisibility(View.GONE);
            //nDescs.setText(tempDescripcion);
        }else {
            String dddf=data.get(position).getUrlmin();
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + Utils.ChangeUrl(dddf))
                    .into(nImagNot);
        }



        nImagNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.listdetimg.clear();
                GlobalVariables.flag_orienta=true;
                //   ((ListView) parent).performItemClick(convertView, position, 0);
                Intent intent=new Intent(v.getContext(), ActImgNot.class);
                intent.putExtra("codreg",data.get(position).getCod_reg());
                //intent.putExtra("url_img", GlobalVariables.Urlbase + data.get(position).getFiledata().get(0).replaceAll("\\s", "%20"));
                intent.putExtra("posIn",0);
                //intent.putExtra(ActVidDet.EXTRA_PARAM_ID, item.getId());
                v.getContext().startActivity(intent);




            }
        });


        return rowView;
    }

}

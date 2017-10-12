package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.Video;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andre on 26/09/2017.
 */

public class VidAdapter extends ArrayAdapter<Video> {


    private Context context;
    private List<Video> data = new ArrayList<Video>();
    DateFormat formatoInicial = new SimpleDateFormat("yyyy-mm-dd'T'00:00:00", new Locale("es", "ES"));
    DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
    public VidAdapter(Context context, List<Video> data) {
        super(context, R.layout.public_videos, data);
        this.data = data;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(context);



        View rowView=inflater.inflate(R.layout.public_videos, null,true);

        ImageView icono = (ImageView) rowView.findViewById(R.id.icon_vid);
        //TextView vNom_publicador = (TextView)  rowView.findViewById(R.id.txvidpub);
        TextView vFecha = (TextView)  rowView.findViewById(R.id.txfechavid);
        TextView vTitulo = (TextView)  rowView.findViewById(R.id.titulo_video);

        ImageView vImagVid = (ImageView)  rowView.findViewById(R.id.imag_vid);
        TextView vcant_vid = (TextView)  rowView.findViewById(R.id.cant_vid);


        ImageView vPlay = (ImageView)  rowView.findViewById(R.id.vid_play);
        TextView vEtiqueta = (TextView)  rowView.findViewById(R.id.videtiq);



        //final String tempNombre=data.get(position).getNom_publicador();
        final String tempFecha=data.get(position).getFecha();
        final String tempTitulo=data.get(position).getTitulo();

        final int tempcant_vid=data.get(position).getCant_video();



        icono.setImageResource(R.drawable.ic_video3);
        //vNom_publicador.setText(tempNombre);
        vFecha.setText(tempFecha);

       /* try {
            vFecha.setText(formatoRender.format(formatoInicial.parse(tempFecha)));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        vTitulo.setText(tempTitulo);
        // vcant_vid.setText(tempcant_vid+"");


/////////////////////////////////////////////////////////////////////////

        //if(tempcant_vid==0)




        if(tempcant_vid==0){
            vcant_vid.setVisibility(View.GONE);
            vEtiqueta.setVisibility(View.GONE);
            vPlay.setVisibility(View.GONE);
            vImagVid.setVisibility(View.GONE);

        }else if(tempcant_vid==1){

            //String url_vid_img="https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/media/GetminFile/4039/MACROSS%202012%20SILVER%20MOO%20RED%20MOON.jpg";
            String url_vid_img=data.get(position).getFiledata().get(0).getUrlmin_vid().replaceAll("\\s","%20");
            vcant_vid.setVisibility(View.GONE);
            vEtiqueta.setVisibility(View.GONE);
            vPlay.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase+url_vid_img)
                    .into(vImagVid);
        }else{


            String url_vid_img=data.get(position).getFiledata().get(0).getUrlmin_vid().replaceAll("\\s","%20");

            vcant_vid.setText(tempcant_vid+"");
            Glide.with(context)
                    .load(GlobalVariables.Urlbase+url_vid_img)
                    .into(vImagVid);

        }



        return rowView;
    }

}
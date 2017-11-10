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
import com.pango.comunicaciones.ActVidDet;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.Vid_Gal;
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
    int posicion;

    private Context context;
    private List<Video> data = new ArrayList<Video>();
    DateFormat formatoInicial = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
    DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");
    public VidAdapter(Context context, List<Video> data) {
        super(context, R.layout.public_videos, data);
        this.data = data;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder viewHolder;
        posicion=position;
        LayoutInflater inflater = LayoutInflater.from(context);



        View rowView=inflater.inflate(R.layout.public_videos, null,true);

        //ImageView icono = (ImageView) rowView.findViewById(R.id.icon_vid);
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

        final String tempUrl=data.get(position).getFiledata().get(0).getUrl_vid();

//        icono.setImageResource(R.drawable.ic_video_final);
        //vNom_publicador.setText(tempNombre);
        //vFecha.setText(tempFecha);

        try {
            vFecha.setText(formatoRender.format(formatoInicial.parse(tempFecha)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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


        vImagVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.cont_posvid=0;
                Intent intent = new Intent(v.getContext(), ActVidDet.class);
                //intent.putExtra("post",position);
                intent.putExtra("urltemp",tempUrl);
                intent.putExtra("isList",true);

                //intent.putExtra("val",0);
                //intent.putExtra(ActVidDet.EXTRA_PARAM_ID, item.getId());
                v.getContext().startActivity(intent);
            }
        });



        return rowView;
    }

}
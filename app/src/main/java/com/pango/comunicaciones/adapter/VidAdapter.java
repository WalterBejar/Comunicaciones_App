package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.ActVidDet;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.controller.ValidUrlController;
import com.pango.comunicaciones.model.Vid_Gal;
import com.pango.comunicaciones.model.Video;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VidAdapter extends ArrayAdapter<Video> {
    int posicion;
    //String tempUrl="";
    Boolean estaVacio=false;

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
    public View getView(final int position, final View convertView, ViewGroup parent) {
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
       String tempUrl="";
        // tempUrl=data.get(position).getFiledata().get(0).getUrl_vid();

       try{
            tempUrl=data.get(position).getFiledata().get(0).getUrl_vid();
        }catch (Exception e){
            e.printStackTrace();
            estaVacio=true;
        }




//        icono.setImageResource(R.drawable.ic_video_final);
        //vNom_publicador.setText(tempNombre);
        //vFecha.setText(tempFecha);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaThn.ttf");

        try {
            vFecha.setTypeface(face);
            vFecha.setText(formatoRender.format(formatoInicial.parse(tempFecha)));
        } catch (ParseException e) {
            e.printStackTrace();

        }

        Typeface face1=Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaMed.ttf");
        vTitulo.setTypeface(face1);
        vTitulo.setText(tempTitulo);
        // vcant_vid.setText(tempcant_vid+"");



/////////////////////////////////////////////////////////////////////////

        //if(tempcant_vid==0)
       // int urlvalido=Utils.verificarUrl(GlobalVariables.Urlbase+tempUrl.replaceAll("\\s","%20"));



        if(tempcant_vid==0){
            vcant_vid.setVisibility(View.GONE);
            vEtiqueta.setVisibility(View.GONE);


            vPlay.setVisibility(View.VISIBLE);
            vImagVid.setImageResource(R.drawable.img_blanco);

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


        final String finalTempUrl = tempUrl;
        vImagVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                GlobalVariables.cont_posvid=0;
                /*if(!estaVacio) {
                    Intent intent = new Intent(v.getContext(), ActVidDet.class);
                    //intent.putExtra("post",position);
                    intent.putExtra("urltemp", tempUrl);
                    intent.putExtra("isList", true);

                    //intent.putExtra("val",0);
                    //intent.putExtra(ActVidDet.EXTRA_PARAM_ID, item.getId());
                    v.getContext().startActivity(intent);
                }*/

                final ValidUrlController obj1 = new ValidUrlController("url","get");
                obj1.execute(GlobalVariables.Urlbase.substring(0, GlobalVariables.Urlbase.length() - 4)+ finalTempUrl);

                final Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (obj1.getStatus() == AsyncTask.Status.FINISHED) {
                            //constraintLayout.setVisibility(View.GONE);
                            //flag_enter=true;
                            if(!estaVacio) {
                                Intent intent = new Intent(v.getContext(), ActVidDet.class);
                                //intent.putExtra("post",position);
                                intent.putExtra("urltemp", finalTempUrl);
                                intent.putExtra("isList", true);

                                //intent.putExtra("val",0);
                                //intent.putExtra(ActVidDet.EXTRA_PARAM_ID, item.getId());
                                v.getContext().startActivity(intent);
                            }

                        } else {
                            h.postDelayed(this, 10);
                        }
                    }
                }, 10);






            }
        });



        return rowView;
    }

}
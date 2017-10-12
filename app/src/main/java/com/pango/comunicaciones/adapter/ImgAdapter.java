package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.ActImag;
import com.pango.comunicaciones.ActImagDet;
import com.pango.comunicaciones.ActImgNot;
import com.pango.comunicaciones.ActSwipeImg;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.controller.ImgdetController;
import com.pango.comunicaciones.model.Imagen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ImgAdapter extends ArrayAdapter<Imagen> {
    private LinearLayout linearLayout;
    private Context context;
    private List<Imagen> data = new ArrayList<Imagen>();
    DateFormat formatoInicial = new SimpleDateFormat("yyyy-mm-dd'T'00:00:00", new Locale("es", "ES"));
    DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

    public ImgAdapter(Context context, List<Imagen> data) {
        super(context, R.layout.public_imagen,data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        //ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.public_imagen, null,true);

        ImageView icono = (ImageView) rowView.findViewById(R.id.icon_imag);
        //TextView iNom_publicador = (TextView)  rowView.findViewById(R.id.tximagpub);
        TextView iFecha = (TextView)  rowView.findViewById(R.id.txfechaimag);
        TextView iTitulo = (TextView)  rowView.findViewById(R.id.titulo_imagen);
        FrameLayout flay=(FrameLayout) rowView.findViewById(R.id.frameLayout);

        TextView icant_img = (TextView)  rowView.findViewById(R.id.cant_img);

        ImageView iImag1 = (ImageView)  rowView.findViewById(R.id.imag1);
        ImageView iImag2 = (ImageView)  rowView.findViewById(R.id.imag2);
        ImageView iImag3 = (ImageView)  rowView.findViewById(R.id.imag3);
        ImageView iImag4 = (ImageView)  rowView.findViewById(R.id.imag4);
        //TextView nDescs= (TextView) rowView.findViewById(R.id.desc_inv);
        linearLayout =(LinearLayout) rowView.findViewById(R.id.linlayh);


        //final String tempNombre=data.get(position).getNom_publicador();
        final String tempFecha=data.get(position).getFecha();
        final String tempTitulo=data.get(position).getTitulo();
        final int tempcant=data.get(position).getCount_img()-4;
        final String codreg_img=data.get(position).getCod_reg();


        icono.setImageResource(R.drawable.ic_fotos3);


        //iNom_publicador.setText(tempNombre);
       iFecha.setText(tempFecha);
       /* try {
            iFecha.setText(formatoRender.format(formatoInicial.parse(tempFecha)));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        iTitulo.setText(tempTitulo);
        icant_img.setText("+"+tempcant);


        final ArrayList<String> img=new ArrayList<String>();
        final ArrayList<String> img2=new ArrayList<String>();

        for (int i = 0; i < data.get(position).getFiledata().size(); i++) {

            String a=data.get(position).getFiledata().get(i).getUrlmin_imag();
            String b=data.get(position).getFiledata().get(i).getUrl_img();
            a.length();
            b.length();
            String cad= a.substring( a.length()-4);
            String cad2= b.substring( a.length()-4);
            if(cad.equals(".jpg")|cad.equals(".png")) {
                img.add(a);
                img2.add(b);
            }
        }



        if(img.size()==0){
            linearLayout.setVisibility(View.GONE);
            iImag1.setVisibility(View.GONE);
            iImag2.setVisibility(View.GONE);
            iImag3.setVisibility(View.GONE);
            iImag4.setVisibility(View.GONE);
            icant_img.setVisibility(View.GONE);
            // flay.setVisibility(View.GONE);


        }else if(img.size()==1){
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(0).replaceAll("\\s","%20"))
                    .into(iImag1);
            iImag1.setVisibility(View.VISIBLE);
            iImag2.setVisibility(View.GONE);
            iImag3.setVisibility(View.GONE);
            iImag4.setVisibility(View.GONE);
            icant_img.setVisibility(View.GONE);
            //flay.setVisibility(View.GONE);


        }else if(img.size()==2){
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(0).replaceAll("\\s","%20"))
                    .into(iImag1);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(1).replaceAll("\\s","%20"))
                    .into(iImag2);
            iImag1.setVisibility(View.VISIBLE);
            iImag2.setVisibility(View.VISIBLE);
            iImag3.setVisibility(View.GONE);
            iImag4.setVisibility(View.GONE);
            icant_img.setVisibility(View.GONE);
            //flay.setVisibility(View.GONE);


        }else if(img.size()==3){

            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(0).replaceAll("\\s","%20"))
                    .into(iImag1);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(1).replaceAll("\\s","%20"))
                    .into(iImag2);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(2).replaceAll("\\s","%20"))
                    .into(iImag3);
            iImag1.setVisibility(View.VISIBLE);
            iImag2.setVisibility(View.VISIBLE);
            iImag3.setVisibility(View.VISIBLE);
            iImag4.setVisibility(View.GONE);
            //flay.setVisibility(View.GONE);
            icant_img.setVisibility(View.GONE);


        }else if(img.size()>=4){

            //String asdf=(GlobalVariables.Urlbase +img.get(0).replaceAll("\\s","%20"));
            Glide.with(context)
                    .load(GlobalVariables.Urlbase +img.get(0).replaceAll("\\s","%20"))
                    .into(iImag1);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(1).replaceAll("\\s","%20"))
                    .into(iImag2);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(2).replaceAll("\\s","%20"))
                    .into(iImag3);
            Glide.with(context)
                    .load(GlobalVariables.Urlbase + img.get(3).replaceAll("\\s","%20"))
                    .into(iImag4);
            iImag1.setVisibility(View.VISIBLE);
            iImag2.setVisibility(View.VISIBLE);
            iImag3.setVisibility(View.VISIBLE);
            iImag4.setVisibility(View.VISIBLE);
            //flay.setVisibility(View.VISIBLE);
            if(img.size()==4){
                icant_img.setVisibility(View.GONE);}else{icant_img.setVisibility(View.VISIBLE);}

        }

        icant_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(convertView, position, 0);
                // Intent intent=new Intent(v.getContext(), ActImgNot.class);
                //intent.putExtra("url_img",GlobalVariables.Urlbase + data.get(position).getFiledata().get(2).replaceAll("\\s", "%20"));
                // intent.putExtra("val",0);
                //v.getContext().startActivity(intent);
            }
        });

        iImag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String adfhh=GlobalVariables.Urlbase +img2.get(2).replaceAll("\\s","%20");
                //   ((ListView) parent).performItemClick(convertView, position, 0);
                //Intent intent=new Intent(v.getContext(), ActImgNot.class);


               // final ImgdetController obj = new ImgdetController("url","get", ActImag.this);
                //obj.execute(GlobalVariables.img_get.getCod_reg());




               Intent intent=new Intent(v.getContext(), ActSwipeImg.class);
                //intent.putExtra(ActImagDet.EXTRA_PARAM_ID, 0);
                intent.putExtra("post",0);
                intent.putExtra("position_p",position);
                intent.putExtra("url_img",img2);


               // intent.putExtra("url_img",GlobalVariables.Urlbase +img2.get(0).replaceAll("\\s","%20"));

                v.getContext().startActivity(intent);

            }
        });
        iImag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   ((ListView) parent).performItemClick(convertView, position, 0);
               // Intent intent=new Intent(v.getContext(), ActImgNot.class);


                Intent intent=new Intent(v.getContext(), ActSwipeImg.class);
                //intent.putExtra(ActImagDet.EXTRA_PARAM_ID, 0);
                intent.putExtra("post",1);
                intent.putExtra("position_p",position);
                intent.putExtra("url_img",img2);

                //intent.putExtra("url_img",GlobalVariables.Urlbase +img2.get(1).replaceAll("\\s","%20"));
                v.getContext().startActivity(intent);

            }
        });

        iImag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   ((ListView) parent).performItemClick(convertView, position, 0);

                String adfhh=GlobalVariables.Urlbase +img2.get(2).replaceAll("\\s","%20");
                //Intent intent=new Intent(v.getContext(), ActImgNot.class);
                Intent intent=new Intent(v.getContext(), ActSwipeImg.class);
                intent.putExtra("post",2);
                intent.putExtra("position_p",position);
                intent.putExtra("url_img",img2);

                //intent.putExtra("url_img",adfhh);

                v.getContext().startActivity(intent);

            }
        });

        iImag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   ((ListView) parent).performItemClick(convertView, position, 0);
               // Intent intent=new Intent(v.getContext(), ActImgNot.class);
                Intent intent=new Intent(v.getContext(), ActSwipeImg.class);
                intent.putExtra("post",3);
                intent.putExtra("position_p",position);

                intent.putExtra("url_img",img2);

                //intent.putExtra("url_img",GlobalVariables.Urlbase +img2.get(3).replaceAll("\\s","%20"));

                v.getContext().startActivity(intent);

            }
        });

        return rowView;
    }

}

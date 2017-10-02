package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.Img_Gal;


public class Adap_Img extends BaseAdapter {
    private Context context;
    public Adap_Img(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {


        return  GlobalVariables.listdetimg.size();
                //GlobalVariables.imagen2.get(GlobalVariables.pos_item_img_det).getFiledata().size();
    }

    @Override
    public Img_Gal getItem(int position) {
        return GlobalVariables.listdetimg.get(position);
                //GlobalVariables.imagen2.get(GlobalVariables.pos_item_img_det).getFiledata().get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();

       // getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }


        ImageView imagen = (ImageView) view.findViewById(R.id.img_adap);
        final Img_Gal item = getItem(position);
        Glide.with(imagen.getContext())
                .load(GlobalVariables.Urlbase+item.getUrlmin_imag())
                .into(imagen);

        return view;


    }
}

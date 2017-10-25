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
import com.pango.comunicaciones.model.Vid_Gal;


/**
 * Created by Andre on 22/08/2017.
 */

public class Adap_Vid extends BaseAdapter {

    private Context context;
    public Adap_Vid(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return GlobalVariables.listdetvid.size();
    }

    @Override
    public Vid_Gal getItem(int position) {
        return GlobalVariables.listdetvid.get(position);
    }

    @Override
    public long getItemId(int position) {

        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_video, viewGroup, false);
        }


        ImageView imagen = (ImageView) view.findViewById(R.id.imgvid_adap);

        final Vid_Gal item = getItem(position);
        Glide.with(imagen.getContext())
                .load(GlobalVariables.Urlbase +item.getUrlmin_vid().replaceAll("\\s","%20"))
                .into(imagen);

        return view;
    }


}

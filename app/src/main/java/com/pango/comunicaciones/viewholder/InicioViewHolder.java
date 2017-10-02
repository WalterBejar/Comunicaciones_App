package com.pango.comunicaciones.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pango.comunicaciones.R;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class InicioViewHolder extends RecyclerView.ViewHolder{
    // views del carrete
    public RelativeLayout relativeLayout;
    public TextView nFecha;
    public TextView nTitulo;
    public ImageView nImagen;

    public InicioViewHolder(View v) {
        super(v);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.lyt_carrete);
        nFecha = (TextView)  v.findViewById(R.id.txt_fecha);
        nTitulo = (TextView)  v.findViewById(R.id.txt_titulo);
        nImagen = (ImageView)  v.findViewById(R.id.img_carrete);
    }
}

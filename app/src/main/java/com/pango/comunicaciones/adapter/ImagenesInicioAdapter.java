package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.ImagenModel;
import com.pango.comunicaciones.viewholder.ImagenesInicioViewHolder;

import java.util.List;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class ImagenesInicioAdapter extends RecyclerView.Adapter<ImagenesInicioViewHolder>{
    private List<ImagenModel> data;
    private Context context;

    public ImagenesInicioAdapter(Context context, List<ImagenModel> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ImagenesInicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrete, parent, false);

        ImagenesInicioViewHolder holder = new ImagenesInicioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImagenesInicioViewHolder holder, final int position) {
        holder.nFecha.setText(data.get(position).getFecha());
        holder.nTitulo.setText(data.get(position).getTitulo());

        Glide.with(context)
                .load(GlobalVariables.Urlbase + data.get(position).getUrlImagen().replaceAll("\\s", "%20"))
                .into(holder.nImagen);

        holder.nImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mandar al detalle
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

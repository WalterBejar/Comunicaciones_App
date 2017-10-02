package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.VideoModel;
import com.pango.comunicaciones.viewholder.VideosInicioViewHolder;

import java.util.List;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class VideosInicioAdapter extends RecyclerView.Adapter<VideosInicioViewHolder>{

    private List<VideoModel> data;
    private Context context;

    public VideosInicioAdapter(Context context, List<VideoModel> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public VideosInicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrete, parent, false);

        VideosInicioViewHolder holder = new VideosInicioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final VideosInicioViewHolder holder, final int position) {
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

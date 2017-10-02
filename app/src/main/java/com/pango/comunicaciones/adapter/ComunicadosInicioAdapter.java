package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.ComunicadoModel;
import com.pango.comunicaciones.viewholder.ComunicadosInicioViewHolder;

import java.util.List;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class ComunicadosInicioAdapter  extends RecyclerView.Adapter<ComunicadosInicioViewHolder> {
    private List<ComunicadoModel> data;
    private Context context;

    public ComunicadosInicioAdapter(Context context, List<ComunicadoModel> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ComunicadosInicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrete, parent, false);

        ComunicadosInicioViewHolder holder = new ComunicadosInicioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ComunicadosInicioViewHolder holder, final int position) {
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

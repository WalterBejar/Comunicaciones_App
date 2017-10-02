package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.controller.NoticiasInicioController;
import com.pango.comunicaciones.model.NoticiaModel;
import com.pango.comunicaciones.viewholder.NoticiasInicioViewHolder;

import java.util.List;

/**
 * Created by WalterBCH on 25/9/2017.
 */

public class NoticiasInicioAdapter extends RecyclerView.Adapter<NoticiasInicioViewHolder>{

    private List<NoticiaModel> data;
    private Context context;

    private OnAdapterInteractionListener mListener;

    public NoticiasInicioAdapter(Context context, List<NoticiaModel> data, NoticiasInicioController controller) {
        this.data = data;
        this.context = context;
        mListener = controller;
    }

    @Override
    public NoticiasInicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrete, parent, false);

        NoticiasInicioViewHolder holder = new NoticiasInicioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NoticiasInicioViewHolder holder, final int position) {
        holder.nFecha.setText(data.get(position).getFecha());
        holder.nTitulo.setText(data.get(position).getTitulo());
        if (data.get(position).getIsChecked())
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#dedede"));
        else
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));

        Glide.with(context)
                .load(GlobalVariables.Urlbase + data.get(position).getUrlImagen().replaceAll("\\s", "%20"))
                .into(holder.nImagen);

        holder.nImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNewsSelected(data.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onFirstNewRender() {
        notifyItemChanged(0);
    }

    // Interface para comunicarnos al controlador
    public interface OnAdapterInteractionListener {
        void onNewsSelected(NoticiaModel noticia);
    }
}

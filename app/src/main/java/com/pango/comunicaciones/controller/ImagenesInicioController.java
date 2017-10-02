package com.pango.comunicaciones.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.ImagenesInicioAdapter;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.ImagenModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class ImagenesInicioController {

    View v;
    RecyclerView listViewImagenes;
    List<ImagenModel> listaImagenes = new ArrayList<ImagenModel>();
    List<Imagen> imagenes = GlobalVariables.imagen2;

    public ImagenesInicioController(View v){
        this.v = v;
        listViewImagenes = (RecyclerView) v.findViewById(R.id.listViewImagenes);
        //listViewNoticias.setHasFixedSize(true);
    }

    public void Execute() {
        DateFormat formatoInicial = new SimpleDateFormat("yyyy-mm-dd'T'00:00:00", new Locale("es", "ES"));
        DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

        try {
            for (Imagen imagen : imagenes) {
                ImagenModel imagenModel = new ImagenModel();
                imagenModel.setTitulo(imagen.getTitulo());
                imagenModel.setFecha(formatoRender.format(formatoInicial.parse(imagen.getFecha())));
                imagenModel.setUrlImagen(imagen.getFiledata().get(0).getUrlmin_imag());
                listaImagenes.add(imagenModel);
            }

        /*for (int i = 0; i < 10; i++)
        {
            ImagenModel imagen = new ImagenModel();
            imagen.setTitulo("Este es el titulo de la imagen Nro: " + (i+1));
            imagen.setFecha("21-09-2017");
            listaImagenes.add(imagen);
        }*/

            ImagenesInicioAdapter adapter = new ImagenesInicioAdapter(v.getContext(), listaImagenes);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(v.getContext());
            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (listaImagenes.size() > 0 & listViewImagenes != null) {
                listViewImagenes.setAdapter(adapter);
            }
            listViewImagenes.setLayoutManager(MyLayoutManager);

        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
}

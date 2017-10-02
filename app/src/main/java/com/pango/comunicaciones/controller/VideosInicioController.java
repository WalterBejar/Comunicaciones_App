package com.pango.comunicaciones.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.VideosInicioAdapter;
import com.pango.comunicaciones.model.Video;
import com.pango.comunicaciones.model.VideoModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class VideosInicioController {

    View v;
    RecyclerView listViewVideos;
    List<VideoModel> listaVideos = new ArrayList<VideoModel>();
    List<Video> videos = GlobalVariables.vidlist;

    public VideosInicioController(View v){
        this.v = v;
        listViewVideos = (RecyclerView) v.findViewById(R.id.listViewVideos);
        //listViewNoticias.setHasFixedSize(true);
    }

    public void Execute() {
        try {

            DateFormat formatoInicial = new SimpleDateFormat("yyyy-mm-dd'T'00:00:00", new Locale("es", "ES"));
            DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

            for (Video video : videos) {
                VideoModel videoModel = new VideoModel();
                videoModel.setTitulo(video.getTitulo());
                videoModel.setFecha(formatoRender.format(formatoInicial.parse(video.getFecha())));
                videoModel.setUrlImagen(video.getFiledata().get(0).getUrlmin_vid());
                listaVideos.add(videoModel);
            }

        /*for (int i = 0; i < 10; i++)
        {
            VideoModel video = new VideoModel();
            video.setTitulo("Este es el titulo del video Nro: " + (i+1));
            video.setFecha("21-09-2017");
            listaVideos.add(video);
        }*/


            VideosInicioAdapter adapter = new VideosInicioAdapter(v.getContext(), listaVideos);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(v.getContext());
            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (listaVideos.size() > 0 & listViewVideos != null) {
                listViewVideos.setAdapter(adapter);
            }
            listViewVideos.setLayoutManager(MyLayoutManager);

        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
}

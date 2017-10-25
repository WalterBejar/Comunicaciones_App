package com.pango.comunicaciones.model;

import java.util.List;

/**
 * Created by Andre on 21/08/2017.
 */

public class Video {

    private String cod_reg;
    private int icon;
    private String fecha;
    private String titulo;
    private List<Vid_Gal> filedata;
    private  int cant_video;

    public Video() {
    }

    public Video(String cod_reg, int icon, String fecha, String titulo, List<Vid_Gal> filedata, int cant_video) {
        this.cod_reg = cod_reg;
        this.icon = icon;
        this.fecha = fecha;
        this.titulo = titulo;
        this.filedata = filedata;
        this.cant_video = cant_video;
    }

    public String getCod_reg() {
        return cod_reg;
    }

    public void setCod_reg(String cod_reg) {
        this.cod_reg = cod_reg;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Vid_Gal> getFiledata() {
        return filedata;
    }

    public void setFiledata(List<Vid_Gal> filedata) {
        this.filedata = filedata;
    }

    public int getCant_video() {
        return cant_video;
    }

    public void setCant_video(int cant_video) {
        this.cant_video = cant_video;
    }
}

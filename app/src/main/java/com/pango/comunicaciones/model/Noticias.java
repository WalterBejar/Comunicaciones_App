package com.pango.comunicaciones.model;

import java.util.ArrayList;

public class Noticias {
    private String cod_reg;
    private int icon;
    private String fecha;
    private String titulo;
    private String descripcion;
    private ArrayList<String> filedata;


    /*
    private String correlativo;
    private String url;
    private String urlmin;*/

    public Noticias() {
    }

    public Noticias(String cod_reg, int icon, String fecha, String titulo, String descripcion, ArrayList<String> filedata) {
        this.cod_reg = cod_reg;
        this.icon = icon;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.filedata = filedata;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<String> getFiledata() {
        return filedata;
    }

    public void setFiledata(ArrayList<String> filedata) {
        this.filedata = filedata;
    }
}

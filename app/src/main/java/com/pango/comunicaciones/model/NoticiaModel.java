package com.pango.comunicaciones.model;

import java.util.ArrayList;

public class NoticiaModel {
    private String cod_reg;
    private String tipo;
    private int icon;
    private String nom_publicador;
    private String fecha;
    private String titulo;
    private String descripcion;
    private String urlImagen;
    private ArrayList<String> filedata;

    private boolean isChecked;


    /*
    private String correlativo;
    private String url;
    private String urlmin;*/

    public NoticiaModel() {
        isChecked = false;
    }

    public NoticiaModel(String cod_reg, String tipo, int icon, String nom_publicador, String fecha, String titulo, String descripcion, ArrayList<String> filedata) {
        this.cod_reg = cod_reg;
        this.tipo = tipo;
        this.icon = icon;
        this.nom_publicador = nom_publicador;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.filedata = filedata;

        isChecked = false;
    }

    public String getCod_reg() {
        return cod_reg;
    }

    public void setCod_reg(String cod_reg) {
        this.cod_reg = cod_reg;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNom_publicador() {
        return nom_publicador;
    }

    public void setNom_publicador(String nom_publicador) {
        this.nom_publicador = nom_publicador;
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

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlImagen() {
        return this.urlImagen;
    }
}

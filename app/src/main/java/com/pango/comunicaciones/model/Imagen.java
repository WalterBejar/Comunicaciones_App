package com.pango.comunicaciones.model;

import java.util.List;

/**
 * Created by Andre on 11/08/2017.
 */

public class Imagen {

    private String cod_reg;
    private String tipo;
    private int icon;
    private String nom_publicador;
    private String fecha;
    private String titulo;
    private List<Img_Gal> filedata;
    private int count_img;


    public Imagen() {
    }

    public Imagen(String cod_reg, String tipo, int icon, String nom_publicador, String fecha, String titulo, List<Img_Gal> filedata, int count_img) {
        this.cod_reg = cod_reg;
        this.tipo = tipo;
        this.icon = icon;
        this.nom_publicador = nom_publicador;
        this.fecha = fecha;
        this.titulo = titulo;
        this.filedata = filedata;
        this.count_img = count_img;
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

    public List<Img_Gal> getFiledata() {
        return filedata;
    }

    public void setFiledata(List<Img_Gal> filedata) {
        this.filedata = filedata;
    }

    public int getCount_img() {
        return count_img;
    }

    public void setCount_img(int count_img) {
        this.count_img = count_img;
    }
}

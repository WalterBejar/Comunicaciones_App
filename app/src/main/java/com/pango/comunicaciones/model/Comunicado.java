package com.pango.comunicaciones.model;

import java.util.ArrayList;

/**
 * Created by Andre on 24/08/2017.
 */

public class Comunicado {

    public String cod_reg;
    //private String tipo;
    public int icon;
    //private String nom_publicador;
    public String fecha;
    public String titulo;
    public String descripcion;
    public String urlmin;


    public Comunicado() {
    }

    public Comunicado(String cod_reg, int icon, String fecha, String titulo, String descripcion, String urlmin) {
        this.cod_reg = cod_reg;
        this.icon = icon;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlmin = urlmin;
    }
/*
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

    public String getUrlmin() {
        return urlmin;
    }

    public void setUrlmin(String urlmin) {
        this.urlmin = urlmin;
    }
    */
}

package com.pango.comunicaciones.model;

import java.util.ArrayList;

/**
 * Created by Andre on 25/08/2017.
 */

public class Publicacion {
   // Noticias


        public static final int T_NOTICIA = 0;
        public static final int T_COMUNICADO = 1;
        public static final int T_IMAGEN = 2;
        public static final int T_VIDEO = 3;



    private String cod_reg;
    private int tipo;
    private int icon;
    private String nom_publicador;
    private String fecha;
    private String titulo;

    private String descripcion;

    ArrayList<String> urlmin;


   // private String urlmin1;

//imagenes
   // private String urlmin2;
   // private String urlmin3;

    //videos

    private int count_p;
    //comunicados


    public Publicacion() {
    }


    public Publicacion(String cod_reg, int tipo, int icon, String nom_publicador, String fecha, String titulo, String descripcion, ArrayList<String> urlmin, int count_p) {
        this.cod_reg = cod_reg;
        this.tipo = tipo;
        this.icon = icon;
        this.nom_publicador = nom_publicador;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlmin = urlmin;
        this.count_p = count_p;
    }

    public String getCod_reg() {
        return cod_reg;
    }

    public void setCod_reg(String cod_reg) {
        this.cod_reg = cod_reg;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
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

    public ArrayList<String> getUrlmin() {
        return urlmin;
    }

    public void setUrlmin(ArrayList<String> urlmin) {
        this.urlmin = urlmin;
    }

    public int getCount_p() {
        return count_p;
    }

    public void setCount_p(int count_p) {
        this.count_p = count_p;
    }
}

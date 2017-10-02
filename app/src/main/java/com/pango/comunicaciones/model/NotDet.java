package com.pango.comunicaciones.model;



public class NotDet {
    private String nombre;
    private int tamanio;
    private String tipo_arc;
    private String url_file;
    private String tipo_det;

    public NotDet() {
    }


    public NotDet(String nombre, int tamanio, String tipo_arc, String url_file, String tipo_det) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.tipo_arc = tipo_arc;
        this.url_file = url_file;
        this.tipo_det = tipo_det;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public String getTipo_arc() {
        return tipo_arc;
    }

    public void setTipo_arc(String tipo_arc) {
        this.tipo_arc = tipo_arc;
    }

    public String getUrl_file() {
        return url_file;
    }

    public void setUrl_file(String url_file) {
        this.url_file = url_file;
    }

    public String getTipo_det() {
        return tipo_det;
    }

    public void setTipo_det(String tipo_det) {
        this.tipo_det = tipo_det;
    }
}

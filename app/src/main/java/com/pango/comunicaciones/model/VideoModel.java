package com.pango.comunicaciones.model;

import java.util.List;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class VideoModel {

    private String cod_reg;
    private String tipo;
    private int icon;
    private String nom_publicador;
    private String fecha;
    private String titulo;
    private List<Vid_Gal> filedata;
    private  int cant_video;
    private String urlImagen;

    public VideoModel() {
    }

    public VideoModel(String cod_reg, String tipo, int icon, String nom_publicador, String fecha, String titulo, List<Vid_Gal> filedata, int cant_video) {
        this.cod_reg = cod_reg;
        this.tipo = tipo;
        this.icon = icon;
        this.nom_publicador = nom_publicador;
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

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlImagen() {
        return this.urlImagen;
    }
}

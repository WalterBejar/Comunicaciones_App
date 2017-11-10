package com.pango.comunicaciones.model;


public class Img_Gal {


    private String Correlativo;
    private String url_img;
    private String urlmin_imag;


    public Img_Gal() {
    }
/*
    public Img_Gal(String correlativo, String urlmin_imag) {
        Correlativo = correlativo;
        this.urlmin_imag = urlmin_imag;
    }*/

    public Img_Gal(String correlativo, String url_img, String urlmin_imag) {
        Correlativo = correlativo;
        this.url_img = url_img;
        this.urlmin_imag = urlmin_imag;
    }

    public String getCorrelativo() {
        return Correlativo;
    }

    public void setCorrelativo(String correlativo) {
        Correlativo = correlativo;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getUrlmin_imag() {
        return urlmin_imag;
    }

    public void setUrlmin_imag(String urlmin_imag) {
        this.urlmin_imag = urlmin_imag;
    }



    public int getId() {
        int a=Correlativo.hashCode();
        return a;
    }


}

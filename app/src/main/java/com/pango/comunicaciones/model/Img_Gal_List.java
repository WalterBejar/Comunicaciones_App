package com.pango.comunicaciones.model;

/**
 * Created by Andre on 06/11/2017.
 */

public class Img_Gal_List {
    private String Correlativo;
    private String urlmin_imag;


    public Img_Gal_List() {
    }

    public Img_Gal_List(String correlativo, String urlmin_imag) {
        Correlativo = correlativo;
        this.urlmin_imag = urlmin_imag;
    }

    public String getCorrelativo() {
        return Correlativo;
    }

    public void setCorrelativo(String correlativo) {
        Correlativo = correlativo;
    }

    public String getUrlmin_imag() {
        return urlmin_imag;
    }

    public void setUrlmin_imag(String urlmin_imag) {
        this.urlmin_imag = urlmin_imag;
    }
}

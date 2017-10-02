package com.pango.comunicaciones.model;

/**
 * Created by Andre on 21/08/2017.
 */

public class Vid_Gal {

    private String Correlativo;
    private String url_vid;
    private String urlmin_vid;


    public Vid_Gal() {
    }

    public Vid_Gal(String correlativo, String url_vid, String urlmin_vid) {
        Correlativo = correlativo;
        this.url_vid = url_vid;
        this.urlmin_vid = urlmin_vid;
    }

    public String getCorrelativo() {
        return Correlativo;
    }

    public void setCorrelativo(String correlativo) {
        Correlativo = correlativo;
    }

    public String getUrl_vid() {
        return url_vid;
    }

    public void setUrl_vid(String url_vid) {
        this.url_vid = url_vid;
    }

    public String getUrlmin_vid() {
        return urlmin_vid;
    }

    public void setUrlmin_vid(String urlmin_vid) {
        this.urlmin_vid = urlmin_vid;
    }

    public int getId() {
        int a=Correlativo.hashCode();
        return a;
    }
}

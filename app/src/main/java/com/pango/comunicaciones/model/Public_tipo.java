package com.pango.comunicaciones.model;

/**
 * Created by Andre on 28/08/2017.
 */

public class Public_tipo {




    private Publicacion public_cont;
    private int tipo_adap;


    public Public_tipo() {
    }

    public Public_tipo(Publicacion public_cont, int tipo_adap) {
        this.public_cont = public_cont;
        this.tipo_adap = tipo_adap;
    }

    public Publicacion getPublic_cont() {
        return public_cont;
    }

    public void setPublic_cont(Publicacion public_cont) {
        this.public_cont = public_cont;
    }

    public int getTipo_adap() {
        return tipo_adap;
    }

    public void setTipo_adap(int tipo_adap) {
        this.tipo_adap = tipo_adap;
    }
}

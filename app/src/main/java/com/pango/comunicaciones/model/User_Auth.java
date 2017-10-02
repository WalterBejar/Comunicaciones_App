package com.pango.comunicaciones.model;

/**
 * Created by Andre on 01/09/2017.
 */

public class User_Auth {
    private String usuario;
    private String pass;
    private String dominio;


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }
}

package com.pango.comunicaciones.model;

/**
 * Created by Andre on 06/09/2017.
 */

public class Notificacion {


    public static final int T_NOTICIA = 0;
    public static final int T_COMUNICADO = 1;
    public static final int T_IMAGEN = 2;
    public static final int T_VIDEO = 3;


    private String cod_reg;
    private int tipo;


    private int icon_alerta;
    private String titulo;
    private String fecha;


    public Notificacion() {
    }

    public Notificacion(String cod_reg, int icon_alerta, String titulo, String fecha ) {
        this.cod_reg = cod_reg;
        this.icon_alerta = icon_alerta;
        this.titulo = titulo;
        this.fecha = fecha;
    }


    public String getCod_reg() {
        return cod_reg;
    }

    public void setCod_reg(String cod_reg) {
        this.cod_reg = cod_reg;
    }

    public int getIcon_alerta() {
        return icon_alerta;
    }

    public void setIcon_alerta(int icon_alerta) {
        this.icon_alerta = icon_alerta;
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
}

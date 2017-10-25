package com.pango.comunicaciones;

import com.pango.comunicaciones.model.ComDet;
import com.pango.comunicaciones.model.Comunicado;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.Img_Gal;
import com.pango.comunicaciones.model.NotDet;
import com.pango.comunicaciones.model.Noticias;
import com.pango.comunicaciones.model.Notificacion;
import com.pango.comunicaciones.model.Vid_Gal;
import com.pango.comunicaciones.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 25/09/2017.
 */

public class GlobalVariables {

    public static String token_auth=null;//tendra el token con la autenticacion
    public static String Urlbase= "https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/";
    public static String Urlbase2 = "entrada/getpaginated/";
    public static String url_token="membership/authenticate?username=antapaccay&password=Tintaya123.&domain=anyaccess";


    public static String CodPersona;
    public static String Nombres;

    //var global de noticias
    public static List<Noticias> noticias2 = new ArrayList<>();
    public static int contador;

    public static int contNoticia;
    public static int contComunicado;
    public static int contFotos;
    public static int contVideos;

    public static int cont_item;
    public static Noticias not2pos;
    public static boolean doclic=false;
    public static List<NotDet> listdetnot = new ArrayList<>();


    //var global comunicados
    public static List<Comunicado> comlist = new ArrayList<Comunicado>();
    public static Comunicado com_pos;
    public static int pos_item_com;
    public static List<ComDet> listdetcom = new ArrayList<>();

    //var global imagenes
    public static int pos_item_img_det;
    public static Imagen img_get;
    public static List<Imagen> imagen2 = new ArrayList<>();
    public static List<Img_Gal> listdetimg = new ArrayList<>();
    public static String cod_public;

    //var global videos
    public static List<Video> vidlist = new ArrayList<>();
    public static int pos_item_vid;
    public static Video vid_det;

    public static List<Vid_Gal> listdetvid = new ArrayList<>();
    public static int cont_posvid=0;


    //autenticacion
    public static String reemplazar(String cadena, String busqueda, String reemplazo) {
        return cadena.replaceAll(busqueda, reemplazo);
    }
    public static String reemplazarUnicode(String cadena) {
        String cadena1 = GlobalVariables.reemplazar(cadena, "\\\\u000a", "\n");
        String cadena2 = GlobalVariables.reemplazar(cadena1,"\\\\u000d", "\r");
        String cadena3 = GlobalVariables.reemplazar(cadena2,"\\\\u0009", "\t");
        return cadena3;
    }


    public static List<Notificacion> notific_data = new ArrayList<Notificacion>();
    public static String cod_public_com;

    public static int cont_alert=1;


    public static int con_status;
    public static String dominio="anyaccess";
    public static int anchoMovil;

    public static int num_vid=3;

}

package com.pango.comunicaciones;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.util.Patterns;

import com.pango.comunicaciones.model.ComDet;
import com.pango.comunicaciones.model.Comunicado;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.Img_Gal;
import com.pango.comunicaciones.model.NotDet;
import com.pango.comunicaciones.model.Noticias;
import com.pango.comunicaciones.model.Notificacion;
import com.pango.comunicaciones.model.TicketModel;
import com.pango.comunicaciones.model.Vid_Gal;
import com.pango.comunicaciones.model.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by Andre on 25/09/2017.
 */

public class GlobalVariables {

    public static String token_auth=null;//tendra el token con la autenticacion
    public static String Urlbase= "https://app.antapaccay.com.pe/SCOM_Service/api/";
    public static String Urlbase2 = "entrada/getpaginated/";
    public static String url_token="membership/authenticate?username=antapaccay&password=Tintaya123.&domain=anyaccess";

    public static String versionApk="5.0";
    public static String versionFromServer="";

    public static String CodPersona="";
    public static String Nombres="";

    //VAR GLOBAL TICKET
    public static List<TicketModel> ListTickets = new ArrayList<>();
    public static int contTickets=0;
    public static int num_tickets=3;

    //var global de noticias
    public static List<Noticias> noticias2 = new ArrayList<>();
    public static int contador=0;


    public static int contNoticia=0;
    public static int contComunicado=0;
    public static int contFotos=0;
    public static int contVideos=0;

    public static int cont_item=0;
    public static Noticias not2pos;
    public static boolean doclic=false;
    public static List<NotDet> listdetnot = new ArrayList<>();


    //var global comunicados
    public static List<Comunicado> comlist = new ArrayList<Comunicado>();
    public static Comunicado com_pos;
    public static int pos_item_com=0;
    public static List<ComDet> listdetcom = new ArrayList<>();

    //var global imagenes
    public static int pos_item_img_det=0;
    public static Imagen img_get;
    public static List<Imagen> imagen2 = new ArrayList<>();
    public static List<Img_Gal> listdetimg = new ArrayList<>();
    public static String cod_public="";

    //var global videos
    public static List<Video> vidlist = new ArrayList<>();
    public static int pos_item_vid=0;
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
    //public static String cod_public_com="";

    public static int cont_alert=1;


    public static int con_status=0;
    public static String dominio="anyaccess";
    public static int anchoMovil=0;

    public static int num_vid=3;

    public static int contNotificA=0;
    public static int contNotific=0;

    public static int contpublic=1;

    public static int contpublicNot=1;
    public static int contpublicImg=1;
    public static int contpublicVid=1;
    public static int contpublicCom=1;





    //public static boolean isScrolling;


   /* public static Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com.pe");
            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }*/


   /* public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }*/


    public static boolean flagcom=true;

    public static boolean flagUpSc=false;

    public static boolean flag_notificacion=false;

    //public static  boolean sw_hd_video;

    public static String cal_sd_hd="";

    public static boolean flag_orienta=true;

    public static int cont_pub_new=0;

    public static boolean flag_up_toast=false;

    public static int position=0;



    public static Stack<Fragment> fragmentStack= new Stack<Fragment>();
    public static String id_phone="Android@";


    public static int con_status_video=0;

    public static boolean flag_changed=false;


    public static boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    public static boolean is_notification=true;

    public static List<String> data_fotos;// = new ArrayList<String>();

    public static int positionImg=0;
}

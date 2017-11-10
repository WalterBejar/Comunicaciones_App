package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.SplashScreenActivity;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.model.Comunicado;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.Img_Gal;
import com.pango.comunicaciones.model.Img_Gal_List;
import com.pango.comunicaciones.model.Noticias;
import com.pango.comunicaciones.model.Vid_Gal;
import com.pango.comunicaciones.model.Video;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataController extends AsyncTask<String,Void,Void> {
int varcant;
    String url;
    String opcion;
    SplashScreenActivity splashScreenActivity;

    ProgressDialog progressDialog;
    ArrayList<Noticias> noticiaData=new ArrayList<Noticias>();
    List<Imagen> imagenData=new ArrayList<Imagen>();
    List<Comunicado> comunicadoData=new ArrayList<Comunicado>();
    List<Video> videoData=new ArrayList<Video>();

    public DataController(String url,String opcion, SplashScreenActivity splashScreenActivity){
        this.url=url;
        this.opcion=opcion;
        this.splashScreenActivity=splashScreenActivity;
    }

    @Override
    protected Void doInBackground(String... params) {
        String a = params[0];
        String b = params[1];

       if (GlobalVariables.Urlbase.equals("")){

           obtener_noticia(a,b);
           obtener_comunicado(a,b);
           obtener_imagen(a,b);
           obtener_video(a,b);

    } else {

           getToken gettoken=new getToken();
           gettoken.getToken();

           obtener_noticia(a,b);
           obtener_comunicado(a,b);
           obtener_imagen(a,b);
           obtener_video(a,b);

       }

        return null;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
    @Override
    protected void onPreExecute() {
        if(opcion=="get") {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(splashScreenActivity, "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get") {

                GlobalVariables.noticias2=noticiaData;
                GlobalVariables.comlist=comunicadoData;
                GlobalVariables.imagen2=imagenData;
                GlobalVariables.vidlist=videoData;


            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }




    public void obtener_noticia(String a, String b){
        try {
        HttpResponse response;

        if(opcion=="get"){
            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP01");//url de cada publicacion

                get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                response = httpClient.execute(get);

                String respstring = EntityUtils.toString(response.getEntity());
                JSONObject respJSON = new JSONObject(respstring);

                JSONArray data_p = respJSON.getJSONArray("Data");
                GlobalVariables.contNoticia = respJSON.getInt("Count");

                for (int i = 0; i < data_p.length(); i++) {
                    JSONObject h = data_p.getJSONObject(i);
                    String Tipo = h.getString("Tipo");


                    String CodRegistro = h.getString("CodRegistro");
                    String Autor = h.getString("Autor");
                    String Fecha = h.getString("Fecha");
                    String Titulo = h.getString("Titulo");
                    String Descripcion = h.getString("Descripcion");
                    int icon = R.drawable.ic_menu_noticias;

                    JSONObject Files = h.getJSONObject("Files");

                    JSONArray Data2 = Files.getJSONArray("Data");

                    JSONObject w = Data2.getJSONObject(0);
                    String Urlmin2 = w.getString("Urlmin");

                    String[] parts = Urlmin2.split("550px;");
                    //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                    //String part2 = parts[1]; //obtiene: 19-A

                    String Urlmin = parts[0] + GlobalVariables.anchoMovil + "px;" + parts[1];

                    noticiaData.add(new Noticias(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));

                }

            }catch (Exception ex){
                Log.w("Error get\n",ex);
            }
        }
    }
    catch (Throwable e) {
        Log.d("InputStream", e.getLocalizedMessage());
    }
}


    public void obtener_comunicado(String a, String b){
        try {
            HttpResponse response;

            if(opcion=="get"){
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP02");//url de cada publicacion

                    get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray data_p = respJSON.getJSONArray("Data");
                    GlobalVariables.contComunicado = respJSON.getInt("Count");


                    for (int i = 0; i < data_p.length(); i++) {
                        JSONObject h = data_p.getJSONObject(i);
                        String Tipo = h.getString("Tipo");


                        String CodRegistro = h.getString("CodRegistro");
                        String Autor = h.getString("Autor");
                        String Fecha = h.getString("Fecha");
                        String Titulo = h.getString("Titulo");
                        String Descripcion = h.getString("Descripcion");
                        int icon = R.drawable.ic_menu_publicaciones;

                        JSONObject Files = h.getJSONObject("Files");
                        JSONArray Data2 = Files.getJSONArray("Data");


                        JSONObject t = Data2.getJSONObject(0);
                        String Urlmin2 = t.getString("Urlmin");

                        String[] parts = Urlmin2.split("550px;");
                        //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                        //String part2 = parts[1]; //obtiene: 19-A

                        String Urlmin = parts[0] + GlobalVariables.anchoMovil + "px;" + parts[1];

                        comunicadoData.add(new Comunicado(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));



                    }
                    // des_data
                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                }
            }
        }
        catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }


    public void obtener_imagen(String a, String b){
        try {
            HttpResponse response;

            if(opcion=="get"){
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP03");//url de cada publicacion

                    //get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray data_p = respJSON.getJSONArray("Data");
                    GlobalVariables.contFotos = respJSON.getInt("Count");


                    for (int i = 0; i < data_p.length(); i++) {
                        JSONObject h = data_p.getJSONObject(i);
                        String Tipo = h.getString("Tipo");


                        String CodRegistro = h.getString("CodRegistro");
                        String Autor = h.getString("Autor");
                        String Fecha = h.getString("Fecha");
                        String Titulo = h.getString("Titulo");
                        int icon = R.drawable.ic_menu_gallery;

                        JSONObject Files = h.getJSONObject("Files");
                        int cant_img = Files.getInt("Count");
                        JSONArray Data2 = Files.getJSONArray("Data");

                        List<Img_Gal_List> datafImg = new ArrayList<>();
                        for (int j = 0; j < Data2.length(); j++) {
                            JSONObject m = Data2.getJSONObject(j);

                            String Correlativo = m.getString("Correlativo");
                            //String Url = Utils.ChangeUrl(m.getString("Url"));
                            String Urlmin = Utils.ChangeUrl(m.getString("Urlmin"));

                            datafImg.add(new Img_Gal_List(Correlativo, Urlmin));
                        }

                        imagenData.add(new Imagen(CodRegistro, icon, Fecha, Titulo, datafImg, cant_img));
                    }
                    // des_data
                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                }
            }
        }
        catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }




    public void obtener_video(String a, String b){
        try {
            HttpResponse response;

            if(opcion=="get"){
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP04");//url de cada publicacion

                    get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray data_p = respJSON.getJSONArray("Data");
                    GlobalVariables.contVideos = respJSON.getInt("Count");


                    for (int i = 0; i < data_p.length(); i++) {
                        JSONObject h = data_p.getJSONObject(i);
                        String Tipo = h.getString("Tipo");


                        String CodRegistro = h.getString("CodRegistro");
                        String Autor = h.getString("Autor");
                        String Fecha = h.getString("Fecha");
                        String Titulo = h.getString("Titulo");
                        int icon = R.drawable.ic_menu_slideshow;

                        JSONObject Files = h.getJSONObject("Files");
                        int CantidadV = Files.getInt("Count");
                        JSONArray Data2 = Files.getJSONArray("Data");


                        List<Vid_Gal> datafile = new ArrayList<>();
                        for (int j = 0; j < Data2.length(); j++) {
                            JSONObject n = Data2.getJSONObject(j);

                            String Correlativo = n.getString("Correlativo");
                            String Url = Utils.ChangeUrl(n.getString("Url"));
                            String Urlmin = Utils.ChangeUrl(n.getString("Urlmin"));

                            datafile.add(new Vid_Gal(Correlativo, Url, Urlmin));

                        }

                        videoData.add(new Video(CodRegistro, icon, Fecha, Titulo, datafile, CantidadV));
                    }
                    // des_data
                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                }
            }
        }
        catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }

















}

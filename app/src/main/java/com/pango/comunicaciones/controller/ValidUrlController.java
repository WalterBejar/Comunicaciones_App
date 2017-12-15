package com.pango.comunicaciones.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.pango.comunicaciones.ActVidDet;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.SplashScreenActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by Andre on 17/11/2017.
 */

public class ValidUrlController extends AsyncTask<String,Void,Void> {

    View v;
    String url="";
    String opcion="";
    //FragmentNoticias Frag;
    ProgressDialog progressDialog;
    //Noticias noticia2;
    //List<Noticias> noticiaList=new ArrayList<Noticias>();
    ListView recList;
    int a;
    boolean cargaData=true;
    //int celda = 3;
    String Url_Video="";
    ActVidDet actVidDet;

    public ValidUrlController(String url,String opcion){
        //this.v=v;
        this.url=url;
        this.opcion=opcion;
        //this.Frag=Frag;
    }

    @Override
    protected Void doInBackground(String... params) {
        //red= GlobalVariables.isOnlineNet();
        try {
            HttpResponse response;
            Url_Video=params[0];


            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase.substring(0, GlobalVariables.Urlbase.length() - 4)+Url_Video);
                    get.setHeader("Content-type", "application/json");
                    GlobalVariables.con_status_video = httpClient.execute(get).getStatusLine().getStatusCode();

                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                    cargaData=false;
                }
            }
            //}
        }
        catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        if(opcion=="get") {
            super.onPreExecute();
            //if(GlobalVariables.noticias2.size()<GlobalVariables.num_vid) {
            //progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
            //}
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get"&&GlobalVariables.con_status_video==200) {
                //GlobalVariables.cont_pub_new=Integer.parseInt(cont_nvic);
                //progressDialog.dismiss();


            }


        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }







}

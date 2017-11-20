package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.model.Noticias;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import layout.FragmentNoticias;

/**
 * Created by Andre on 08/11/2017.
 */

public class contadorController extends AsyncTask<String,Void,Void> {

    View v;
    String url;
    String opcion;
    //FragmentNoticias Frag;
    ProgressDialog progressDialog;
    //Noticias noticia2;
    //List<Noticias> noticiaList=new ArrayList<Noticias>();
    ListView recList;
    int a;
    boolean cargaData=true;
    //int celda = 3;
    String cont_nvic;
    String tipo_public;
    public contadorController(View v,String url,String opcion){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        //this.Frag=Frag;

        //recList.setOnScrollListener(this);

    }

    @Override
    protected Void doInBackground(String... params) {
        //red= GlobalVariables.isOnlineNet();
        try {
            HttpResponse response;
            cont_nvic=params[0];
            tipo_public=params[1];

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+GlobalVariables.Urlbase2+1+"/"+0+tipo_public);
                    get.setHeader("Content-type", "application/json");
                    GlobalVariables.con_status = httpClient.execute(get).getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status==200) {

                        response = httpClient.execute(get);
                        String respstring = EntityUtils.toString(response.getEntity());

                        JSONObject respJSON = new JSONObject(respstring);

                        GlobalVariables.cont_pub_new = respJSON.getInt("Count");//obtiene el total de publicaciones en general

                    }
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
            if (opcion == "get"&&!cargaData) {
                GlobalVariables.cont_pub_new=Integer.parseInt(cont_nvic);
                //progressDialog.dismiss();
            }


        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }



}
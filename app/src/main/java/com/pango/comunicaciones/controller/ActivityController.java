package com.pango.comunicaciones.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.IActivity;
import com.pango.comunicaciones.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActivityController extends AsyncTask<String,Void,Void> {

    ProgressDialog progressDialog;
    IActivity activity;
    Activity ActContext;
    public String url;
    //String url_token;
    int con_status;
    String token_auth;
    String opcion;
    String respstring;
    String Resultado="";
    String Tipo="";
    boolean cargaData=true;
    String[] strings;

    View v;


    public ActivityController(String opcion, String url, IActivity activity, Activity ActContext) {
        this.activity = activity;
        this.url = url;
        this.ActContext = ActContext;
        this.opcion = opcion;
    }

    @Override
    protected void onPreExecute() {
        String paginas[]= opcion.split("-");
        int pag=1;
        if(paginas.length>1) pag=Integer.parseInt(paginas[1]);
        if(pag==1){
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(ActContext, "", "Cargando");
        }

    }


    @Override
    protected Void doInBackground(String... strings) {
        ConnectivityManager cmanager = (ConnectivityManager) ActContext.getSystemService(ActContext.CONNECTIVITY_SERVICE);
        final NetworkInfo info= cmanager.getActiveNetworkInfo();
        this.strings=strings;
        if(info!=null&&info.isConnected()) {

            try {
                String json = strings[0];
                if (opcion.contains("get")) {
                    Tipo = json;
                    //generarToken(url_token);
                    HttpResponse response;
                    HttpClient httpClient = Utils.getNewHttpClient();
                    HttpGet get = new HttpGet(url);
                    //GlobalVariables.token_auth=token_auth;
                    //if (opcion == "get" && GlobalVariables.token_auth.length() > 40) {
                    //get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    get.setHeader("Content-type", "application/json");
                    //}
                    //get.abort();
                    response = httpClient.execute(get);
                    GlobalVariables.con_status = response.getStatusLine().getStatusCode();
                    respstring = EntityUtils.toString(response.getEntity());

                    /*
                    if(GlobalVariables.con_status== 401){
                        if(Utils.reloadToken(ActContext))doInBackground(strings);
                        else Toast.makeText(ActContext,"Ocurrio un error al recargar la aplicación, Reinicie la App.",Toast.LENGTH_SHORT).show();
                    }
                    else respstring = EntityUtils.toString(response.getEntity());

                    */

                    //JSONObject respJSON = new JSONObject(respstring);

                } else if (opcion.contains("post")) {
/*
                    Tipo = strings.length > 1 ? Tipo = strings[1] : "";
                    HttpResponse response;
                    InputStream inputStream = null;
                    String result = "";

                    HttpClient httpclient = Utils.getNewHttpClient();
                    HttpPost httpPost = new HttpPost(url);

                    StringEntity se = new StringEntity(json, "UTF-8");
                    httpPost.setEntity(se);
                    httpPost.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    httpPost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httpPost);

                    GlobalVariables.con_status = httpResponse.getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status== 401){
                        if(Utils.reloadToken(ActContext))doInBackground(strings);
                        else Toast.makeText(ActContext,"Ocurrio un error al recargar la aplicación, Reinicie la App.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        inputStream = httpResponse.getEntity().getContent();
                        if (inputStream != null)
                            result = convertInputStreamToString(inputStream);
                        else
                            result = "Did not work!";
                        String responsepost = GlobalVariables.reemplazarUnicode(result);
                        Resultado = responsepost;
                    }

                    */

                }

            } catch (Throwable e) {
                Log.d("InputStream", e.getLocalizedMessage());
                cargaData = false;

            }

        }else {
            GlobalVariables.con_status=0;
            //Toast.makeText(ActContext, "Not connected",Toast.LENGTH_LONG).show();
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
    protected void onPostExecute(Void result) {

        if(cargaData) {
            switch (GlobalVariables.con_status) {
                case 404:
                    //Toast.makeText((Context) activity,"Not Found",Toast.LENGTH_SHORT).show();
                    activity.error("Not Found "+ "("+ GlobalVariables.con_status + ")", Tipo);
                    break;
                case 307:
                    //Toast.makeText((Context) activity,"Se perdio la conexion al servidor",Toast.LENGTH_SHORT).show();
                    activity.error("Se perdio la conexion al servidor "+ "("+ GlobalVariables.con_status + ")", Tipo);
                    break;
                case 500:
                    //Toast.makeText((Context) activity,"Ocurrio un error interno en el servidor",Toast.LENGTH_SHORT).show();
                    activity.error("Ocurrio un error interno en el servidor "+ "("+ GlobalVariables.con_status + ")", Tipo);
                    break;
                case 0:
                    //IActivity activity, Activity ActContext
                    //Utils.cargar_alerta(ActContext.getApplicationContext(),ActContext);
                    activity.error("Se perdio la conexión a internet ", Tipo);

                    break;
                default:
                    if(opcion.contains("post"))
                        try {
                            activity.successpost(Resultado,Tipo);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    else try {
                        activity.success(respstring, Tipo);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            String paginas[]= opcion.split("-");
            int pag=1;
            if(paginas.length>1) pag=Integer.parseInt(paginas[1]);
            //if(pag==1) progressDialog.dismiss();
        }
        else {
            if(progressDialog!=null)progressDialog.dismiss();
            activity.error("Ocurrio un error al intentar enviar peticion.", Tipo);
        }

    }












}

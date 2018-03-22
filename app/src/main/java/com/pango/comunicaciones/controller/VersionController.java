package com.pango.comunicaciones.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Andre on 21/03/2018.
 */

public class VersionController extends AsyncTask<String,Void,Void> {



    Activity activity;
    //String url_token;
    //ProgressDialog progressDialog;

    //int con_status;
    String url_version;

    public VersionController(String url_version,Activity activity) {
        this.url_version = url_version;
        this.activity=activity;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url_version);
            get.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(get);
            String respstring2 = EntityUtils.toString(response.getEntity());
            //GlobalVariables.con_status = response.getStatusLine().getStatusCode();
            if (respstring2.equals("")) {
                GlobalVariables.versionFromServer = "";
                // GlobalVariables.con_status =0;
            } else {
                GlobalVariables.versionFromServer = respstring2.substring(1, respstring2.length() - 1);
                //Utils.token=respstring2.substring(1, respstring2.length() - 1);

            }

            //conseguir la data de usuario

            //Obtener_dataUser(GlobalVariables.token_auth);



        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        //progressDialog = ProgressDialog.show((Context) activity, "", "Iniciando sesión");
       /* if (opcion == "get") {
            super.onPreExecute();
            progressDialog = ProgressDialog.show((Context) activity, "", "Iniciando sesión");
        }*/
    }

    @Override
    protected void onPostExecute(Void result) {
/*
        switch (GlobalVariables.con_status) {
            case 0:
                Toast.makeText((Context) activity,"No hay conexion a internet",Toast.LENGTH_SHORT).show();
                break;
            case 401:
                Toast.makeText((Context) activity,"Ocurrio un error de conexion",Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText((Context) activity,"Not Found",Toast.LENGTH_SHORT).show();
                break;
            case 307:
                Toast.makeText((Context) activity,"Se perdio la conexion al servidor",Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText((Context) activity,"Ocurrio un error interno en el servidor",Toast.LENGTH_SHORT).show();
                break;

            default:
                if(GlobalVariables.token_auth.length()>40){
                    activity.success(""+GlobalVariables.con_status,"");

                }else{
                    Toast.makeText((Context) activity,GlobalVariables.token_auth,Toast.LENGTH_SHORT).show();
                    break;
                }


        }
*/
        //progressDialog.dismiss();
        //mainActivity.success();

    }






}

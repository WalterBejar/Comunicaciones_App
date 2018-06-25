package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pango.comunicaciones.Datos_Usuario;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.Noticias;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by BOB on 16/04/2018.
 */

public class UpdateController extends AsyncTask<String,Void,Void> {

    View v;
    boolean cargaData=true;
    Datos_Usuario Activity;
    String Resultado;
    ProgressDialog progressDialog;

    public UpdateController(Datos_Usuario activity){
        Resultado="";
        Activity=activity;
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
                HttpResponse response;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"media/UploadAllApp");
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    get.setHeader("Content-type", "application/json");

                    response = httpClient.execute(get);

                    GlobalVariables.con_status = response.getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status==200) {

                        String respstring2= EntityUtils.toString(response.getEntity());
                        Resultado =respstring2.substring(1, respstring2.length() - 1);
                    }


                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                    cargaData=false;
                }

        } catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(Activity, "Actualizando Base de datos.", "Esta operación puede tomar varios minutos, Espere...");
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (cargaData) {

                progressDialog.dismiss();

              /*  StringBuilder sb = new StringBuilder();
                String dataStr[]=Resultado.split("@");
                for (String item: dataStr) {
                    sb.append(item);
                    sb.append("\n");
                }
*/
                    AlertDialog alertDialog = new AlertDialog.Builder(Activity).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Resultado");
                    alertDialog.setIcon(R.drawable.confirmicon);
                    alertDialog.setMessage(Resultado.replace("@","\n"));
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Activity.finish();
                        }
                    });

                    alertDialog.show();

            } else {
                progressDialog.dismiss();
                Toast.makeText(Activity, "Error cuando se conectaba con el servidor", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception ex) {
            Log.w("Error", ex);
        }


    }
}

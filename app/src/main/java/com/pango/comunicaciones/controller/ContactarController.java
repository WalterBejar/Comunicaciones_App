package com.pango.comunicaciones.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pango.comunicaciones.Contactar;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Andre on 03/01/2018.
 */

public class ContactarController extends AsyncTask<String,Void,Void> {
    View v;
    String asunto="";
    String mensaje="";
    String opcion="";
    Contactar contactar;
    String Resultado="";
    boolean cargaData=true;
    ProgressDialog progressDialog;
    int status;
    public ContactarController(String asunto,String mensaje, String opcion, Contactar contactar) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.opcion = opcion;
        this.contactar = contactar;
        //recList=(ListView) v.findViewById(R.id.frag_not);
        //recList.setOnScrollListener(this);
    }



    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;

            if (opcion == "post") {
                InputStream inputStream = null;
                String result = "";

                try {
            //https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/membership/Recoverypass
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost (GlobalVariables.Urlbase+"persona/SendFeedback");
                    String json = "";
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("urlmin",asunto);
                    jsonObject.accumulate("url",mensaje);
                    json += jsonObject.toString();
                    StringEntity se = new StringEntity(json);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    httpPost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    status=httpResponse.getStatusLine().getStatusCode();
                    inputStream = httpResponse.getEntity().getContent();
                    if(inputStream != null)
                        result = convertInputStreamToString(inputStream);
                    else
                        result = "Did not work!";
                    String responsepost= GlobalVariables.reemplazarUnicode(result);
                    Resultado=responsepost;

                } catch (Exception ex) {
                    Log.w("Error get\n", ex);
                    cargaData = false;

                }
            }

        } catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
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
        if (opcion == "post") {
            //super.onPreExecute();
            progressDialog = ProgressDialog.show(contactar, "", "Enviando peticion");
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (opcion == "post" && cargaData) {
                progressDialog.dismiss();


                String es=Resultado.substring(1,2);
                int resul=Integer.parseInt(es);
                if (status!=200||resul==-1||resul==0) {

                    //Toast.makeText(contactar,Resultado,Toast.LENGTH_SHORT).show();

                    final AlertDialog alertDialog = new AlertDialog.Builder(contactar).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Error");
                    alertDialog.setIcon(R.drawable.erroricon);
                    alertDialog.setMessage("Ocurrio un problema durante el envio, inténtelo de nuevo");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();

                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(contactar).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Mensaje enviado");
                    alertDialog.setIcon(R.drawable.confirmicon);
                    alertDialog.setMessage("Su mensaje ha sido enviado");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            contactar.finish();
                        }
                    });
                    alertDialog.show();
                }

            } else {
                progressDialog.dismiss();
                Toast.makeText(contactar, "Error cuando se conectaba con el servidor", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception ex) {
            Log.w("Error", ex);
        }
    }

}

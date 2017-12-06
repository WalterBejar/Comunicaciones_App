package com.pango.comunicaciones.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.CambiarPassword;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Recuperar_password;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import layout.FragmentTickets;

/**
 * Created by Andre on 27/11/2017.
 */

public class ResPassController extends AsyncTask<String,Void,Void> {

    View v;
    int st;
    String email;
    String opcion;
    Recuperar_password recuperar_password;
    String Resultado="";
    boolean cargaData=true;

    //cambiar a fragmenttickets
    //FragmentTickets Frag;
    //String Resultado="";
    //String CodRegistro;
    ProgressDialog progressDialog;
    //CambiarPassword cambiarPassword;
    //String respstring;
    //boolean cargaData=true;
    //TextView tx_mensaje;

    //ArrayList<Integer> Roles= new ArrayList<Integer>();

    public ResPassController(String email, String opcion, Recuperar_password recuperar_password) {
        this.email = email;
        this.opcion = opcion;
        this.recuperar_password = recuperar_password;
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
                    HttpPost httpPost = new HttpPost (GlobalVariables.Urlbase+"membership/Recoverypass");

                    String json = "";
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("url",email);
                    json += jsonObject.toString();

                    StringEntity se = new StringEntity(json);

                    httpPost.setEntity(se);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httpPost);

                    inputStream = httpResponse.getEntity().getContent();
                    if(inputStream != null)
                        result = convertInputStreamToString(inputStream);
                    else
                        result = "Did not work!";
                    String responsepost= GlobalVariables.reemplazarUnicode(result);
                    Resultado=responsepost;


               /*     {
                        "url":"alexander.quille@glencore.com.pe"
                    }
*/


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
            progressDialog = ProgressDialog.show(recuperar_password, "", "Enviando peticion");
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (opcion == "post" && cargaData) {

                progressDialog.dismiss();
                String es=Resultado.substring(1,2);
                 //Toast.makeText(recuperar_password,Resultado,Toast.LENGTH_SHORT).show();

                if (es.equals("E")) {

                    Toast.makeText(recuperar_password,Resultado,Toast.LENGTH_SHORT).show();

                   /* Toast toast1= Toast.makeText(recuperar_password,Resultado,Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();*/

                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(recuperar_password).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Recuperar contraseña");
                    alertDialog.setIcon(R.drawable.confirmicon);
                    alertDialog.setMessage(Resultado);
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //GlobalVariables.flag_changed = true;
                            //cambiarPassword.finish();
                            //cambiarPassword.startActivity(getIntent());

                            recuperar_password.finish();
                           /* Save_status(false);
                            Intent intent = new Intent(cambiarPassword, MainActivity.class);
                            intent.putExtra("respuesta", true);
                            cambiarPassword.startActivity(intent);*/
                            //cambiarPassword.startActivity(new Intent(cambiarPassword, SplashScreenActivity.class));

                        }
                    });

                    alertDialog.show();



                }




            } else {
                progressDialog.dismiss();
                Toast.makeText(recuperar_password, "Error cuando se conectaba con el servidor", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception ex) {
            Log.w("Error", ex);
        }


    }
}
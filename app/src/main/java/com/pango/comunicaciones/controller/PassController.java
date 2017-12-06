package com.pango.comunicaciones.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.CambiarPassword;
import com.pango.comunicaciones.Datos_Usuario;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.MainActivity;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.ReservaTicketDetalle;
import com.pango.comunicaciones.ReservaTicketFiltro;
import com.pango.comunicaciones.SplashScreenActivity;
import com.pango.comunicaciones.Utils;

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

import layout.FragmentTickets;

/**
 * Created by Andre on 22/11/2017.
 */

public class PassController extends AsyncTask<String,Void,Void> {

    View v;
    int st;
    String url;
    String opcion;
    //cambiar a fragmenttickets
    FragmentTickets Frag;
    String Resultado="";
    String CodRegistro;
    ProgressDialog progressDialog;
    CambiarPassword cambiarPassword;
    String respstring;
    boolean cargaData=true;
    TextView tx_mensaje;

    //ArrayList<Integer> Roles= new ArrayList<Integer>();

    public PassController(CambiarPassword cambiarPassword, String url, String opcion){
        this.cambiarPassword=cambiarPassword;
        this.url=url;
        this.opcion=opcion;
        //recList=(ListView) v.findViewById(R.id.frag_not);
        //recList.setOnScrollListener(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String a=params[0];
            String b=params[1];
            String c=params[2];

            //generarToken(a,b,c);

            if(opcion=="get"){
                try {

                       // https://app.antapaccay.com.pe/ProPortal/SCOM_Serivice/menbership/Changuepass/Usuario/Passwors/NewPasword

                    //https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/membership/Changuepass?usuario=ccapatinta&password=123456&newpassword=ccapatinta
//https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/membership/Changuepass?usuario=ccapatinta&password=12345&newpassword=ccapatinta
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpGet get = new HttpGet(GlobalVariables.Urlbase+"membership/Changuepass?usuario="+a+"&password="+b+"&newpassword="+c);
                        //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                        get.setHeader("Content-type", "application/json");
                        response = httpClient.execute(get);
                        GlobalVariables.con_status = response.getStatusLine().getStatusCode();//getStatusLine().getStatusCode();

                    if(GlobalVariables.con_status==200) {
                         respstring = EntityUtils.toString(response.getEntity());
                    }

                    //else{respstring="";}

                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                    cargaData=false;

                }
            }

        }
        catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        if(opcion=="get") {
            //super.onPreExecute();
            progressDialog = ProgressDialog.show(cambiarPassword, "", "Enviando peticion");
        }

    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get"&&GlobalVariables.con_status==200&&cargaData) {

                progressDialog.dismiss();
                tx_mensaje=(TextView) cambiarPassword.findViewById(R.id.tx_mensaje);

              //  Toast.makeText(cambiarPassword,respstring,Toast.LENGTH_SHORT).show();

                if(respstring.contains(Utils.usuario)){


            AlertDialog alertDialog = new AlertDialog.Builder(cambiarPassword).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Cambio de contraseña");
            alertDialog.setIcon(R.drawable.confirmicon);
            alertDialog.setMessage(respstring);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GlobalVariables.flag_changed=true;
                    //cambiarPassword.finish();
                    //cambiarPassword.startActivity(getIntent());
                    Save_status(true);
                    Save_Datalogin(Utils.usuario,"");


                    /*Intent intent=new Intent(cambiarPassword, MainActivity.class);
                    intent.putExtra("respuesta",true);
                    cambiarPassword.startActivity(intent);
                    cambiarPassword.finish();
*/


                    cambiarPassword.startActivity(new Intent(cambiarPassword, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    cambiarPassword.finish();



                    //Datos_Usuario datos_usuario=new Datos_Usuario();
                    //datos_usuario.finish();
                    //cambiarPassword.startActivity(new Intent(cambiarPassword, SplashScreenActivity.class));

                }
            });

            alertDialog.show();

                }else{
                    tx_mensaje.setText(respstring);

                }



            }else {
                progressDialog.dismiss();
                Toast.makeText(cambiarPassword,"Error en el servidor",Toast.LENGTH_SHORT).show();

            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }


    public void Save_status(boolean ischecked){
        SharedPreferences check_status = cambiarPassword.getSharedPreferences("checked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_estado = check_status.edit();
        editor_estado.putBoolean("check", ischecked);
        editor_estado.commit();
    }

    public void Save_Datalogin(String user,String password){

        SharedPreferences user_login = cambiarPassword.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_user = user_login.edit();
        editor_user.putString("user", user);
        editor_user.putString("password",password);
        editor_user.commit();
    }



}

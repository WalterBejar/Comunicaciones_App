package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.ReservaTicketFiltro;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.model.Noticias;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import layout.FragmentInicio2;
import layout.FragmentTickets;


public class AuthController extends AsyncTask<String,Void,Void> {
    View v;
    int st=0;
    String url="";
    String opcion="";
    //cambiar a fragmenttickets
    FragmentTickets Frag;
    ProgressDialog progressDialog;

    String CodPersona="";
    String Nombres="";

    String usuario="";
    String email="";
    ArrayList<Integer> Roles= new ArrayList<Integer>();

    public AuthController(View v, String url, String opcion, FragmentTickets Frag){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.Frag=Frag;
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

            generarToken(a,b,c);

            if(opcion=="get"&&GlobalVariables.token_auth.length()>40){
                try {


                    if(GlobalVariables.con_status==200){

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"persona/Get_Usuario/"+GlobalVariables.id_phone);
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);

                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());

                    JSONObject respJSON = new JSONObject(respstring);
                        GlobalVariables.con_status=response.getStatusLine().getStatusCode();

                        if(GlobalVariables.con_status ==200)
                        {
                            CodPersona=respJSON.getString("DNI");
                            Nombres=respJSON.getString("Nombres");

                            usuario=respJSON.getString("CodUsuario");
                            email=respJSON.getString("Email");

                            JSONArray Data2 = respJSON.getJSONArray("Roles");
                            for (int j = 0; j < Data2.length(); j++) {
                                Roles.add((Integer) Data2.get(j));
                            }
                            st=1;
                        }
                        else st=0;

                    }else{
                        st=-1;
                        //response=Utils.token;


                    }

                }catch (Exception ex){
                    Log.w("Error get\n",ex);

                }
            }

        }
        catch (Throwable e) {
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
        if(opcion=="get") {
            //super.onPreExecute();
            progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Iniciando sesión");
        }

    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get"&&GlobalVariables.token_auth.length()>40) {

                if(st<0){
                    if(GlobalVariables.con_status==0)
                    Toast.makeText(v.getContext(),"Ocurrio un error interno en el servidor",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(v.getContext(),GlobalVariables.con_status+" Usuario y/o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else if(st== 0){
                    Toast.makeText(v.getContext(),"("+GlobalVariables.con_status+")"+" Ocurrio un error interno en el servidor",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(v.getContext(),"logueo correcto",Toast.LENGTH_SHORT).show();

                    Utils.codPersona=CodPersona;
                    Utils.nombres=Nombres;
                    Utils.usuario=usuario;
                    Utils.email=email;

                    Utils.esAdmin=false;
                    Utils.esAdminWeb=false;
                    for (int k=0;k<Roles.size();k++){
                        if(Roles.get(k)==1){
                            Utils.esAdmin=true;
                        }
                        else if(Roles.get(k)==4){
                            Utils.esAdminWeb=true;
                        }
                    }


                    Intent toReservaTicketFiltro = new Intent(v.getContext(), ReservaTicketFiltro.class);
                    v.getContext().startActivity(toReservaTicketFiltro);

                    progressDialog.dismiss();

                }

            }
            else {
                progressDialog.dismiss();
                Toast.makeText(v.getContext(),GlobalVariables.token_auth,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }




    public void generarToken(String a, String b, String c){


        try {
            HttpResponse response;
            HttpClient httpclient = new DefaultHttpClient();
            InputStream inputStream = null;
            String result = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("username",a);
                jsonObject.accumulate("password",b);
                jsonObject.accumulate("domain",c);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            HttpPost httpPost = new HttpPost (GlobalVariables.Urlbase+"membership/authenticate");

            StringEntity se = new StringEntity(jsonObject.toString(),"UTF-8");
            httpPost.setEntity(se);
            httpPost.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);

            GlobalVariables.con_status=httpResponse.getStatusLine().getStatusCode();

            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else  result = "Did not work!";

            String responsepost= GlobalVariables.reemplazarUnicode(result);
            GlobalVariables.token_auth = responsepost.substring(1, responsepost.length() - 1);
            Utils.token=responsepost.substring(1, responsepost.length() - 1);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
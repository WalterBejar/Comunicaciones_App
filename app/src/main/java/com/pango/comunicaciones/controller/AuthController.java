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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import layout.FragmentTickets;


public class AuthController extends AsyncTask<String,Void,Void> {
    View v;
    boolean st;
    String url;
    String opcion;
    FragmentTickets Frag;
    String Resultado="";
    String CodRegistro;
    ProgressDialog progressDialog;
    Noticias noticia2;

    String CodPersona;
    String Nombres;
    ArrayList<Integer> Roles= new ArrayList<Integer>();

   // ArrayList<String> dataUser=new ArrayList<String>();
   // List<User_Auth> user_auth=new ArrayList<User_Auth>();
    //ListView recList;
  //  int a;
   // int celda = 3;

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

            if(opcion=="get"){
                try {

                    if(GlobalVariables.con_status==200){
                    st=true;

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"persona/Get_Usuario");
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);
                    String respstring = EntityUtils.toString(response.getEntity());

                    JSONObject respJSON = new JSONObject(respstring);

                        CodPersona=respJSON.getString("DNI");
                        Nombres=respJSON.getString("Nombres");

                        JSONArray Data2 = respJSON.getJSONArray("Roles");
                        for (int j = 0; j < Data2.length(); j++) {
                            Roles.add((Integer) Data2.get(j));

                        }


                    }else{
                        st=false;
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
            progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Iniciando sesion");
        }

    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get") {

                if(st==false){
                    Toast.makeText(v.getContext(),"Usuario y/o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }else{
                    Toast.makeText(v.getContext(),"logueo correcto",Toast.LENGTH_SHORT).show();

                    Utils.codPersona=CodPersona;
                    Utils.nombres=Nombres;

                    Utils.esAdmin=false;
                    for (int k=0;k<Roles.size();k++){
                        if(Roles.get(k)==1){
                            Utils.esAdmin=true;
                        }
                    }


                    Intent toReservaTicketFiltro = new Intent(v.getContext(), ReservaTicketFiltro.class);
                    v.getContext().startActivity(toReservaTicketFiltro);

                    progressDialog.dismiss();

                }

            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }




    public void generarToken(String a, String b, String c){


        try {
            HttpResponse response;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(GlobalVariables.Urlbase+"membership/authenticate?"+"username="+a+"&password="+b+"&domain="+c);
            get.setHeader("Content-type", "application/json");
            response = httpClient.execute(get);
            String respstring2 = EntityUtils.toString(response.getEntity());

            if(respstring2.equals(""))
                {
                    GlobalVariables.token_auth=null;
                    GlobalVariables.con_status =0;
                }else {
                    GlobalVariables.token_auth = respstring2.substring(1, respstring2.length() - 1);
                    Utils.token=respstring2.substring(1, respstring2.length() - 1);
                    GlobalVariables.con_status = httpClient.execute(get).getStatusLine().getStatusCode();
                }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
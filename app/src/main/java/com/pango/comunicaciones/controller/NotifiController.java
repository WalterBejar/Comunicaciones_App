package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.NotificAdapter;
import com.pango.comunicaciones.model.Notificacion;

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
import java.util.List;

import layout.FragmentNotificacion;

public class NotifiController extends AsyncTask<String,Void,Void> {

    View v;
    String url;
    String opcion;
    FragmentNotificacion Frag;
    ProgressDialog progressDialog;
    Notificacion notificacion;
    List<Notificacion> notificList=new ArrayList<Notificacion>();
    ListView list_alertas;

    public NotifiController(View v, String url, String opcion, FragmentNotificacion Frag){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.Frag=Frag;
        list_alertas=(ListView) v.findViewById(R.id.list_notifica);
        //recList.setOnScrollListener(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            //String a=params[0];
            //String b=params[1];

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"alerta/getall");
                   // get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    JSONArray notific = respJSON.getJSONArray("Data");

                    //GlobalVariables.cont_item=notific.length();
                    //GlobalVariables.contador=respJSON.getInt("Count");//obtiene el total de publicaciones en general

                    for (int i = 0; i < notific.length(); i++) {
                        JSONObject c = notific.getJSONObject(i);
                        String T =c.getString("Tipo");
                        //String A="TP02";
                        //  if(T.equals("TP01")) {

                        String CodRegistro = c.getString("CodRegistro");
                        //String Tipo = c.getString("Tipo");
                        int icon = R.drawable.ic_menu_notificacion;
                        String Titulo = c.getString("Titulo");
                        String Fecha = c.getString("Fecha");

                        notificList.add(new Notificacion(CodRegistro, icon,Titulo,Fecha));

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
            super.onPreExecute();
            progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){

        //aqui esta toda la data publicList

        try {
            if (opcion == "get") {

                NotificAdapter ca = new NotificAdapter(v.getContext(),notificList);
                list_alertas.setAdapter(ca);
                //ca.notifyDataSetChanged();
                progressDialog.dismiss();
                GlobalVariables.notific_data=notificList;
                //  GlobalVariables.noticias2.get(0);
                //loading = false;
            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
}
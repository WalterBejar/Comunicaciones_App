package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.ComAdapter;
import com.pango.comunicaciones.model.Comunicado;

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

import layout.FragmentComunicados;

/**
 * Created by Andre on 26/09/2017.
 */

public class ComController extends AsyncTask<String,Void,Void> {

    View v;
    String url;
    String opcion;
    FragmentComunicados Frag;
    ProgressDialog progressDialog;
    Comunicado comunicado;
    List<Comunicado> comList=new ArrayList<Comunicado>();
    ListView recListCom;
    int a;
    //int celda = 3;

    public ComController(View v,String url,String opcion, FragmentComunicados Frag){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.Frag=Frag;
        recListCom=(ListView) v.findViewById(R.id.l_frag_com);
        //recList.setOnScrollListener(this);
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String a=params[0];
            String b=params[1];

            getToken gettoken=new getToken();
            gettoken.getToken();

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+a+"/"+b+"/TP02");
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    JSONArray comunic = respJSON.getJSONArray("Data");

                    GlobalVariables.cont_item=comunic.length();
                    GlobalVariables.contador=respJSON.getInt("Count");//obtiene el total de publicaciones en general
                    int inc=0;
                    for (int i = 0; i < comunic.length(); i++) {
                        JSONObject c = comunic.getJSONObject(i);
                        String T =c.getString("Tipo");
                        //String A="TP02"

                        //comunicado:2
                       // if(T.equals("TP02")) {
                            inc+=1;
                            String CodRegistro = c.getString("CodRegistro");
                            String Tipo = c.getString("Tipo");
                            int icon = R.drawable.ic_menu_publicaciones;
                            String Autor = c.getString("Autor");
                            String Fecha = c.getString("Fecha");
                            String Titulo = c.getString("Titulo");
                            String Descripcion = c.getString("Descripcion");

                            JSONObject Files = c.getJSONObject("Files");
                            JSONArray Data2 = Files.getJSONArray("Data");

                            ArrayList<String> dataf = new ArrayList<>();
                            //ArrayList<String> datafc = new ArrayList<>();

                            int hg=Data2.length();
                            for (int j = 0; j <hg; j++) {
                                JSONObject h = Data2.getJSONObject(j);

                                String Correlativo = h.getString("Correlativo");
                                String Url = h.getString("Url");
                                String Urlmin = h.getString("Urlmin");

                                dataf.add(Correlativo);
                                dataf.add(Url.replaceAll("\\s","%20"));
                                dataf.add(Urlmin.replaceAll("\\s","%20"));
                            }
                         /*   if (hg != 0) {
                                dataf.get(0);
                            }*/
                            comList.add(new Comunicado(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, Descripcion, dataf));
                        //}
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
        try {
            if (opcion == "get") {
                ComAdapter ca = new ComAdapter(v.getContext(),comList);
                recListCom.setAdapter(ca);
                progressDialog.dismiss();
                GlobalVariables.comlist=comList;
            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }




}

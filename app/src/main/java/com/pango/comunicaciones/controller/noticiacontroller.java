package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.NoticiaAdapter;
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
import java.util.List;

import layout.FragmentNoticias;

/**
 * Created by Andre on 25/09/2017.
 */

public class noticiacontroller extends AsyncTask<String,Void,Void> {

    View v;
    String url;
    String opcion;
    FragmentNoticias Frag;
    ProgressDialog progressDialog;
    Noticias noticia2;
    List<Noticias> noticiaList=new ArrayList<Noticias>();
    ListView recList;
    int a;
    //int celda = 3;

    public noticiacontroller(View v,String url,String opcion, FragmentNoticias Frag){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.Frag=Frag;
        recList=(ListView) v.findViewById(R.id.l_frag_not);
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
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+GlobalVariables.Urlbase2+a+"/"+b+"/TP01");
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);
                    String respstring = EntityUtils.toString(response.getEntity());

                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray noticias = respJSON.getJSONArray("Data");

                    GlobalVariables.cont_item=noticias.length();
                    GlobalVariables.contador=respJSON.getInt("Count");//obtiene el total de publicaciones en general

                    for (int i = 0; i < noticias.length(); i++) {
                        JSONObject c = noticias.getJSONObject(i);
                        String T =c.getString("Tipo");
                        //String A="TP02";
                        //if(T.equals("TP01")) {

                            String CodRegistro = c.getString("CodRegistro");
                            String Tipo = c.getString("Tipo");
                            int icon = R.drawable.ic_menu_noticias;
                            String Autor = c.getString("Autor");
                            String Fecha = c.getString("Fecha");
                            String Titulo = c.getString("Titulo");
                            String Descripcion = c.getString("Descripcion");

                            JSONObject Files = c.getJSONObject("Files");
                            JSONArray Data2 = Files.getJSONArray("Data");

                            ArrayList<String> dataf = new ArrayList<>();
                            for (int j = 0; j < Data2.length(); j++) {
                                JSONObject h = Data2.getJSONObject(j);

                                String Correlativo = h.getString("Correlativo");
                                String Url = h.getString("Url");
                                String Urlmin = h.getString("Urlmin");

                                dataf.add(Correlativo);
                                dataf.add(Url.replaceAll("\\s","%20"));
                                dataf.add(Urlmin.replaceAll("\\s","%20"));
                            }
                            //dataf.get(0);
                            noticiaList.add(new Noticias(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, Descripcion, dataf));
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
                NoticiaAdapter ca = new NoticiaAdapter(v.getContext(),noticiaList);
                recList.setAdapter(ca);
                progressDialog.dismiss();
                GlobalVariables.noticias2=noticiaList;
                //  GlobalVariables.noticias2.get(0);
            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }



}
package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
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
    boolean red;

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
        //red= GlobalVariables.isOnlineNet();
        red=false;
            try {
            HttpResponse response;
            String a=params[0];
            String b=params[1];

                //  getToken gettoken=new getToken();
                // gettoken.getToken();
               // if(red==true){

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+GlobalVariables.Urlbase2+a+"/"+b+"/TP01");
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    get.setHeader("Content-type", "application/json");

                    response = httpClient.execute(get);
                    String respstring = EntityUtils.toString(response.getEntity());

                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray noticias = respJSON.getJSONArray("Data");

                    GlobalVariables.cont_item=noticias.length();
                    GlobalVariables.contNoticia=respJSON.getInt("Count");//obtiene el total de publicaciones en general

                    for (int i = 0; i < noticias.length(); i++) {
                        JSONObject c = noticias.getJSONObject(i);
                        //String T =c.getString("Tipo");
                        //String A="TP02";
                        //if(T.equals("TP01")) {

                            String CodRegistro = c.getString("CodRegistro");
                            //String Tipo = c.getString("Tipo");
                            int icon = R.drawable.ic_menu_noticias;
                            String Fecha = c.getString("Fecha");
                            String Titulo = c.getString("Titulo");
                            String Descripcion = c.getString("Descripcion");

                            JSONObject Files = c.getJSONObject("Files");
                            JSONArray Data2 = Files.getJSONArray("Data");

                            ArrayList<String> dataf = new ArrayList<>();
                            for (int j = 0; j < Data2.length(); j++) {
                                JSONObject h = Data2.getJSONObject(j);

                                String Url = h.getString("Url");
                                String Urlmin2 = h.getString("Urlmin");

                                String[] parts = Urlmin2.split("550px;");
                                //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                                //String part2 = parts[1]; //obtiene: 19-A

                                String Urlmin=parts[0]+ GlobalVariables.anchoMovil+"px;"+parts[1];

                                dataf.add(Utils.ChangeUrl(Url));
                                dataf.add(Utils.ChangeUrl(Urlmin));
                            }
                            //dataf.get(0);
                            noticiaList.add(new Noticias(CodRegistro, icon, Fecha, Titulo, Descripcion, dataf));
                            GlobalVariables.noticias2.add(new Noticias(CodRegistro, icon, Fecha, Titulo, Descripcion, dataf));

                        //}
                    }
                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                }
            }
            //}
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
            if(GlobalVariables.noticias2.size()<3) {
                progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
            }
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get") {

               // if (red==true){

                    if(GlobalVariables.noticias2.size()<=3){

                NoticiaAdapter ca = new NoticiaAdapter(v.getContext(),GlobalVariables.noticias2);
                recList.setAdapter(ca);
                    progressDialog.dismiss();
                }
            /*}else{
                    progressDialog.dismiss();

                    Toast.makeText(v.getContext(),"Se perdio la conexión a  internet",Toast.LENGTH_SHORT).show();
                }*/
                // GlobalVariables.noticias2=GlobalVariables.noticias2.add(noticiaList) ;

                //  GlobalVariables.noticias2.get(0);
            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }



}
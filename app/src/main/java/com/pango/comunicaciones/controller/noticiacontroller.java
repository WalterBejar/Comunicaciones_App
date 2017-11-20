package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.MainActivity;
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
    boolean cargaData=true;
    Boolean loadingTop;
    int value_loadingTop;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;
    MainActivity mainActivity;
    //int celda = 3;
    ProgressBar progressBar;

    /*public noticiacontroller(ProgressDialog progress, MainActivity act) {
        this.progress = progress;
        this.act = act;
    }
    */
    public noticiacontroller(View v,String url,String opcion, FragmentNoticias Frag){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.Frag=Frag;
        recList=(ListView) v.findViewById(R.id.l_frag_not);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout);
        textView2 =(TextView)v.findViewById(R.id.textView);
        progressBar=(ProgressBar) v.findViewById(R.id.pbar_not);
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
            loadingTop=Boolean.parseBoolean(params[2]);
                //value_loadingTop=Integer.parseInt()

                //  getToken gettoken=new getToken();
                // gettoken.getToken();
               // if(red==true){

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+GlobalVariables.Urlbase2+a+"/"+b+"/TP01/"+GlobalVariables.id_phone);
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    get.setHeader("Content-type", "application/json");
                    GlobalVariables.con_status = httpClient.execute(get).getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status==200) {

                        response = httpClient.execute(get);
                        String respstring = EntityUtils.toString(response.getEntity());

                        JSONObject respJSON = new JSONObject(respstring);

                        JSONArray noticias = respJSON.getJSONArray("Data");

                        GlobalVariables.cont_item = noticias.length();
                        GlobalVariables.contNoticia = respJSON.getInt("Count");//obtiene el total de publicaciones en general

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

                            JSONObject h = Data2.getJSONObject(0);
                            String Urlmin2 = h.getString("Urlmin");

                            String[] parts = Urlmin2.split("550px;");
                            //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                            //String part2 = parts[1]; //obtiene: 19-A

                            String Urlmin = parts[0] + GlobalVariables.anchoMovil + "px;" + parts[1];

                            noticiaList.add(new Noticias(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));
                            GlobalVariables.noticias2.add(new Noticias(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));

                            //}
                        }
                    }
                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                    cargaData=false;
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
        if(opcion=="get"&&GlobalVariables.flag_up_toast) {
            super.onPreExecute();
            //if(GlobalVariables.noticias2.size()<GlobalVariables.num_vid) {
            Toast.makeText(v.getContext(),"Actualizando, por favor espere...",Toast.LENGTH_SHORT).show();
            GlobalVariables.flag_up_toast=false;
            //progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
            //}
        }else if(GlobalVariables.noticias2.size()<GlobalVariables.num_vid){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            //progressDialog.setVisibility(View.GONE);
            //progressDialog = ProgressDialog.show(v.getContext(),"","Cargando ...");
           /* progressDialog = new ProgressDialog(v.getContext());
            //pDialog.setMessage("Buffering...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
*/
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get"&&GlobalVariables.con_status==200&&cargaData) {

               // if (red==true){

                  //  if(GlobalVariables.noticias2.size()<=GlobalVariables.num_vid){

                NoticiaAdapter ca = new NoticiaAdapter(v.getContext(),GlobalVariables.noticias2);
                recList.setAdapter(ca);
               // }
                ca.notifyDataSetChanged();
                if(GlobalVariables.flagUpSc==true){
                    recList.setSelection(0);
                    GlobalVariables.flagUpSc=false;
                }else
                if(GlobalVariables.noticias2.size()>3&&GlobalVariables.noticias2.size()<GlobalVariables.contNoticia) {
                    //recListImag.smoothScrollToPosition(GlobalVariables.imagen2.size()-3);
                    recList.setSelection(GlobalVariables.noticias2.size()-4);

                }else if(GlobalVariables.noticias2.size()==GlobalVariables.contNoticia){
                    recList.setSelection(GlobalVariables.noticias2.size());
                }

              /*  if(loadingTop)
                {
                    loadingTop=false;
                    swipeRefreshLayout.setRefreshing(false);
                    textView2.setVisibility(View.GONE);
                    swipeRefreshLayout.setEnabled( false );
                }
*/

              /*  if(GlobalVariables.noticias2.size()==6) {
                    GlobalVariables.contpublicNot=3;
                }else {
                    GlobalVariables.contpublicNot += 1;

                }*/
               // GlobalVariables.noticias2.size();
                GlobalVariables.contpublicNot += 1;
               // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            /*}else{
                    progressDialog.dismiss();

                    Toast.makeText(v.getContext(),"Se perdio la conexión a  internet",Toast.LENGTH_SHORT).show();
                }*/
                // GlobalVariables.noticias2=GlobalVariables.noticias2.add(noticiaList) ;

                //  GlobalVariables.noticias2.get(0);
            }else if(GlobalVariables.con_status!=200){
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                int y=GlobalVariables.con_status;

                Toast.makeText(v.getContext(),"Error en el servidor"+"("+y+")",Toast.LENGTH_SHORT).show();
            }else {
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(v.getContext(),"Revise su conexion a internet",Toast.LENGTH_SHORT).show();
            }

            /*if(loadingTop)
            {
                loadingTop=false;
                swipeRefreshLayout.setRefreshing(false);
                textView2.setVisibility(View.GONE);
                swipeRefreshLayout.setEnabled( false );
            }*/
        }catch (Exception ex){
            Log.w("Error",ex);
        }
        if(loadingTop)
        {
            loadingTop=false;
            swipeRefreshLayout.setRefreshing(false);
            textView2.setVisibility(View.GONE);
            swipeRefreshLayout.setEnabled( false );
        }


    }



}
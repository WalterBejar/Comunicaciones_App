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
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
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
    String url="";
    String opcion="";
    FragmentComunicados Frag;
    ProgressDialog progressDialog;
    Comunicado comunicado;
    List<Comunicado> comList=new ArrayList<Comunicado>();
    ListView recListCom;
    int a;
    boolean cargaData=true;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;
    Boolean loadingTop;
    ProgressBar progressBar;

    public ComController(View v,String url,String opcion, FragmentComunicados Frag){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.Frag=Frag;
        recListCom=(ListView) v.findViewById(R.id.l_frag_com);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout2);
        textView2 =(TextView)v.findViewById(R.id.textView2);
        //recList.setOnScrollListener(this);
        progressBar=(ProgressBar) v.findViewById(R.id.pbar_com);

    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String a=params[0];
            String b=params[1];
            loadingTop=Boolean.parseBoolean(params[2]);

            //getToken gettoken=new getToken();
            //gettoken.getToken();

            if(opcion=="get"&&GlobalVariables.flagcom==true){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+a+"/"+b+"/TP02/"+GlobalVariables.id_phone);
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    get.setHeader("Content-type", "application/json");
                    response = httpClient.execute(get);
                    GlobalVariables.con_status = response.getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status==200) {


                        String respstring = EntityUtils.toString(response.getEntity());
                        JSONObject respJSON = new JSONObject(respstring);
                        JSONArray comunic = respJSON.getJSONArray("Data");

                        GlobalVariables.cont_item = comunic.length();
                        GlobalVariables.contComunicado = respJSON.getInt("Count");//obtiene el total de publicaciones en general
                        int inc = 0;
                        for (int i = 0; i < comunic.length(); i++) {
                            JSONObject c = comunic.getJSONObject(i);
                            //String T =c.getString("Tipo");
                            //String A="TP02"

                            //comunicado:2
                            // if(T.equals("TP02")) {
                            inc += 1;
                            String CodRegistro = c.getString("CodRegistro");
                            //String Tipo = c.getString("Tipo");
                            int icon = R.drawable.ic_menu_publicaciones;
                            //String Autor = c.getString("Autor");
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

                            //comList.add(new Comunicado(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));
                            GlobalVariables.comlist.add(new Comunicado(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));

                            //}
                        }
                    }

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

            //if(GlobalVariables.comlist.size()<GlobalVariables.num_vid) {
                Toast.makeText(v.getContext(),"Actualizando, por favor espere...",Toast.LENGTH_SHORT).show();
                GlobalVariables.flag_up_toast=false;
        //}
        }else if(GlobalVariables.comlist.size()<GlobalVariables.num_vid){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            //progressDialog = ProgressDialog.show(v.getContext(), "", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get"&&GlobalVariables.con_status==200&&cargaData) {
                //if(GlobalVariables.comlist.size()<=GlobalVariables.num_vid){
                ComAdapter ca = new ComAdapter(v.getContext(),GlobalVariables.comlist);
                recListCom.setAdapter(ca);
                //progressDialog.dismiss();
                //GlobalVariables.comlist=comList;
                //}

                ca.notifyDataSetChanged();
                if(GlobalVariables.flagUpSc==true){
                    recListCom.setSelection(0);
                    GlobalVariables.flagUpSc=false;
                }else
                if(GlobalVariables.comlist.size()>3&&GlobalVariables.comlist.size()<GlobalVariables.contComunicado) {
                    //recListImag.smoothScrollToPosition(GlobalVariables.imagen2.size()-3);
                    recListCom.setSelection(GlobalVariables.comlist.size()-4);

                }else if(GlobalVariables.comlist.size()==GlobalVariables.contComunicado){
                    recListCom.setSelection(GlobalVariables.comlist.size());
                    GlobalVariables.flagcom=false;
                }

                GlobalVariables.contpublicCom+=1;
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);


            }else  if(GlobalVariables.con_status!=200){
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(v.getContext(),"Error en el servidor",Toast.LENGTH_SHORT).show();
            }else {
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(v.getContext(),"Revise su conexion a internet",Toast.LENGTH_SHORT).show();
            }



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

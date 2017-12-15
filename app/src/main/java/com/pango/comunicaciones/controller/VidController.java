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
import com.pango.comunicaciones.adapter.VidAdapter;
import com.pango.comunicaciones.model.Vid_Gal;
import com.pango.comunicaciones.model.Video;

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

import layout.FragmentVideos;

/**
 * Created by Andre on 26/09/2017.
 */

public class VidController extends AsyncTask<String,Void,Void> {

    View v;
    String url="";
    String opcion="";
    FragmentVideos Frag;
    ProgressDialog progressDialog;
    List<Video> videoList = new ArrayList<Video>();
    ListView recListVid;
    int a=0;
    boolean cargaData=true;

    Boolean loadingTop;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;
    ProgressBar progressBar;

    public VidController(View v, String url, String opcion, FragmentVideos Frag) {
        this.v = v;
        this.url = url;
        this.opcion = opcion;
        this.Frag = Frag;
        recListVid = (ListView) v.findViewById(R.id.recycler_vid);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout4);
        textView2 =(TextView)v.findViewById(R.id.textView4);
        //recList.setOnScrollListener(this);
        progressBar=(ProgressBar) v.findViewById(R.id.pbar_vid);

    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String a = params[0];
            String b = params[1];
            loadingTop=Boolean.parseBoolean(params[2]);

           // getToken gettoken=new getToken();
           // gettoken.getToken();

            if (opcion == "get") {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/TP04/"+GlobalVariables.id_phone);
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    get.setHeader("Content-type", "application/json");
                    response = httpClient.execute(get);

                    GlobalVariables.con_status = response.getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status==200) {

                        String respstring = EntityUtils.toString(response.getEntity());
                        JSONObject respJSON = new JSONObject(respstring);
                        JSONArray video = respJSON.getJSONArray("Data");

                        GlobalVariables.cont_item = video.length();
                        GlobalVariables.contVideos = respJSON.getInt("Count");

                        for (int i = 0; i < video.length(); i++) {
                            JSONObject c = video.getJSONObject(i);
                            //String T = c.getString("Tipo");
                            //String A="TP02";
                            //if (T.equals("TP04")) {

                            String CodRegistro = c.getString("CodRegistro");
                            //String Tipo = c.getString("Tipo");
                            int icon = R.drawable.ic_video_final;
                            //String Autor = c.getString("Autor");
                            String Fecha = c.getString("Fecha");
                            String Titulo = c.getString("Titulo");

                            JSONObject Files = c.getJSONObject("Files");
                            int CantidadV = Files.getInt("Count");
                            JSONArray Data2 = Files.getJSONArray("Data");

                            //GlobalVariables.cant_vid=Files.getInt("count");

                            //GlobalVariables.cant_vid=2;
                            List<Vid_Gal> dataf = new ArrayList<>();
                            for (int j = 0; j < Data2.length(); j++) {
                                JSONObject h = Data2.getJSONObject(j);

                                String Correlativo = Integer.toString(j);
                                String Url = h.getString("Url");
                                String Urlmin2 = h.getString("Urlmin");

                                String[] parts = Urlmin2.split("550px;");
                                //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                                //String part2 = parts[1]; //obtiene: 19-A

                                String Urlmin = parts[0] + GlobalVariables.anchoMovil + "px;" + parts[1];


                                dataf.add(new Vid_Gal(Correlativo, Utils.ChangeUrl(Url), Utils.ChangeUrl(Urlmin)));

                            }
                            //dataf.get(0);
                           // videoList.add(new Video(CodRegistro, icon, Fecha, Titulo, dataf, CantidadV));
                            GlobalVariables.vidlist.add(new Video(CodRegistro, icon, Fecha, Titulo, dataf, CantidadV));
                            // }
                        }
                    }

                } catch (Exception ex) {
                    Log.w("Error get\n", ex);
                    cargaData=false;

                }
            }
        } catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    @Override
    protected void onPreExecute() {
        if (opcion == "get"&&GlobalVariables.flag_up_toast) {
            super.onPreExecute();

            //if(GlobalVariables.vidlist.size()<GlobalVariables.num_vid) {
            Toast.makeText(v.getContext(),"Actualizando, por favor espere...",Toast.LENGTH_SHORT).show();
            GlobalVariables.flag_up_toast=false;
    //    }
    }else if(GlobalVariables.vidlist.size()<GlobalVariables.num_vid){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            //progressDialog = ProgressDialog.show(v.getContext(), "", "Cargando publicaciones...");
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (opcion == "get"&&GlobalVariables.con_status==200&&cargaData) {
                //if(GlobalVariables.vidlist.size()<=GlobalVariables.num_vid) {

                    VidAdapter ca = new VidAdapter(v.getContext(), GlobalVariables.vidlist);
                    recListVid.setAdapter(ca);
                    //progressDialog.dismiss();
               //}
                ca.notifyDataSetChanged();
                if(GlobalVariables.flagUpSc==true){
                    recListVid.setSelection(0);
                    GlobalVariables.flagUpSc=false;
                }else
                if(GlobalVariables.vidlist.size()>3&&GlobalVariables.vidlist.size()<GlobalVariables.contVideos) {
                    //recListImag.smoothScrollToPosition(GlobalVariables.imagen2.size()-3);
                    recListVid.setSelection(GlobalVariables.vidlist.size()-4);

                }else if(GlobalVariables.vidlist.size()==GlobalVariables.contVideos){
                    recListVid.setSelection(GlobalVariables.vidlist.size());
                }


                GlobalVariables.contpublicVid+=1;
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);




            }else if(GlobalVariables.con_status!=200){
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(v.getContext(),"Error en el servidor",Toast.LENGTH_SHORT).show();
            }else {
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(v.getContext(),"Revise su conexion a internet",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.w("Error", ex);
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

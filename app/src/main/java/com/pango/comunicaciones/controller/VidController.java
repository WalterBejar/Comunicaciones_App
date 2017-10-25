package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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
    String url;
    String opcion;
    FragmentVideos Frag;
    ProgressDialog progressDialog;
    Video video;
    List<Video> videoList = new ArrayList<Video>();
    ListView recListVid;
    int a;

    public VidController(View v, String url, String opcion, FragmentVideos Frag) {
        this.v = v;
        this.url = url;
        this.opcion = opcion;
        this.Frag = Frag;
        recListVid = (ListView) v.findViewById(R.id.recycler_vid);
        //recList.setOnScrollListener(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String a = params[0];
            String b = params[1];

           // getToken gettoken=new getToken();
           // gettoken.getToken();

            if (opcion == "get") {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/TP04");
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

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
                            int CantidadV=Files.getInt("Count");
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

                                String Urlmin=parts[0]+ GlobalVariables.anchoMovil+"px;"+parts[1];



                                dataf.add(new Vid_Gal(Correlativo, Utils.ChangeUrl(Url), Utils.ChangeUrl(Urlmin)));

                            }
                            //dataf.get(0);
                            videoList.add(new Video(CodRegistro, icon, Fecha, Titulo, dataf,CantidadV));
                        GlobalVariables.vidlist.add(new Video(CodRegistro, icon, Fecha, Titulo, dataf,CantidadV));
                       // }
                    }
                } catch (Exception ex) {
                    Log.w("Error get\n", ex);
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
        if (opcion == "get") {
            if(GlobalVariables.vidlist.size()<3) {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
        }}
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (opcion == "get") {
                if(GlobalVariables.vidlist.size()<=3) {

                    VidAdapter ca = new VidAdapter(v.getContext(), GlobalVariables.vidlist);
                    recListVid.setAdapter(ca);
                    progressDialog.dismiss();
                }
                //GlobalVariables.vidlist = videoList;
                //  GlobalVariables.noticias2.get(0);
            }
        } catch (Exception ex) {
            Log.w("Error", ex);
        }
    }
}

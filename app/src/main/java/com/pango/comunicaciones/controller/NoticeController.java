package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.NoticeAdapter;
import com.pango.comunicaciones.adapter.NoticiaAdapter;
import com.pango.comunicaciones.model.GetNoticiaModel;
import com.pango.comunicaciones.model.GetTicketModel;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import layout.FragmentNoticias;

/**
 * Created by BOB on 30/10/2017.
 */


public class NoticeController extends AsyncTask<String, String, String> {


    Context context;
    String url;
    String opcion;
    FragmentNoticias Frag;
    ProgressDialog progressDialog;
    Noticias noticia2;
    ArrayList<Noticias> noticiaList=new ArrayList<Noticias>();
    ListView recList;
    int a;

    NoticeAdapter noticeAdapter;

    public NoticeController(NoticeAdapter noticeAdapter,Context context){
        this.noticeAdapter=noticeAdapter;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {

            super.onPreExecute();
            if(GlobalVariables.noticias2.size()<3) {
                progressDialog = ProgressDialog.show(context, "Loading", "Cargando publicaciones...");
            }
    }

    @Override
    protected  void onPostExecute(String str){
        try {
            super.onPostExecute(str);
            switch (str) {
                case "404":
                    break;
                case "500":
                    break;
                default:
                    Gson gson = new Gson();
                    GetNoticiaModel getNoticiaModel = gson.fromJson(str, GetNoticiaModel.class);
                    GlobalVariables.noticias2.addAll(getNoticiaModel.Data);
                  //  GlobalVariables.pageNotice++;
                    GlobalVariables.contNoticia = getNoticiaModel.Count;
                    // NoticiaAdapter ca = new NoticiaAdapter(v.getContext(),GlobalVariables.noticias2);
                   // recList.setAdapter(ca);

                    if(GlobalVariables.noticias2.size()<=3){

                        noticeAdapter = new NoticeAdapter(context);
                        recList.setAdapter(noticeAdapter);
                        //noticeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }



            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }

    @Override
    protected String doInBackground(String... params) {
            HttpResponse response;
            String a=params[0];
       // if(a == "0" && GlobalVariables.noticias2.size()!=0) return "";
                try {

                    URL url = new URL(Utils.getUrlForPublicacion("TP01",a, GlobalVariables.ElementPerpager));
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    //con.setRequestProperty("Authorization", "Bearer " + Utils.token);
                    con.setRequestMethod("GET");
                    con.connect();

                    switch (con.getResponseCode()) {
                        case 200:
                            InputStream in = con.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder result = new StringBuilder();
                            String line;
                            while((line = reader.readLine()) != null) {
                                result.append(line);
                            }
                            return result.toString();
                        default:
                            return "" + con.getResponseCode();
                    }
                }catch (Exception ex){
                    Log.w("Error get\n",ex);
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
}
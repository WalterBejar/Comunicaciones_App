package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pango.comunicaciones.ActImag;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.Adap_Img;
import com.pango.comunicaciones.model.Img_Gal;

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


public class ImgdetController extends AsyncTask<String,Void,Void>  {

    //View v;
    String url;
    String opcion;
    ActImag actImag;

    ProgressDialog progressDialog;
    ArrayList<String> ImgdetArray=new ArrayList<String>();
    List<Img_Gal> view_image=new ArrayList<Img_Gal>();
    // ListView recList;
    ImageView imag0;
   // TextView tx1;
    TextView tx2;
    TextView tx3;
    private GridView gridView;
    private Adap_Img adaptador;
    //TextView tx4;
    //WebView content;

    // int a;
    // int celda = 3;

    public ImgdetController(String url, String opcion, ActImag actImag){
        this.url=url;
        this.opcion=opcion;
        this.actImag=actImag;
        // recList=(ListView) v.findViewById(R.id.list_recycler);
        imag0 =(ImageView) actImag.findViewById(R.id.icon_image);

        //tx1 = (TextView)  actImag.findViewById(R.id.tx_publicador2);
        tx2 = (TextView)  actImag.findViewById(R.id.txfecha2);
        tx3 = (TextView)  actImag.findViewById(R.id.imag_titulo2);


       // tx4 = (TextView)  v.findViewById(R.id.not_subt);
        //content=(WebView) v.findViewById(R.id.Visor);
        //recList.setOnScrollListener(this);
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String codreg=params[0];

            //String b=params[1];
            getToken gettoken=new getToken();
            gettoken.getToken();


            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"entrada/Getentrada/"+codreg);//url de cada publicacion
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    //notdetArray.add(R.drawable.ic_menu_noticias);

                    ImgdetArray.add(respJSON.getString("Autor"));
                    ImgdetArray.add(respJSON.getString("Fecha"));
                    ImgdetArray.add(respJSON.getString("Titulo"));
                   // ImgdetArray.add(respJSON.getString("Subtitulo"));
                  //  ImgdetArray.add(respJSON.getString("Descripcion"));

                    JSONObject Files = respJSON.getJSONObject("Files");
                    JSONArray Data2 = Files.getJSONArray("Data");

                    for (int i = 0; i < Data2.length(); i++) {
                        JSONObject h = Data2.getJSONObject(i);
/*
                        private String Correlativo;
                        private String url_img;
                        private String urlmin_imag;*/

                        String correlativo=h.getString("Correlativo");
                       // int tamanio=h.getInt("Tamanio");
                        String url_file=h.getString("Url");
                        String urlmin=h.getString("Urlmin");


                        view_image.add(new Img_Gal(correlativo,url_file.replaceAll("\\s","%20"),urlmin.replaceAll("\\s","%20")));

                    }

                    // des_data
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
            progressDialog = ProgressDialog.show(actImag, "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get") {

                GlobalVariables.listdetimg=view_image;//datos correlativo,url,urlmin
             //   String asd=ImgdetArray.get(0);

             //   GlobalVariables.Imgdet=ImgdetArray;
                imag0.setImageResource(R.drawable.ic_menu_gallery);

                //String df= ImgdetArray.get(0);
               // tx1.setText(df);
                tx2.setText(ImgdetArray.get(1));
                tx3.setText(ImgdetArray.get(2));

                gridView = (GridView) actImag.findViewById(R.id.grid_imag);
                adaptador = new Adap_Img(actImag);
                gridView.setAdapter(adaptador);
                progressDialog.dismiss();


                /*Not2Adapter ca = new Not2Adapter(v.getContext(),noticiaList);
                recList.setAdapter(ca);
                progressDialog.dismiss();
                GlobalVariables.noticias2=noticiaList;*/
                //  GlobalVariables.noticias2.get(0);
            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }






}

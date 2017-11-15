package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pango.comunicaciones.ActImag;
import com.pango.comunicaciones.ActSwipeImg;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.TouchImageView;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.Adap_Img;
import com.pango.comunicaciones.adapter.ViewPagerAdapter;
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


public class ListImgdetController extends AsyncTask<String,Void,Void> {


    //View v;
    String url;
    String opcion;
    ActSwipeImg actSwipeImg;
    TouchImageView imagenExtendida;
    ViewPager viewPager;
    ViewPagerAdapter adapter;


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
    String posIn;
    String posPub;
    // int a;
    // int celda = 3;

    public ListImgdetController(String url, String opcion, ActSwipeImg actSwipeImg){
        this.url=url;
        this.opcion=opcion;
        this.actSwipeImg=actSwipeImg;

        imagenExtendida = (TouchImageView) actSwipeImg.findViewById(R.id.imagen_extendida);


    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String codreg=params[0];
            posIn=params[1];
            posPub=params[2];


            //String b=params[1];
           // getToken gettoken=new getToken();
           // gettoken.getToken();


            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"entrada/Getentrada/"+codreg+"/"+GlobalVariables.id_phone);//url de cada publicacion
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    //notdetArray.add(R.drawable.ic_menu_noticias);

                   /* ImgdetArray.add(respJSON.getString("Autor"));
                    ImgdetArray.add(respJSON.getString("Fecha"));
                    ImgdetArray.add(respJSON.getString("Titulo"));*/
                    // ImgdetArray.add(respJSON.getString("Subtitulo"));
                    //  ImgdetArray.add(respJSON.getString("Descripcion"));

                    JSONObject Files = respJSON.getJSONObject("Files");
                    JSONArray Data2 = Files.getJSONArray("Data");

                    for (int i = 0; i < Data2.length(); i++) {
                        JSONObject h = Data2.getJSONObject(i);


                        String correlativo=Integer.toString(i);
                        // int tamanio=h.getInt("Tamanio");
                        String url_file=h.getString("Url");
                        String urlmin2=h.getString("Urlmin");

                        String[] parts = urlmin2.split("550px;");
                        //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                        //String part2 = parts[1]; //obtiene: 19-A

                        String urlmin=parts[0]+ GlobalVariables.anchoMovil+"px;"+parts[1];


                        view_image.add(new Img_Gal(correlativo, Utils.ChangeUrl(url_file),Utils.ChangeUrl(urlmin)));

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
            progressDialog = ProgressDialog.show(actSwipeImg, "Loading", "Cargando ...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get") {
                //if(GlobalVariables.listdetimg.size()==0) {
                    GlobalVariables.listdetimg = view_image;//datos correlativo,url,urlmin
                //}

                viewPager = (ViewPager)actSwipeImg.findViewById(R.id.viewPager2);
                adapter = new ViewPagerAdapter(actSwipeImg,GlobalVariables.listdetimg,Integer.parseInt(posIn));
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(Integer.parseInt(posIn),true);
                progressDialog.dismiss();

            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }






}




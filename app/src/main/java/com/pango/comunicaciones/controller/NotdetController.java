package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.ActNotDetalle;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.NotDet;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotdetController extends AsyncTask<String,Void,Void> {
    int count_files=0;

    View v;
    String url;
    String opcion;
    ActNotDetalle actNotDet;
    ProgressDialog progressDialog;
    ArrayList<String> notdetArray=new ArrayList<String>();
    List<NotDet> des_data=new ArrayList<NotDet>();
    //List<NotDet> asig_des=new ArrayList<NotDet>();

    // ListView recList;
    ImageView imag0;
     //TextView tx1;
     TextView tx2;
     TextView tx3;
     TextView tx4;
    WebView content;
    ImageButton adj;

   // int a;
   // int celda = 3;

    public NotdetController(String url, String opcion, ActNotDetalle actNotDet){
        this.url=url;
        this.opcion=opcion;
        this.actNotDet=actNotDet;
       // recList=(ListView) v.findViewById(R.id.list_recycler);

        //recList.setOnScrollListener(this);
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String codreg=params[0];
            String titulo_not=params[1];
            String fecha_not=params[2];



            //String b=params[1];

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"entrada/Getentrada/"+codreg+"/"+GlobalVariables.id_phone);//url de cada publicacion
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    get.setHeader("Content-type", "application/json");
                    GlobalVariables.con_status = httpClient.execute(get).getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status==200) {
                        response = httpClient.execute(get);

                        String respstring = EntityUtils.toString(response.getEntity());
                        JSONObject respJSON = new JSONObject(respstring);
                        //notdetArray.add(R.drawable.ic_menu_noticias);
                        //notdetArray.add(respJSON.getString("Autor"));
                        notdetArray.add(fecha_not);
                        notdetArray.add(titulo_not);
                        //notdetArray.add(respJSON.getString("Subtitulo"));
                        notdetArray.add(respJSON.getString("Descripcion"));

                        JSONObject Files = respJSON.getJSONObject("Files");
                        JSONArray Data2 = Files.getJSONArray("Data");
                        int count = Files.getInt("Count");
                        if (count != 0) {
                            for (int i = 0; i < Data2.length(); i++) {
                                JSONObject h = Data2.getJSONObject(i);


                                String nombre = h.getString("Nombre");
                                int tamanio = h.getInt("Tamanio");
                                String tipoarc = h.getString("TipoArchivo");
                                String url_file = h.getString("Url");
                                String tipo_det = h.getString("Tipo");

                                if (tipo_det.equals("FL01")) {
                                    count_files = count_files + 1;

                                    des_data.add(new NotDet(nombre, tamanio, tipoarc, url_file, tipo_det));

                                }

                            }
                        }
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
            progressDialog = ProgressDialog.show(actNotDet, "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {

            if (opcion == "get"&&GlobalVariables.con_status==200) {
                DateFormat formatoInicial = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
                DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");

                GlobalVariables.listdetnot=des_data;


                imag0 =(ImageView) actNotDet.findViewById(R.id.icon_notdet);

                //tx1 = (TextView)  v.findViewById(R.id.tx_publicador);
                tx2 = (TextView)  actNotDet.findViewById(R.id.txfecha);
                tx3 = (TextView)  actNotDet.findViewById(R.id.not_titulo);
                content=(WebView) actNotDet.findViewById(R.id.Visor);
                //adj=(ImageButton) actNotDet.findViewById(R.id.ib_adj);





                imag0.setImageResource(R.drawable.ic_noticia_final2);
               String df= notdetArray.get(0);
                //tx1.setText(df);
                //tx2.setText(notdetArray.get(1));

                try {
                    tx2.setText(formatoRender.format(formatoInicial.parse(notdetArray.get(0))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                tx3.setText(notdetArray.get(1));
                //String g=notdetArray.get(3);
             /*   if(g.equals("null")){
                    tx4.setVisibility(View.GONE);
                }else{
                    tx4.setText(notdetArray.get(3));
                    }*/
                content.setWebViewClient(new MyWebViewClient());

                content.loadDataWithBaseURL("",notdetArray.get(2) , "text/html", "UTF-8", "");
                WebSettings settings=content.getSettings();
                settings.setJavaScriptEnabled(true);

                //content.getSettings().setBuiltInZoomControls(true);

                /*if(des_data.size()==0||count_files==0){
                    adj.setVisibility(View.GONE);
                }else
                {
                    adj.setVisibility(View.VISIBLE);
                }*/

                progressDialog.dismiss();
                /*Not2Adapter ca = new Not2Adapter(v.getContext(),noticiaList);
                recList.setAdapter(ca);
                progressDialog.dismiss();
                GlobalVariables.noticias2=noticiaList;*/
                //  GlobalVariables.noticias2.get(0);



            }else {
                progressDialog.dismiss();
                Toast.makeText(actNotDet,"Error en el servidor",Toast.LENGTH_SHORT).show();
            }



        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }


    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){

            //view.loadUrl(url);

            if (Uri.parse(url).getHost().equals("")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            actNotDet.startActivity(intent);
           // return true;
            return true;

        }
    }



}

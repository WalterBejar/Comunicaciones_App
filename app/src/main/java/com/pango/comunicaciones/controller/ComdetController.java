package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.ActComDetalle;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.ComDet;

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


public class ComdetController extends AsyncTask<String,Void,Void> {
    int count_files;

    View v;
    String url="";
    String opcion="";
    ActComDetalle actComDetalle;
    ProgressDialog progressDialog;
    ArrayList<String> comdetArray=new ArrayList<String>();
    List<ComDet> des_data=new ArrayList<ComDet>();
    // ListView recList;
    ImageView imag0;
    TextView tx1;
    TextView tx2;
    TextView tx3;
    WebView content;
    //ImageButton adj_com;
Button btn_adjuntos;
    // int a;
    // int celda = 3;

    public ComdetController(String url, String opcion, ActComDetalle actComDetalle){
        this.url=url;
        this.opcion=opcion;
        this.actComDetalle=actComDetalle;
        // recList=(ListView) v.findViewById(R.id.list_recycler);

        //recList.setOnScrollListener(this);
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String codreg=params[0];
            String titulo=params[1];
            String fecha=params[2];

            //String b=params[1];

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"entrada/Getentrada/"+codreg+"/"+GlobalVariables.id_phone);//url de cada publicacion
                    //get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);

                    response = httpClient.execute(get);

                    GlobalVariables.con_status = response.getStatusLine().getStatusCode();


                    if(GlobalVariables.con_status==200) {

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    //notdetArray.add(R.drawable.ic_menu_noticias);
                   // comdetArray.add(respJSON.getString("Autor"));
                    /*    if(fecha.equals("")&&titulo.equals("")){

                            comdetArray.add(respJSON.getString("Fecha"));
                            //comdetArray.add(fecha);

                            comdetArray.add(respJSON.getString("Autor"));
                        }else {


                            comdetArray.add(fecha);
                            comdetArray.add(titulo);
                        }
*/

                        comdetArray.add(fecha);
                        comdetArray.add(titulo);
                    comdetArray.add(respJSON.getString("Descripcion"));



                    JSONObject Files = respJSON.getJSONObject("Files");
                    JSONArray Data2 = Files.getJSONArray("Data");

                    for (int i = 0; i < Data2.length(); i++) {
                        JSONObject h = Data2.getJSONObject(i);

                        String nombre=h.getString("Nombre");
                        int tamanio=h.getInt("Tamanio");
                        String tipoarc=h.getString("TipoArchivo");
                        String url_file=h.getString("Url");
                        String tipo_det=h.getString("Tipo");

                        if(tipo_det.equals("FL01")){
                            count_files=count_files+1;
                            des_data.add(new ComDet(nombre,tamanio,tipoarc,url_file,tipo_det));

                        }


                    }



                    // des_data
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
            progressDialog = ProgressDialog.show(actComDetalle, "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get"&&GlobalVariables.con_status==200) {
                DateFormat formatoInicial = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
                DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");
                GlobalVariables.listdetcom=des_data;

                GlobalVariables.flag_notificacion=false;

                imag0 =(ImageView) actComDetalle.findViewById(R.id.comdet_icon);

                // tx1 = (TextView)  v.findViewById(R.id.comdet_pub);
                tx2 = (TextView)  actComDetalle.findViewById(R.id.comdet_fecha);
                tx3 = (TextView)  actComDetalle.findViewById(R.id.comdet_titulo);
                content=(WebView) actComDetalle.findViewById(R.id.com_visor);
                //adj_com=(ImageButton) actComDetalle.findViewById(R.id.com_adj);
                btn_adjuntos=(Button) actComDetalle.findViewById(R.id.btn_adjuntos);


                imag0.setImageResource(R.drawable.ic_evento);
                //String df= comdetArray.get(0);
                //tx1.setText(df);
                //tx2.setText(comdetArray.get(1));

                try {

                  /*  if(GlobalVariables.is_notification) {
                        tx2.setText(comdetArray.get(0));
                    }else{*/

                        //tx2.setText(formatoRender.format(formatoInicial.parse(comdetArray.get(0))));
                    if (!GlobalVariables.is_notification){
                        tx2.setText(formatoRender.format(formatoInicial.parse(comdetArray.get(0))));
                    }else{
                        tx2.setText(comdetArray.get(0));
                    }
                   // }



                } catch (ParseException e) {
                    e.printStackTrace();
                }


                tx3.setText(comdetArray.get(1));
               // String g=comdetArray.get(3);
/*                if(g.equals("null")){
                    tx4.setVisibility(View.GONE);
                }else{
                    tx4.setText(comdetArray.get(3));
                }*/


                content.setWebViewClient(new MyWebViewClient());
                content.loadDataWithBaseURL("",comdetArray.get(2) , "text/html", "UTF-8", "");
                WebSettings settings=content.getSettings();
                settings.setJavaScriptEnabled(true);

                content.getSettings().setBuiltInZoomControls(true);
                content.getSettings().setDisplayZoomControls(false);


                if(des_data.size()==0||count_files==0){
                    btn_adjuntos.setVisibility(View.GONE);
                }else
                {
                    btn_adjuntos.setVisibility(View.VISIBLE);
                }

/*                content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(actComDetalle,"Error en el servidor",Toast.LENGTH_SHORT).show();

                    }
                });
*/
                progressDialog.dismiss();


            }else {
                progressDialog.dismiss();
                Toast.makeText(v.getContext(),"Error en el servidor",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Log.w("Error",ex);
            GlobalVariables.is_notification=true;

        }
        GlobalVariables.is_notification=true;


    }


    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;

        }
    }



}

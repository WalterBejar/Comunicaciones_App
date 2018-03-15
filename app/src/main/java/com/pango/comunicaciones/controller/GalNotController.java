package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.Galeria_noticias;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.Adap_Img;
import com.pango.comunicaciones.model.Img_Gal;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 04/01/2018.
 */

public class GalNotController extends AsyncTask<String,Void,Void> {

    //View v;
    String url="";
    String opcion="";
    Galeria_noticias galeria_noticias;

    ProgressDialog progressDialog;
    ArrayList<String> ImgdetArray=new ArrayList<String>();
    List<Img_Gal> view_image=new ArrayList<Img_Gal>();
    // ListView recList;
    ImageView imag0;
    // TextView tx1;
    TextView txfecha;
    TextView txtitulo;
    private GridView gridView;
    private Adap_Img adaptador;
    //TextView tx4;
    //WebView content;

    // int a;
    // int celda = 3;

    public GalNotController(String url, String opcion, Galeria_noticias galeria_noticias){
        this.url=url;
        this.opcion=opcion;
        this.galeria_noticias=galeria_noticias;
        // recList=(ListView) v.findViewById(R.id.list_recycler);
        imag0 =(ImageView) galeria_noticias.findViewById(R.id.icon_notgal);

        //tx1 = (TextView)  actImag.findViewById(R.id.tx_publicador2);
        txfecha = (TextView)  galeria_noticias.findViewById(R.id.ngfecha2);
        txtitulo = (TextView)  galeria_noticias.findViewById(R.id.notgal_titulo);


        // tx4 = (TextView)  v.findViewById(R.id.not_subt);
        //content=(WebView) v.findViewById(R.id.Visor);
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
            // getToken gettoken=new getToken();
            // gettoken.getToken();


            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"entrada/Getentrada/"+codreg+"/"+GlobalVariables.id_phone);//url de cada publicacion
                    //  get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    GlobalVariables.con_status = response.getStatusLine().getStatusCode();
                    if(GlobalVariables.con_status==200) {


                        String respstring = EntityUtils.toString(response.getEntity());
                        JSONObject respJSON = new JSONObject(respstring);
                        //notdetArray.add(R.drawable.ic_menu_noticias);

                        //ImgdetArray.add(respJSON.getString("Autor"));

                        ImgdetArray.add(fecha);
                        ImgdetArray.add(titulo);
                        // ImgdetArray.add(respJSON.getString("Subtitulo"));
                        //  ImgdetArray.add(respJSON.getString("Descripcion"));

                        JSONObject Files = respJSON.getJSONObject("Files");
                        JSONArray Data2 = Files.getJSONArray("Data");

                        for (int i = 0; i < Data2.length(); i++) {
                            JSONObject h = Data2.getJSONObject(i);

                            String correlativo = Integer.toString(i);
                            // int tamanio=h.getInt("Tamanio");
                            String url_file = h.getString("Url");
                            String urlmin2 = h.getString("Urlmin");

                            String[] parts = urlmin2.split("550px;");
                            //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                            //String part2 = parts[1]; //obtiene: 19-A

                            String urlmin = parts[0] + GlobalVariables.anchoMovil + "px;" + parts[1];


                            view_image.add(new Img_Gal(correlativo, Utils.ChangeUrl(url_file), Utils.ChangeUrl(urlmin)));

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

    @Override
    protected void onPreExecute() {
        if(opcion=="get") {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(galeria_noticias, "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        //actImag.success();

        try {
            if (opcion == "get"&&GlobalVariables.con_status==200) {
                //DateFormat formatoInicial = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
                //DateFormat formatoRender = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");
                GlobalVariables.listdetimg=view_image;//datos correlativo,url,urlmin
                //   String asd=ImgdetArray.get(0);

                //   GlobalVariables.Imgdet=ImgdetArray;
                imag0.setImageResource(R.drawable.ic_noticia3);

                //String df= ImgdetArray.get(0);
                // tx1.setText(df);
                //tx2.setText(ImgdetArray.get(1));

                try {
                    txfecha.setText(ImgdetArray.get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                txtitulo.setText(ImgdetArray.get(1));

                gridView = (GridView) galeria_noticias.findViewById(R.id.grid_notgal);
                adaptador = new Adap_Img(galeria_noticias);
                gridView.setAdapter(adaptador);
                progressDialog.dismiss();


                /*Not2Adapter ca = new Not2Adapter(v.getContext(),noticiaList);
                recList.setAdapter(ca);
                progressDialog.dismiss();
                GlobalVariables.noticias2=noticiaList;*/
                //  GlobalVariables.noticias2.get(0);
            }else {
                progressDialog.dismiss();
                Toast.makeText(galeria_noticias,"Error en el servidor",Toast.LENGTH_SHORT).show();
            }


        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }

}

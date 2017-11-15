package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.ImgAdapter;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.Img_Gal;
import com.pango.comunicaciones.model.Img_Gal_List;

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

import layout.FragmentImagenes;

public class ImgController extends AsyncTask<String,Void,Void> /*implements   AbsListView.OnScrollListener*/ {
    View v;
    String url;
    String opcion;
    FragmentImagenes Frag;
    ProgressDialog progressDialog;
    Imagen imagen;
    List<Imagen> imagenList = new ArrayList<Imagen>();
    ListView recListImag;
    int a;
    int celda = 3;
    boolean red;
    boolean cargaData=true;
    Boolean loadingTop;
    int value_loadingTop;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;

    public ImgController(View v, String url, String opcion, FragmentImagenes Frag) {
        this.v = v;
        this.url = url;
        this.opcion = opcion;
        this.Frag = Frag;
        recListImag = (ListView) v.findViewById(R.id.list_imag);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout3);
        textView2 =(TextView)v.findViewById(R.id.textView3);
        //recList.setOnScrollListener(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String a = params[0];
            String b = params[1];
            loadingTop=Boolean.parseBoolean(params[2]);

            //red= GlobalVariables.isOnlineNet();
          //  getToken gettoken=new getToken();
           // gettoken.getToken();
           // if(red==false){

            if (opcion == "get") {
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + "entrada/getpaginated/" + a + "/" + b + "/TP03/"+GlobalVariables.id_phone);
                    get.setHeader("Content-type", "application/json");
                    GlobalVariables.con_status = httpClient.execute(get).getStatusLine().getStatusCode();
                    //GlobalVariables.con_status =404;
                    if(GlobalVariables.con_status==200){

                    // get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);
                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    JSONArray image = respJSON.getJSONArray("Data");

                    GlobalVariables.cont_item = image.length();
                    GlobalVariables.contFotos = respJSON.getInt("Count");
                    for (int i = 0; i < image.length(); i++) {
                        JSONObject c = image.getJSONObject(i);
                        //String T = c.getString("Tipo");
                        //String A="TP02";
                       // if (T.equals("TP03")) {

                            String CodRegistro = c.getString("CodRegistro");
                            //String Tipo = c.getString("Tipo");
                            int icon = R.drawable.ic_menu_noticias;
                           // String Autor = c.getString("Autor");
                            String Fecha = c.getString("Fecha");
                            String Titulo = c.getString("Titulo");
                            //String Descripcion = c.getString("Descripcion");

                            JSONObject Files = c.getJSONObject("Files");
                            int cant_img=Files.getInt("Count");
                            JSONArray Data2 = Files.getJSONArray("Data");

                            List<Img_Gal_List> dataf = new ArrayList<>();
                            for (int j = 0; j < Data2.length(); j++) {
                                JSONObject h = Data2.getJSONObject(j);

                                String Correlativo = Integer.toString(j);
                                //////////////////////////
                                //String Url = "";
                                String Urlmin2 = h.getString("Urlmin");

                                String[] parts = Urlmin2.split("550px;");
                                //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                                //String part2 = parts[1]; //obtiene: 19-A

                                String Urlmin=parts[0]+ GlobalVariables.anchoMovil+"px;"+parts[1];


                                dataf.add(new Img_Gal_List(Correlativo, Utils.ChangeUrl(Urlmin)));

                              /*  dataf.add(Correlativo);
                                dataf.add(Url);
                                dataf.add(Urlmin);*/
                            }
                            // dataf.get(0);
                            imagenList.add(new Imagen(CodRegistro, icon, Fecha, Titulo, dataf,cant_img));
                        GlobalVariables.imagen2.add(new Imagen(CodRegistro, icon, Fecha, Titulo, dataf,cant_img));
                       // }
                    }

                    }



                } catch (Exception ex) {
                    Log.w("Error get\n", ex);
                    cargaData=false;
                    //Toast.makeText(,"Error en el servidor",Toast.LENGTH_SHORT).show();
                }
            }//}
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
            //if(GlobalVariables.imagen2.size()<GlobalVariables.num_vid) {
            Toast.makeText(v.getContext(),"Actualizando, por favor espere...",Toast.LENGTH_SHORT).show();
            GlobalVariables.flag_up_toast=false;
                //progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
           // }
        }else{
            super.onPreExecute();
            progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (opcion == "get"&&GlobalVariables.con_status==200&&cargaData) {


                   // if (red==true){
                //if(GlobalVariables.imagen2.size()<=GlobalVariables.num_vid) {
                    ImgAdapter ca = new ImgAdapter(v.getContext(), GlobalVariables.imagen2);

                    recListImag.setAdapter(ca);
                //ca.
                    //ca.updateData(GlobalVariables.imagen2);
                    ca.notifyDataSetChanged();
                if(GlobalVariables.flagUpSc==true){
                    recListImag.setSelection(0);
                    GlobalVariables.flagUpSc=false;
                }else
                if(GlobalVariables.imagen2.size()>3&&GlobalVariables.imagen2.size()<GlobalVariables.contFotos) {
                    //recListImag.smoothScrollToPosition(GlobalVariables.imagen2.size()-3);
                    recListImag.setSelection(GlobalVariables.imagen2.size()-4);

                }else if(GlobalVariables.imagen2.size()==GlobalVariables.contFotos){
                    recListImag.setSelection(GlobalVariables.imagen2.size());
                }


                GlobalVariables.contpublicImg+=1;
                    progressDialog.dismiss();



               /* }}else{
                        progressDialog.dismiss();

                        Toast.makeText(v.getContext(),"Se perdió la conexión a  internet",Toast.LENGTH_SHORT).show();*/
                 //   }

                //GlobalVariables.imagen2 = imagenList;
                //  GlobalVariables.noticias2.get(0);
            }else if(GlobalVariables.con_status!=200) {
                progressDialog.dismiss();
                int y=GlobalVariables.con_status;
                Toast.makeText(v.getContext(),"Error en el servidor"+"("+y+")",Toast.LENGTH_SHORT).show();

            }else {
                progressDialog.dismiss();

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





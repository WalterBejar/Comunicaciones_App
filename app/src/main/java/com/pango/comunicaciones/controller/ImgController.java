package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.ImgAdapter;
import com.pango.comunicaciones.model.Imagen;
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

import layout.FragmentImagenes;

/**
 * Created by Andre on 26/09/2017.
 */

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

    public ImgController(View v, String url, String opcion, FragmentImagenes Frag) {
        this.v = v;
        this.url = url;
        this.opcion = opcion;
        this.Frag = Frag;
        recListImag = (ListView) v.findViewById(R.id.list_imag);

        //recList.setOnScrollListener(this);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String a = params[0];
            String b = params[1];

            getToken gettoken=new getToken();
            gettoken.getToken();

            if (opcion == "get") {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + "entrada/getpaginated/" + a + "/" + b + "/TP03");
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    JSONArray image = respJSON.getJSONArray("Data");

                    GlobalVariables.cont_item = image.length();
                    GlobalVariables.contador = respJSON.getInt("Count");

                    for (int i = 0; i < image.length(); i++) {
                        JSONObject c = image.getJSONObject(i);
                        String T = c.getString("Tipo");
                        //String A="TP02";
                       // if (T.equals("TP03")) {

                            String CodRegistro = c.getString("CodRegistro");
                            String Tipo = c.getString("Tipo");
                            int icon = R.drawable.ic_menu_noticias;
                            String Autor = c.getString("Autor");
                            String Fecha = c.getString("Fecha");
                            String Titulo = c.getString("Titulo");
                            //String Descripcion = c.getString("Descripcion");

                            JSONObject Files = c.getJSONObject("Files");
                            int cant_img=Files.getInt("Count");
                            JSONArray Data2 = Files.getJSONArray("Data");

                            List<Img_Gal> dataf = new ArrayList<>();
                            for (int j = 0; j < Data2.length(); j++) {
                                JSONObject h = Data2.getJSONObject(j);

                                String Correlativo = h.getString("Correlativo");
                                String Url = h.getString("Url");
                                String Urlmin = h.getString("Urlmin");

                                dataf.add(new Img_Gal(Correlativo, Url.replaceAll("\\s","%20"), Urlmin.replaceAll("\\s","%20")));

                              /*  dataf.add(Correlativo);
                                dataf.add(Url);
                                dataf.add(Urlmin);*/
                            }
                            // dataf.get(0);
                            imagenList.add(new Imagen(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, dataf,cant_img));
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
            super.onPreExecute();
            progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Cargando publicaciones...");
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (opcion == "get") {
                ImgAdapter ca = new ImgAdapter(v.getContext(), imagenList);
                recListImag.setAdapter(ca);
                progressDialog.dismiss();
                GlobalVariables.imagen2 = imagenList;
                //  GlobalVariables.noticias2.get(0);
            }
        } catch (Exception ex) {
            Log.w("Error", ex);
        }
    }
}





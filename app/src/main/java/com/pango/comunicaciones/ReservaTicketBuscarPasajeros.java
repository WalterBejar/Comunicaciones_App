package com.pango.comunicaciones;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pango.comunicaciones.model.GetPasajeroModel;
import com.pango.comunicaciones.model.PasajeroModel;
import com.pango.comunicaciones.model.PersonaPostReservaModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReservaTicketBuscarPasajeros extends AppCompatActivity {

    String codigoTicket;
    int cantidad = 6;
    int page2=1;
    int contPasajeros=0;
    boolean buscar=false;

    ProgressDialog progressDialog;
    ArrayList<PasajeroModel> listaPasajeros;

    boolean[] listaCheckBoxPasajeros;
    BuscarAdapter buscarAdapter;
    ListView listaBuscarPasajeros;
    ImageButton btn_borrarDNI,btn_borrarNombre,btn_borrarEmpresa;
    Button botonAgregar;
    String strDNI = "-", strNombre = "-", strEmpresa = "-";
    EditText editTextDNI, editTextNombre, editTextEmpresa;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_buscar_pasajeros);
        setTitle("Reserva de Buses");

        listaPasajeros = new ArrayList<PasajeroModel>();
        listaCheckBoxPasajeros= new boolean[0];
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbus2);
        setSupportActionBar(toolbar);
        botonAgregar=(Button) findViewById(R.id.botonAgregarPasajeroALista) ;
        editTextDNI = (EditText) findViewById(R.id.textboxPasajeroDNI);
        editTextNombre = (EditText) findViewById(R.id.textboxPasajeroNombre);
        editTextEmpresa = (EditText) findViewById(R.id.textboxPasajeroEmpresa);
        listaBuscarPasajeros = (ListView) findViewById(R.id.listaBuscarPasajeros);
        btn_borrarDNI = (ImageButton) findViewById(R.id.btn_deleteDNI);
        btn_borrarNombre = (ImageButton) findViewById(R.id.btn_deleteName);
        btn_borrarEmpresa = (ImageButton) findViewById(R.id.btn_deleteEmpresa);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Conectándose al servidor");
        progressDialog.setMessage("Por favor, espere...");
        progressDialog.setCancelable(false);

        buscarAdapter = new BuscarAdapter();
        listaBuscarPasajeros.setAdapter(buscarAdapter);

        btn_borrarDNI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDNI.setText("");
            }
        });
        btn_borrarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNombre.setText("");
            }
        });
        btn_borrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmpresa.setText("");
            }
        });

        // Recibir codigo ticket
        Bundle extras = getIntent().getExtras();
        codigoTicket = extras.getString("CodigoTicket");

        builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Desea continuar?");//("Está a punto de eliminar un conjunto de reservas.");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Gestionar la reserva y cerrar el diálogo
                new AgregarPasajeros().execute();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar diálogo
                dialog.dismiss();
            }
        });

        listaBuscarPasajeros.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount){

                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //loadNextDataFromApi(page);
                if(listaPasajeros.size()!=contPasajeros) {
                    new BuscarPasajeros().execute(String.valueOf(page2));
                    return true; // ONLY if more data is actually being loaded; false otherwise.
                }else{
                    return false;
                }
            }
        });
    }

    public void clickEnBuscar(View view){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        buscar=true;
        page2=1;
        new BuscarPasajeros().execute();
    }

    public void clickEnAgregarSeleccionados(View view){
        //new AgregarPasajeros().execute();
        builder.setIcon(R.drawable.confirmicon);
        builder.setMessage("Está a punto de agregar un conjunto de reservas.");
        alertDialog = builder.create();
        alertDialog.show();
    }

    public class BuscarPasajeros extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (editTextDNI.getText().toString().equals(""))
                strDNI = "-";
            else
                strDNI = editTextDNI.getText().toString();
            if (editTextNombre.getText().toString().equals(""))
                strNombre = "-";
            else
                strNombre = editTextNombre.getText().toString();
            if (editTextEmpresa.getText().toString().equals(""))
                strEmpresa = "-";
            else
                strEmpresa = editTextEmpresa.getText().toString();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            switch (str) {
                case "404":
                    break;
                case "500":
                    break;
                default:
                    Gson gson = new Gson();
                    GetPasajeroModel getPasajeroModel = gson.fromJson(str, GetPasajeroModel.class);
                    contPasajeros= getPasajeroModel.Count;
                    // add selectect
                    ArrayList<PasajeroModel> PasajerosCheckt = new ArrayList<PasajeroModel>();
                    for (int i = 0 ; i < listaPasajeros.size() ; i++ )
                        if (listaCheckBoxPasajeros[i])
                            PasajerosCheckt.add(listaPasajeros.get(i));
                    //remove selected of new list
                    for (PasajeroModel l: PasajerosCheckt) {
                        for (PasajeroModel p: getPasajeroModel.Data) {
                            if(p.DNI.equals(l.DNI))
                                getPasajeroModel.Data.remove(p);
                        }
                    }

                    if(buscar){

                        listaPasajeros.clear();
                        listaPasajeros.addAll(PasajerosCheckt);
                        listaPasajeros.addAll(getPasajeroModel.Data);// getPasajeroModel.Data;

                        listaCheckBoxPasajeros = new boolean[contPasajeros + PasajerosCheckt.size()];
                       for(int i=0;i<PasajerosCheckt.size();i++)
                           listaCheckBoxPasajeros[i]=true;
                        buscar=false;
                    }
                    else
                        listaPasajeros.addAll(getPasajeroModel.Data);


                    page2++;
                    buscarAdapter.notifyDataSetChanged();
            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(Utils.getUrlForReservaTicketBuscarPasajeros(strDNI, strNombre, strEmpresa,page2, cantidad));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Authorization", "Bearer " + Utils.token);
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
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            return null;
        }
    }

    public class AgregarPasajeros extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            switch (str) {
                case "404":
                    break;
                case "500":
                    break;
                default:
                    Gson gson = new Gson();
                    GetPasajeroModel getPasajeroModel = gson.fromJson(str, GetPasajeroModel.class);
                    Toast.makeText(getApplicationContext(),"La operación se ha realizado con éxito",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent data = new Intent();
                    data.setData(Uri.parse(gson.toJson(getPasajeroModel)));
                    setResult(RESULT_OK, data);
                    //finishActivity(1);
                    finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                List<PersonaPostReservaModel> listaPasajerosAAgregar = new ArrayList<PersonaPostReservaModel>();
                Gson gson = new Gson();
                for (int i = 0 ; i < listaPasajeros.size() ; i++ )
                    if (listaCheckBoxPasajeros[i])
                        listaPasajerosAAgregar.add(Utils.fromPasajeroToPersona(listaPasajeros.get(i), codigoTicket));

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost;
                httpPost = new HttpPost(Utils.getUrlForAgregarReserva());

                StringEntity postString = new StringEntity( "{\"Data\":"+gson.toJson(listaPasajerosAAgregar.toArray())+"}");
                httpPost.setEntity(postString);
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", "Bearer " + Utils.token );

                HttpResponse response = httpClient.execute(httpPost);


                switch (response.getStatusLine().getStatusCode()) {
                    case 200:
                        InputStream in = response.getEntity().getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        return result.toString();
                    default:
                        return "" + response.getStatusLine().getStatusCode();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            return null;
        }
    }

    public class BuscarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (listaPasajeros == null)
                return 0;
            return listaPasajeros.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (listaPasajeros == null)
                return  null;
            convertView = getLayoutInflater().inflate(R.layout.reserva_tickets_listapasajeros, null);
            String nombreCompleto = listaPasajeros.get(position).NOMBRES;
            /*
            if (listaPasajeros[position].ApellidoPaterno != null)
                nombreCompleto = nombreCompleto + " " + listaPasajeros[position].ApellidoPaterno;
            if (listaPasajeros[position].ApellidoMaterno != null)
                nombreCompleto = nombreCompleto + " " + listaPasajeros[position].ApellidoMaterno;
*/
            TextView pasajeroNombre = (TextView) convertView.findViewById(R.id.lblPasajeroNombre);
            pasajeroNombre.setText(nombreCompleto);

            TextView pasajeroEmpresa = (TextView) convertView.findViewById(R.id.lblPasajeroEmpresa);
            pasajeroEmpresa.setText(listaPasajeros.get(position).EMPRESA);

            TextView pasajeroDNI = (TextView) convertView.findViewById(R.id.lblPasajeroDNI);
            pasajeroDNI.setText(listaPasajeros.get(position).DNI);

            CheckBox pasajeroCheckSeleccionar = (CheckBox) convertView.findViewById(R.id.checkBoxListaPasajeros);
            pasajeroCheckSeleccionar.setChecked(listaCheckBoxPasajeros[position]);
            pasajeroCheckSeleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaCheckBoxPasajeros[position] = !listaCheckBoxPasajeros[position];
                    boolean flag=false;
                    for(int i=0;i<listaCheckBoxPasajeros.length;i++){
                        if(listaCheckBoxPasajeros[i]){
                            flag=true;
                        }
                    }
                    botonAgregar.setEnabled(flag);
                }
            });

            return convertView;
        }
    }
}

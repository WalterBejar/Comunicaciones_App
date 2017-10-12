package com.pango.comunicaciones;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class ReservaTicketListaPasajeros extends AppCompatActivity {

    String codigoTicket;
    PasajeroModel[] listaPasajeros = {};
    boolean[] listaCheckBoxPasajeros = {};

    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    Button botonEliminar;

    ListaAdapter listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_lista_pasajeros);
        setTitle("Reserva de Buses");

        botonEliminar=(Button) findViewById(R.id.botonEliminarPasajero);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Conectándose al servidor");
        progressDialog.setMessage("Por favor, espere...");
        progressDialog.setCancelable(false);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Está a punto de eliminar un conjunto de reservas.");
        builder.setMessage("¿Desea continuar?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Gestionar la reserva y cerrar el diálogo
                new EliminarPasajeros().execute();
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

        // Recibir codigo ticket
        Bundle extras = getIntent().getExtras();
        codigoTicket = extras.getString("CodigoTicket");

        ListView listaTickets = (ListView) findViewById(R.id.listaPasajeros);
        listaAdapter = new ListaAdapter();
        listaTickets.setAdapter(listaAdapter);
        new GetLista().execute();


    }

    public void clickEnEliminarPasajeros(View view) {
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void toBuscarPasajeros(View view) {
        Intent toReservaTicketBuscarPasajeros = new Intent(getApplicationContext(), ReservaTicketBuscarPasajeros.class);
        toReservaTicketBuscarPasajeros.putExtra("CodigoTicket", codigoTicket);
        startActivityForResult(toReservaTicketBuscarPasajeros,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //

        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Gson gson = new Gson();
                listaPasajeros = gson.fromJson(data.getData().toString(), PasajeroModel[].class);
                listaCheckBoxPasajeros = new boolean[listaPasajeros.length];
                listaAdapter.notifyDataSetChanged();
            }
        }
    }

    public class GetLista extends AsyncTask<String, String, String> {
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
                    listaPasajeros = getPasajeroModel.Data;
                    listaCheckBoxPasajeros = new boolean[listaPasajeros.length];
                    listaAdapter.notifyDataSetChanged();
            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(Utils.getUrlForReservaTicketListaPasajeros(codigoTicket));
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


    public class EliminarPasajeros extends AsyncTask<String, String, String> {
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
                    Toast.makeText(getApplicationContext(),"La operación se ha realizado con éxito",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    GetPasajeroModel getPasajeroModel = gson.fromJson(str, GetPasajeroModel.class);
                    listaPasajeros = getPasajeroModel.Data;
                    listaCheckBoxPasajeros = new boolean[listaPasajeros.length];
                    listaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                List<PersonaPostReservaModel> listaPasajerosAEliminar = new ArrayList<PersonaPostReservaModel>();
                Gson gson = new Gson();
                for (int i = 0 ; i < listaPasajeros.length ; i++ )
                    if (listaCheckBoxPasajeros[i])
                        listaPasajerosAEliminar.add(Utils.fromPasajeroToPersona(listaPasajeros[i], codigoTicket));

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost;
                httpPost = new HttpPost(Utils.getUrlForEliminarReserva());

                StringEntity postString = new StringEntity("{\"Data\":"+ gson.toJson(listaPasajerosAEliminar.toArray())+"}");
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

    public class ListaAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (listaPasajeros == null)
                return 0;
            return listaPasajeros.length;
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
            String nombreCompleto = listaPasajeros[position].NOMBRES;
            /*
            if (listaPasajeros[position].ApellidoPaterno != null)
                nombreCompleto = nombreCompleto + " " + listaPasajeros[position].ApellidoPaterno;
            if (listaPasajeros[position].ApellidoMaterno != null)
                nombreCompleto = nombreCompleto + " " + listaPasajeros[position].ApellidoMaterno;
*/
            TextView pasajeroNombre = (TextView) convertView.findViewById(R.id.lblPasajeroNombre);
            pasajeroNombre.setText(nombreCompleto);

            TextView pasajeroEmpresa = (TextView) convertView.findViewById(R.id.lblPasajeroEmpresa);
            pasajeroEmpresa.setText(listaPasajeros[position].EMPRESA);

            TextView pasajeroDNI = (TextView) convertView.findViewById(R.id.lblPasajeroDNI);
            pasajeroDNI.setText(listaPasajeros[position].DNI);

            CheckBox pasajeroCheckEliminar = (CheckBox) convertView.findViewById(R.id.checkBoxListaPasajeros);

            pasajeroCheckEliminar.setEnabled(!listaPasajeros[position].DNI.equals("DNI Reservado"));

            pasajeroCheckEliminar.setChecked(listaCheckBoxPasajeros[position]);
            pasajeroCheckEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaCheckBoxPasajeros[position] = !listaCheckBoxPasajeros[position];
                    boolean flag=false;
                    for(int i=0;i<listaCheckBoxPasajeros.length;i++){
                        if(listaCheckBoxPasajeros[i]){
                            flag=true;
                        }
                    }
                    botonEliminar.setEnabled(flag);
                }
            });
            return convertView;
        }
    }
}

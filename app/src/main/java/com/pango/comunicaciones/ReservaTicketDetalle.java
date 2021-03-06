package com.pango.comunicaciones;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pango.comunicaciones.model.GetPasajeroModel;
import com.pango.comunicaciones.model.PasajeroModel;
import com.pango.comunicaciones.model.PersonaPostReservaModel;
import com.pango.comunicaciones.model.TicketModel;

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

public class ReservaTicketDetalle extends AppCompatActivity {

    String codigoTicket;
    TicketModel ticket;

    String[] busDetalleListaNombre = {"Nro Programa","Nombre Bus","Origen", "Destino", "Fecha", "Hora","Disponibles","Ocupados","Total Asientos", "Reservas Hecha", "Tipo Bus","Patente", "Marca", "Modelo", "Tipo Vehiculo"};




    DetalleAdapter detalleAdapter;
    ProgressDialog progressDialog;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;


    Button botonGestionarPasajeros, botonGestionarReserva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_detalle);
        setTitle("Reserva de Buses");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbus3);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Conectándose al servidor");
        progressDialog.setMessage("Por favor, espere...");
        progressDialog.setCancelable(false);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Desea continuar?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Gestionar la reserva y cerrar el diálogo
                new GestionarReserva().execute();
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

        botonGestionarPasajeros = (Button) findViewById(R.id.botonGestionarPasajeros);
        botonGestionarReserva = (Button) findViewById(R.id.botonGestionarReserva);


        // Establecer visibilidad de botonGestionarPasajeros
        if (Utils.esAdmin)
            botonGestionarPasajeros.setVisibility(View.VISIBLE);
        else
            botonGestionarPasajeros.setVisibility(View.GONE);

        // Recibir codigo ticket
        Bundle extras = getIntent().getExtras();
        codigoTicket = extras.getString("CodigoTicket");

        // Asignar adapter a lista
        detalleAdapter = new DetalleAdapter();
        ListView listaDetalles = (ListView) findViewById(R.id.listaDetalles);
        listaDetalles.setAdapter(detalleAdapter);

        //LLamar a GetBusDetalles
        new GetBusDetalles().execute();

    }

    public void gestionarReserva(View view){
        String titulo = "";
        if (ticket.SEPARADO)
        {
            builder.setIcon(R.drawable.erroricon);
            builder.setMessage("Está a punto de eliminar su reserva en este viaje.");
        }
        else{
            builder.setIcon(R.drawable.confirmicon);
            builder.setMessage("Está a punto de reservar un ticket en este viaje.");
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void toReservaTicketListaPasajeros(View view){
        Intent toReservaTicketListaPasajeros = new Intent(getApplicationContext(), ReservaTicketListaPasajeros.class);
        toReservaTicketListaPasajeros.putExtra("CodigoTicket", ticket.IDPROG);
        startActivity(toReservaTicketListaPasajeros);
    }

    public class GetBusDetalles extends AsyncTask<String, String, String> {
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
                    ticket = gson.fromJson(str, TicketModel.class);

                    if (ticket.SEPARADO) {
                        botonGestionarReserva.setText("Eliminar reserva");
                        //Toast.makeText(getApplicationContext(),"Despues de pedir detalles ticket.Separado: "+ticket.Separado,Toast.LENGTH_SHORT).show();
                    }
                    else
                        botonGestionarReserva.setText("Reservar ticket");

                    // Actualizar lista
                    detalleAdapter.notifyDataSetChanged();

            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(Utils.getUrlForReservaTicketDetalle(codigoTicket));
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

    public class GestionarReserva extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            switch (str) {
                case "401":
                    /*Intent myIntent = new Intent(ReservaTicketDetalle.this, MainActivity.class);
                    myIntent.putExtra("respuesta", true); //Optional parameters
                    ReservaTicketDetalle.this.startActivity(myIntent);
                    finish();*/

                    startActivity(new Intent(ReservaTicketDetalle.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();



                    break;
                case "307":
                    Toast.makeText(getApplicationContext(),"Se perdio la conexion al servidor",Toast.LENGTH_SHORT).show();
                    break;
                case "450":
                    Toast.makeText(getApplicationContext(),"Ocurrio un error de conexion",Toast.LENGTH_SHORT).show();
                    break;
                case "500":
                    Toast.makeText(getApplicationContext(),"Ocurrio un error interno en el servidor",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Gson gson = new Gson();
                    GetPasajeroModel getPasajeroModel = gson.fromJson(str, GetPasajeroModel.class);
                    ArrayList<PasajeroModel> listaPasajeros;
                    listaPasajeros = getPasajeroModel.Data;
                    String sms="La operación se ha realizado con éxito";
                    //int pass=0;
                    for (PasajeroModel p :listaPasajeros) {
                        if(p.DNI.equals(Utils.codPersona))
                        {
                            //Toast.makeText(getApplicationContext(),p.DSCR,Toast.LENGTH_SHORT).show();
                            //pass=p.RESPUESTA;
                            AlertDialog alertDialog = new AlertDialog.Builder(ReservaTicketDetalle.this).create();
                            if(p.RESPUESTA>0){
                                alertDialog.setTitle("Ok");
                                if(p.RESPUESTA==1)
                                alertDialog.setIcon(R.drawable.confirmicon);
                                else{
                                    alertDialog.setTitle("Advertencia");
                                    alertDialog.setIcon(R.drawable.warrninicon);
                                }
                            }
                            else {
                                alertDialog.setTitle("Ocurrio un Error");
                                alertDialog.setIcon(R.drawable.erroricon);
                            }
                            alertDialog.setMessage(p.DSCR);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            break;
                        }
                    }
                   // if(pass>0){
                        ticket.SEPARADO = !ticket.SEPARADO;
                        if (ticket.SEPARADO)
                            botonGestionarReserva.setText("Eliminar reserva");
                        else
                            botonGestionarReserva.setText("Reservar ticket");

                        // Pedir detalles bus (actualizar cantidad pasajeros)
                        new GetBusDetalles().execute();
                    //}
            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                PersonaPostReservaModel[] persona = {Utils.getPersonaPostReservaModel(ticket.IDPROG)};
                Gson gson = new Gson();

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost;
                if (ticket.SEPARADO)
                    httpPost = new HttpPost(Utils.getUrlForEliminarReserva());
                else
                    httpPost = new HttpPost(Utils.getUrlForAgregarReserva());
                StringEntity postString = new StringEntity("{\"Data\":"+gson.toJson(persona)+"}");
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
                return "450";
            }
        }
    }

    public class DetalleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (ticket == null)
                return 0;
            return busDetalleListaNombre.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (ticket == null)
                return  null;

            convertView = getLayoutInflater().inflate(R.layout.reserva_tickets_detalle, null);

            TextView ladoIzquierdo = (TextView) convertView.findViewById(R.id.lblBusCaracteristica);
            ladoIzquierdo.setText(busDetalleListaNombre[position]);

            TextView ladoDerecho = (TextView) convertView.findViewById(R.id.lblBusDetalle);
            ladoDerecho.setText(Utils.getTicketProperty(ticket,busDetalleListaNombre[position]));

            return convertView;
        }
    }
}

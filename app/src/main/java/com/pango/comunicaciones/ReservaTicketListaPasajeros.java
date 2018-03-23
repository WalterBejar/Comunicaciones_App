package com.pango.comunicaciones;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import java.util.List;

public class ReservaTicketListaPasajeros extends AppCompatActivity {

    String codigoTicket;
    ArrayList<PasajeroModel> listaPasajeros = new  ArrayList<PasajeroModel>();
    boolean[] listaCheckBoxPasajeros = {};

    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    Button botonEliminar;

    ListaAdapter listaAdapter;
    boolean pass=true;
    TicketModel ticket;
    TextView textLoading;
    SwipeRefreshLayout swipeRefreshLayout;
    String[] busDetalleListaNombre = {"Nro Programa","Nombre Bus","Origen", "Destino", "Fecha", "Hora","Disponibles","Ocupados","Total Asientos", "Reservas Hecha", "Tipo Bus","Patente", "Marca", "Modelo", "Tipo Vehiculo"};
    DetalleAdapter detalleAdapter;
    boolean loadingTop=false;
    boolean listenerFlag;
    boolean upFlag;
    boolean downFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_lista_pasajeros);
        setTitle("Reserva de Buses");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbus4);
        setSupportActionBar(toolbar);

        botonEliminar=(Button) findViewById(R.id.botonAgregarPasajero);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Conectándose al servidor");
        progressDialog.setMessage("Por favor, espere...");
        progressDialog.setCancelable(false);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Desea continuar?");//("Está a punto de eliminar un conjunto de reservas.");

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
        // refresh
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        textLoading =(TextView)findViewById(R.id.textLoading);
        textLoading.setVisibility(View.GONE);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);


        // Recibir codigo ticket
        Bundle extras = getIntent().getExtras();
        codigoTicket = extras.getString("CodigoTicket");

        // Asignar adapter a lista
        detalleAdapter = new DetalleAdapter();
        ListView listaDetalles = (ListView) findViewById(R.id.listaDetalles);
        listaDetalles.setAdapter(detalleAdapter);

        //LLamar a GetBusDetalles
        new GetBusDetalles().execute();

        ListView listaTickets = (ListView) findViewById(R.id.listaPasajeros);
        listaAdapter = new ListaAdapter();
        listaTickets.setAdapter(listaAdapter);
        new GetLista().execute();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadingTop=true;
                textLoading.setVisibility(View.VISIBLE);
                new GetBusDetalles().execute();
                new GetLista().execute();
            }
        });


        listaTickets.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    listenerFlag = false;
                    Log.d("--:","---------------------------");
                }
                if (upFlag && scrollState == SCROLL_STATE_IDLE) {
                    upFlag = false;
                    swipeRefreshLayout.setEnabled( true );
                }
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    listenerFlag = true;
                    swipeRefreshLayout.setEnabled( false );
                    Log.d("started","comenzo");
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                Log.d("+1:",""+view.canScrollVertically(1));
                Log.d("-1:",""+view.canScrollVertically(-1));
                // Log.d("x:",""+view.getScrollX());
                if (listenerFlag && !view.canScrollVertically(1)){
                    downFlag = true;
                    upFlag = false;
                }
                if (listenerFlag && !view.canScrollVertically(-1)){
                    upFlag = true;
                    downFlag = false;
                }
            }
        });
    }

    public void showListviewDetalle(View view){
        ListView listaDetalles = (ListView) findViewById(R.id.listaDetalles);
        if (listaDetalles.getVisibility() == View.GONE)
            listaDetalles.setVisibility(View.VISIBLE);
        else
            listaDetalles.setVisibility(View.GONE);
    }

    public void clickEnEliminarPasajeros(View view) {
        builder.setIcon(R.drawable.erroricon);
        builder.setMessage("Está a punto de eliminar un conjunto de reservas.");
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
                listaPasajeros = gson.fromJson(data.getData().toString(),GetPasajeroModel.class).Data;
                listaCheckBoxPasajeros = new boolean[listaPasajeros.size()];
                listaAdapter.notifyDataSetChanged();
            }
        }
    }

    public class GetLista extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           if(pass) progressDialog.show();
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            switch (str) {
                case "401":
                    /*Intent myIntent = new Intent(ReservaTicketListaPasajeros.this, MainActivity.class);
                    myIntent.putExtra("respuesta", true); //Optional parameters
                    ReservaTicketListaPasajeros.this.startActivity(myIntent);
                    finish();*/

                    startActivity(new Intent(ReservaTicketListaPasajeros.this, MainActivity.class)
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
                    listaPasajeros = getPasajeroModel.Data;
                    listaCheckBoxPasajeros = new boolean[listaPasajeros.size()];
                    if(listaPasajeros.size() ==0)Toast.makeText(getApplicationContext(),"0 pasajeros",Toast.LENGTH_SHORT).show();
                    listaAdapter.notifyDataSetChanged();

                    swipeRefreshLayout.setRefreshing(false);
                    textLoading.setVisibility(View.GONE);
                    loadingTop=false;
                    pass=false;
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
                return "450";
            }

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
                    listaCheckBoxPasajeros = new boolean[listaPasajeros.size()];
                    listaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                List<PersonaPostReservaModel> listaPasajerosAEliminar = new ArrayList<PersonaPostReservaModel>();
                Gson gson = new Gson();
                for (int i = 0 ; i < listaPasajeros.size() ; i++ )
                    if (listaCheckBoxPasajeros[i])
                        listaPasajerosAEliminar.add(Utils.fromPasajeroToPersona(listaPasajeros.get(i), codigoTicket));

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
            boolean delete=true;

            TextView pasajeroNombre = (TextView) convertView.findViewById(R.id.lblPasajeroNombre);
            pasajeroNombre.setText(nombreCompleto);
            String BackgrColor= "#FFFFFF";
            if(listaCheckBoxPasajeros[position]) {
                BackgrColor= "#D6EAF8";
            }
            convertView.setBackgroundColor(Color.parseColor(BackgrColor));

            TextView pasajeroEmpresa = (TextView) convertView.findViewById(R.id.lblPasajeroEmpresa);
            int colorInicial=0;
            if(listaPasajeros.get(position).DNI.equals("DNI Reservado"))delete=false;
            if(listaPasajeros.get(position).DSCR != null){
                String tempString=listaPasajeros.get(position).DSCR;
                Spannable spanString = new SpannableString(tempString);
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                if(listaPasajeros.get(position).RESPUESTA>0){
                    colorInicial=listaPasajeros.get(position).RESPUESTA;
                    spanString.setSpan(new ForegroundColorSpan(Color.GREEN), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(colorInicial==1)convertView.setBackgroundColor(Color.parseColor("#A9DFBF"));
                    else  convertView.setBackgroundColor(Color.parseColor("#F9E79F"));
                }
                else{
                   // spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
                   // spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
                    spanString.setSpan(new ForegroundColorSpan(Color.RED), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    convertView.setBackgroundColor(Color.parseColor("#FADBD8"));
                    delete=false;
                    colorInicial=-1;
                }
                pasajeroEmpresa.setText(spanString);
            }
            else pasajeroEmpresa.setText(listaPasajeros.get(position).EMPRESA);

            TextView pasajeroDNI = (TextView) convertView.findViewById(R.id.lblPasajeroDNI);
            pasajeroDNI.setText(listaPasajeros.get(position).DNI);

            TextView lblCargo=(TextView) convertView.findViewById(R.id.lblCargo);

            if(listaPasajeros.get(position).CARGO.equals("")||listaPasajeros.get(position).CARGO==null){
                lblCargo.setVisibility(View.GONE);
            }else {
                lblCargo.setText(listaPasajeros.get(position).CARGO);
            }





            CheckBox pasajeroCheckEliminar = (CheckBox) convertView.findViewById(R.id.checkBoxListaPasajeros);

            pasajeroCheckEliminar.setEnabled(delete);

            pasajeroCheckEliminar.setChecked(listaCheckBoxPasajeros[position]);


            final View finalConvertView = convertView;
            final int finalColorInicial = colorInicial;

            pasajeroCheckEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaCheckBoxPasajeros[position] = !listaCheckBoxPasajeros[position];
                    String BackgrColor= "#FFFFFF";
                    if(listaCheckBoxPasajeros[position])  BackgrColor= "#D6EAF8";
                    else if(finalColorInicial >0){
                        if(finalColorInicial==1) BackgrColor= "#A9DFBF";
                        else BackgrColor= "#F9E79F";
                    }
                    else if(finalColorInicial <0)BackgrColor= "#FADBD8";
                    finalConvertView.setBackgroundColor(Color.parseColor(BackgrColor));
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

 // Detalle Tickets
    public class GetBusDetalles extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog.show();
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
                    // Actualizar lista
                    detalleAdapter.notifyDataSetChanged();
            }
           // progressDialog.dismiss();
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

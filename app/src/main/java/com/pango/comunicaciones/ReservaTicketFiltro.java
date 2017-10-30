package com.pango.comunicaciones;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pango.comunicaciones.model.GetTicketModel;
import com.pango.comunicaciones.model.TicketModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservaTicketFiltro extends AppCompatActivity {

    //String[] terminalesCodigos;
    ArrayList<String> terminalesNombres;
    ArrayList<TicketModel> tickets;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button botonBuscarTickets, botonEscogerFecha;
    Spinner spinnerOrigen, spinnerDestino;

    ProgressDialog progressDialog;
    private int pageCount = 2;
    String origenEscogido;
    String destinoEscogido;
    String fechaEscogida;

    ListView listaTickets;
    int cantidadTickets;
    int contTickets;
    int page2=1;
    boolean escogioOrigen;
    boolean escogioDestino;
    boolean escogioFecha;
    boolean buscar;
    FiltroAdapter filtroAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_filtro);
        setTitle("Reserva de Buses");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbus1);
        setSupportActionBar(toolbar);

        botonBuscarTickets = (Button) findViewById(R.id.botonBuscarTickets);
        botonEscogerFecha = (Button) findViewById(R.id.botonEscogerFecha);
        spinnerOrigen = (Spinner) findViewById(R.id.spinnerOrigen);
        spinnerDestino = (Spinner) findViewById(R.id.spinnerDestino);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Conectándose al servidor");
        progressDialog.setMessage("Por favor, espere...");
        progressDialog.setCancelable(false);
        cantidadTickets = 6;
        contTickets=0;

        buscar= false;
        tickets = new ArrayList<TicketModel>();

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date actual = myCalendar.getTime();
                SimpleDateFormat dt = new SimpleDateFormat("dd 'de' MMMM");
                botonEscogerFecha.setText(dt.format(actual));

                escogioFecha = true;
                dt = new SimpleDateFormat("yyyyMMdd");
                fechaEscogida = dt.format(actual);
                if (escogioOrigen && escogioDestino && escogioFecha)
                    botonBuscarTickets.setEnabled(true);
            }

        };

        new GetTerminales().execute();

        listaTickets = (ListView) findViewById(R.id.listaTickets);
        filtroAdapter = new FiltroAdapter();
        listaTickets.setAdapter(filtroAdapter);

        listaTickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toReservaTicketDetalle = new Intent(getApplicationContext(), ReservaTicketDetalle.class);
                toReservaTicketDetalle.putExtra("CodigoTicket", tickets.get(position).IDPROG);
                startActivity(toReservaTicketDetalle);
            }
        });

        listaTickets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount){

            // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //loadNextDataFromApi(page);
                if(tickets.size()!=contTickets) {
                    new BuscarTickets().execute(String.valueOf(page2));
                    return true; // ONLY if more data is actually being loaded; false otherwise.
                }else{
                    return false;
                }
            }
        });
        new BuscarTickets().execute();
    }


    public void clickEnBuscarViajes(View view){
        buscar=true;
        page2=1;
        new BuscarTickets().execute();
    }

    public void showHideGrupo(View view){
        ConstraintLayout grupo = (ConstraintLayout) findViewById(R.id.grupoConstraint);
        if (grupo.getVisibility() == View.GONE)
            grupo.setVisibility(View.VISIBLE);
        else
            grupo.setVisibility(View.GONE);
    }

    public void escogerFecha(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(Calendar.HOUR, 0);
        tempCalendar.set(Calendar.MINUTE, 0);
        tempCalendar.set(Calendar.SECOND, 0);
        tempCalendar.set(Calendar.MILLISECOND, 0);

        datePickerDialog.getDatePicker().setMinDate(tempCalendar.getTimeInMillis());
        tempCalendar.set(Calendar.MONTH, (new Date()).getMonth() + 1);
        datePickerDialog.getDatePicker().setMaxDate(tempCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

// controlador
    public class BuscarTickets extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            if (!escogioDestino)
                destinoEscogido = "-";
            if (!escogioOrigen)
                origenEscogido = "-";
            if (!escogioFecha)
                fechaEscogida = "-";//Utils.getFechaHoy();
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
                    GetTicketModel getTicketModel = gson.fromJson(str, GetTicketModel.class);
                    if(buscar){
                        tickets = getTicketModel.Data;
                        buscar=false;
                    }
                    else tickets.addAll(getTicketModel.Data);
                    page2++;
                    contTickets = getTicketModel.Count;
                    filtroAdapter.notifyDataSetChanged();
            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            int page;
            if(params.length==0) page=1;
            else page=Integer.parseInt(params[0]);

            try {
                String url2 =Utils.getUrlForBuscarTickets(origenEscogido, destinoEscogido, fechaEscogida,page, cantidadTickets);
                URL url = new URL(Utils.getUrlForBuscarTickets(origenEscogido, destinoEscogido, fechaEscogida,page, cantidadTickets));
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
                //Toast.makeText(getApplicationContext(),con.getResponseCode(),Toast.LENGTH_SHORT).show();
                //return "" + con.getResponseCode();
                //return token;
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            return null;
        }
    }

    public class GetTerminales extends AsyncTask<String, String, String> {
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
                    final Terminal arrayTerminales = gson.fromJson(str, Terminal.class);
                   // terminalesCodigos = new String[arrayTerminales.length];
                    terminalesNombres= new ArrayList<>();
                    terminalesNombres.add("-");
                    terminalesNombres.addAll(arrayTerminales.Data);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.pango.comunicaciones.ReservaTicketFiltro.this,
                            android.R.layout.simple_spinner_item, terminalesNombres);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDestino.setAdapter(adapter);
                    spinnerOrigen.setAdapter(adapter);

                    spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            destinoEscogido = terminalesNombres.get(position);
                            escogioDestino = true;
                            if (escogioOrigen && escogioDestino && escogioFecha)
                                botonBuscarTickets.setEnabled(true);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            destinoEscogido = "ESCOJA UN DESTINO";
                        }
                    });
                    spinnerOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            origenEscogido = terminalesNombres.get(position);
                            escogioOrigen = true;
                            if (escogioOrigen && escogioDestino && escogioFecha)
                                botonBuscarTickets.setEnabled(true);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            origenEscogido = "ESCOJA UN ORIGEN";
                        }
                    });
            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(Utils.getUrlForReservaTicketTerminales());
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
                //Toast.makeText(getApplicationContext(),con.getResponseCode(),Toast.LENGTH_SHORT).show();
                //return "" + con.getResponseCode();
                //return token;
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            return null;
        }

    }

    public class FiltroAdapter extends BaseAdapter{

        DateFormat formatoInicial = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd MMM");
        DateFormat formatoHora = new SimpleDateFormat("h:mm a");
        @Override
        public int getCount() {
            if (tickets != null)
                return tickets.size();
            return 0;
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
            if (tickets == null)
                return  null;
            convertView = getLayoutInflater().inflate(R.layout.reserva_tickets_row, null);

            TextView busNombre = (TextView) convertView.findViewById(R.id.lblBusNombre);
            busNombre.setText(tickets.get(position).SERVICIO+"\n"+tickets.get(position).ORIGEN+"\n"+tickets.get(position).DESTINO);

            TextView busHora = (TextView) convertView.findViewById(R.id.lblBusHora);
            try {
                Date temp= formatoInicial.parse(tickets.get(position).FECHA+"T"+tickets.get(position).HORA);
                busHora.setText(formatoFecha.format(temp).replace(".","").toUpperCase()+"\n"+formatoHora.format(temp).replace(". ","").replace(".",""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // nFecha.setText(formatoFecha.format(formatoInicial.parse((tickets.get(position).FECHA.substring(4,8)+"")));
            TextView busALibres = (TextView) convertView.findViewById(R.id.lblBusAsientosLibres);
            busALibres.setText("" + tickets.get(position).DISPONIBLES);

            TextView busAOcupados = (TextView) convertView.findViewById(R.id.lblBusAsientosOcupados);
            busAOcupados.setText("" + tickets.get(position).RESERVAS);

            if (tickets.get(position).SEPARADO)
            {
                convertView.setBackgroundColor(Color.parseColor("#689F38"));//("#689F38"));
                ((TextView) convertView.findViewById(R.id.lblBusAsientosLibres)).setTextColor(Color.parseColor("#ffffff"));
            }
            /*ImageView checkReserva = (ImageView) convertView.findViewById(R.id.imgBusReservado);
            if (!tickets.get(position).SEPARADO)
                checkReserva.setVisibility(View.INVISIBLE);
            else
                checkReserva.setVisibility(View.VISIBLE);
*/
            return convertView;
        }
    }
}

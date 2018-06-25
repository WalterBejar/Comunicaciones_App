package com.pango.comunicaciones;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Random;

import layout.FragmentTickets;

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

    String origenEscogido2="-";
    String destinoEscogido2="-";
    String fechaEscogida2="-";

    ListView listaTickets;
    TextView textLoading;
    SwipeRefreshLayout swipeRefreshLayout;
    int cantidadTickets;
    int contTickets;
    int page2=1;
    boolean escogioOrigen;
    boolean escogioDestino;
    boolean escogioFecha;
    boolean buscar;
    FiltroAdapter filtroAdapter;
    boolean showTotal=true;
    boolean loadingTop=false;
    boolean listenerFlag;
    boolean upFlag;
    boolean downFlag;

    ImageView user_buses;
    ImageView user_contact;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_filtro);
        //setTitle("Reserva de Buses");
        textView3=(TextView) findViewById(R.id.textView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonEscogerFecha.setText("TODAS LAS FECHAS");
                fechaEscogida="-";
            }
        });
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbus1);
        //setSupportActionBar(toolbar);
        user_buses = (ImageView) findViewById(R.id.usuario_buses);
        user_buses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservaTicketFiltro.this, Datos_Usuario.class);
                startActivity(intent);


            }
        });

        user_contact = (ImageView) findViewById(R.id.usuario_contacto);
        user_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservaTicketFiltro.this, Contactar.class);
                startActivity(intent);


            }
        });

        //////////////////////////////////////
        destinoEscogido="-";
        origenEscogido="-";
        botonBuscarTickets = (Button) findViewById(R.id.botonBuscarTickets);
        botonEscogerFecha = (Button) findViewById(R.id.botonEscogerFecha);
        spinnerOrigen = (Spinner) findViewById(R.id.spinnerOrigen);
        spinnerDestino = (Spinner) findViewById(R.id.spinnerDestino);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Conectándose al servidor");
        progressDialog.setMessage("Por favor, espere...");
        progressDialog.setCancelable(false);
        cantidadTickets = 8;
        contTickets=0;

        buscar= true;
        tickets = new ArrayList<TicketModel>();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        textLoading =(TextView)findViewById(R.id.textLoading);
        textLoading.setVisibility(View.GONE);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
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

        //new GetTerminales().execute();
        setTerminal();
        listaTickets = (ListView) findViewById(R.id.listaTickets);
        filtroAdapter = new FiltroAdapter();
        listaTickets.setAdapter(filtroAdapter);

        listaTickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateFormat formatoInicial = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
                long timenow=new Date().getTime()+24*60*60*1000,timebus=0;
                try {

                    Date temp= formatoInicial.parse(tickets.get(position).FECHA+"T18:00:00");
                    timebus=temp.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (Utils.esAdmin){
                    Intent toReservaTicketDetalle = new Intent(getApplicationContext(), ReservaTicketListaPasajeros.class);
                    toReservaTicketDetalle.putExtra("CodigoTicket", tickets.get(position).IDPROG);
                    startActivity(toReservaTicketDetalle);
                }
                else if(timenow<=timebus){

                    Intent toReservaTicketDetalle = new Intent(getApplicationContext(), ReservaTicketDetalle.class);
                    toReservaTicketDetalle.putExtra("CodigoTicket", tickets.get(position).IDPROG);
                    startActivity(toReservaTicketDetalle);
                }else{
                    Toast.makeText(getApplicationContext(),"Reserva de bus no disponible",Toast.LENGTH_SHORT).show();

                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadingTop=true;
                textLoading.setVisibility(View.VISIBLE);
                new BuscarTickets().execute("1",String.valueOf(tickets.size()));
                buscar=true;
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
                if (downFlag && scrollState == SCROLL_STATE_IDLE) {
                    downFlag = false;
                    if(tickets.size()!=contTickets) {
                       // Toast.makeText(getApplicationContext(),"ACEPTO DOWNFLAG",Toast.LENGTH_SHORT).show();
                        new BuscarTickets().execute(String.valueOf(page2),String.valueOf(cantidadTickets));
                        //  return true; // ONLY if more data is actually being loaded; false otherwise.
                    }else{

                        if(showTotal)Toast.makeText(getApplicationContext(),"Total de Registros:"+contTickets,Toast.LENGTH_SHORT).show();
                        showTotal=false;
                        //return false;
                    }

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

        new BuscarTickets().execute();
    }

    public void setTerminal(){

        terminalesNombres= new ArrayList<>();
        String[] terminal={"TODOS LOS ORÍGENES", "AEROPUERTO AREQUIPA",                "AREQUIPA",                "AREQUIPA NORTE",                "CUSCO",                "TINTAYA"};
        for(int i =0; i< terminal.length;i++)
            terminalesNombres.add(terminal[i]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.pango.comunicaciones.ReservaTicketFiltro.this,
                android.R.layout.simple_spinner_item, terminalesNombres);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerOrigen.setAdapter(adapter);


        ArrayList<String> TerminalDestino = new ArrayList<String>();
        TerminalDestino.add("TODOS LOS DESTINOS");
        for(int i =1; i< terminal.length;i++)
            TerminalDestino.add(terminal[i]);

        ArrayAdapter<String> adapterD = new ArrayAdapter<String>(com.pango.comunicaciones.ReservaTicketFiltro.this,android.R.layout.simple_spinner_item, TerminalDestino);
        adapterD.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDestino.setAdapter(adapterD);

        spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextSize(14);
                destinoEscogido = terminalesNombres.get(position);
                escogioDestino = true;
                if (escogioOrigen && escogioDestino && escogioFecha)
                    botonBuscarTickets.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                destinoEscogido = "- SEL. DESTINO -";
            }
        });
        spinnerOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextSize(14);
                origenEscogido = terminalesNombres.get(position);
                escogioOrigen = true;
                if (escogioOrigen && escogioDestino && escogioFecha)
                    botonBuscarTickets.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                origenEscogido = "- SEL. ORIGEN -";
            }
        });
    }

    public void clickEnBuscarViajes(View view){
        buscar=true;
        page2=1;
        showTotal=true;
        if (!escogioDestino || destinoEscogido.contains("SELECCIONE"))
            destinoEscogido = "-";
        if (!escogioOrigen || origenEscogido.contains("SELECCIONE"))
            origenEscogido = "-";
        if (!escogioFecha)
            fechaEscogida = "-";//Utils.getFechaHoy();
        destinoEscogido2=destinoEscogido;
        origenEscogido2=origenEscogido;
        fechaEscogida2=fechaEscogida;

        new BuscarTickets().execute();
        swipeRefreshLayout.setEnabled( true );
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
            if(buscar) progressDialog.show();
            else if(!loadingTop)Toast.makeText(getApplicationContext(),"Cargando mas datos... Espere",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            switch (str) {
                case "401":
                   /* Intent myIntent = new Intent(ReservaTicketFiltro.this, MainActivity.class);
                    myIntent.putExtra("respuesta", true); //Optional parameters
                    ReservaTicketFiltro.this.startActivity(myIntent);
                    finish();*/

                    startActivity(new Intent(ReservaTicketFiltro.this, MainActivity.class)
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
                    GetTicketModel getTicketModel = gson.fromJson(str, GetTicketModel.class);
                    if(buscar){
                        tickets = getTicketModel.Data;
                        buscar=false;
                    }
                    else tickets.addAll(getTicketModel.Data);
                    page2++;
                    contTickets = getTicketModel.Count;

                    if(loadingTop)
                    {
                        loadingTop=false;
                        swipeRefreshLayout.setRefreshing(false);
                        textLoading.setVisibility(View.GONE);
                        if(contTickets!=tickets.size()) swipeRefreshLayout.setEnabled( false );
                    }
                    filtroAdapter.notifyDataSetChanged();
            }
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                int page=1;
                int elementPager=cantidadTickets;
                if(params.length>0){
                    page=Integer.parseInt(params[0]);
                    elementPager=Integer.parseInt(params[1]);
                }


                URL url = new URL(Utils.getUrlForBuscarTickets(origenEscogido2, destinoEscogido2, fechaEscogida2,page, elementPager));
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
                //}
                //Toast.makeText(getApplicationContext(),con.getResponseCode(),Toast.LENGTH_SHORT).show();
                //return "" + con.getResponseCode();
                //return token;
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
                return "450";
            }

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
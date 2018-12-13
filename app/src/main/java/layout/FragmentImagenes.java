package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.ActImag;
import com.pango.comunicaciones.EndlessScrollListener;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.IActivity;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.ImgAdapter;
import com.pango.comunicaciones.controller.ActivityController;
import com.pango.comunicaciones.controller.ComController;
import com.pango.comunicaciones.controller.ImgController;
import com.pango.comunicaciones.controller.contadorController;
import com.pango.comunicaciones.controller.noticiacontroller;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.Img_Gal_List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pango.comunicaciones.GlobalVariables.imagen2;
import static com.pango.comunicaciones.GlobalVariables.noticias2;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentImagenes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentImagenes extends Fragment implements IActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentImagenes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentImagenes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentImagenes newInstance(String param1, String param2) {
        FragmentImagenes fragment = new FragmentImagenes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
/////////////////////////////////////////////////////////////////////////////////
    ListView recListImag;
    Context context;
    int res=1;
    View rootView;
   // boolean userScrolled = false;
   // public static final String TAG = "INFOAPP";
    boolean upFlag;
    boolean downFlag;
    boolean listenerFlag;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;
    boolean loadingTop=false;
    ConstraintLayout constraintLayout;
    boolean flag_enter=true;
    String url= "";
    List<Imagen> imagenList;
    ImgAdapter ca;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_imagenes, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

       // GlobalVariables.contpublic=1;

        recListImag = (ListView) rootView.findViewById(R.id.list_imag);

       //final ImgController obj = new ImgController(rootView,"url","get", FragmentImagenes.this);
        //obj.execute(String.valueOf(1),String.valueOf(GlobalVariables.num_vid));

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipelayout3);
        textView2 =(TextView)rootView.findViewById(R.id.textView3);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        constraintLayout=(ConstraintLayout) rootView.findViewById(R.id.const_main);
        constraintLayout.setVisibility(View.GONE);
        progressBar=(ProgressBar) rootView.findViewById(R.id.pbar_img);


        if(GlobalVariables.imagen2.size()==0) {

            /*
            final ImgController obj = new ImgController(rootView,"url","get", FragmentImagenes.this);
            obj.execute(String.valueOf(1),String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));
*/
            progressBar.setVisibility(View.VISIBLE);

            url = GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+"1"+"/"+GlobalVariables.num_vid+"/TP03/"+GlobalVariables.id_phone;
            final ActivityController obj = new ActivityController("get", url, FragmentImagenes.this, getActivity());
            obj.execute("");



        }else {
            /*ImgAdapter ca = new ImgAdapter(context, GlobalVariables.imagen2);
            recListImag.setAdapter(ca);
            */
            success("","-1");
        }

        /*final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater2 = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.act_imag, null);
        builder.setView(v);



*/



        recListImag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Click", "click en el elemento " + position + " de mi ListView");

                // if(!GlobalVariables.isScrolling){
                GlobalVariables.pos_item_img_det=position;

                GlobalVariables.img_get= GlobalVariables.imagen2.get(position);

                String titulo=GlobalVariables.imagen2.get(position).getTitulo();
                String fecha=GlobalVariables.imagen2.get(position).getFecha();

                //se conecta a un activity//
                Intent intent = new Intent(getActivity(), ActImag.class);
                intent.putExtra("titulo",titulo);
                intent.putExtra("fecha",fecha);

                startActivity(intent);
            }


            // }
        });

        //recListImag.canScrollVertically();

//if(res!=1) {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                textView2.setVisibility(View.VISIBLE);
                /*(new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        swipeRefreshLayout.setRefreshing(true);*/
                        loadingTop=true;
                        textView2.setVisibility(View.VISIBLE);

                        GlobalVariables.imagen2.clear();
                        GlobalVariables.contpublicImg=2;

                        GlobalVariables.flagUpSc=true;
                        GlobalVariables.flag_up_toast=true;
/*
                        final ImgController obj = new ImgController(rootView,"url","get", FragmentImagenes.this);
                        obj.execute(String.valueOf(1),String.valueOf(6),String.valueOf(loadingTop),String.valueOf(loadingTop));
  */
                //recListImag.setVisibility(View.GONE);

                flag_enter=false;


                Toast.makeText(rootView.getContext(),"Actualizando, por favor espere...",Toast.LENGTH_SHORT).show();

                url = GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+"1"+"/"+"6"+"/TP03/"+GlobalVariables.id_phone;
                final ActivityController obj = new ActivityController("get-0", url, FragmentImagenes.this, getActivity());
                obj.execute("0");





                        //new BuscarTickets().execute("1",String.valueOf(tickets.size()));
                        //buscar=true;
/*
                    }
                },3000);*/
            }
        });




        recListImag.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    listenerFlag = false;
                    Log.d("--:","---------------------------");
                }
                if (upFlag && scrollState == SCROLL_STATE_IDLE) {
                    upFlag = false;
                  //  Toast.makeText(rootView.getContext(),"ACEPTO UPFLAG",Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setEnabled( true );


                }
                if (downFlag && scrollState == SCROLL_STATE_IDLE) {
                    downFlag = false;
                   // Toast.makeText(rootView.getContext(),"ACEPTO DOWNFLAG",Toast.LENGTH_SHORT).show();

                    if(GlobalVariables.imagen2.size()!=GlobalVariables.contFotos&&flag_enter) {
                        constraintLayout.setVisibility(View.VISIBLE);
                        flag_enter=false;
/*
                        final ImgController obj = new ImgController(rootView, "url", "get", FragmentImagenes.this);
                        obj.execute(String.valueOf(GlobalVariables.contpublicImg), String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));
*/

                        GlobalVariables.contpublicImg+=1;
                        Log.d("contImg","a="+GlobalVariables.contpublicImg +"b="+GlobalVariables.num_vid);

                        url = GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+GlobalVariables.contpublicImg+"/"+GlobalVariables.num_vid+"/TP03/"+GlobalVariables.id_phone;
                        final ActivityController obj = new ActivityController("get-2", url, FragmentImagenes.this, getActivity());
                        obj.execute("2");



                        /*
                        final Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (obj.getStatus() == AsyncTask.Status.FINISHED) {
                                    constraintLayout.setVisibility(View.GONE);
                                    flag_enter=true;

                                } else {
                                    h.postDelayed(this, 50);
                                }
                            }
                        }, 250);

                        */




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

        listenerFlag = false;

        return rootView;
    }


    ////////////////////////////////////////////////////////////////////////


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void success(String data, String Tipo){

        progressBar.setVisibility(View.GONE);
        flag_enter=true;
        //imagenList = new ArrayList<>();

        if(Tipo.equals("")) {
            //dataFromServer(data);
            GlobalVariables.imagen2.addAll( dataFromServer(data));
            ca = new ImgAdapter(getActivity(), GlobalVariables.imagen2);
            recListImag.setAdapter(ca);

            if(GlobalVariables.imagen2.size()==0){
                swipeRefreshLayout.setVisibility(View.INVISIBLE);
                //tx_mensajeb.setVisibility(View.VISIBLE);
            }else{
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                //tx_mensajeb.setVisibility(View.GONE);
            }
        }else if(Tipo.equals("-1")) {

            ca = new ImgAdapter(getActivity(), GlobalVariables.imagen2);
            recListImag.setAdapter(ca);


        }else if(Tipo.equals("0")) {
            GlobalVariables.imagen2.addAll( dataFromServer(data));

            ca = new ImgAdapter(getActivity(), GlobalVariables.imagen2);
            recListImag.setAdapter(ca);
            swipeRefreshLayout.setRefreshing(false);
            textView2.setVisibility(View.GONE);
            swipeRefreshLayout.setEnabled(false);
            //recListImag.setVisibility(View.VISIBLE);

        }else if (Tipo.equals("2")){


            for(Imagen item:dataFromServer(data))
                ca.add(item);
            ca.notifyDataSetChanged();
            constraintLayout.setVisibility(View.GONE);


        }



    }



    @Override
    public void successpost(String data, String Tipo) {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void error(String mensaje, String Tipo) {
        progressBar.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.GONE);

        Toast.makeText(rootView.getContext(),mensaje ,Toast.LENGTH_SHORT).show();
    }



    public List<Imagen> dataFromServer(String respstring) {
        List<Imagen> imagen2 = new ArrayList<>();

        try {

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
                        //imagenList.add(new Imagen(CodRegistro, icon, Fecha, Titulo, dataf,cant_img));
                        imagen2.add(new Imagen(CodRegistro, icon, Fecha, Titulo, dataf,cant_img));
                        // }
                    }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imagen2;

    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

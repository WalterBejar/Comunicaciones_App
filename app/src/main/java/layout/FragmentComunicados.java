package layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.google.gson.Gson;
import com.pango.comunicaciones.ActComDetalle;
import com.pango.comunicaciones.EndlessScrollListener;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.IActivity;
import com.pango.comunicaciones.MainActivity;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.ComAdapter;
import com.pango.comunicaciones.controller.ActivityController;
import com.pango.comunicaciones.controller.ComController;
import com.pango.comunicaciones.controller.ImgController;
import com.pango.comunicaciones.controller.contadorController;
import com.pango.comunicaciones.controller.noticiacontroller;
import com.pango.comunicaciones.model.Comunicado;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pango.comunicaciones.GlobalVariables.comlist;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentComunicados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentComunicados extends Fragment implements IActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //List<Comunicado> comList;

    public FragmentComunicados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentComunicados.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentComunicados newInstance(String param1, String param2) {
        FragmentComunicados fragment = new FragmentComunicados();
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
/////////////////////////////////////////////////////////////
    //int a=0;
    private int pageCount = 0;
    Context context;

    ListView recListCom;
    int in=3;
    boolean upFlag;
    boolean downFlag;
    boolean listenerFlag;
    static int paginacion2=1;

    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;
    boolean loadingTop=false;
    ConstraintLayout constraintLayout;
    boolean flag_enter=true;
    String url= "";
    ComAdapter ca;
    ProgressBar progressBar;
    View rootView;
/*
    public  void success(){

    }
*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        rootView = inflater.inflate(R.layout.fragment_comunicados, container, false);
        recListCom = (ListView) rootView.findViewById(R.id.l_frag_com);
        progressBar=(ProgressBar) rootView.findViewById(R.id.pbar_com);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipelayout2);
        textView2 =(TextView)rootView.findViewById(R.id.textView2);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        constraintLayout=(ConstraintLayout) rootView.findViewById(R.id.const_main);
        constraintLayout.setVisibility(View.GONE);


        if(GlobalVariables.comlist.size()==0) {
           //   final ComController obj = new ComController(rootView, "url", "get-0", FragmentComunicados.this);
           // obj.execute(String.valueOf(1), String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));

            progressBar.setVisibility(View.VISIBLE);

            url = GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+"1"+"/"+GlobalVariables.num_vid+"/TP02/"+GlobalVariables.id_phone;
            final ActivityController obj = new ActivityController("get-"+ paginacion2, url, FragmentComunicados.this, getActivity());
            obj.execute("");



        }else {

            success("","-1");


        }
        recListCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Click", "click en el elemento " + position + " de mi ListView");
                GlobalVariables.com_pos= comlist.get(position);//captura los datos en la posiscion que se hace clic y almacena en not2pos

                GlobalVariables.doclic=true;
                GlobalVariables.pos_item_com=position;

                String titulo=comlist.get(position).titulo;
                String fecha=comlist.get(position).fecha;


                //se conecta a un activity//

                GlobalVariables.is_notification=false;
                Intent intent = new Intent(getActivity(), ActComDetalle.class);

                intent.putExtra("titulo",titulo);
                intent.putExtra("fecha",fecha);

                startActivity(intent);

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                textView2.setVisibility(View.VISIBLE);
            /*    (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        swipeRefreshLayout.setRefreshing(true);*/
                        loadingTop=true;
                        textView2.setVisibility(View.VISIBLE);

                        GlobalVariables.comlist.clear();
                        GlobalVariables.contpublicCom=2;
                        GlobalVariables.flagcom=true;
                        GlobalVariables.flagUpSc=true;
                        GlobalVariables.flag_up_toast=true;
                        flag_enter=false;
                //recListCom.setVisibility(View.GONE);
                        /*
                        final ComController obj = new ComController(rootView,"url","get", FragmentComunicados.this);
                        obj.execute(String.valueOf(1),String.valueOf(6),String.valueOf(loadingTop));
*/                      Toast.makeText(rootView.getContext(),"Actualizando, por favor espere...",Toast.LENGTH_SHORT).show();

                        url = GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+"1"+"/"+"6"+"/TP02/"+GlobalVariables.id_phone;
                        final ActivityController obj = new ActivityController("get-0", url, FragmentComunicados.this, getActivity());
                        obj.execute("0");





/*
                    }
                },3000);*/
            }
        });



        recListCom.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    listenerFlag = false;
                    Log.d("--:","---------------------------");
                }
                if (upFlag && scrollState == SCROLL_STATE_IDLE) {
                    upFlag = false;
                   // Toast.makeText(rootView.getContext(),"ACEPTO UPFLAG",Toast.LENGTH_SHORT).show();

                    swipeRefreshLayout.setEnabled( true );
                    Log.d("srl1", "true"+"up: "+upFlag+"down: "+downFlag);

                }
                if (downFlag && scrollState == SCROLL_STATE_IDLE) {
                    downFlag = false;
                  //  Toast.makeText(rootView.getContext(),"ACEPTO DOWNFLAG",Toast.LENGTH_SHORT).show();

                    if(GlobalVariables.comlist.size()!=GlobalVariables.contComunicado&&flag_enter) {
                        constraintLayout.setVisibility(View.VISIBLE);
                        flag_enter=false;
/*
                        final ComController obj = new ComController(rootView, "url", "get-2", FragmentComunicados.this);
                        obj.execute(String.valueOf(GlobalVariables.contpublicCom), String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));
*/

                        GlobalVariables.contpublicCom+=1;

                        url = GlobalVariables.Urlbase+ GlobalVariables.Urlbase2+GlobalVariables.contpublicCom+"/"+GlobalVariables.num_vid+"/TP02/"+GlobalVariables.id_phone;
                        final ActivityController obj = new ActivityController("get-2", url, FragmentComunicados.this, getActivity());
                        obj.execute("2");



                    }

                }
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    listenerFlag = true;
                    swipeRefreshLayout.setEnabled( false );
                    Log.d("srl2", "false");

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

                    swipeRefreshLayout.setEnabled( false );
                    Log.d("srl3", "false");
                }
            }




               /* if(mLastFirstVisibleItem<firstVisibleItem)
                {
                    Log.i("SCROLLING DOWN","TRUE");
                    ///if()
                }

                if(mLastFirstVisibleItem>firstVisibleItem)
                {
                    Log.i("SCROLLING UP","TRUE");


                }
                mLastFirstVisibleItem=firstVisibleItem;*/


        });
        return rootView;
    }






//////////////////////////////////////////////
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
    public void success(String data, String Tipo) {
        progressBar.setVisibility(View.GONE);
        flag_enter=true;
        listenerFlag = false;

        //comList= new ArrayList<>();


        if(Tipo.equals("")) {
            //flag_enter=true;
            GlobalVariables.comlist.addAll( dataFromServer(data));

            dataFromServer(data);

            ca = new ComAdapter(getActivity(), GlobalVariables.comlist);
            recListCom.setAdapter(ca);

            if(GlobalVariables.comlist.size()==0){
                swipeRefreshLayout.setVisibility(View.INVISIBLE);
                //tx_mensajeb.setVisibility(View.VISIBLE);
            }else{
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                //tx_mensajeb.setVisibility(View.GONE);
            }
        }else if(Tipo.equals("-1")) {

            ca = new ComAdapter(context, GlobalVariables.comlist);
            recListCom.setAdapter(ca);
        }else if(Tipo.equals("0")) {
            dataFromServer(data);
            GlobalVariables.comlist.addAll( dataFromServer(data));

            ca = new ComAdapter(getActivity(), GlobalVariables.comlist);
            recListCom.setAdapter(ca);
            swipeRefreshLayout.setRefreshing(false);
            textView2.setVisibility(View.GONE);
            swipeRefreshLayout.setEnabled(false);
            Log.d("srl4", "false");

            //recListCom.setVisibility(View.VISIBLE);


        }else if (Tipo.equals("2")){


            for(Comunicado item:dataFromServer(data))
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






    public List<Comunicado> dataFromServer(String respstring) {
        List<Comunicado> comTemp = new ArrayList<>();

        try {
            JSONObject respJSON = new JSONObject(respstring);
            JSONArray comunic = respJSON.getJSONArray("Data");

            GlobalVariables.cont_item = comunic.length();
            GlobalVariables.contComunicado = respJSON.getInt("Count");//obtiene el total de publicaciones en general


            int inc = 0;
            for (int i = 0; i < comunic.length(); i++) {
                JSONObject c = comunic.getJSONObject(i);
                //String T =c.getString("Tipo");
                //String A="TP02"

                //comunicado:2
                // if(T.equals("TP02")) {
                inc += 1;
                String CodRegistro = c.getString("CodRegistro");
                //String Tipo = c.getString("Tipo");
                int icon = R.drawable.ic_menu_publicaciones;
                //String Autor = c.getString("Autor");
                String Fecha = c.getString("Fecha");
                String Titulo = c.getString("Titulo");
                String Descripcion = c.getString("Descripcion");

                JSONObject Files = c.getJSONObject("Files");
                JSONArray Data2 = Files.getJSONArray("Data");

                JSONObject h = Data2.getJSONObject(0);

                String Urlmin2 = h.getString("Urlmin");

                String[] parts = Urlmin2.split("550px;");
                //String part1 = parts[0]+ GlobalVariables.anchoMovil+"px"; //obtiene: 19
                //String part2 = parts[1]; //obtiene: 19-A

                String Urlmin = parts[0] + GlobalVariables.anchoMovil + "px;" + parts[1];

                //comList.add(new Comunicado(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));
                comTemp.add(new Comunicado(CodRegistro, icon, Fecha, Titulo, Descripcion, Urlmin));


            }


    } catch (JSONException e) {
        e.printStackTrace();
    }

    return comTemp;


    }





}

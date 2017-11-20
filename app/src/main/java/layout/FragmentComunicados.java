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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.ActComDetalle;
import com.pango.comunicaciones.EndlessScrollListener;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.ComAdapter;
import com.pango.comunicaciones.controller.ComController;
import com.pango.comunicaciones.controller.ImgController;
import com.pango.comunicaciones.controller.contadorController;
import com.pango.comunicaciones.controller.noticiacontroller;
import com.pango.comunicaciones.model.Comunicado;

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
public class FragmentComunicados extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
    int a;
    int pag=1;
    int celda=20;
    private int pageCount = 0;
    Context context;

    private boolean isThereMore;
    List<Comunicado> lcom;
    Comunicado comunicado;
    ListView recListCom;
    int in=3;
    boolean upFlag;
    boolean downFlag;
    boolean listenerFlag;

    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;
    boolean loadingTop=false;
    ConstraintLayout constraintLayout;
    boolean flag_enter=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        final View rootView = inflater.inflate(R.layout.fragment_comunicados, container, false);
        recListCom = (ListView) rootView.findViewById(R.id.l_frag_com);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipelayout2);
        textView2 =(TextView)rootView.findViewById(R.id.textView2);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        constraintLayout=(ConstraintLayout) getActivity().findViewById(R.id.const_main);


        if(GlobalVariables.comlist.size()==0) {
              final ComController obj = new ComController(rootView, "url", "get", FragmentComunicados.this);
        obj.execute(String.valueOf(1), String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));

        }else {
            ComAdapter ca = new ComAdapter(context, GlobalVariables.comlist);
            recListCom.setAdapter(ca);
        }
        recListCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Click", "click en el elemento " + position + " de mi ListView");
                GlobalVariables.com_pos= comlist.get(position);//captura los datos en la posiscion que se hace clic y almacena en not2pos

                GlobalVariables.doclic=true;
                GlobalVariables.pos_item_com=position;

                String titulo=comlist.get(position).getTitulo();
                String fecha=comlist.get(position).getFecha();


                //se conecta a un activity//
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
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        swipeRefreshLayout.setRefreshing(true);
                        loadingTop=true;
                        textView2.setVisibility(View.VISIBLE);


                        GlobalVariables.comlist.clear();
                        GlobalVariables.contpublicCom=2;
                        GlobalVariables.flagcom=true;
                        GlobalVariables.flagUpSc=true;

                        GlobalVariables.flag_up_toast=true;
                        final ComController obj = new ComController(rootView,"url","get", FragmentComunicados.this);
                        obj.execute(String.valueOf(1),String.valueOf(6),String.valueOf(loadingTop));


                    }
                },3000);
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


                  /*  final contadorController obj1 = new contadorController(rootView,"url","get");
                    obj1.execute(String.valueOf(GlobalVariables.contComunicado),"/TP02");
                    final Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (obj1.getStatus() == AsyncTask.Status.FINISHED) {

                                if(GlobalVariables.contNoticia!=GlobalVariables.cont_pub_new){
                                    GlobalVariables.comlist.clear();
                                    GlobalVariables.contpublicCom=1;
                                    GlobalVariables.flagcom=true;
                                    GlobalVariables.flagUpSc=true;

                                    GlobalVariables.flag_up_toast=true;
                                    final ComController obj = new ComController(rootView,"url","get", FragmentComunicados.this);
                                    obj.execute(String.valueOf(1),String.valueOf(6));
                                }
                            } else {
                                h.postDelayed(this, 50);
                            }
                        }
                    }, 250);*/



                }
                if (downFlag && scrollState == SCROLL_STATE_IDLE) {
                    downFlag = false;
                  //  Toast.makeText(rootView.getContext(),"ACEPTO DOWNFLAG",Toast.LENGTH_SHORT).show();

                    if(GlobalVariables.comlist.size()!=GlobalVariables.contComunicado&&flag_enter) {
                        constraintLayout.setVisibility(View.VISIBLE);
                        flag_enter=false;

                        final ComController obj = new ComController(rootView, "url", "get", FragmentComunicados.this);
                        obj.execute(String.valueOf(GlobalVariables.contpublicCom), String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));

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

        listenerFlag = false;


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

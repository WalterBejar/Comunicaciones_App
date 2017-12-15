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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.ActVid;
import com.pango.comunicaciones.EndlessScrollListener;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.Utils;
import com.pango.comunicaciones.adapter.VidAdapter;
import com.pango.comunicaciones.controller.ImgController;
import com.pango.comunicaciones.controller.VidController;
import com.pango.comunicaciones.controller.contadorController;
import com.pango.comunicaciones.controller.noticiacontroller;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.Vid_Gal;
import com.pango.comunicaciones.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentVideos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentVideos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentVideos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentVideos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentVideos newInstance(String param1, String param2) {
        FragmentVideos fragment = new FragmentVideos();
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
/////////////////////////////////////////////////////////////////////
    ListView recListVid;
    Context context;
   // int a;
    View rootView;
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

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_videos, container, false);
        context = container.getContext();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        recListVid = (ListView) rootView.findViewById(R.id.recycler_vid);
        //LinearLayoutManager llm_vid = new LinearLayoutManager(getActivity());
        //llm_vid.setOrientation(LinearLayoutManager.VERTICAL);


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipelayout4);
        textView2 =(TextView)rootView.findViewById(R.id.textView4);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        constraintLayout=(ConstraintLayout) getActivity().findViewById(R.id.const_main);
        constraintLayout.setVisibility(View.GONE);

        /*List<Vid_Gal> dataf = new ArrayList<>();
        dataf.add(new Vid_Gal("0","Videos/2616.mp4", Utils.ChangeUrl("media/GetminFile/2616/550px;Maratón 10k 2017.jpg")));

        GlobalVariables.vidlist.add(new Video("SC2017000582",R.drawable.ic_menu_send,"2017-10-13T00:00:00","Maratón 10k 2017",dataf,1));
        GlobalVariables.vidlist.add(new Video("SC2017000582",R.drawable.ic_menu_send,"2017-10-13T00:00:00","Maratón 10k 2017",dataf,1));
        GlobalVariables.vidlist.add(new Video("SC2017000582",R.drawable.ic_menu_send,"2017-10-13T00:00:00","Maratón 10k 2017",dataf,1));

        VidAdapter ca = new VidAdapter(context, GlobalVariables.vidlist);
        recListVid.setAdapter(ca);*/



        if(GlobalVariables.vidlist.size()==0) {
            final VidController obj = new VidController(rootView, "url", "get", FragmentVideos.this);
            obj.execute(String.valueOf(1), String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));
        }else {
        VidAdapter ca = new VidAdapter(context, GlobalVariables.vidlist);
        recListVid.setAdapter(ca);
        }

        /*final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater2 = getActivity().getLayoutInflater();


        View v = inflater.inflate(R.layout.act_vid, null);
        builder.setView(v);
*/
        recListVid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("Click", "click en el elemento " + position + " de mi ListView");
                GlobalVariables.pos_item_vid=position;
                GlobalVariables.vid_det = GlobalVariables.vidlist.get(position);

                String titulo=GlobalVariables.vidlist.get(position).getTitulo();
                String fecha=GlobalVariables.vidlist.get(position).getFecha();

                Intent intent = new Intent(getActivity(), ActVid.class);

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

                        GlobalVariables.vidlist.clear();
                        GlobalVariables.contpublicVid=2;
                        GlobalVariables.flagUpSc=true;
                        GlobalVariables.flag_up_toast=true;
                        final VidController obj = new VidController(rootView,"url","get", FragmentVideos.this);
                        obj.execute(String.valueOf(1),String.valueOf(6),String.valueOf(loadingTop));
                        //new BuscarTickets().execute("1",String.valueOf(tickets.size()));
                        //buscar=true;

                    }
                },3000);
            }
        });


        recListVid.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    if(GlobalVariables.vidlist.size()!=GlobalVariables.contVideos&&flag_enter) {
                        constraintLayout.setVisibility(View.VISIBLE);
                        flag_enter=false;

                        final VidController obj = new VidController(rootView, "url", "get", FragmentVideos.this);
                        obj.execute(String.valueOf(GlobalVariables.contpublicVid), String.valueOf(GlobalVariables.num_vid),String.valueOf(loadingTop));

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




       /* recListVid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                int page2=page;

                if(GlobalVariables.vidlist.size()<GlobalVariables.contVideos) {

                    final VidController obj = new VidController(rootView,"url","get", FragmentVideos.this);
                    obj.execute(String.valueOf(page2),String.valueOf(GlobalVariables.num_vid));
                    pageCount++;

                    return true; // ONLY if more data is actually being loaded; false otherwise.
                }else{
                    //flag=true;
                    return false;

                }
            }
        });*/


        return rootView;

    }
///////////////////////////////////////////////////////////////////////////////////7
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

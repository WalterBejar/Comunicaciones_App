package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pango.comunicaciones.ActComDetalle;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.controller.NotifiController;

import static com.pango.comunicaciones.GlobalVariables.comlist;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentNotificacion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentNotificacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotificacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentNotificacion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotificacion.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotificacion newInstance(String param1, String param2) {
        FragmentNotificacion fragment = new FragmentNotificacion();
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private ListView list_alertas;
    private BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =inflater.inflate(R.layout.fragment_notificacion, container, false);
        list_alertas = (ListView) rootView.findViewById(R.id.list_notifica);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
       // MenuItem videoItem = bottomNavigationView.getMenu().getItem(4);
       // videoItem.setIcon(R.drawable.ic_menu_send);



        final NotifiController obj = new NotifiController(rootView,"url","get", FragmentNotificacion.this);
        obj.execute();




        list_alertas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlobalVariables.cod_public= GlobalVariables.notific_data.get(position).getCod_reg();
                //GlobalVariables.doclic=true;

                GlobalVariables.com_pos= null;//captura los datos en la posiscion que se hace clic y almacena en not2pos
                //GlobalVariables.pos_item_com=position;

                String titulo=GlobalVariables.notific_data.get(position).getTitulo();
                String fecha=GlobalVariables.notific_data.get(position).getFecha();

                GlobalVariables.is_notification=false;
                Intent intent = new Intent(getActivity(), ActComDetalle.class);
                intent.putExtra("titulo",titulo);
                intent.putExtra("fecha",fecha);

                startActivity(intent);


            }
        });




        return rootView;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////






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









               /* android.app.Fragment fragment = null;
                fragment = new Frag_com_det();
                if(null!=fragment) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();


                }*/
package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.pango.comunicaciones.MainActivity;
import com.pango.comunicaciones.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInicio2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicio2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentInicio2() { 
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInicio2.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInicio2 newInstance(String param1, String param2) {
        FragmentInicio2 fragment = new FragmentInicio2();
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

    //////////////////////////////////////////////////
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    CardView card1,card2,card3,card4,card5,card6;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_inicio3, container, false);
        bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        //getActivity().setTitle("Antapaccay");
        bottomNavigationView.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

      /*  navigationView.getMenu().findItem(R.id.nav_noticias).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_videos).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_tickets).setChecked(false);
*/
        //disableItem();

        card1 = (CardView) view.findViewById(R.id.card1);
        card2 = (CardView) view.findViewById(R.id.card2);
        card3 = (CardView) view.findViewById(R.id.card3);
        card4 = (CardView) view.findViewById(R.id.card4);
        card5 = (CardView) view.findViewById(R.id.card5);
        card6 = (CardView) view.findViewById(R.id.card6);



        card1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Noticias");


                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new FragmentNoticias();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, nuevoFragmento);
                transaction.addToBackStack(null);
                // Commit a la transacción
                transaction.commit();



                navigationView.getMenu().findItem(R.id.nav_noticias).setChecked(true);
                bottomNavigationView.setVisibility(View.VISIBLE);
                bottomNavigationView.getMenu().findItem(R.id.navigation_noticias).setChecked(true);


            }
        });


        card2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Eventos");


                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new FragmentComunicados();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, nuevoFragmento);
                transaction.addToBackStack(null);
                // Commit a la transacción
                transaction.commit();

                //bottomNavigationView.getMenu().findItem(R.id.navigation_imagenes).setChecked(true);
                bottomNavigationView.setVisibility(View.VISIBLE);
                navigationView.getMenu().findItem(R.id.nav_publicaciones).setChecked(true);


            }
        });
        card3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Fotos");


                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new FragmentImagenes();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, nuevoFragmento);
                transaction.addToBackStack(null);
                // Commit a la transacción
                transaction.commit();
                bottomNavigationView.getMenu().findItem(R.id.navigation_imagenes).setChecked(true);
                bottomNavigationView.setVisibility(View.VISIBLE);
                navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(true);

            }
        });
        card4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Reserva de Buses");


                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new FragmentTickets();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, nuevoFragmento);
                transaction.addToBackStack(null);
                // Commit a la transacción
                transaction.commit();

                bottomNavigationView.getMenu().findItem(R.id.navigation_tickets).setChecked(true);
                bottomNavigationView.setVisibility(View.VISIBLE);
                navigationView.getMenu().findItem(R.id.nav_tickets).setChecked(true);

            }
        });
        card5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Videos");

                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new FragmentVideos();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, nuevoFragmento);
                transaction.addToBackStack(null);
                // Commit a la transacción
                transaction.commit();

                bottomNavigationView.getMenu().findItem(R.id.navigation_videos).setChecked(true);
                bottomNavigationView.setVisibility(View.VISIBLE);
                navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
            }
        });
        card6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Redes Sociales");

                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new FragmentRedesSociales();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, nuevoFragmento);
                transaction.addToBackStack(null);
                // Commit a la transacción
                transaction.commit();

                //bottomNavigationView.getMenu().findItem(R.id.navigation_).setChecked(true);
                bottomNavigationView.setVisibility(View.VISIBLE);
                navigationView.getMenu().findItem(R.id.nav_RedesSociales).setChecked(true);
            }
        });


        return view;
    }


    public  void disableItem(){
        navigationView.getMenu().findItem(R.id.nav_noticias).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_videos).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_tickets).setChecked(false);
    }




/////////////////////////////////////////////
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

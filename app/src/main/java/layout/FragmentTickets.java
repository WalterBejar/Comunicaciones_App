package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pango.comunicaciones.R;
import com.pango.comunicaciones.controller.AuthController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTickets.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTickets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTickets extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentTickets() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTickets.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTickets newInstance(String param1, String param2) {
        FragmentTickets fragment = new FragmentTickets();
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
////////////////////////////////////////////////////////////////////////////////////////////////////
   // ArrayList<String> user_auth=new ArrayList<String>();

    Button btn_ingresar;
    EditText tx_user;
    EditText tx_pass;
    ImageButton btn_borrarUser;
    ImageButton btn_borrarPass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            final View rootView =inflater.inflate(R.layout.fragment_tickets, container, false);



       // AppBarLayout appBarLayout=(AppBarLayout) getActivity().findViewById(R.id.barlayout);
        //appBarLayout.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        tx_user=(EditText) rootView.findViewById(R.id.usuario);
        tx_pass=(EditText) rootView.findViewById(R.id.password);

        btn_borrarUser = (ImageButton) rootView.findViewById(R.id.btn_deleteUser);
        btn_borrarPass = (ImageButton) rootView.findViewById(R.id.btn_deletePass);

        btn_borrarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tx_user.setText("");
            }
        });
        btn_borrarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tx_pass.setText("");
            }
        });

        btn_ingresar= (Button) rootView.findViewById(R.id.btningresar);
        btn_ingresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String a=tx_user.getText().toString();
                String b=tx_pass.getText().toString();
                String c=Recuperar_data();//MODIFICA ESTO
                        //Recuperar_data();
                if(c.equals("")){
            Toast.makeText(v.getContext(),"El valor de login de dominio no existe, ve a configuraciones para añadirlo",Toast.LENGTH_SHORT).show();

            }else {


                // Toast.makeText(v.getContext(),"logueo",Toast.LENGTH_SHORT).show();
                final AuthController obj = new AuthController(rootView, "url", "get", FragmentTickets.this);
                obj.execute(a, b, c);
            }
            //probar
           //     Intent intent = new Intent(v.getContext(), ActFiltro.class);
          //      startActivity(intent);

            }});



        return rootView;




    }

    public String Recuperar_data() {

        SharedPreferences settings =  this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);
        String dominio_user = settings.getString("domain","anyaccess");
        //Toast.makeText(this.getActivity(), nombre, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this.getActivity(),dominio_user, Toast.LENGTH_SHORT).show();
        return dominio_user;
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////
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

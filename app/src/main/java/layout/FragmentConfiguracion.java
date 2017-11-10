package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.SplashScreenActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentConfiguracion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentConfiguracion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConfiguracion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentConfiguracion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConfiguracion.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConfiguracion newInstance(String param1, String param2) {
        FragmentConfiguracion fragment = new FragmentConfiguracion();
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
    Switch sw_sonido, sw_video;
    EditText url_base, dom;
    Button b_save;
    Context context;
    Boolean sw_hd_video;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuracion, container, false);
        // Inflate the layout for this fragment
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        sw_sonido = (Switch) rootView.findViewById(R.id.switch_sonido);
        Boolean switchState = sw_sonido.isChecked();

        sw_video = (Switch) rootView.findViewById(R.id.switch_video);
        sw_video.setChecked(obtener_estado());
        sw_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean sw_hd_video = sw_video.isChecked();
                GuardarEstado(sw_hd_video);
                if(!sw_hd_video){
                    GlobalVariables.cal_sd_hd="_SD.";
                }else
                {
                    GlobalVariables.cal_sd_hd=".";
                }
            }
        });


        //Boolean sw_hd_video = sw_video.isChecked(); //verificar el estado del video

        //sw_video.setChecked(true);


        url_base = (EditText) rootView.findViewById(R.id.url_base);
        //url_base.setText("https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/");
        dom = (EditText) rootView.findViewById(R.id.dom);
        //dom.setText("anyaccess");
        b_save = (Button) rootView.findViewById(R.id.b_save);


        ///registro por default


     /*   SharedPreferences url_save = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences dominio = this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = url_save.edit();
        editor.putString("url", "https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/");

        SharedPreferences.Editor editor2 = dominio.edit();
        editor2.putString("domain","anyaccess");

        editor.commit();
        editor2.commit();*/
       // Recuperar_data();

      //



        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // boolean isValidUrl(urlvalido);
                //boolean est=URLUtil.isValidUrl("https:/app.antapaccay.com.pe/proportal/scom_service/api");

                //sw_hd_video = sw_video.isChecked();


                String a = url_base.getText().toString().replace(" ","");

                boolean est=URLUtil.isValidUrl(url_base.getText().toString());
                String ultimo = a.substring(a.length() - 1);

                if(est==true&ultimo.equals("/")){
                    Registrar(v);
                Recuperar_data();
                    /*boolean isHD=obtener_calidad();
                    if(isHD){
                        GlobalVariables.cal_sd_hd=".sd";
                        sw_video.setChecked(isHD);
                    }*/

                //getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
                GlobalVariables.cont_alert=1;////////////////////////////

                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "la url es incorrecta", Toast.LENGTH_SHORT).show();

                }


            }
        });

        //sharedpreference para url del servidor
        SharedPreferences url_save = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        url_base.setText(url_save.getString("url", "https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/"));

        //sharedpreference para url del dominio
        SharedPreferences dominio = this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);
        dom.setText(dominio.getString("domain", "anyaccess"));





        return rootView;

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public void Registrar(View v) {

        SharedPreferences url_save = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences dominio = this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);



        SharedPreferences.Editor editor = url_save.edit();
        editor.putString("url", url_base.getText().toString());

        SharedPreferences.Editor editor2 = dominio.edit();
        editor2.putString("domain", dom.getText().toString());



        editor.commit();
        editor2.commit();
        //v.finish();
    }

    public void Recuperar_data() {

        SharedPreferences settings =  this.getActivity().getSharedPreferences("dom", Context.MODE_PRIVATE);
        String dominio_user = settings.getString("domain","");
        //Toast.makeText(this.getActivity(), nombre, Toast.LENGTH_SHORT).show();

        Toast.makeText(this.getActivity(),"Se guardaron los cambios", Toast.LENGTH_SHORT).show();
    }





    public void GuardarEstado(boolean estado){
        SharedPreferences cal_vid = this.getActivity().getSharedPreferences("calidad", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = cal_vid.edit();
        editor3.putBoolean("cal", estado);
        editor3.commit();

    }





    public boolean obtener_estado(){
        SharedPreferences cal_vid =  this.getActivity().getSharedPreferences("calidad", Context.MODE_PRIVATE);
        Boolean calvid = cal_vid.getBoolean("cal",false);

        return calvid;
    }


}

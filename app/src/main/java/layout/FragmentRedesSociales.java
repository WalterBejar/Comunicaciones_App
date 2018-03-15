package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pango.comunicaciones.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRedesSociales.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRedesSociales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRedesSociales extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentRedesSociales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRedesSociales.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRedesSociales newInstance(String param1, String param2) {
        FragmentRedesSociales fragment = new FragmentRedesSociales();
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
CardView card1, card2, card3, card4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowview = inflater.inflate(R.layout.fragment_redes_sociales, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        final String facebookId = "fb://page/1392365134368940";

        final String urlPage = "https://www.facebook.com/Antapaccay/";
        final String urlPageln = "https://www.linkedin.com/company/3658000";
        final String youtube = "https://www.youtube.com/user/TintayaAntapaccay";
        final String instagram = "https://www.instagram.com/antapaccay/";


        card1 =(CardView) rowview.findViewById(R.id.cardredes1);
        card2 =(CardView) rowview.findViewById(R.id.cardredes2);
        card3 =(CardView) rowview.findViewById(R.id.cardredes3);
        card4 =(CardView) rowview.findViewById(R.id.cardredes4);



        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)));
                } catch (Exception e) {
                    Log.e(TAG, "Aplicación no instalada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }
            }
        });


    card2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPageln)));
        } catch (Exception e) {
            Log.e(TAG, "Aplicación no instalada.");
            //Abre url de pagina.
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPageln)));
        }




    }
});


       card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube )));
                } catch (Exception e) {
                    Log.e(TAG, "Aplicación no instalada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube)));
                }

            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instagram )));
                } catch (Exception e) {
                    Log.e(TAG, "Aplicación no instalada.");
                    //Abre url de pagina.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instagram)));
                }

            }
        });

        return rowview;
    }

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

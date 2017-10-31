package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pango.comunicaciones.ActImag;
import com.pango.comunicaciones.EndlessScrollListener;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.ImgAdapter;
import com.pango.comunicaciones.controller.ComController;
import com.pango.comunicaciones.controller.ImgController;

import static com.pango.comunicaciones.GlobalVariables.imagen2;
import static com.pango.comunicaciones.GlobalVariables.noticias2;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentImagenes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentImagenes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentImagenes extends Fragment {
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_imagenes, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        recListImag = (ListView) rootView.findViewById(R.id.list_imag);

        if(GlobalVariables.imagen2.size()==0) {
            final ImgController obj = new ImgController(rootView,"url","get", FragmentImagenes.this);
            obj.execute(String.valueOf(1),String.valueOf(3));
        }else {
            ImgAdapter ca = new ImgAdapter(context, GlobalVariables.imagen2);
            recListImag.setAdapter(ca);
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

                GlobalVariables.img_get= imagen2.get(position);

                String titulo=imagen2.get(position).getTitulo();
                String fecha=imagen2.get(position).getFecha();

                //se conecta a un activity//
                Intent intent = new Intent(getActivity(), ActImag.class);
                intent.putExtra("titulo",titulo);
                intent.putExtra("fecha",fecha);

                startActivity(intent);
            }


            // }
        });


//if(res!=1) {

    recListImag.setOnScrollListener(new EndlessScrollListener() {
        @Override
        public boolean onLoadMore(int page, int totalItemCount) {

            int page2 = page;
            int pageCount = 2;

            if (GlobalVariables.imagen2.size() < GlobalVariables.contFotos) {
                final ImgController obj = new ImgController(rootView, "url", "get", FragmentImagenes.this);
                obj.execute(String.valueOf(GlobalVariables.contpublic), String.valueOf(GlobalVariables.num_vid));
                //GlobalVariables.contpublic=GlobalVariables.contpublic+1;

                return true; // ONLY if more data is actually being loaded; false otherwise.
            } else {
                //flag=true;
                return false;

            }
        }
    });

//}

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

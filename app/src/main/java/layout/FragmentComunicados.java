package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pango.comunicaciones.ActComDetalle;
import com.pango.comunicaciones.EndlessScrollListener;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.ComAdapter;
import com.pango.comunicaciones.controller.ComController;
import com.pango.comunicaciones.model.Comunicado;

import java.util.List;

import static com.pango.comunicaciones.GlobalVariables.comlist;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentComunicados.OnFragmentInteractionListener} interface
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        final View rootView = inflater.inflate(R.layout.fragment_comunicados, container, false);
        recListCom = (ListView) rootView.findViewById(R.id.l_frag_com);

        final ComController obj = new ComController(rootView, "url", "get", FragmentComunicados.this);
        obj.execute(String.valueOf(1), String.valueOf(GlobalVariables.num_vid));

      /*  if(GlobalVariables.comlist.size()==0) {

        }else {
            ComAdapter ca = new ComAdapter(context, GlobalVariables.comlist);
            recListCom.setAdapter(ca);
        }*/
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

        recListCom.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {

                int page2=page;


                if(GlobalVariables.comlist.size()<GlobalVariables.contComunicado) {


                    final ComController obj = new ComController(rootView,"url","get", FragmentComunicados.this);
                    obj.execute(String.valueOf(page2),String.valueOf(GlobalVariables.num_vid));
                    pageCount++;


                 /*
                 String Url = "media/GetImagen/6767/Salud Mental 2016 - 1.jpg";
                 String Urlmin = "media/GetminFile/6767/Salud Mental 2016 - 1.jpg";
                 ArrayList<String> dataf = new ArrayList<>();
                 dataf.add("6767");
                 dataf.add(Url.replaceAll("\\s", "%20"));
                 dataf.add(Urlmin.replaceAll("\\s", "%20"));

                 GlobalVariables.noticias2.add(new Noticias("SC2017000549", "TP01", R.drawable.ic_menu_noticias, "Ojeda, Christiam A (", "2017-10-09T00:00:00", "CUIDEMOS NUESTRA SALUD MENTAL", "Una de cada cinco personas en ámbito laboral puede experimentar un trastorno de salud mental. Los problemas de salud mental tienen un impacto directo en los lugares de trabajo.", dataf));
*/

                    return true; // ONLY if more data is actually being loaded; false otherwise.
                }else{
                    //flag=true;
                    return false;

                }
            }
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

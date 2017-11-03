package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.pango.comunicaciones.EndlessScrollListener;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.NoticeAdapter;
import com.pango.comunicaciones.controller.NoticeController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentNotices.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentNotices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotices extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public  Context context;
    public ListView recList;
    public NoticeAdapter noticeAdapter;
    public boolean showTotal=true;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentNotices() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotices.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotices newInstance(String param1, String param2) {
        FragmentNotices fragment = new FragmentNotices();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = container.getContext();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        final View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);
        recList = (ListView) rootView.findViewById(R.id.l_frag_not);

        //noticeAdapter = new NoticeAdapter(context);
        //recList.setAdapter(noticeAdapter);

        recList.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount){

            // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //loadNextDataFromApi(page);
                if(GlobalVariables.noticias2.size()!= GlobalVariables.contNoticia) {
                    new NoticeController(noticeAdapter,context).execute(page+"");
                    return true; // ONLY if more data is actually being loaded; false otherwise.
                }else{

                    if(showTotal) Toast.makeText(context,"Total de Registros:"+GlobalVariables.contNoticia,Toast.LENGTH_SHORT).show();
                    showTotal=false;
                    return false;
                }
            }
        });
        new NoticeController(noticeAdapter,context).execute("1");

        return rootView;//inflater.inflate(R.layout.fragment_fragment_notices, container, false);
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

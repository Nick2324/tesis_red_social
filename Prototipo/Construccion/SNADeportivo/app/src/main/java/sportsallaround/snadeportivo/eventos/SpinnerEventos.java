package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sna_deportivo.pojo.evento.ConstantesEventos;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.AttachObjetoListener;
import sportsallaround.utils.ObjetoListenerSpinner;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpinnerEventos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpinnerEventos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpinnerEventos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private ObjetoListenerSpinner listener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpinnerEventos.
     */
    // TODO: Rename and change types and number of parameters
    public static SpinnerEventos newInstance(String param1, String param2) {
        SpinnerEventos fragment = new SpinnerEventos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SpinnerEventos() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spinner_eventos, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setTiposEventos();
        this.setOnItemSelected();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener)activity;
            listener = (ObjetoListenerSpinner)((AttachObjetoListener)mListener).getObjetoListener(this.getClass());
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void setTiposEventos() {
        Spinner tipoEvento = (Spinner)getView().findViewById(R.id.spinner_tipo_evento);
        ArrayList<ConstantesEventos> tiposEventos = new ArrayList<ConstantesEventos>();
        for(ConstantesEventos ce:ConstantesEventos.values())
            tiposEventos.add(ce);
        ArrayAdapter<ConstantesEventos> adapterTipoEvento =
                new ArrayAdapter<ConstantesEventos>(getView().getContext(),
                                                    android.R.layout.simple_spinner_dropdown_item,
                                                    tiposEventos);
        tipoEvento.setAdapter(adapterTipoEvento);
    }

    private void setOnItemSelected(){
        Spinner tiposEventos = (Spinner)getView().findViewById(R.id.spinner_tipo_evento);
        tiposEventos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner tiposEventos = (Spinner) parentView;
                listener.onItemSelected((ConstantesEventos) tiposEventos.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                listener.onNothingSelected();
            }

        });
    }

}

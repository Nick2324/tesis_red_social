package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sna_deportivo.pojo.evento.TiposEventos;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.gui.AttachObjetoListener;
import sportsallaround.utils.gui.ObjetoListenerSpinner;

public class SpinnerEventos extends Fragment {

    private ObjetoListenerSpinner listener;

    public SpinnerEventos() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spinner_eventos, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setTiposEventos();
        this.setOnItemSelected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ObjetoListenerSpinner)((AttachObjetoListener)activity).getObjetoListener(this.getClass());
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ObjetoListenerSpinner");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void setTiposEventos() {
        Spinner tipoEvento = (Spinner)getView().findViewById(R.id.spinner_tipo_evento);
        ArrayList<TiposEventos> tiposEventos = new ArrayList<TiposEventos>();
        for(TiposEventos ce:TiposEventos.values())
            tiposEventos.add(ce);
        ArrayAdapter<TiposEventos> adapterTipoEvento =
                new ArrayAdapter<TiposEventos>(getView().getContext(),
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
                listener.onItemSelected((TiposEventos) tiposEventos.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                listener.onNothingSelected();
            }

        });
    }

    public TiposEventos getValueSpinnerEventos(){
        Spinner spinnerEventos = (Spinner)getView().findViewById(R.id.spinner_tipo_evento);
        return (TiposEventos)spinnerEventos.getSelectedItem();
    }

}

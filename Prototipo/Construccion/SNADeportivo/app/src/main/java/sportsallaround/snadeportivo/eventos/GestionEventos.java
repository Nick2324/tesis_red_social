package sportsallaround.snadeportivo.eventos;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sportsallaround.snadeportivo.R;

public class GestionEventos extends Fragment {


    public GestionEventos() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestion_eventos, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        //!*!*!*!*! onActivityCreated?
        this.setUpListeners();
    }

    public void setUpListeners(){
        Button eventosSNA = (Button)getView().findViewById(R.id.button_eventos_sna);
        eventosSNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button misEventos = (Button)getView().findViewById(R.id.button_mis_eventos);
        misEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

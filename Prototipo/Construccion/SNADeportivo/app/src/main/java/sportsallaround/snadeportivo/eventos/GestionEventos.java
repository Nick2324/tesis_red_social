package sportsallaround.snadeportivo.eventos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;

public class GestionEventos extends Fragment {


    public GestionEventos() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestion_eventos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedStateInstance) {
        super.onActivityCreated(savedStateInstance);
        this.setUpListeners();
    }

    public void setUpListeners(){
        Button eventosSNA = (Button)getView().findViewById(R.id.button_eventos_sna);
        eventosSNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                                           GestionEventosLista.class);
                intent.putExtra(Constants.USUARIO,
                                getActivity().getIntent().
                                              getExtras().
                                              getParcelable(Constants.USUARIO));
                startActivity(intent);
            }
        });
        Button misEventos = (Button)getView().findViewById(R.id.button_mis_eventos);
        misEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar listado con concepto diferente
                Intent intent = new Intent(getActivity(),
                        GestionEventosLista.class);
                intent.putExtra(Constants.USUARIO,
                        getActivity().getIntent().
                                getExtras().
                                getParcelable(Constants.USUARIO));
                startActivity(intent);
            }
        });
    }

}

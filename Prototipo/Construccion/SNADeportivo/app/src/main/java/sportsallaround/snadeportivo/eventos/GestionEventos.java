package sportsallaround.snadeportivo.eventos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.Constants;

public class GestionEventos extends Fragment {


    public GestionEventos() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestion_eventos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        this.setUpListeners();
    }

    private void setUpListeners(){
        Button misEventos = (Button)getActivity().findViewById(R.id.button_mis_eventos);
        misEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                extra.putString(ConstantesEvento.OWNER_EVENTO,
                        ConstantesEvento.OWNER);
                startActivity(formarIntent(GestionEventosLista.class, extra));
            }
        });

        Button eventosSNA = (Button)getActivity().findViewById(R.id.button_eventos_sna);
        eventosSNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                extra.putString(ConstantesEvento.OWNER_EVENTO,
                        ConstantesEvento.NO_OWNER);
                startActivity(formarIntent(GestionEventosLista.class, extra));
            }
        });
    }

    private Intent formarIntent(Class clase,Bundle parametrosExtra){
        Intent intent = new Intent(getActivity(),clase);
        Bundle extra = parametrosExtra;
        extra.putString(Constants.USUARIO,
                ((Usuario) getActivity().getIntent().
                        getExtras().
                        getParcelable(Constants.USUARIO)).toString());
        intent.putExtra(Constants.DATOS_FUNCIONALIDAD, extra);

        return intent;
    }

}

package sportsallaround.utils.gui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sportsallaround.snadeportivo.R;

public class TituloActividad extends Fragment {

    private int idTitulo;

    public interface InitializerTituloActividad{

        public int getIdTituloActividad();

    }

    public TituloActividad() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_titulo_actividad, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.idTitulo = ((InitializerTituloActividad) activity).getIdTituloActividad();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement InitializerTituloActividad");
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        TextView titulo = ((TextView)getView().findViewById(R.id.textview_titulo_actividad));
        titulo.setText(getResources().getString(this.idTitulo));
    }

}

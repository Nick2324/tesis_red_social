package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sna_deportivo.pojo.evento.TiposEventos;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.gui.AttachObjetoListener;
import sportsallaround.utils.gui.ObjetoListener;
import sportsallaround.utils.gui.ObjetoListenerSpinner;

public class CrearEvento extends Activity implements ObjetoListenerSpinner,AttachObjetoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
        setTitle(getResources().getString(R.string.title_activity_crear_evento));
    }

    @Override
    public void onStart(){
        super.onStart();
        prepararListeners();
    }

    private void prepararListeners(){

        final Context actividad = this;

        Button crearEvento = (Button)findViewById(R.id.button_crear_evento_especifico);
        crearEvento.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EL INTENT DEBERIA SER DISTINTO (O NO?) DEPENDIENDO DEL TIPO DE EVENTO
                Bundle extras = getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD);
                extras.putString(ConstantesEvento.TIPO_EVENTO,
                        ((SpinnerEventos)getFragmentManager().findFragmentById(
                        R.id.fragment_tipo_evento_crear)).getValueSpinnerEventos().getNombreClase());
                extras.putString(ConstantesEvento.SERVICIO_EVENTO,
                        ((SpinnerEventos)getFragmentManager().findFragmentById(
                                R.id.fragment_tipo_evento_crear)).
                                getValueSpinnerEventos().getServicio());
                extras.putString(Constants.FUNCIONALIDAD,ConstantesEvento.CREAR_EVENTO);
                extras.putString(ConstantesEvento.TIPO_MENU,
                        ConstantesEvento.MENU_ADMIN_EVENTOS);
                Intent intent = new Intent(actividad, InformacionGeneralEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,extras);
                startActivity(intent);
            }
        });
    }

    @Override
    public ObjetoListener getObjetoListener(Class claseListener) {
        return this;
    }

    @Override
    public void onItemSelected(Object objetoSeleccionado) {
        TextView descripcionEventos =
                (TextView) findViewById(R.id.textview_descripcion_tipo_evento);
        descripcionEventos.setText(((TiposEventos)objetoSeleccionado).getDescripcion());
    }

    @Override
    public void onNothingSelected() {

    }

}

package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sna_deportivo.pojo.evento.ConstantesEventos;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.AttachObjetoListener;
import sportsallaround.utils.ObjetoListener;
import sportsallaround.utils.ObjetoListenerSpinner;

public class CrearEvento extends Activity implements ObjetoListenerSpinner,AttachObjetoListener,
                                                     SpinnerEventos.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
    }

    @Override
    public void onStart(){
        super.onStart();
        prepararListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepararListeners(){

        final Context actividad = this;

        Button crearEvento = (Button)findViewById(R.id.button_crear_evento_especifico);
        crearEvento.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambiar por chain
                Intent intent = new Intent(actividad, InformacionGeneralEvento.class);
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
        descripcionEventos.setText(((ConstantesEventos)objetoSeleccionado).getDescripcion());
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

}

package sportsallaround.snadeportivo.eventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sna_deportivo.pojo.evento.ConstantesEventos;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;

public class CrearEvento extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
        prepararVistas();
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

    private void prepararVistas(){
        //BUSCAR MANERA DE REDUCIR ESTE CODIGO
        Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento_crear);
        ArrayList<ConstantesEventos> tiposEventos = new ArrayList<ConstantesEventos>();
        for(ConstantesEventos ce:ConstantesEventos.values())
            tiposEventos.add(ce);
        ArrayAdapter<ConstantesEventos> adapterTipoEvento =
                new ArrayAdapter<ConstantesEventos>(this,android.R.layout.simple_spinner_dropdown_item,tiposEventos);
        tipoEvento.setAdapter(adapterTipoEvento);
    }

    private void prepararListeners(){

        final Context actividad = this;

        Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento_crear);
        tipoEvento.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView descripcionEventos =
                        (TextView) findViewById(R.id.textview_descripcion_tipo_evento);
                descripcionEventos.setText(((ConstantesEventos) parent.
                        getAdapter().getItem(position)).getDescripcion());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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

}

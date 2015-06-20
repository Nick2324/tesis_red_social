package sportsallaround.snadeportivo.eventos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.sna_deportivo.pojo.evento.Evento;
import java.util.ArrayList;

import sportsallaround.snadeportivo.R;

public class GestionEventos extends ActionBarActivity {

    private Evento[] eventos;

    private void setListaEventos(){
        /*ListView listaEventos = (ListView)findViewById(R.id.layout_lista_eventos);
        ArrayList<String> arrayListaEventos = new ArrayList<String>();
        ArrayAdapter<String> datosListaEventos =
                new ArrayAdapter<String> (this,R.layout.activity_gestion_eventos,arrayListaEventos);
        listaEventos.setAdapter(datosListaEventos);*/
    }

    private void setDatosEventos(){
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_eventos);
        setDatosEventos();
        setListaEventos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestion_eventos, menu);
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
}

package sportsallaround.snadeportivo.eventos;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactory;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ServiceUtils;

public class GestionEventos extends ActionBarActivity {

    private Evento[] eventos;
    private String resultadoPeticion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_eventos);
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

    @Override
    public void onStart(){
        super.onStart();
        this.setTiposEventos();
        this.setDatosEventos();
        //this.setListaEventos();
    }

    private void setTiposEventos(){
        Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento);
        ArrayList<ConstantesEventos> tiposEventos = new ArrayList<ConstantesEventos>();
        for(ConstantesEventos ce:ConstantesEventos.values())
            tiposEventos.add(ce);
        ArrayAdapter<ConstantesEventos> adapterTipoEvento =
                new ArrayAdapter<ConstantesEventos>(this,android.R.layout.simple_spinner_dropdown_item,tiposEventos);
        tipoEvento.setAdapter(adapterTipoEvento);
    }

    private void setListaEventos(){
        ListView listaEventos = (ListView)findViewById(R.id.listview_lista_eventos);
        ArrayList<String> arrayListaEventos = new ArrayList<String>();
        for(int i = 0;i < eventos.length;i++)
            arrayListaEventos.add(eventos[i].getNombre());
        ArrayAdapter<String> datosListaEventos =
                new ArrayAdapter<String> (this,android.R.layout.simple_selectable_list_item,arrayListaEventos);
        listaEventos.setAdapter(datosListaEventos);
    }

    private class PeticionEventos extends AsyncTask{

        public Context contexto;

        @Override
        protected Object doInBackground(Object[] params) {
            Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento);
            try {
                JSONObject parametros = new JSONObject();
                parametros.put("activo", true);
                Log.d("Nick:peticion","Inicio la peticion");
                ConstantesEventos itemSeleccionado = (ConstantesEventos)tipoEvento.getSelectedItem();
                resultadoPeticion = ServiceUtils.invokeService(parametros,
                        Constants.SERVICES_PATH_EVENTOS + itemSeleccionado.getServicio(),
                        "POST");
                Log.d("NICK:END**","Termino la peticion");
            }catch (JSONException e){
                Log.w("Error consulta eventos", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object response){
            Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento);
            if (response != null) {
                try {
                    JSONArray responseJson = new JSONArray((String) response);
                    eventos = null;
                    eventos = new Evento[responseJson.length()];
                    for (int i = 0; i < responseJson.length(); i++) {
                        eventos[i] = new ProductorFactory().
                                getEventosFactory(tipoEvento.getSelectedItem().toString()).
                                crearEvento();
                        eventos[i].deserializarJson(JsonUtils.JsonStringToObject(responseJson.getString(i)));
                    }
                    Log.d("NICK:END**", "Termino de armar eventos");
                } catch (JSONException e) {
                    Log.w("Error consulta eventos", e.getMessage());
                    Log.d("Error consulta eventos", e.getMessage());
                } catch (ExcepcionJsonDeserializacion e) {
                    Log.w("Error consulta eventos", e.getMessage());
                    Log.d("Error consulta eventos", e.getMessage());
                }

                ListView listaEventos = (ListView) findViewById(R.id.listview_lista_eventos);
                ArrayList<String> arrayListaEventos = new ArrayList<String>();
                for (int i = 0; i < eventos.length; i++)
                    arrayListaEventos.add(eventos[i].getNombre());
                ArrayAdapter<String> datosListaEventos =
                        new ArrayAdapter<String>(contexto, R.layout.activity_gestion_eventos, arrayListaEventos);
                listaEventos.setAdapter(datosListaEventos);
            }

            Log.d("NICK:END**", "Termino postexecute");

        }

    }

    private void setDatosEventos(){

        try {
            PeticionEventos pe = new PeticionEventos();
            pe.contexto = this;
            pe.execute(null);
        }catch(Exception e){
            Log.d("NICK:FAIL**",e.getMessage());
        }

    }

}

package sportsallaround.snadeportivo.eventos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactory;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.KeyValueItem;
import sportsallaround.utils.ServiceUtils;

public class GestionEventos extends ActionBarActivity{

    private ArrayList<Evento> eventos;
    private String resultadoPeticion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_eventos);
        this.prepararCaractWidgets();
        this.prepararListeners();
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
    }

    private class PeticionEventos extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            ConstantesEventos itemSeleccionado = (ConstantesEventos)params[0];
            return ServiceUtils.invokeService(null,
                   Constants.SERVICES_PATH_EVENTOS + itemSeleccionado.getServicio(),
                   "GET");
        }

        @Override
        protected void onPostExecute(Object response){
            Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento);
            if (response != null && !((String)response).equals("")) {
                try {
                    JSONArray responseJson = new JSONArray((String) response);
                    eventos = new ArrayList<Evento>();
                    for (int i = 0; i < responseJson.length(); i++) {
                        eventos.add(new ProductorFactory().
                                getEventosFactory(((ConstantesEventos)tipoEvento.getSelectedItem()).
                                        getServicio()).
                                crearEvento());
                        eventos.get(i).deserializarJson(
                                JsonUtils.JsonStringToObject(responseJson.getString(i)));
                    }
                    setListaEventos();
                } catch (JSONException e) {
                    Log.w("SNA:JSONException", e.getMessage());
                } catch (ExcepcionJsonDeserializacion e) {
                    Log.w("SNA:ExcepcionJsonDes", e.getMessage());
                }
            }

        }

        public void realizarPeticion(ConstantesEventos ce){
            try {
                this.execute(new Object[]{ce});
            }catch(Exception e){
                Log.w("SNA:Exception",e.getMessage());
            }
        }

    }

    private void prepararCaractWidgets(){
        final Context actividad = this;
        TextSwitcher informacionEvento = (TextSwitcher)findViewById(R.id.switcher_lista_eventos);
        informacionEvento.setFactory(
                new ViewFactory() {

                    public View makeView() {
                        TextView descripcionGeneralEv = new TextView(actividad);
                        descripcionGeneralEv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                        descripcionGeneralEv.setTextSize(16);
                        return descripcionGeneralEv;
                    }

                });

        informacionEvento.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        informacionEvento.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    public static class Dialogs extends DialogFragment{
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("He sido presionado").setPositiveButton("lo que sea", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Dialogs d = new Dialogs();
                    //d.show(((GestionEventos)actividad).getFragmentManager(),"tag");
                }
            });
            return builder.create();
        }
    }

    private void prepararListeners(){
        final Context actividad = this;
        ListView listaEventos = (ListView)findViewById(R.id.listview_lista_eventos);
        listaEventos.setOnItemClickListener(
                new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        ListView listaEventos = (ListView) parent;
                        int i = Integer.parseInt(((KeyValueItem) listaEventos.getAdapter().getItem(position)).getKey().toString());
                        Log.d("Nick:Clicked", eventos.get(i).toString());
                        TextSwitcher informacionEvento = (TextSwitcher) findViewById(R.id.switcher_lista_eventos);
                        informacionEvento.setText(eventos.get(i).getNombre() + "\n" +
                                eventos.get(i).getDescripcion() + "\n" +
                                "Fecha de creacion: " + eventos.get(i).getFechaCreacion() + "\n" +
                                "Fecha de inicio: " + eventos.get(i).getFechaInicio());
                    }
                }
        );
        Spinner tiposEventos = (Spinner)findViewById(R.id.spinner_tipo_evento);
        tiposEventos.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner tiposEventos = (Spinner) parentView;
                new PeticionEventos().realizarPeticion((ConstantesEventos) tiposEventos.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        EditText busquedaEvento = (EditText)findViewById(R.id.edittext_busqueda_administracion_eventos);
        busquedaEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ListView listaEventos = (ListView)findViewById(R.id.listview_lista_eventos);
                ((ArrayAdapter)listaEventos.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}

        });
        Button crearEvento = (Button)findViewById(R.id.button_crear_evento);
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearEventoIntent = new Intent(actividad,CrearEvento.class);
                startActivity(crearEventoIntent);
            }
        });
    }


    private void setTiposEventos() {
        Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento);
        ArrayList<ConstantesEventos> tiposEventos = new ArrayList<ConstantesEventos>();
        for(ConstantesEventos ce:ConstantesEventos.values())
            tiposEventos.add(ce);
        ArrayAdapter<ConstantesEventos> adapterTipoEvento =
                new ArrayAdapter<ConstantesEventos>(this,android.R.layout.simple_spinner_dropdown_item,tiposEventos);
        tipoEvento.setAdapter(adapterTipoEvento);
    }

    private void setListaEventos() {
        ListView listaEventos = (ListView) findViewById(R.id.listview_lista_eventos);
        listaEventos.setAdapter(null);
        ArrayList<KeyValueItem> arrayListaEventos =
                new ArrayList<KeyValueItem>();
        for (Evento e : eventos)
            arrayListaEventos.add(new KeyValueItem(eventos.indexOf(e), e.getNombre()));
        ArrayAdapter<KeyValueItem> datosListaEventos =
                new ArrayAdapter<KeyValueItem>(this,
                        android.R.layout.simple_selectable_list_item,
                        arrayListaEventos);
        listaEventos.setAdapter(datosListaEventos);
    }

    private void setDatosEventos(){
        Spinner tipoEvento = (Spinner)findViewById(R.id.spinner_tipo_evento);
        new PeticionEventos().realizarPeticion((ConstantesEventos)tipoEvento.getSelectedItem());
    }

}

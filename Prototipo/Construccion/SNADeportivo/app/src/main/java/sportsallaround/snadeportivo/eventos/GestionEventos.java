package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import sportsallaround.utils.gui.AttachObjetoListener;
import sportsallaround.utils.Constants;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ObjetoListener;
import sportsallaround.utils.gui.ObjetoListenerSpinner;
import sportsallaround.utils.ServiceUtils;
import sportsallaround.utils.gui.TituloActividad;

public class GestionEventos extends Activity implements AttachObjetoListener, TituloActividad.InitializerTituloActividad{

    private ArrayList<Evento> eventos;


    private class PeticionSpinner implements ObjetoListenerSpinner{

        private Context contexto;

        public PeticionSpinner(Context contexto){
            this.contexto = contexto;
        }

        @Override
        public void onItemSelected(Object objetoSeleccionado) {
            new PeticionEventos(this.contexto).realizarPeticion((ConstantesEventos)objetoSeleccionado);
        }

        @Override
        public void onNothingSelected() {}
    }

    private class PeticionEventos extends AsyncTask{

        Context contexto;

        public PeticionEventos(Context contexto){
            this.contexto = contexto;
        }

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
                    ((GestionEventos)this.contexto).setListaEventos();
                } catch (JSONException e) {
                    ((GestionEventos)this.contexto).clearListaEventos();
                    Log.w("SNA:JSONException", e.getMessage());
                } catch (ExcepcionJsonDeserializacion e) {
                    ((GestionEventos)this.contexto).clearListaEventos();
                    Log.w("SNA:ExcepcionJsonDes", e.getMessage());
                }
            }else{
                ((GestionEventos)this.contexto).clearListaEventos();
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

    protected void prepararListeners() {
        final Context actividad = this;
        ListView listaEventos = (ListView) findViewById(R.id.listview_lista_eventos);
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

        EditText busquedaEvento = (EditText) findViewById(R.id.edittext_busqueda_administracion_eventos);
        busquedaEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ListView listaEventos = (ListView) findViewById(R.id.listview_lista_eventos);
                ((ArrayAdapter) listaEventos.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        Button crearEvento = (Button) findViewById(R.id.button_crear_evento);
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearEventoIntent = new Intent(actividad, CrearEvento.class);
                startActivity(crearEventoIntent);
            }
        });
    }

    protected void clearListaEventos(){
        ListView listaEventos = (ListView) findViewById(R.id.listview_lista_eventos);
        if(listaEventos.getAdapter() != null){
            ((ArrayAdapter)listaEventos.getAdapter()).clear();
        }
    }

    protected void setListaEventos() {
        ListView listaEventos = (ListView) findViewById(R.id.listview_lista_eventos);
        this.clearListaEventos();
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

    @Override
    public ObjetoListener getObjetoListener(Class claseListener) {
        if(claseListener.getSimpleName().equals("SpinnerEventos")){
            return new PeticionSpinner(this);
        }
        return null;
    }

    @Override
    public int getIdTituloActividad() {
        return R.string.title_activity_gestion_eventos;
    }

}

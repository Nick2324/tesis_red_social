package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

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
import sportsallaround.utils.gui.DescripcionGo;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.ObjetoListener;
import sportsallaround.utils.gui.ObjetoListenerSpinner;
import sportsallaround.utils.ServiceUtils;
import sportsallaround.utils.gui.TituloActividad;

public class GestionEventosLista extends Activity
        implements AttachObjetoListener,
                   TituloActividad.InitializerTituloActividad,
                   ListaConFiltro.CallBackListaConFiltro,
                   DescripcionGo.DescripcionGoCallBack{

    private ArrayList<Evento> eventos;

    private class PeticionSpinner implements ObjetoListenerSpinner{

        private Context contexto;

        public PeticionSpinner(Context contexto){
            this.contexto = contexto;
        }

        @Override
        public void onItemSelected(Object objetoSeleccionado) {
            new PeticionEventos(this.contexto).realizarPeticion((ConstantesEventos) objetoSeleccionado);
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
            Log.d("Nick","Inicio peticion");
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
                    ((ListaConFiltro)getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                            llenarLista();
                } catch (JSONException e) {
                    ((ListaConFiltro)getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                            limpiarLista();
                    Log.w("SNA:JSONException", e.getMessage());
                } catch (ExcepcionJsonDeserializacion e) {
                    ((ListaConFiltro)getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                            limpiarLista();
                    Log.w("SNA:ExcepcionJsonDes", e.getMessage());
                }
            }else{
                ((ListaConFiltro)getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                        limpiarLista();
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
        setContentView(R.layout.activity_gestion_eventos_lista);
        this.prepararListeners();
    }

    protected void prepararListeners() {
        final Context actividad = this;
        Button crearEvento = (Button) findViewById(R.id.button_crear_evento);
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearEventoIntent = new Intent(actividad, CrearEvento.class);
                startActivity(crearEventoIntent);
            }
        });
    }

    private ArrayList<KeyValueItem> convertirEventosKeyValueItem(){
        ArrayList<KeyValueItem> arrayListaEventos =
                new ArrayList<KeyValueItem>();
        for (Evento e : eventos)
            arrayListaEventos.add(new KeyValueItem(eventos.indexOf(e), e.getNombre()));
        return arrayListaEventos;
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
        return R.string.titulo_gestion_eventos_lista;
    }

    @Override
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        if(identificadorFragmento.equals(getResources().getString(R.string.lista_evento_string))){
            int i = Integer.parseInt(item.getKey().toString());
            Log.d("Nick:Clicked", eventos.get(i).toString());
            ((DescripcionGo)getFragmentManager().findFragmentById(R.id.fragment_switcher_info)).
                    setDescripcion(eventos.get(i).getNombre() + "\n" +
                            eventos.get(i).getDescripcion() + "\n" +
                            "Fecha de creacion: " + eventos.get(i).getFechaCreacion() + "\n" +
                            "Fecha de inicio: " + eventos.get(i).getFechaInicio());
        }
    }

    @Override
    public ArrayList<KeyValueItem> generarAdapter(String identificadorFragmento) {
        if(identificadorFragmento.equals(getResources().getString(R.string.lista_evento_string))){
            //return this.convertirEventosKeyValueItem();
        }
        return null;
    }

    @Override
    public ArrayList<KeyValueItem> regenerarAdapter(String identificadorFragmento) {
        if(identificadorFragmento.equals(getResources().getString(R.string.lista_evento_string))){
            return this.convertirEventosKeyValueItem();
        }
        return null;
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {}

    @Override
    public String getNombreBoton() {
        return "Ir al evento";
    }

    @Override
    public String getDefaultDescripcion() {
        return "Elija un evento para desplegar su informacion";
    }

    @Override
    public void go() {
        //!*!*!*!*!*!*!*!
    }

}

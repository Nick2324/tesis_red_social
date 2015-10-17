package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.FactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.snadeportivo.eventos.peticiones.CreaParticipante;
import sportsallaround.snadeportivo.eventos.peticiones.EliminarSolicitud;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ConstructorArrObjSNS;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;

public class ListadoSolicitudes extends Activity implements ListaConFiltro.CallBackListaConFiltro{

    private ArrayList<ObjectSNSDeportivo> solicitudes;
    private MenuSNS menuSNS;

    private class PeticionSolicitudParticipantes extends Peticion{

        private Evento evento;
        private String tipoEvento;

        public PeticionSolicitudParticipantes(Evento evento, String tipoEvento){
            this.evento = evento;
            this.tipoEvento = tipoEvento;
        }

        @Override
        public void calcularMetodo() {
            this.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            this.servicio = Constants.SERVICES_PATH_EVENTOS + this.tipoEvento + "/" +
                            this.evento.getId() + "/" +
                            Constants.SERVICES_PATH_EVE_SOLICITUDES;
        }

        @Override
        public void calcularParams(){
            try {
                super.params = new JSONObject();
                //PONIENDO EVENTO
                //Los eventos deben estar activos
                JSONArray arrayEventos = new JSONArray();
                arrayEventos.put(new JSONObject(this.evento.stringJson()));
                JSONObject parametrosEvento = new JSONObject();
                parametrosEvento.put(this.evento.getClass().getSimpleName(),
                        arrayEventos);
                super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                        parametrosEvento);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void doInBackground() {}

        @Override
        public void onPostExcecute(String resultadoPeticion){
            Log.d("Nick:RespuestaTask",resultadoPeticion);
            if(resultadoPeticion != null) {
                try {
                    solicitudes =
                            ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                                    new JSONArray(resultadoPeticion));
                    ((ListaConFiltro) getFragmentManager().findFragmentById(
                            R.id.fragment_solicitud_de_participante)).llenarLista();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_solicitudes);
        setTitle(getResources().getString(R.string.titulo_gestion_eventos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String funcionalidad = getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).
                getString(Constants.FUNCIONALIDAD);
        String tipoMenu = getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).
                getString(ConstantesEvento.TIPO_MENU);
        this.menuSNS = new ProductorMenuEventos().proveerMenuEventos(tipoMenu);
        getMenuInflater().inflate(menuSNS.getIdMenu(), menu);
        this.menuSNS.esconderItems(menu, new ProductorMenuEventos().
                proveerAnalizadorItem(funcionalidad));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.menuSNS.comportamiento(this, item.getItemId(), getIntent().
                getExtras().getBundle(Constants.DATOS_FUNCIONALIDAD));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        try {
            Evento evento = new ProductorFactoryEvento().getEventosFactory(
                    getIntent().
                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                    getString(ConstantesEvento.SERVICIO_EVENTO)).crearEvento();
            evento.deserializarJson((JsonObject)JsonUtils.JsonStringToObject(getIntent().
                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                    getString(ConstantesEvento.EVENTO_MANEJADO)));
            new PeticionSolicitudParticipantes(
                    evento,
                    getIntent().
                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                    getString(ConstantesEvento.SERVICIO_EVENTO)).ejecutarPeticion();
        } catch (ExcepcionJsonDeserializacion e) {
            e.printStackTrace();
        } catch (ProductorFactoryExcepcion e) {
            e.printStackTrace();
        }
    }

    @Override
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        final KeyValueItem itemSeleccionado = item;
        final Context contexto = this;
        if(identificadorFragmento.equals(getResources().getString(
                R.string.fragment_solicitud_de_participante))) {
            new AlertDialog.Builder(this).
                    setTitle(contexto.getResources().getString(R.string.alert_resolver_pet_eve_msn) + " " +
                            ((Usuario) item.getValue()).getPrimerNombre()).
                    setItems(new CharSequence[]{contexto.getResources().
                                    getString(R.string.alert_aceptar_pet_eve_button),
                                    contexto.getResources().
                                            getString(R.string.alert_eliminar_petic_eve_button)},
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Usuario usuario = (Usuario) itemSeleccionado.getValue();
                                        Evento evento = new ProductorFactoryEvento().
                                                getEventosFactory(getIntent().
                                                        getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                        getString(ConstantesEvento.SERVICIO_EVENTO)).
                                                crearEvento();
                                        evento.deserializarJson((JsonObject) JsonUtils.
                                                JsonStringToObject(getIntent().
                                                        getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                        getString(ConstantesEvento.EVENTO_MANEJADO)));
                                        if (which == 0) {
                                            new CreaParticipante(contexto, getIntent().
                                                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                    getString(ConstantesEvento.SERVICIO_EVENTO), evento,
                                                    usuario).ejecutarPeticion();
                                        } else if (which == 1) {
                                            new EliminarSolicitud(contexto, evento,
                                                    usuario,
                                                    getIntent().
                                                            getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                            getString(ConstantesEvento.SERVICIO_EVENTO),
                                                    itemSeleccionado).ejecutarPeticion();
                                        }
                                    } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                                        excepcionJsonDeserializacion.printStackTrace();
                                    } catch (ProductorFactoryExcepcion e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).create().show();
        }
    }

    @Override
    public ArrayList<KeyValueItem> generarAdapter(String identificadorFragmento) {
        return null;
    }

    @Override
    public ArrayList<KeyValueItem> regenerarAdapter(String identificadorFragmento) {
        ArrayList<KeyValueItem> adapter = new ArrayList<KeyValueItem>();
        if(identificadorFragmento.equals(getResources().getString(
                R.string.fragment_solicitud_de_participante))){
            adapter = ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.solicitudes,
                                                                      new String[]{"primerNombre",
                                                                              "usuario"});
            if(this.solicitudes != null)
                this.solicitudes.clear();
        }
        return adapter;
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {}

}

package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.FactoryUsuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ConstructorArrObjSNS;
import sportsallaround.utils.Peticion;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.TituloActividad;

public class ListadoParticipantes extends Activity
        implements TituloActividad.InitializerTituloActividad,ListaConFiltro.CallBackListaConFiltro{

    private ArrayList<ObjectSNSDeportivo> participantes;
    private MenuEventos menuEventos;

    private class ObtenerParticipantes extends Peticion{

        private Context contexto;
        private Evento evento;
        private String tipoEvento;

        public ObtenerParticipantes(Context contexto,
                                    Evento evento,
                                    String tipoEvento){
            this.contexto = contexto;
            this.evento = evento;
            this.tipoEvento = tipoEvento;
        }

        @Override
        public void calcularMetodo() {
            this.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            if(this.evento != null && this.evento.getId() != null) {
                super.servicio = Constants.SERVICES_PATH_EVENTOS +
                       tipoEvento + "/" +
                        evento.getId() + "/" +
                        Constants.SERVICES_PATH_EVE_PARTICIPANTES;
            }
        }

        @Override
        public void calcularParams() {
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
        public void onPostExcecute(String resultadoPeticion) {
            if(resultadoPeticion != null){
                try {
                    participantes =
                            ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                                                                        new JSONArray(resultadoPeticion));
                    ((ListaConFiltro)getFragmentManager().
                            findFragmentById(R.id.fragment_lista_participantes)).llenarLista();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this.contexto,this.contexto.getResources().
                        getString(R.string.toast_listado_participantes_vacio),Toast.LENGTH_LONG).show();
            }
        }
    }

    private class EliminarParticipante extends Peticion {

        Context contexto;
        Evento evento;
        Usuario usuario;
        String tipoEvento;
        KeyValueItem itemSeleccionado;

        public EliminarParticipante(Context contexto,
                                    Evento evento,
                                    Usuario usuario,
                                    String tipoEvento,
                                    KeyValueItem itemSeleccionado) {
            this.contexto = contexto;
            this.evento = evento;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
            this.itemSeleccionado = itemSeleccionado;
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento + "/" +
                    this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_EVE_PARTICIPANTES +
                    this.usuario.getUsuario();
        }

        @Override
        public void calcularParams() {
            super.params = new JSONObject();
            try {
                //PONIENDO USUARIO
                JSONArray arrayUsuarios = new JSONArray();
                arrayUsuarios.put(new JSONObject(this.usuario.stringJson()));
                JSONObject parametrosUsuario = new JSONObject();
                parametrosUsuario.put(this.usuario.getClass().getSimpleName(),
                        arrayUsuarios);
                super.params.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                        parametrosUsuario);

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
            if(resultadoPeticion != null){
                try {
                    JSONObject resultado = new JSONObject(resultadoPeticion);
                    if("200".equals(resultado.getString("caracterAceptacion"))){
                        ((ListaConFiltro)getFragmentManager().findFragmentById(
                                R.id.fragment_lista_participantes)).
                                eliminarElemento(itemSeleccionado);
                        Toast.makeText(this.contexto,
                                this.contexto.getResources().
                                        getString(R.string.toast_elimina_participante),
                                Toast.LENGTH_LONG).show();
                    }else{
                        this.alertError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.alertError();
                }
            }else{
                this.alertError();
            }
        }

        private void alertError(){
            new AlertDialog.Builder(this.contexto).
                    setTitle(this.contexto.getResources().
                            getString(R.string.alert_elimina_participante_tit)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.alert_error_elimina_participante_msn)).
                    setNeutralButton(this.contexto.getResources().
                            getString(R.string.BOTON_NEUTRAL), null).
                    create().show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_participantes);
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
        this.menuEventos = new ProductorMenuEventos().proveerMenuEventos(tipoMenu);
        getMenuInflater().inflate(menuEventos.getMenuId(), menu);
        this.menuEventos.esconderSegunFuncionalidadRol(this,funcionalidad,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.menuEventos.comportamiento(this, item.getItemId(),getIntent().
                getExtras().getBundle(Constants.DATOS_FUNCIONALIDAD));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setUpLista();
    }

    @Override
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        final Context contexto = this;
        final KeyValueItem itemSeleccionado = item;
        new AlertDialog.Builder(this).
            setTitle(getResources().getString(R.string.alert_selecciona_participante_tit)).
            setItems(new CharSequence[]{getResources().
                    getString(R.string.alert_selecciona_participante_eliminar)}, new DialogInterface.OnClickListener() {
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
                            new EliminarParticipante(
                                    contexto,
                                    evento,
                                    usuario,
                                    getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                            getString(ConstantesEvento.SERVICIO_EVENTO),
                                    itemSeleccionado).ejecutarPeticion();
                        }
                    }catch (ExcepcionJsonDeserializacion e){
                        new AlertDialog.Builder(contexto).
                                setTitle(getResources().
                                        getString(R.string.alert_elimina_participante_tit)).
                                setMessage(getResources().
                                        getString(R.string.alert_error_elimina_participante_msn)).
                                setNeutralButton(getResources().
                                        getString(R.string.BOTON_NEUTRAL), null).
                                create().show();
                        e.printStackTrace();
                    }
                }
            }).create().show();
    }

    @Override
    public ArrayList<KeyValueItem> generarAdapter(String identificadorFragmento) {
        return null;
    }

    @Override
    public ArrayList<KeyValueItem> regenerarAdapter(String identificadorFragmento) {
        ArrayList<KeyValueItem> retorno =
                ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.participantes,new String[]{"primerNombre","usuario"});
        this.participantes.clear();
        return retorno;
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {}

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        if(tagFragmento.equals(getResources().getString(R.string.lista_participantes)))
            return getResources().getString(R.string.lista_participantes);
        return null;
    }

    private void setUpLista(){
        try {
            Evento evento = (Evento)new ProductorFactoryEvento().producirFacObjetoSNS(
                    getIntent().getExtras().
                            getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                            ConstantesEvento.TIPO_EVENTO)).getObjetoSNS();
            evento.deserializarJson(JsonUtils.JsonStringToObject(getIntent().getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                    ConstantesEvento.EVENTO_MANEJADO)));
            new ObtenerParticipantes(this,evento,getIntent().getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                    ConstantesEvento.SERVICIO_EVENTO)).ejecutarPeticion();
        } catch (ExcepcionJsonDeserializacion e) {
            e.printStackTrace();
        }
    }
}

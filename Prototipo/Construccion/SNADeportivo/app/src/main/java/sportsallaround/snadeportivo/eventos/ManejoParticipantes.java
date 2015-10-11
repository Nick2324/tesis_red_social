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
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.FactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
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

public class ManejoParticipantes extends Activity implements ListaConFiltro.CallBackListaConFiltro{

    private ArrayList<ObjectSNSDeportivo> solicitudDeParticipantes;
    private ArrayList<ObjectSNSDeportivo> solicitudParaParticipantes;
    private MenuEventos menuEventos;

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
            if(resultadoPeticion != null) {
                try {
                    solicitudDeParticipantes =
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

    private class PeticionListaUsuarios extends Peticion{

        private String fragmento;

        public PeticionListaUsuarios(String fragmento){
            this.fragmento = fragmento;
        }

        @Override
        public void calcularMetodo() {
            this.metodo = "GET";
        }

        @Override
        public void calcularServicio() {
            this.servicio = Constants.SERVICES_PATH_USUARIOS;
        }

        @Override
        public void calcularParams() {}

        @Override
        public void doInBackground() {}

        @Override
        public void onPostExcecute(String resultadoPeticion){
            try{
                solicitudParaParticipantes =
                        ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                                    new JSONArray(resultadoPeticion));
                ((ListaConFiltro) getFragmentManager().findFragmentById(
                            R.id.fragment_solicitud_para_participante)).llenarLista();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private class CreaInvitadoEvento extends Peticion{


        private Context contexto;
        private String tipoEvento;
        private Evento evento;
        private Usuario usuario;

        public CreaInvitadoEvento(Context contexto,
                                  String tipoEvento,
                                  Evento evento,
                                  Usuario usuario) {
            this.contexto = contexto;
            this.tipoEvento = tipoEvento;
            this.evento = evento;
            this.usuario = usuario;
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_EVENTOS + this.tipoEvento + "/" +
                    this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_EVE_INVITACIONES;
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
        public void onPostExcecute(String resultadoPeticion) {
            if(resultadoPeticion != null){
                Toast.makeText(this.contexto,this.contexto.getResources().
                                getString(R.string.toast_invita_usu_eve),
                        Toast.LENGTH_SHORT).show();
            }else{
                new AlertDialog.Builder(this.contexto).
                        setTitle(this.contexto.getResources().
                                getString(R.string.alert_invita_usu_eve_tit)).
                        setMessage(this.contexto.getResources().
                                getString(R.string.alert_error_invita_usu_eve_msn)).
                        setNeutralButton(this.contexto.getResources().
                                getString(R.string.BOTON_NEUTRAL), null).
                        create().show();
            }
        }
    }

    private class CreaParticipante extends Peticion{

        private Context contexto;
        private String tipoEvento;
        private Evento evento;
        private Usuario usuario;

        public CreaParticipante(Context contexto,
                                String tipoEvento,
                                Evento evento,
                                Usuario usuario) {
            this.contexto = contexto;
            this.tipoEvento = tipoEvento;
            this.evento = evento;
            this.usuario = usuario;
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_EVENTOS + this.tipoEvento + "/" +
                             this.evento.getId() + "/" +
                             Constants.SERVICES_PATH_EVE_PARTICIPANTES;
        }

        @Override
        public void calcularParams(){
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
                Toast.makeText(this.contexto,this.contexto.getResources().
                                             getString(R.string.toast_crea_participante_eve),
                        Toast.LENGTH_SHORT).show();
            }else{
                new AlertDialog.Builder(this.contexto).
                        setTitle(this.contexto.getResources().
                                getString(R.string.alert_crea_participante_eve_tit)).
                        setMessage(this.contexto.getResources().
                                getString(R.string.alert_error_crea_participante_eve_msn)).
                        setNeutralButton(this.contexto.getResources().
                                getString(R.string.BOTON_NEUTRAL), null).
                        create().show();
            }
        }

    }

    private class EliminarSolicitud extends Peticion{

        private Context contexto;
        private Evento evento;
        private Usuario usuario;
        private String tipoEvento;
        private KeyValueItem itemSolicitud;

        public EliminarSolicitud(Context contexto,
                                 Evento evento,
                                 Usuario usuario,
                                 String tipoEvento,
                                 KeyValueItem itemSolicitud) {
            this.contexto = contexto;
            this.evento = evento;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
            this.itemSolicitud = itemSolicitud;
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento + "/" + this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_EVE_SOLICITUDES + this.usuario.getUsuario();
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
                    JSONObject resultado = new JSONObject(resultadoPeticion);
                    if(resultado.getString("caracterAceptacion").equals("200")){
                        ((ListaConFiltro)((Activity)this.contexto).
                                getFragmentManager().findFragmentById(
                                R.id.fragment_solicitud_de_participante)).
                                eliminarElemento(this.itemSolicitud);
                        Toast.makeText(this.contexto,
                                this.contexto.getResources().getString(
                                        R.string.toast_elimina_solicitud_exito),
                                Toast.LENGTH_LONG).show();
                    }else{
                        this.alertError();
                    }
                } catch (JSONException e) {
                    this.alertError();
                    e.printStackTrace();
                }
            }else{
                this.alertError();
            }
        }

        private void alertError(){
            new AlertDialog.Builder(this.contexto).
                    setTitle(this.contexto.getResources().
                            getString(R.string.alert_elimina_sol_eve_tit)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.alert_error_sol_eve_msn)).
                    setNeutralButton(this.contexto.getResources().
                            getString(R.string.BOTON_NEUTRAL),null).create().show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejo_participantes);
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
        this.menuEventos.esconderSegunFuncionalidadRol(this, funcionalidad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.menuEventos.comportamiento(this, item.getItemId(), getIntent().
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
        } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
            excepcionJsonDeserializacion.printStackTrace();
        }
        new PeticionListaUsuarios(getResources().getString(
                R.string.fragment_solicitud_para_participante)).ejecutarPeticion();
    }

    @Override
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        final KeyValueItem itemSeleccionado = item;
        final Context contexto = this;
        if(identificadorFragmento.equals(getResources().getString(
                R.string.fragment_solicitud_de_participante))){
            new AlertDialog.Builder(this).
                setTitle(contexto.getResources().getString(R.string.alert_resolver_pet_eve_msn)+" " +
                        ((Usuario) item.getValue()).getPrimerNombre()).
                setItems(new CharSequence[]{contexto.getResources().
                                getString(R.string.alert_aceptar_pet_eve_button),
                                contexto.getResources().
                                        getString(R.string.alert_eliminar_petic_eve_button)},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Usuario usuario = (Usuario)itemSeleccionado.getValue();
                                    Evento evento = new ProductorFactoryEvento().
                                            getEventosFactory(getIntent().
                                                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                    getString(ConstantesEvento.SERVICIO_EVENTO)).
                                            crearEvento();
                                    evento.deserializarJson((JsonObject) JsonUtils.
                                            JsonStringToObject(getIntent().
                                                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                    getString(ConstantesEvento.EVENTO_MANEJADO)));
                                    if(which == 0){
                                        new CreaParticipante(contexto,getIntent().
                                                getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                getString(ConstantesEvento.SERVICIO_EVENTO),evento,
                                                usuario).ejecutarPeticion();
                                    }else if(which == 1){
                                        new EliminarSolicitud(contexto,evento,
                                                usuario,
                                                getIntent().
                                                getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                getString(ConstantesEvento.SERVICIO_EVENTO),
                                                itemSeleccionado).ejecutarPeticion();
                                    }
                                } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                                    excepcionJsonDeserializacion.printStackTrace();
                                }
                            }
            }).create().show();
        }else if(identificadorFragmento.equals(getResources().getString(
                    R.string.fragment_solicitud_para_participante))){
            new AlertDialog.Builder(this).
                    setTitle(contexto.getResources().getString(R.string.alert_invita_eve_msn) +
                            " " +
                            ((Usuario) item.getValue()).getPrimerNombre()+"?").
                    setItems(new CharSequence[]{contexto.getResources().
                                    getString(R.string.alert_invita_eve_button),
                                    contexto.getResources().getString(R.string.BOTON_NEGATIVO)},
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Usuario usuario = (Usuario)itemSeleccionado.getValue();
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
                                            new CreaInvitadoEvento(contexto,getIntent().
                                                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                                    getString(ConstantesEvento.SERVICIO_EVENTO),evento,
                                                    usuario).ejecutarPeticion();
                                            dialog.cancel();
                                        }
                                    } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                                        excepcionJsonDeserializacion.printStackTrace();
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
            adapter = ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.solicitudDeParticipantes,
                                                                      new String[]{"primerNombre",
                                                                              "usuario"});
            if(this.solicitudDeParticipantes != null)
                this.solicitudDeParticipantes.clear();
        }else if(identificadorFragmento.equals(getResources().getString(
                R.string.fragment_solicitud_para_participante))){
            adapter = ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.solicitudParaParticipantes,
                                                                      new String[]{"primerNombre",
                                                                              "usuario"});
            if(this.solicitudParaParticipantes != null)
                this.solicitudParaParticipantes.clear();
        }
        return adapter;
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {}

}

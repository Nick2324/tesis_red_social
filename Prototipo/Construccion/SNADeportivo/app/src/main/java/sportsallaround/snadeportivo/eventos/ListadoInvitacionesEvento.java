package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
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
import sportsallaround.utils.gui.TituloActividad;

public class ListadoInvitacionesEvento extends Activity
        implements TituloActividad.InitializerTituloActividad,ListaConFiltro.CallBackListaConFiltro{

    private ArrayList<ObjectSNSDeportivo> invitaciones;
    private Usuario usuario;

    private class TraerInvitacionesUsuario extends Peticion {

        private Context contexto;
        private Usuario usuario;
        private String tipoEvento;

        public TraerInvitacionesUsuario(Context contexto,
                                        Usuario usuario,
                                        String tipoEvento) {
            this.contexto = contexto;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "GET";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_USUARIOS +
                    this.usuario.getUsuario() + "/" +
                    Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento + "/" +
                    Constants.SERVICES_PATH_EVE_INVITACIONES;
        }

        @Override
        public void calcularParams() {
            try {
                super.params = new JSONObject();
                //PONIENDO USUARIO
                JSONArray arrayUsuarios = new JSONArray();
                arrayUsuarios.put(new JSONObject(this.usuario.stringJson()));
                JSONObject parametrosUsuario = new JSONObject();
                parametrosUsuario.put(this.usuario.getClass().getSimpleName(),
                        arrayUsuarios);
                super.params.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                        parametrosUsuario);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void doInBackground() {
            this.setPeticionBody(true);
        }

        @Override
        public void onPostExcecute(String resultadoPeticion) {
            if(resultadoPeticion != null){
                try {
                    invitaciones =
                            ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                                    new JSONArray(resultadoPeticion));
                    ((ListaConFiltro)getFragmentManager().
                            findFragmentById(R.id.fragment_lista_invitaciones_evento)).llenarLista();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this.contexto, this.contexto.getResources().
                        getString(R.string.toast_listado_invitaciones_vacio), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class EliminarInvitacion extends Peticion{

        private Context contexto;
        private Usuario usuario;
        private String tipoEvento;
        private Evento evento;
        private KeyValueItem seleccionado;

        public EliminarInvitacion(Context contexto,
                                  Usuario usuario,
                                  String tipoEvento,
                                  KeyValueItem seleccionado) {
            this.contexto = contexto;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
            this.seleccionado = seleccionado;
            this.evento = (Evento)seleccionado.getValue();
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "DELETE";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_USUARIOS +
                    this.usuario.getUsuario() + "/" +
                    Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento  + "/" + this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_EVE_INVITACIONES;
        }

        @Override
        public void calcularParams() {
            try {
                super.params = new JSONObject();
                //PONIENDO USUARIO
                JSONArray arrayUsuarios = new JSONArray();
                arrayUsuarios.put(new JSONObject(this.usuario.stringJson()));
                JSONObject parametrosUsuario = new JSONObject();
                parametrosUsuario.put(this.usuario.getClass().getSimpleName(),
                        arrayUsuarios);
                super.params.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                        parametrosUsuario);

                //PONIENDO EVENTO
                Evento evento = (Evento)this.seleccionado.getValue();
                JSONArray arrayEventos = new JSONArray();
                arrayEventos.put(new JSONObject(evento.stringJson()));
                JSONObject parametrosEventos = new JSONObject();
                parametrosEventos.put(evento.getClass().getSimpleName(),
                        arrayEventos);
                super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                        parametrosEventos);
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
                    JSONObject respuesta = new JSONObject(resultadoPeticion);
                    if(respuesta.getString("caracterAceptacion") != null &&
                       (respuesta.getString("caracterAceptacion").equals("200") ||
                               respuesta.getString("caracterAceptacion").equals("204"))){
                        //ELIMINANDO ELEMENTO DE LA LISTA
                        ((ListaConFiltro)((Activity)this.contexto).getFragmentManager().
                                findFragmentById(R.id.fragment_lista_invitaciones_evento)).
                                eliminarElemento(this.seleccionado);
                        Toast.makeText(this.contexto,this.contexto.getResources().
                                getString(R.string.toast_invitacion_eliminada_exito),
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
                            getString(R.string.alert_elimina_invitacion_tit)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.alert_elimina_invitacion_err_msn)).
                    setNeutralButton(this.contexto.getResources().
                            getString(R.string.BOTON_NEUTRAL), null).
                    create().show();
        }
    }

    private class AceptarInvitacion extends Peticion{

        private Context contexto;
        private Usuario usuario;
        private String tipoEvento;
        private Evento evento;
        private KeyValueItem seleccionado;

        public AceptarInvitacion(Context contexto,
                                  Usuario usuario,
                                  String tipoEvento,
                                  KeyValueItem seleccionado) {
            this.contexto = contexto;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
            this.seleccionado = seleccionado;
            this.evento = (Evento)seleccionado.getValue();
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_USUARIOS +
                    this.usuario.getUsuario() + "/" +
                    Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento + this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_EVE_INVITACIONES;;
        }

        @Override
        public void calcularParams() {
            try {
                super.params = new JSONObject();
                //PONIENDO USUARIO
                JSONArray arrayUsuarios = new JSONArray();
                arrayUsuarios.put(new JSONObject(this.usuario.stringJson()));
                JSONObject parametrosUsuario = new JSONObject();
                parametrosUsuario.put(this.usuario.getClass().getSimpleName(),
                        arrayUsuarios);
                super.params.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                        parametrosUsuario);

                //PONIENDO EVENTO
                Evento evento = (Evento)this.seleccionado.getValue();
                JSONArray arrayEventos = new JSONArray();
                arrayEventos.put(new JSONObject(evento.stringJson()));
                JSONObject parametrosEventos = new JSONObject();
                parametrosEventos.put(evento.getClass().getSimpleName(),
                        arrayEventos);
                super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                        parametrosEventos);
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
                    JSONObject respuesta = new JSONObject(resultadoPeticion);
                    if(respuesta.getString("caracterAceptacion") != null &&
                            (respuesta.getString("caracterAceptacion").equals("200") ||
                                    respuesta.getString("caracterAceptacion").equals("204"))){
                        //ELIMINANDO ELEMENTO DE LA LISTA
                        ((ListaConFiltro)((Activity)this.contexto).getFragmentManager().
                                findFragmentById(R.id.fragment_lista_invitaciones_evento)).
                                eliminarElemento(this.seleccionado);
                        Toast.makeText(this.contexto,this.contexto.getResources().
                                        getString(R.string.toast_invitacion_aceptada_exito),
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
                            getString(R.string.alert_acepta_invitacion_tit)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.alert_acepta_invitacion_err_msn)).
                    setNeutralButton(this.contexto.getResources().
                            getString(R.string.BOTON_NEUTRAL), null).
                    create().show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_invitaciones_evento);
        setTitle(getResources().getString(R.string.titulo_gestion_eventos));
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setUpObjetos();
        this.setUpLista();
    }

    @Override
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        final Context contexto = this;
        final KeyValueItem itemSeleccionado = item;
        new AlertDialog.Builder(this).
                setTitle(getResources().getString(R.string.alert_selecciona_invitacion_tit)).
                setItems(new CharSequence[]{
                        getResources().getString(R.string.alert_button_aceptar_invitacion),
                        getResources().getString(R.string.alert_button_eliminar_invitacion)
                        }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            new AceptarInvitacion(contexto,usuario,
                                    getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                    getString(ConstantesEvento.SERVICIO_EVENTO),
                                    itemSeleccionado).ejecutarPeticion();
                        }else if(which == 1){
                            new EliminarInvitacion(contexto,usuario,
                                    getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                            getString(ConstantesEvento.SERVICIO_EVENTO),
                                    itemSeleccionado).ejecutarPeticion();
                        }else{
                            new AlertDialog.Builder(contexto).
                                    setTitle(getResources().
                                            getString(R.string.alert_elimina_participante_tit)).
                                    setMessage(getResources().
                                            getString(R.string.alert_error_elimina_participante_msn)).
                                    setNeutralButton(getResources().
                                            getString(R.string.BOTON_NEUTRAL), null).
                                    create().show();
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
                ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.invitaciones, new String[]{"primerNombre", "usuario"});
        this.invitaciones.clear();
        return retorno;
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {}

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        return getResources().getString(R.string.titulo_invitacion_eventos_participante);
    }

    private void setUpLista(){
            new TraerInvitacionesUsuario(this,this.usuario,getIntent().getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                    ConstantesEvento.SERVICIO_EVENTO)).ejecutarPeticion();

    }

    private void setUpObjetos(){
        try{
            this.usuario = new Usuario();
            this.usuario.deserializarJson((JsonObject) JsonUtils.
                    JsonStringToObject(getIntent().
                            getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                            getString(Constants.USUARIO)));
        } catch (ExcepcionJsonDeserializacion e) {
            e.printStackTrace();
        }
    }
}

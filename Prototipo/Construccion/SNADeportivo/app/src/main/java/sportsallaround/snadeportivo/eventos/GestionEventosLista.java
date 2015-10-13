package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.ProductorFactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.evento.TiposEventos;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.LanzadorPeticionEncadenada;
import sportsallaround.utils.Peticion;
import sportsallaround.utils.PeticionEncadenada;
import sportsallaround.utils.gui.AttachObjetoListener;
import sportsallaround.utils.Constants;
import sportsallaround.utils.gui.DescripcionGo;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.ObjetoListener;
import sportsallaround.utils.gui.ObjetoListenerSpinner;

public class GestionEventosLista extends Activity
        implements AttachObjetoListener,
                   ListaConFiltro.CallBackListaConFiltro,
                   DescripcionGo.DescripcionGoCallBack{

    private ArrayList<Evento> eventos;
    private KeyValueItem tipoNumeroEvento;

    private class PeticionDeporteEvento extends PeticionEncadenada{

        private Context contexto;
        private Evento evento;
        private String tipoEvento;
        private Bundle extras;

        public PeticionDeporteEvento(Context contexto,
                                     Evento evento,
                                     String tipoEvento,
                                     Bundle extras) {
            super("Deporte");
            this.contexto = contexto;
            this.evento = evento;
            this.tipoEvento = tipoEvento;
            this.extras = extras;
        }

        @Override
        public boolean onPostExecuteEncadenado(String resultadoPeticion) {
            if(resultadoPeticion != null) {
                try {
                    JSONObject resultado = new JSONObject(resultadoPeticion);
                    String caracterAceptacion =
                            resultado.getString("caracterAceptacion");
                    if(caracterAceptacion != null &&
                            (caracterAceptacion.equals("200") ||
                                    caracterAceptacion.equals("204"))){
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        public void ejecutaTareaUltimaPeticion() {}

        @Override
        public void calcularMetodo() {
            super.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento + "/" + this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_DEPORTES;
        }

        @Override
        public void calcularParams() {
            super.params = new JSONObject();
            try {
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
        public void doInBackground() {

        }
    }

    private class PeticionGeneroEvento extends PeticionEncadenada{

        private Context contexto;
        private Evento evento;
        private String tipoEvento;
        private Bundle extras;

        public PeticionGeneroEvento(Context contexto,
                                    Evento evento,
                                    String tipoEvento,
                                    Bundle extras) {
            super("Genero");
            this.contexto = contexto;
            this.evento = evento;
            this.tipoEvento = tipoEvento;
            this.extras = extras;
        }

        @Override
        public boolean onPostExecuteEncadenado(String resultadoPeticion) {
            if(resultadoPeticion != null) {
                try {
                    JSONObject resultado = new JSONObject(resultadoPeticion);
                    String caracterAceptacion =
                            resultado.getString("caracterAceptacion");
                    if(caracterAceptacion != null &&
                       (caracterAceptacion.equals("200") ||
                        caracterAceptacion.equals("204"))){
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        public void ejecutaTareaUltimaPeticion() {
            //Preparando datos adicionales del intent
            if(super.peticionesEncadenadas != null) {
                try {
                    if(peticionesEncadenadas.getString("Deporte") != null) {
                        //PONE DATOS DE DEPORTE
                        JSONObject respuesta = new JSONObject(peticionesEncadenadas.
                                getString("Deporte"));
                        if(respuesta.getString("datosExtra") != null) {
                            this.extras.putString(ConstantesEvento.DEPORTE_MANEJADO,
                                    respuesta.getString("datosExtra"));
                        }
                    }
                    if(peticionesEncadenadas.getString("Genero") != null) {
                        //PONE DATOS DE GENERO
                        JSONObject respuesta = new JSONObject(peticionesEncadenadas.
                                getString("Genero"));
                        if(respuesta.getString("datosExtra") != null) {
                            this.extras.putString(ConstantesEvento.GENERO_MANEJADO,
                                    respuesta.getString("datosExtra"));
                        }
                    }
                    Intent intent = new Intent(this.contexto, InformacionGeneralEvento.class);
                    intent.putExtra(Constants.DATOS_FUNCIONALIDAD, extras);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.alertError();
                }
            } else {
                this.alertError();
            }
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "POST";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento + "/" + this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_GENERO;
        }

        @Override
        public void calcularParams() {
            super.params = new JSONObject();
            try {
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

        private void alertError(){
            new AlertDialog.Builder(this.contexto).
                    setTitle(this.contexto.getResources().
                            getString(R.string.descripcion_go_error_ir_eve_tit)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.descripcion_go_error_ir_eve_msn)).
                    setNeutralButton(this.contexto.getResources().
                            getString(R.string.BOTON_NEUTRAL), null).
                    create().show();
        }

    }

    private class PeticionSpinner implements ObjetoListenerSpinner {

        private Context contexto;

        public PeticionSpinner(Context contexto){
            this.contexto = contexto;
        }

        @Override
        public void onItemSelected(Object objetoSeleccionado) {
            if(ConstantesEvento.NO_OWNER.equals(
                    getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD)
                    .getString(ConstantesEvento.OWNER_EVENTO))) {
                new PeticionEventos(this.contexto,
                        (TiposEventos) objetoSeleccionado).ejecutarPeticion();
            }else if(ConstantesEvento.OWNER.equals(
                    getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD)
                            .getString(ConstantesEvento.OWNER_EVENTO))){
                Usuario usuario = (Usuario)new ProductorFactoryUsuario().
                        producirFacObjetoSNS(Usuario.class.getSimpleName()).getObjetoSNS();
                try {
                    usuario.deserializarJson((JsonObject) JsonUtils.JsonStringToObject(
                            getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                    getString(Constants.USUARIO)
                    ));
                    new PeticionEventos(this.contexto,
                            (TiposEventos) objetoSeleccionado,
                            usuario).ejecutarPeticion();
                } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                    new AlertDialog.Builder(this.contexto).
                            setTitle(this.contexto.getResources().
                                    getString(R.string.alert_lista_eventos_err_tit))
                            .setMessage(this.contexto.getResources().
                                    getString(R.string.alert_lista_eventos_err_msn)).
                            setNeutralButton(this.contexto.getResources()
                                    .getString(R.string.BOTON_NEUTRAL), null).create().show();
                    excepcionJsonDeserializacion.printStackTrace();
                }
            }else{
                new AlertDialog.Builder(this.contexto).
                        setTitle(this.contexto.getResources().
                                getString(R.string.alert_lista_eventos_err_tit))
                        .setMessage(this.contexto.getResources().
                                getString(R.string.alert_lista_eventos_err_msn)).
                        setNeutralButton(this.contexto.getResources()
                                .getString(R.string.BOTON_NEUTRAL), null).create().show();
            }
        }

        @Override
        public void onNothingSelected() {}
    }

    private class PeticionEventos extends Peticion {

        private Context contexto;
        private TiposEventos tipoEvento;
        private Usuario usuario;

        public PeticionEventos(Context contexto,TiposEventos tipoEvento){
            this.contexto = contexto;
            this.tipoEvento = tipoEvento;
        }

        public PeticionEventos(Context contexto,
                               TiposEventos tipoEvento,
                               Usuario usuario){
            this.contexto = contexto;
            this.tipoEvento = tipoEvento;
            this.usuario = usuario;
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "GET";
        }

        @Override
        public void calcularServicio() {
            if(this.usuario != null){
                super.servicio = Constants.SERVICES_PATH_USUARIOS +
                        this.usuario.getUsuario() + "/" +
                        Constants.SERVICES_PATH_EVENTOS + tipoEvento.getServicio();
            }else{
                super.servicio = Constants.SERVICES_PATH_EVENTOS + tipoEvento.getServicio();
            }

        }

        @Override
        public void calcularParams() {
            try {
                if(this.usuario != null) {
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
                    Evento e = new ProductorFactoryEvento().
                            getEventosFactory(this.tipoEvento.getServicio()).
                            crearEvento();
                    //Los eventos deben estar activos
                    e.setActivo(true);
                    JSONArray arrayEventos = new JSONArray();
                    arrayEventos.put(new JSONObject(e.stringJson()));
                    JSONObject parametrosEvento = new JSONObject();
                    parametrosEvento.put(e.getClass().getSimpleName(),
                            arrayEventos);
                    super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                            parametrosEvento);
                    Log.d("Nick:params", super.params.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void doInBackground() {
            super.setPeticionBody(true);
        }

        @Override
        public void onPostExcecute(String resultadoPeticion) {
            if(this.usuario != null && params == null){
                new AlertDialog.Builder(this.contexto).
                        setTitle(this.contexto.getResources().
                                getString(R.string.alert_lista_eventos_err_tit)).
                        setMessage(this.contexto.getResources().
                                getString(R.string.alert_lista_eventos_err_msn)).
                        setNeutralButton(this.contexto.getResources().
                                getString(R.string.BOTON_NEUTRAL),null).
                        create().show();
            }else {
                Spinner tipoEvento = (Spinner) findViewById(R.id.spinner_tipo_evento);
                if (resultadoPeticion != null && !resultadoPeticion.equals("")) {
                    try {
                        JSONArray responseJson = new JSONArray(resultadoPeticion);
                        eventos = new ArrayList<Evento>();
                        for (int i = 0; i < responseJson.length(); i++) {
                            eventos.add(new ProductorFactoryEvento().
                                    getEventosFactory(((TiposEventos) tipoEvento.getSelectedItem()).
                                            getServicio()).
                                    crearEvento());
                            eventos.get(i).deserializarJson(
                                    JsonUtils.JsonStringToObject(responseJson.getString(i)));
                        }
                        ((ListaConFiltro) getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                                llenarLista();
                    } catch (JSONException e) {
                        ((ListaConFiltro) getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                                limpiarLista();
                        Log.w("SNA:JSONException", e.getMessage());
                    } catch (ExcepcionJsonDeserializacion e) {
                        ((ListaConFiltro) getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                                limpiarLista();
                        Log.w("SNA:ExcepcionJsonDes", e.getMessage());
                    }
                } else {
                    ((ListaConFiltro) getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                            limpiarLista();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_eventos_lista);
        setTitle(getResources().getString(R.string.titulo_gestion_eventos_lista));
        this.setUpListeners();
    }

    protected void setUpListeners() {
        final Context actividad = this;
        Button crearEvento = (Button) findViewById(R.id.button_crear_evento);
        if(getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                getString(ConstantesEvento.OWNER_EVENTO).equals(ConstantesEvento.OWNER)) {
            crearEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent crearEventoIntent = new Intent(actividad, CrearEvento.class);
                    crearEventoIntent.putExtra(Constants.DATOS_FUNCIONALIDAD,
                            getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD));
                    startActivity(crearEventoIntent);
                }
            });
        }else if(getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                getString(ConstantesEvento.OWNER_EVENTO).equals(ConstantesEvento.NO_OWNER)){
            crearEvento.setVisibility(View.GONE);
        }
    }

    private ArrayList<KeyValueItem> convertirEventosKeyValueItem(){
        ArrayList<KeyValueItem> arrayListaEventos =
                new ArrayList<KeyValueItem>();
        for (Evento e : eventos) {
            arrayListaEventos.add(new KeyValueItem(eventos.indexOf(e), e.getNombre()));
        }
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
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        if(identificadorFragmento.equals(getResources().getString(R.string.lista_evento_string))){
            int i = Integer.parseInt(item.getKey().toString());
            ((DescripcionGo)getFragmentManager().findFragmentById(R.id.fragment_switcher_info)).
                    setDescripcion(eventos.get(i).getNombre() + "\n" +
                            eventos.get(i).getDescripcion() + "\n" +
                            getResources().getString(R.string.descripcion_go_eve_fecha_crea)
                            + ": " + eventos.get(i).getFechaCreacion() + "\n" +
                            getResources().getString(R.string.descripcion_go_eve_fecha_ini)
                            + ": " + eventos.get(i).getFechaInicio());

            tipoNumeroEvento = new KeyValueItem(new Integer(i),
                    ((SpinnerEventos)getFragmentManager().
                    findFragmentById(R.id.fragment_tipo_evento)).getValueSpinnerEventos());
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
        return getResources().getString(R.string.descripcion_go_boton_ir_eve);
    }

    @Override
    public String getDefaultDescripcion() {
        return getResources().getString(R.string.descripcion_go_default_ir_eve);
    }

    @Override
    public void go() {
        if(this.tipoNumeroEvento != null) {
            Bundle extra = getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD);
            //Poniendo extras generales
            extra.putString(ConstantesEvento.TIPO_EVENTO,
                    ((TiposEventos) tipoNumeroEvento.getValue()).getNombreClase());
            extra.putString(ConstantesEvento.EVENTO_MANEJADO,
                    this.eventos.get((Integer) tipoNumeroEvento.getKey()).stringJson());
            extra.putString(ConstantesEvento.SERVICIO_EVENTO,
                    ((TiposEventos) tipoNumeroEvento.getValue()).getServicio());
            //Poniendo extras especificos y lanzando intent
            if(ConstantesEvento.OWNER.equals(extra.getString(ConstantesEvento.OWNER_EVENTO))) {
                extra.putString(Constants.FUNCIONALIDAD,
                        ConstantesEvento.ACTUALIZAR_EVENTO);
                extra.putString(ConstantesEvento.TIPO_MENU,
                        ConstantesEvento.MENU_ADMIN_EVENTOS);
                //Ejecutando peticion encadenada
                LanzadorPeticionEncadenada lpe = new LanzadorPeticionEncadenada();
                lpe.setSiguientePeticion(new PeticionDeporteEvento(this,
                        this.eventos.get((Integer) tipoNumeroEvento.getKey()),
                        extra.getString(ConstantesEvento.SERVICIO_EVENTO),extra));
                lpe.setSiguientePeticion(new PeticionGeneroEvento(this,
                        this.eventos.get((Integer) tipoNumeroEvento.getKey()),
                        extra.getString(ConstantesEvento.SERVICIO_EVENTO),extra));
                lpe.ejecutarPeticion();
            }else if(ConstantesEvento.NO_OWNER.equals(extra.getString(ConstantesEvento.OWNER_EVENTO))){
                extra.putString(Constants.FUNCIONALIDAD,
                        ConstantesEvento.PARTICIPANTE_EVENTO);
                extra.putString(ConstantesEvento.TIPO_MENU,
                        ConstantesEvento.MENU_PART_EVENTOS);
                Intent intent = new Intent(this,PerfilEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD, extra);
                startActivity(intent);
            }else{
                new AlertDialog.Builder(this).setTitle(getResources().
                        getString(R.string.descripcion_go_error_ir_eve_tit)+" "+
                        this.eventos.get((Integer) tipoNumeroEvento.getKey()).getNombre()).
                        setMessage(getResources().getString(R.string.descripcion_go_error_ir_eve_msn)).
                        setNeutralButton(getResources().getString(R.string.BOTON_NEUTRAL)
                                ,null).create().show();

            }
        }
    }

}

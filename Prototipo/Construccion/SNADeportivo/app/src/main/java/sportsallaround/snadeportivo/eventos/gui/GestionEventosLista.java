package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sna_deportivo.pojo.usuarios.ProductorFactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.TiposEventos;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.peticiones.PeticionDeporteEvento;
import sportsallaround.snadeportivo.eventos.peticiones.PeticionEventos;
import sportsallaround.snadeportivo.eventos.peticiones.PeticionEventosAsistente;
import sportsallaround.snadeportivo.eventos.peticiones.PeticionGeneroEvento;
import sportsallaround.utils.generales.LanzadorPeticionEncadenada;
import sportsallaround.utils.generales.PeticionListaCallback;
import sportsallaround.utils.gui.AttachObjetoListener;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.gui.DescripcionGo;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.ObjetoListener;
import sportsallaround.utils.gui.ObjetoListenerSpinner;

public class GestionEventosLista extends Activity
        implements AttachObjetoListener,
                   ListaConFiltro.CallBackListaConFiltro,
                   DescripcionGo.DescripcionGoCallBack,
                   PeticionListaCallback {

    private ArrayList<Evento> eventos;
    private KeyValueItem tipoNumeroEvento;

    public class PeticionSpinner implements ObjetoListenerSpinner {

        private Context contexto;

        public PeticionSpinner(Context contexto){
            this.contexto = contexto;
        }

        @Override
        public void onItemSelected(Object objetoSeleccionado) {
            if(ConstantesEvento.NO_OWNER.equals(
                    getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD)
                            .getString(ConstantesEvento.OWNER_EVENTO))) {
                if(ConstantesEvento.POSIBLE_PARTICIPANTE_EVENTO.equals(
                        getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD)
                                .getString(Constants.FUNCIONALIDAD))) {
                    new PeticionEventos(this.contexto,
                            (TiposEventos) objetoSeleccionado).ejecutarPeticion();
                }else if(ConstantesEvento.PARTICIPANTE_EVENTO.equals(
                        getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD)
                                .getString(Constants.FUNCIONALIDAD))){
                    Usuario usuario = (Usuario)new ProductorFactoryUsuario().
                            producirFacObjetoSNS(Usuario.class.getSimpleName()).getObjetoSNS();
                    try {
                        usuario.deserializarJson((JsonObject) JsonUtils.JsonStringToObject(
                                getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                        getString(Constants.USUARIO)
                        ));
                        new PeticionEventosAsistente(this.contexto,
                                (TiposEventos) objetoSeleccionado,usuario).
                                ejecutarPeticion();
                    }catch(ExcepcionJsonDeserializacion e){
                        e.printStackTrace();
                    }
                }
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
                } catch (ExcepcionJsonDeserializacion e) {
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
                            getString(R.string.alert_lista_eventos_err_tit))
                    .setMessage(this.contexto.getResources().
                            getString(R.string.alert_lista_eventos_err_msn)).
                    setNeutralButton(this.contexto.getResources()
                            .getString(R.string.BOTON_NEUTRAL), null).create().show();
        }

        @Override
        public void onNothingSelected() {}
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

    @Override
    public void llenarListaDesdePeticion(ArrayList<ObjectSNSDeportivo> listaObtenida) {
        this.eventos = new ArrayList<Evento>();
        for(ObjectSNSDeportivo obj:listaObtenida){
            this.eventos.add((Evento)obj);
        }
        ((ListaConFiltro) getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                llenarLista();
    }

    @Override
    public void limpiarListaDesdePeticion() {
        ((ListaConFiltro) getFragmentManager().findFragmentById(R.id.fragment_lista_eventos)).
                limpiarLista();
    }

    @Override
    public void eliminarItem(KeyValueItem aEliminar) {

    }

}

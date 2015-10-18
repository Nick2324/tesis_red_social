package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.peticiones.CreaParticipante;
import sportsallaround.snadeportivo.eventos.peticiones.EliminarInvitacion;
import sportsallaround.snadeportivo.eventos.peticiones.TraerInvitacionesUsuario;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ConstructorArrObjSNS;
import sportsallaround.utils.generales.PeticionListaCallback;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.TituloActividad;

public class ListadoInvitacionesEvento extends Activity
        implements TituloActividad.InitializerTituloActividad,ListaConFiltro.CallBackListaConFiltro,
        PeticionListaCallback {

    private ArrayList<ObjectSNSDeportivo> invitaciones;
    private Usuario usuario;

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
                            new CreaParticipante(contexto,
                                    getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                    getString(ConstantesEvento.SERVICIO_EVENTO),
                                    (Evento)itemSeleccionado.getValue(),usuario,itemSeleccionado).
                                    ejecutarPeticion();
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
                ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.invitaciones, new String[]{"nombre"});
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
                    ConstantesEvento.TIPO_EVENTO)).ejecutarPeticion();

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

    @Override
    public void llenarListaDesdePeticion(ArrayList<ObjectSNSDeportivo> listaObtenida) {
        this.invitaciones = listaObtenida;
        ((ListaConFiltro)getFragmentManager().
                findFragmentById(R.id.fragment_lista_invitaciones_evento)).llenarLista();
    }

    @Override
    public void limpiarListaDesdePeticion() {
        ((ListaConFiltro)getFragmentManager().
                findFragmentById(R.id.fragment_lista_invitaciones_evento)).limpiarLista();
    }

    @Override
    public void eliminarItem(KeyValueItem aEliminar) {
        ((ListaConFiltro)this.getFragmentManager().
                findFragmentById(R.id.fragment_lista_invitaciones_evento)).
                eliminarElemento(aEliminar);
    }

}

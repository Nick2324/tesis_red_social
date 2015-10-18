package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.peticiones.CreaInvitadoEvento;
import sportsallaround.snadeportivo.eventos.peticiones.PeticionListaUsuarios;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ConstructorArrObjSNS;
import sportsallaround.utils.generales.PeticionListaCallback;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;

public class ListadoInvitarAParticipar extends Activity
        implements ListaConFiltro.CallBackListaConFiltro,PeticionListaCallback {

    private ArrayList<ObjectSNSDeportivo> solicitudParaParticipantes;
    private MenuSNS menuSNS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_invitar_aparticipar);
        setTitle(getResources().getString(R.string.titulo_gestion_eventos));
    }

    @Override
    protected void onStart(){
        super.onStart();
        new PeticionListaUsuarios(this).ejecutarPeticion();
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
        this.menuSNS.esconderItems(menu,new ProductorMenuEventos().
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
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        final Context contexto = this;
        final KeyValueItem itemSeleccionado = item;
        if(identificadorFragmento.equals(getResources().getString(
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
                                    } catch (ExcepcionJsonDeserializacion e) {
                                        e.printStackTrace();
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

    @Override
    public void llenarListaDesdePeticion(ArrayList<ObjectSNSDeportivo> listaObtenida) {
        this.solicitudParaParticipantes = listaObtenida;
        ((ListaConFiltro) getFragmentManager().findFragmentById(
                R.id.fragment_solicitud_para_participante)).llenarLista();
    }

    @Override
    public void limpiarListaDesdePeticion() {

    }

    @Override
    public void eliminarItem(KeyValueItem aEliminar) {

    }
}

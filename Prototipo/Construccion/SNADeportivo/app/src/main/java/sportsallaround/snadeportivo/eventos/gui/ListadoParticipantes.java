package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.snadeportivo.eventos.peticiones.EliminarParticipante;
import sportsallaround.snadeportivo.eventos.peticiones.ObtenerParticipantes;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ConstructorArrObjSNS;
import sportsallaround.utils.generales.PeticionListaCallback;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.TituloActividad;

public class ListadoParticipantes extends Activity
        implements TituloActividad.InitializerTituloActividad,
        ListaConFiltro.CallBackListaConFiltro,PeticionListaCallback {

    private ArrayList<ObjectSNSDeportivo> participantes;
    private MenuSNS menuSNS;

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
        this.menuSNS = new ProductorMenuEventos().proveerMenuEventos(tipoMenu);
        getMenuInflater().inflate(menuSNS.getIdMenu(), menu);
        this.menuSNS.esconderItems(menu, new ProductorMenuEventos().
                proveerAnalizadorItem(funcionalidad));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.menuSNS.comportamiento(this, item.getItemId(),getIntent().
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
                    } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                        productorFactoryExcepcion.printStackTrace();
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
        } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
            productorFactoryExcepcion.printStackTrace();
        }
    }

    @Override
    public void llenarListaDesdePeticion(ArrayList<ObjectSNSDeportivo> listaObtenida) {
        this.participantes = listaObtenida;
        ((ListaConFiltro)getFragmentManager().
                findFragmentById(R.id.fragment_lista_participantes)).llenarLista();
    }

    @Override
    public void limpiarListaDesdePeticion() {

    }

    @Override
    public void eliminarItem(KeyValueItem aEliminar) {
        ((ListaConFiltro)getFragmentManager().findFragmentById(
                R.id.fragment_lista_participantes)).
                eliminarElemento(aEliminar);
    }

}

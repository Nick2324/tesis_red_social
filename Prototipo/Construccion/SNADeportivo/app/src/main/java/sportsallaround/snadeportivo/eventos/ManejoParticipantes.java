package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sna_deportivo.pojo.usuarios.FactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

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

        private String fragmento;

        public PeticionSolicitudParticipantes(String fragmento){
            this.fragmento = fragmento;
        }

        @Override
        public void calcularMetodo() {
            this.metodo = "GET";
        }

        @Override
        public void calcularServicio() {
            this.servicio = Constants.SERVICES_PATH_EVENTOS +
                            Constants.SERVICES_PATH_EVE_PARTICIPANTES;
        }

        @Override
        public void calcularParams(){
            try {
                super.params = new JSONObject(getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                        getString(ConstantesEvento.EVENTO_MANEJADO));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void doInBackground() {}

        @Override
        public void onPostExcecute(String resultadoPeticion){
            try{
                solicitudDeParticipantes =
                        ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                                    new JSONArray(resultadoPeticion));
                ((ListaConFiltro) getFragmentManager().findFragmentById(
                            R.id.fragment_solicitud_de_participante)).llenarLista();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void setFragmento(String fragmento){
            this.fragmento = fragmento;
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
        new PeticionSolicitudParticipantes(getResources().getString(
                R.string.fragment_solicitud_de_participante)).ejecutarPeticion();
        new PeticionSolicitudParticipantes(getResources().getString(
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
                                if(which == 0){
                                    Toast.makeText(contexto, "por implementar Aceptar participante", Toast.LENGTH_LONG);
                                }else if(which == 1){
                                    ((ListaConFiltro)getFragmentManager().findFragmentById(
                                            R.id.fragment_solicitud_de_participante)).
                                            eliminarElemento(itemSeleccionado);
                                    Toast.makeText(contexto,"por implementar Eliminar petición participante", Toast.LENGTH_LONG);
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
                                    if (which == 0) {
                                        Toast.makeText(contexto, "Invitar por implementar", Toast.LENGTH_LONG);
                                        dialog.cancel();
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

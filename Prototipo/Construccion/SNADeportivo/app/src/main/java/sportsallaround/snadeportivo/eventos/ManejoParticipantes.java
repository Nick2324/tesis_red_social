package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.FactoryDeporte;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.Key;
import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ConstructorArrObjSNS;
import sportsallaround.utils.Peticion;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.TituloActividad;

public class ManejoParticipantes extends Activity implements ListaConFiltro.CallBackListaConFiltro,
        TituloActividad.InitializerTituloActividad{

    private ArrayList<ObjectSNSDeportivo> solicitudDeParticipantes;
    private ArrayList<ObjectSNSDeportivo> solicitudParaParticipantes;
    private MenuEventos menuEventos;

    private class PeticionSolicitudParticipantes extends Peticion{

        private String fragmento;

        @Override
        public void calcularMetodo() {
            this.metodo = "GET";
        }

        @Override
        public void calcularServicio() {
            this.servicio = Constants.SERVICES_PATH_DEPORTES;
        }

        @Override
        public void calcularParams() {}

        @Override
        public void doInBackground() {}

        @Override
        public void onPostExcecute(String resultadoPeticion){
            try{
                Log.d("Nick:Peticion", resultadoPeticion);

                if(this.fragmento.equals(getResources().getString(R.string.fragment_solicitud_de_participante))) {
                    solicitudDeParticipantes =
                            ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryDeporte(),
                                    new JSONArray(resultadoPeticion));
                    ((ListaConFiltro) getFragmentManager().findFragmentById(
                            R.id.fragment_solicitud_de_participante)).llenarLista();
                }else if(this.fragmento.equals(getResources().getString(R.string.fragment_solicitud_para_participante))){
                    solicitudParaParticipantes =
                            ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryDeporte(),
                                    new JSONArray(resultadoPeticion));
                    ((ListaConFiltro) getFragmentManager().findFragmentById(
                            R.id.fragment_solicitud_para_participante)).llenarLista();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void setFragmento(String fragmento){
            this.fragmento = fragmento;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejo_participantes);
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
        this.menuEventos.comportamiento(this, item.getItemId(),getIntent().
                getExtras().getBundle(Constants.DATOS_FUNCIONALIDAD));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        PeticionSolicitudParticipantes llenadoListas1 =
                new PeticionSolicitudParticipantes();
        llenadoListas1.setFragmento(getResources().getString(
                R.string.fragment_solicitud_de_participante));
        llenadoListas1.ejecutarPeticion();
        PeticionSolicitudParticipantes llenadoListas2 =
                new PeticionSolicitudParticipantes();
        llenadoListas2.setFragmento(getResources().getString(
                R.string.fragment_solicitud_para_participante));
        llenadoListas2
                .ejecutarPeticion();
    }

    @Override
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        Log.d("Nick","Entro a realizar accion "+identificadorFragmento);
        final KeyValueItem itemSeleccionado = item;
        if(identificadorFragmento.equals(getResources().getString(
                R.string.fragment_solicitud_de_participante))){
            new AlertDialog.Builder(this).
                setTitle("Decide que hacer con " +
                        ((Deporte) item.getValue()).getNombre()).
                setItems(new CharSequence[]{"Aceptar participante",
                                            "Eliminar petición participante"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    Log.d("Nick", "Aceptar");
                                }else if(which == 1){
                                    ((ListaConFiltro)getFragmentManager().findFragmentById(
                                            R.id.fragment_solicitud_de_participante)).
                                            eliminarElemento(itemSeleccionado);
                                    //!*!*!*!*!*!*! Enviar señal de eliminar
                                }
                            }
            }).create().show();
        }else if(identificadorFragmento.equals(getResources().getString(
                    R.string.fragment_solicitud_para_participante))){
            new AlertDialog.Builder(this).
                    setTitle("¿Invitar a " +
                            ((Deporte) item.getValue()).getNombre()+"?").
                    setItems(new CharSequence[]{"Invitar"},
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which == 0){
                                        Log.d("Nick","Invitado");
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
                                                                      "nombre");
            if(this.solicitudDeParticipantes != null)
                this.solicitudDeParticipantes.clear();
        }else if(identificadorFragmento.equals(getResources().getString(
                R.string.fragment_solicitud_para_participante))){
            adapter = ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.solicitudParaParticipantes,
                                                                      "nombre");
            if(this.solicitudParaParticipantes != null)
                this.solicitudParaParticipantes.clear();
        }
        return adapter;
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {}

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        //!*!*!*!*!*!*! Nombre del evento!!!
        return getResources().getString(R.string.titulo_gestion_eventos);
    }

}

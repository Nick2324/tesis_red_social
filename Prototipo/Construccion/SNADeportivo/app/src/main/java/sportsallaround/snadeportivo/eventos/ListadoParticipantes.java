package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sna_deportivo.pojo.deportes.FactoryDeporte;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

import org.json.JSONArray;
import org.json.JSONException;

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
        @Override
        public void calcularMetodo() {
            this.metodo = "GET";
        }

        @Override
        public void calcularServicio() {
            this.servicio = Constants.SERVICES_PATH_DEPORTES;
        }

        @Override
        public void calcularParams() {
            this.params = null;
        }

        @Override
        public void doInBackground() {}

        @Override
        public void onPostExcecute(String resultadoPeticion) {
            try {
                participantes =
                        ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryDeporte(),
                                                                    new JSONArray(resultadoPeticion));
                ((ListaConFiltro)getFragmentManager().
                        findFragmentById(R.id.fragment_lista_participantes)).llenarLista();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_participantes);
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
        final KeyValueItem itemSeleccionado = item;
        new AlertDialog.Builder(this).
            setTitle("¿Que desea hacer con el participante?").
            setItems(new CharSequence[]{"Eliminar participante"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        Log.d("Nick:Part","Elimino participante");
                        ((ListaConFiltro)getFragmentManager().findFragmentById(
                                R.id.fragment_lista_participantes)).
                                eliminarElemento(itemSeleccionado);
                        //!*!*!*!*!*!*!*! Llamar peticion de eliminacion de participante
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
                ConstructorArrObjSNS.producirArrayAdapterObjSNS(this.participantes,"nombre");
        this.participantes.clear();
        return retorno;
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {}

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        if(tagFragmento.equals(getResources().getString(R.string.titulo_gestion_eventos)))
            return getResources().getString(R.string.titulo_gestion_eventos);
        else if(tagFragmento.equals(getResources().getString(R.string.lista_participantes)))
            return getResources().getString(R.string.lista_participantes);
        return null;
    }

    private void setUpLista(){
        new ObtenerParticipantes().ejecutarPeticion();
    }
}

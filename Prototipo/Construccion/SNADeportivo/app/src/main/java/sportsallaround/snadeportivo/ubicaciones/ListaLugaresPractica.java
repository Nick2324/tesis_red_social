package sportsallaround.snadeportivo.ubicaciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sna_deportivo.pojo.ubicacion.LugarPractica;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ubicaciones.general.ConstantesUbicacion;
import sportsallaround.snadeportivo.ubicaciones.peticiones.TraerLugaresPractica;
import sportsallaround.utils.generales.ConstructorArrObjSNS;
import sportsallaround.utils.generales.PeticionListaCallback;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.ListaConFiltro;
import sportsallaround.utils.gui.TituloActividad;

public class ListaLugaresPractica extends Activity implements
        TituloActividad.InitializerTituloActividad,
        ListaConFiltro.CallBackListaConFiltro,
        PeticionListaCallback{

    private ArrayList<ObjectSNSDeportivo> lugaresPractica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lugares_practica);
        setTitle(getResources().getString(R.string.title_gestion_ubicaciones));
        setUpObjetos();
    }

    @Override
    protected void onStart(){
        super.onStart();
        new TraerLugaresPractica(this).ejecutarPeticion();
    }

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        return getResources().getString(R.string.listview_filtro_lugares_practica);
    }

    @Override
    public void llenarListaDesdePeticion(ArrayList<ObjectSNSDeportivo> listaObtenida) {
        this.lugaresPractica = listaObtenida;
        ((ListaConFiltro)getFragmentManager().
                findFragmentById(R.id.listview_filtro_lista_lugares_practica)).llenarLista();
    }

    @Override
    public void limpiarListaDesdePeticion() {
        ((ListaConFiltro)getFragmentManager().
                findFragmentById(R.id.listview_filtro_lista_lugares_practica)).limpiarLista();
    }

    @Override
    public void eliminarItem(KeyValueItem aEliminar) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento) {
        //Se podria cambiar por manejar el evento segun las opciones que vengan en el intent
        //con un chain
        if(ConstantesUbicacion.FOR_RESULT_ACTION_LUGAR_PRACTICA.equals(getIntent().getAction())){
            LugarPractica lugarPractica = (LugarPractica)item.getValue();
            Bundle extra = new Bundle();
            extra.putString(ConstantesUbicacion.LUGAR_PRACTICA_INTENT,
                    lugarPractica.stringJson());
            Intent intent = getIntent();
            intent.putExtra(ConstantesUbicacion.FOR_RESULT_ACTION_LUGAR_PRACTICA,
                            extra);
            this.setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public ArrayList<KeyValueItem> generarAdapter(String identificadorFragmento) {
        return ConstructorArrObjSNS.producirArrayAdapterObjSNS(
                this.lugaresPractica,new String[]{"nombre"});
    }

    @Override
    public ArrayList<KeyValueItem> regenerarAdapter(String identificadorFragmento) {
        return ConstructorArrObjSNS.producirArrayAdapterObjSNS(
                this.lugaresPractica,new String[]{"nombre"});
    }

    @Override
    public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento) {
        throw new UnsupportedOperationException();
    }

    private void setUpObjetos(){
        this.lugaresPractica = new ArrayList<ObjectSNSDeportivo>();
    }
}

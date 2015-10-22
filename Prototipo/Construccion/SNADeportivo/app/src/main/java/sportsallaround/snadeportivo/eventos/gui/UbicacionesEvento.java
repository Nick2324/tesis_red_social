package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.snadeportivo.eventos.peticiones.CrearUbicacionEvento;
import sportsallaround.snadeportivo.eventos.peticiones.TraerUbicacionEvento;
import sportsallaround.snadeportivo.ubicaciones.ListaLugaresPractica;
import sportsallaround.snadeportivo.ubicaciones.NearbyLocationsActivity;
import sportsallaround.snadeportivo.ubicaciones.general.ConstantesUbicacion;
import sportsallaround.snadeportivo.ubicaciones.peticiones.TraerUbicaciones;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.PeticionObjectCallback;

public class UbicacionesEvento extends Activity implements PeticionObjectCallback{

    private MenuSNS menuSNS;
    private Ubicacion ubicacionEvento;

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        this.setUpObjetos(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_evento);
        setTitle(getResources().getString(R.string.titulo_gestion_eventos_lista));
        this.setUpInterfaz();
        this.setUpObjetos(getIntent());
    }

    private void setUpInterfaz(){
        if(ConstantesEvento.PARTICIPANTE_EVENTO.equals(getIntent().
                getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                getString(Constants.FUNCIONALIDAD)) ||
           ConstantesEvento.POSIBLE_PARTICIPANTE_EVENTO.equals(getIntent().
           getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
           getString(Constants.FUNCIONALIDAD))){
            Button cambiarUbicacion = (Button)findViewById(R.id.button_cambiar_ubicacion_evento);
              cambiarUbicacion.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        traerUbicacionEvento();
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
        Bundle extras = getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD);
        extras.putString(ConstantesEvento.UBICACION_MANEJADA,
                this.ubicacionEvento.toJSONObject());
        this.menuSNS.comportamiento(this, item.getItemId(),
                extras);
        return super.onOptionsItemSelected(item);
    }

    private void traerUbicacionEvento(){
        if(this.ubicacionEvento == null) {
            try {
                Evento evento = new ProductorFactoryEvento().
                        getEventosFactory(getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                getString(ConstantesEvento.SERVICIO_EVENTO)).crearEvento();
                evento.deserializarJson((JsonObject) JsonUtils.JsonStringToObject(
                        getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                getString(ConstantesEvento.EVENTO_MANEJADO)));
                new TraerUbicacionEvento(this, evento, getIntent().
                        getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                        getString(ConstantesEvento.SERVICIO_EVENTO)).ejecutarPeticion();
            } catch (ExcepcionJsonDeserializacion e) {
                this.alertError("traerUbicacionEvento");
                e.printStackTrace();
            } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                this.alertError("traerUbicacionEvento");
                productorFactoryExcepcion.printStackTrace();
            }
        }
    }

    private void setUpDatosActividad(){
        //CONVIERTE UBICACION
        if(this.ubicacionEvento != null) {
            if (this.ubicacionEvento.getPais() != null) {
                TextView pais = (TextView) findViewById(R.id.textview_pais_valor);
                pais.setText(this.ubicacionEvento.getPais().getNombre());
            }
            if (this.ubicacionEvento.getCiudad() != null) {
                TextView ciudad = (TextView) findViewById(R.id.textview_ciudad_valor);
                ciudad.setText(this.ubicacionEvento.getCiudad().getNombre());
            }
            if (this.ubicacionEvento.getLugar() != null) {
                TextView lugar = (TextView) findViewById(R.id.textview_lugar_valor);
                lugar.setText(this.ubicacionEvento.getLugar().getNombre());
            }
        }
    }

    private void setUpObjetos(Intent intent){
        if(intent.getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                getString(ConstantesEvento.UBICACION_MANEJADA) != null) {
            try {
                this.ubicacionEvento = new Ubicacion(new JSONObject(
                        intent.getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                        getString(ConstantesEvento.UBICACION_MANEJADA)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void verUbicacion(View v){
        Intent intent = new Intent(this, NearbyLocationsActivity.class);
        Bundle extras = getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD);
        extras.putString(ConstantesUbicacion.UBICACION_FOCO,this.ubicacionEvento.toJSONObject());
        intent.putExtra(Constants.DATOS_FUNCIONALIDAD,extras);
        startActivity(intent);
    }

    public void cambiarUbicacion(View v){
        Bundle extraFuncionalidad = getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD);
        Intent intent = new Intent(this, ListaLugaresPractica.class);
        intent.putExtra(Constants.DATOS_FUNCIONALIDAD,extraFuncionalidad);
        intent.setAction(ConstantesUbicacion.FOR_RESULT_ACTION_LUGAR_PRACTICA);
        startActivityForResult(intent, ConstantesUbicacion.FOR_RESULT_LUGAR_PRACTICA);
    }

    @Override
    public void getObjetoPeticion(Object objetoPeticion, String identificadorPeticion) {
        if(identificadorPeticion.equals(TraerUbicaciones.class.getSimpleName())) {
            try {
                Evento evento = new ProductorFactoryEvento().getEventosFactory(
                        getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                        getString(ConstantesEvento.SERVICIO_EVENTO)).crearEvento();
                evento.deserializarJson((JsonObject)JsonUtils.JsonStringToObject(
                        getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                        getString(ConstantesEvento.EVENTO_MANEJADO)));
                new CrearUbicacionEvento(
                        this,
                        (Ubicacion)objetoPeticion,
                        evento,
                        getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                                getString(ConstantesEvento.SERVICIO_EVENTO)).ejecutarPeticion();
            } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                this.alertError(null);
                productorFactoryExcepcion.printStackTrace();
            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                this.alertError(null);
                excepcionJsonDeserializacion.printStackTrace();
            }
        }else if(identificadorPeticion.equals(TraerUbicacionEvento.class.getSimpleName())){
            Ubicacion ubicacionTemporal = (Ubicacion)objetoPeticion;
            if(ubicacionTemporal != null && ubicacionTemporal.getId() != null) {
                new TraerUbicaciones(
                        this,
                        ubicacionTemporal,
                        ConstantesEvento.CALLBACK_TRAE_UBICACION_EVENTO).ejecutarPeticion();
            }
        }else if(identificadorPeticion.equals(ConstantesEvento.CALLBACK_TRAE_UBICACION_EVENTO) ||
                 CrearUbicacionEvento.class.getSimpleName().equals(identificadorPeticion)){
            this.ubicacionEvento = (Ubicacion)objetoPeticion;
            setUpDatosActividad();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ConstantesUbicacion.FOR_RESULT_LUGAR_PRACTICA){
            if(resultCode == Activity.RESULT_OK) {
                this.ubicacionEvento = new Ubicacion();
                try {
                    LugarPractica lugarObtenido = new LugarPractica(new JSONObject(
                            data.getBundleExtra(ConstantesUbicacion.FOR_RESULT_ACTION_LUGAR_PRACTICA).
                            getString(ConstantesUbicacion.LUGAR_PRACTICA_INTENT)));
                    this.ubicacionEvento.setLugar(lugarObtenido);
                    new TraerUbicaciones(this,this.ubicacionEvento).ejecutarPeticion();
                } catch (JSONException e) {
                    this.alertError(null);
                    e.printStackTrace();
                }
            }
        }
    }

    private void alertError(String metodo){
        if(metodo != null){
            if(metodo.equals(ConstantesEvento.ERROR_TRAER_UBICACION_EVENTO)) {
                new AlertDialog.Builder(this).
                        setTitle(this.getResources().
                                getString(R.string.alert_traer_ubicacion_evento_tit)).
                        setMessage(this.getResources().
                                getString(R.string.alert_traer_ubicacion_evento_msn_err)).
                        setNeutralButton(this.getResources().getString(R.string.BOTON_NEUTRAL),
                                null).
                        create().show();
            }
        }else {
            new AlertDialog.Builder(this).
                    setTitle(getResources().
                            getString(R.string.alert_crea_ubicacion_evento_tit)).
                    setMessage(getString(R.string.alert_crea_ubicacion_evento_err_msn)).
                    setNeutralButton(getString(R.string.BOTON_NEUTRAL), null).
                    create().show();
        }
    }

}

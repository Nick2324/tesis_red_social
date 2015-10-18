package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
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

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.snadeportivo.eventos.peticiones.TraerUbicacionEvento;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.PeticionObjectCallback;

public class UbicacionesEvento extends Activity implements PeticionObjectCallback{

    private MenuSNS menuSNS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones_evento);
        setTitle(getResources().getString(R.string.titulo_gestion_eventos_lista));
        this.setUpInterfaz();
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
        if(getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
           getString(ConstantesEvento.UBICACION_MANEJADA) != null) {
            this.setUpDatosActividad(getIntent().
                    getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                    getString(ConstantesEvento.UBICACION_MANEJADA));
        }else{
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
                new AlertDialog.Builder(this).
                        setTitle(this.getResources().
                                getString(R.string.alert_traer_ubicacion_evento_tit)).
                        setMessage(this.getResources().
                                getString(R.string.alert_traer_ubicacion_evento_msn_err)).
                        setNeutralButton(this.getResources().getString(R.string.BOTON_NEUTRAL),
                                null).
                        create().show();
                e.printStackTrace();
            } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                productorFactoryExcepcion.printStackTrace();
            }
        }
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
        extras.putString(ConstantesEvento.UBICACION_MANEJADA, null);
        this.menuSNS.comportamiento(this, item.getItemId(),
                extras);
        return super.onOptionsItemSelected(item);
    }

    private void setUpDatosActividad(String ubicacion){
        //CONVIERTE UBICACION
        TextView pais = (TextView)findViewById(R.id.textview_pais_valor);
        pais.setText(null);
        TextView ciudad = (TextView)findViewById(R.id.textview_ciudad_valor);
        ciudad.setText(null);
        TextView lugar = (TextView)findViewById(R.id.textview_lugar_valor);
        lugar.setText(null);
    }

    public void verUbicacion(){

    }

    public void cambiarUbicacion(){

    }

    @Override
    public void getObjetoPeticion(Object objetoPeticion) {
        if(objetoPeticion instanceof String) {
            setUpDatosActividad((String)objetoPeticion);
        }
    }
}

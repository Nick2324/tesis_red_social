package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;

public class UbicacionesEvento extends Activity {

    private MenuSNS menuSNS;

    private class TraerUbicacionEvento extends Peticion {

        private Context contexto;
        private Evento evento;
        private String tipoEvento;

        private TraerUbicacionEvento(Context contexto, Evento evento, String tipoEvento) {
            this.contexto = contexto;
            this.evento = evento;
            this.tipoEvento = tipoEvento;
        }

        @Override
        public void calcularMetodo() {
            super.metodo = "GET";
        }

        @Override
        public void calcularServicio() {
            super.servicio = Constants.SERVICES_PATH_EVENTOS +
                    this.tipoEvento + "/" +
                    this.evento.getId() + "/" +
                    Constants.SERVICES_PATH_UBICACIONES;
        }

        @Override
        public void calcularParams() {
            try {
                super.params = new JSONObject();
                //PONIENDO EVENTO
                JSONArray arrayEventos = new JSONArray();
                arrayEventos.put(new JSONObject(this.evento.stringJson()));
                JSONObject parametrosEvento = new JSONObject();
                parametrosEvento.put(this.evento.getClass().getSimpleName(),
                        arrayEventos);
                super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                        parametrosEvento);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void doInBackground() {
            super.setPeticionBody(true);
        }

        @Override
        public void onPostExcecute(String resultadoPeticion) {
            if(resultadoPeticion != null){
                try {
                    JSONObject resultado = new JSONObject(resultadoPeticion);
                    if(resultado.getString("caracterAceptacion") != null &&
                       resultado.getString("caracterAceptacion").equals("200")){
                        setUpDatosActividad(resultado.getString("datosExtra"));
                    }else{
                        this.alertError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.alertError();
                }
            }
        }

        private void alertError(){
            new AlertDialog.Builder(this.contexto).
                    setTitle(this.contexto.getResources().
                            getString(R.string.alert_traer_ubicacion_evento_tit)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.alert_traer_ubicacion_evento_msn_err)).
                    setNeutralButton(this.contexto.getResources().getString(R.string.BOTON_NEUTRAL),
                            null).
                    create().show();
        }

    }

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

}

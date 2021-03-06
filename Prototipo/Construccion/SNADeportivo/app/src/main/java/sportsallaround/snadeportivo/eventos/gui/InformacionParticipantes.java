package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sna_deportivo.pojo.entidadesEstaticas.EntidadesGenerales;
import com.sna_deportivo.pojo.entidadesEstaticas.FactoryGenero;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.SpinnerDesdeBD;
import sportsallaround.utils.gui.TituloActividad;

public class InformacionParticipantes extends Activity
        implements TituloActividad.InitializerTituloActividad,
        SpinnerDesdeBD.InitializerSpinnerBD{

    private Evento evento;
    private Genero genero;
    private MenuSNS menuSNS;

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        this.setUpObjetos(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_participantes);
        setTitle(getResources().getString(R.string.titulo_gestion_eventos_lista));
        this.setUpObjetos(getIntent());
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setUpViewsPropActividad();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.setUpDatosActividad();
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
        if(this.volcarDatosObjeto()) {
            Bundle extras = getIntent().getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD);
            extras.putString(ConstantesEvento.EVENTO_MANEJADO, this.evento.stringJson());
            extras.putString(ConstantesEvento.GENERO_MANEJADO, this.genero.stringJson());
            this.menuSNS.comportamiento(this, item.getItemId(),
                    extras);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpViewsPropActividad(){
        if(getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD) != null){
            String funcionalidad = getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                    getString(Constants.FUNCIONALIDAD);
            if(funcionalidad.equals(ConstantesEvento.POSIBLE_PARTICIPANTE_EVENTO)){
                ((EditText)findViewById(R.id.numero_participantes_evento_info_general)).setClickable(false);
                ((EditText)findViewById(R.id.rango_minimo_edad_evento_info_general)).setEnabled(false);
                ((EditText)findViewById(R.id.rango_maximo_edad_evento_info_general)).setEnabled(false);
                ((SpinnerDesdeBD)getFragmentManager().findFragmentById(R.id.spinner_genero)).
                        inactivarSpinner();
            }
        }
    }

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        if(tagFragmento.equals(getResources().getString(R.string.caracteristicas_participantes)))
            return getResources().getString(R.string.caracteristicas_participantes);
        else if(tagFragmento.equals(getResources().getString(R.string.general_participantes)))
            return getResources().getString(R.string.general_participantes);
        return null;
    }

    @Override
    public FactoryObjectSNSDeportivo getFactoryObjetosSNS() {
        return new FactoryGenero();
    }

    @Override
    public String getServicio() {
        return Constants.SERVICES_PATH_GENERALES +
               EntidadesGenerales.GENERO.getServicio();
    }

    @Override
    public String getMetodo() {
        return "GET";
    }

    @Override
    public JSONObject getParam() {
        return new JSONObject();
    }

    @Override
    public String getAtributoMostradoSpinner() {
        return "nombre";
    }

    @Override
    public String getTituloSpinner() {
        return getResources().getString(R.string.spinner_genero_evento);
    }

    @Override
    public void onPostExcecute() {
        //GENERO
        if(this.genero.getNombre() != null){
            ((SpinnerDesdeBD)getFragmentManager().findFragmentById(
                    R.id.spinner_genero)).setSeleccionado(this.genero);
        }
    }

    @Override
    public void onItemSelectedSpinnerBD(KeyValueItem seleccionado, String tagFragmento) {
        this.genero = (Genero)seleccionado.getValue();
    }

    @Override
    public void onNothingSelectedSpinnerBD(String tagFragmento) {}

    public boolean volcarDatosObjeto(){
        EditText texto = null;
        try {
            texto = (EditText) findViewById(R.id.numero_participantes_evento_info_general);
            if(texto.getText() != null && texto.getText().length() != 0) {
                evento.setNumMaxParticipantes(Integer.parseInt(texto.getText().toString()));
            }else{
                evento.setNumMaxParticipantes(null);
            }
            texto = (EditText) findViewById(
                    R.id.rango_maximo_edad_evento_info_general);
            if(texto.getText() != null && texto.getText().length() != 0) {
                evento.setRangoMaxEdad(Integer.parseInt(texto.getText().toString()));
            }else{
                evento.setRangoMaxEdad(null);
            }
            texto = (EditText) findViewById(
                    R.id.rango_minimo_edad_evento_info_general);
            if(texto.getText() != null  && texto.getText().length() != 0) {
                evento.setRangoMinEdad(Integer.parseInt(texto.getText().toString()));
            }else{
                evento.setRangoMinEdad(null);
            }
        }catch (NumberFormatException e){
            new AlertDialog.Builder(this).setTitle(getResources().
                    getString(R.string.alert_menu_evento_error_titulo)).
                    setMessage(getResources().getString(R.string.alert_menu_evento_error_mensaje)).
                    setNeutralButton(getResources().getString(R.string.BOTON_NEUTRAL),
                            null).create().show();
            return false;
        }

        return true;
    }

    public void setUpDatosActividad() {
        //DATOS DE EVENTO
        if(this.evento != null) {
            if (this.evento.getNumMaxParticipantes() != null) {
                ((EditText) findViewById(R.id.numero_participantes_evento_info_general)).setText(
                        this.evento.getNumMaxParticipantes() + ""
                );
            }
            if (this.evento.getRangoMaxEdad() != null) {
                ((EditText) findViewById(R.id.rango_maximo_edad_evento_info_general)).setText(
                        this.evento.getRangoMaxEdad() + ""
                );
            }
            if (this.evento.getRangoMinEdad() != null) {
                ((EditText) findViewById(R.id.rango_minimo_edad_evento_info_general)).setText(
                        this.evento.getRangoMinEdad() + ""
                );
            }
        }

    }

    public void setUpObjetos(Intent intent){
        try {
            this.evento = (Evento)new ProductorFactoryEvento().producirFacObjetoSNS(intent.getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD).
                    getString(ConstantesEvento.TIPO_EVENTO)).getObjetoSNS();
            if(intent.getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                    ConstantesEvento.EVENTO_MANEJADO) != null){
                try {
                    this.evento.deserializarJson(JsonUtils.JsonStringToObject(intent.getExtras().
                            getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                            ConstantesEvento.EVENTO_MANEJADO)));
                } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                    excepcionJsonDeserializacion.printStackTrace();
                }
            }
        } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
            productorFactoryExcepcion.printStackTrace();
        }
        this.genero = new Genero();
        if(intent.getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                ConstantesEvento.GENERO_MANEJADO) != null){
            try {
                this.genero.deserializarJson(JsonUtils.JsonStringToObject(intent.getExtras().
                        getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                        ConstantesEvento.GENERO_MANEJADO)));
            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                excepcionJsonDeserializacion.printStackTrace();
            }
        }
    }

}

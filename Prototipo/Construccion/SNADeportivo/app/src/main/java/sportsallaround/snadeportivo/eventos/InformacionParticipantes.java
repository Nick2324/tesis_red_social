package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.FactoryDeporte;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactory;
import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.SpinnerDesdeBD;
import sportsallaround.utils.gui.TituloActividad;

public class InformacionParticipantes extends Activity
        implements TituloActividad.InitializerTituloActividad,
        SpinnerDesdeBD.InitializerSpinnerBD{

    private Evento evento;
    private MenuEventos menuEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_participantes);
        //CREANDO EVENTO CON TODOS LOS DATOS NECESARIOS
        this.evento = new ProductorFactory().getEventosFactory(getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).
                getString(ConstantesEvento.TIPO_EVENTO)).
                crearEvento();
        if(getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                ConstantesEvento.EVENTO_MANEJADO) != null){
            try {
                this.evento.deserializarJson(JsonUtils.JsonStringToObject(getIntent().getExtras().
                        getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                        ConstantesEvento.EVENTO_MANEJADO)));
            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                excepcionJsonDeserializacion.printStackTrace();
            }
        }
    }

    @Override
    public void onStart(){
        super.onStart();
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
        this.menuEventos = new ProductorMenuEventos().proveerMenuEventos(tipoMenu);
        getMenuInflater().inflate(menuEventos.getMenuId(), menu);
        this.menuEventos.esconderSegunFuncionalidadRol(this, funcionalidad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(this.volcarDatosObjeto()) {
            Bundle extras = getIntent().getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD);
            extras.putString(ConstantesEvento.EVENTO_MANEJADO, this.evento.stringJson());
            this.menuEventos.comportamiento(this, item.getItemId(),
                    extras);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        if(tagFragmento.equals(getResources().getString(R.string.caracteristicas_participantes)))
            return getResources().getString(R.string.caracteristicas_participantes);
        else if(tagFragmento.equals(getResources().getString(R.string.general_participantes)))
            return getResources().getString(R.string.general_participantes);
        else if(tagFragmento.equals(getResources().getString(R.string.titulo_gestion_eventos_lista)))
             return getResources().getString(R.string.titulo_gestion_eventos_lista);
        return null;
    }

    @Override
    public FactoryObjectSNSDeportivo getFactoryObjetosSNS() {
        return new FactoryDeporte();
    }

    @Override
    public String getServicio() {
        return Constants.SERVICES_PATH_DEPORTES;
    }

    @Override
    public String getMetodo() {
        return "GET";
    }

    @Override
    public JSONObject getParam() {
        return null;
    }

    @Override
    public String getAtributoMostradoSpinner() {
        return "nombre";
    }

    @Override
    public String getTituloSpinner() {
        return "Genero";
    }

    @Override
    public void onPostExcecute() {}

    @Override
    public void onItemSelectedSpinnerBD(KeyValueItem seleccionado, String tagFragmento) {
        Log.d("Nick:Deporte", "Se selecciona deporte " + ((Deporte) seleccionado.getValue()).toString());
    }

    @Override
    public void onNothingSelectedSpinnerBD(String tagFragmento) {}

    public boolean volcarDatosObjeto(){
        EditText texto = null;
        try {
            texto = (EditText) findViewById(R.id.numero_participantes_evento_info_general);
            if(texto.getText() != null)
                evento.setNumMaxParticipantes(Integer.parseInt(texto.toString()));
            texto = (EditText) findViewById(
                    R.id.rango_maximo_edad_evento_info_general);
            if(texto.getText() != null)
                evento.setRangoMaxEdad(Integer.parseInt(texto.toString()));
            texto = (EditText) findViewById(
                    R.id.rango_minimo_edad_evento_info_general);
            if(texto.getText() != null)
                evento.setRangoMinEdad(Integer.parseInt(texto.toString()));
        }catch (NumberFormatException e){
            new AlertDialog.Builder(this).setTitle("Ejecución de opcion").
                    setMessage("Error al ejecutar la opción. Llene los campos con el formato propio").
                    setNegativeButton("Ok",null).create().show();
            return false;
        }
        return true;
    }

    public void setUpDatosActividad() {
        if(this.evento.getNumMaxParticipantes() != null)
            ((EditText)findViewById(R.id.numero_participantes_evento_info_general)).setText(
                    this.evento.getNumMaxParticipantes()+""
            );
        if(this.evento.getRangoMaxEdad() != null)
            ((EditText)findViewById(R.id.rango_maximo_edad_evento_info_general)).setText(
                    this.evento.getRangoMaxEdad()+""
            );
        if(this.evento.getRangoMinEdad() != null)
            ((EditText)findViewById(R.id.rango_minimo_edad_evento_info_general)).setText(
                    this.evento.getRangoMinEdad()+""
            );
    }
}

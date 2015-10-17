package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.FactoryDeporte;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONObject;

import java.util.Calendar;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.gui.DatePickerFragment;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.OnDatePickedListener;
import sportsallaround.utils.gui.SpinnerDesdeBD;
import sportsallaround.utils.gui.TimePickerFragment;

public class InformacionGeneralEvento extends Activity
        implements OnDatePickedListener,TimePickerFragment.OnTimePickedListener,
                   SpinnerDesdeBD.InitializerSpinnerBD{

    private Evento evento;
    private Deporte deporte;
    private MenuSNS menuSNS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_general_evento);
        setTitle(getResources().getString(R.string.title_activity_informacion_general_evento));
        this.setUpListeners();
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setUpViewsPropActividad();

    }

    @Override
    public void onResume(){
        super.onResume();
        this.setUpObjetos();
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
        this.volcarDatosObjetos();
        Bundle extras = getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD);
        extras.putString(ConstantesEvento.EVENTO_MANEJADO, this.evento.stringJson());
        extras.putString(ConstantesEvento.DEPORTE_MANEJADO, this.deporte.stringJson());
        this.menuSNS.comportamiento(this, item.getItemId(), extras);
        return super.onOptionsItemSelected(item);
    }

    public void setUpViewsPropActividad(){
        if(getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD) != null){
            String funcionalidad = getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD).
                    getString(Constants.FUNCIONALIDAD);
            if(funcionalidad.equals(ConstantesEvento.POSIBLE_PARTICIPANTE_EVENTO)){
                ((EditText)findViewById(R.id.nombre_evento_info_general)).setEnabled(false);
                ((EditText)findViewById(R.id.descripcion_evento_info_general)).setEnabled(false);
                ((EditText)findViewById(R.id.fecha_inicio_evento_info_general)).setEnabled(false);
                ((EditText)findViewById(R.id.fecha_final_evento_info_general)).setEnabled(false);
                ((EditText)findViewById(R.id.hora_inicio_evento_info_general)).setEnabled(false);
                ((EditText)findViewById(R.id.hora_final_evento_info_general)).setEnabled(false);
                ((EditText)findViewById(R.id.fecha_creacion_evento)).setEnabled(false);
                ((SpinnerDesdeBD)getFragmentManager().findFragmentById(R.id.fragment_tipo_deporte)).
                        inactivarSpinner();
            }
            if(funcionalidad.equals(ConstantesEvento.CREAR_EVENTO)){
                ((TextView)findViewById(R.id.textview_fecha_creacion_eve)).setVisibility(View.GONE);
                ((EditText)findViewById(R.id.fecha_creacion_evento)).setVisibility(View.GONE);
            }
            if(funcionalidad.equals(ConstantesEvento.ACTUALIZAR_EVENTO)){
                ((TextView)findViewById(R.id.textview_fecha_creacion_eve)).setVisibility(View.VISIBLE);
                ((EditText)findViewById(R.id.fecha_creacion_evento)).setVisibility(View.VISIBLE);
            }
        }
    }

    public void setUpListeners(){

        final Context contexto = this;

        //FECHA Y TIEMPO DE INICIO
        EditText dateInicio = (EditText)findViewById(R.id.fecha_inicio_evento_info_general);
        dateInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerFragment fecha = new DatePickerFragment();
                    fecha.show(getFragmentManager(), "date_inicio_evento");

                }
            }
        });

        EditText timeInicio = (EditText)findViewById(R.id.hora_inicio_evento_info_general);
        timeInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerFragment tiempo = new TimePickerFragment();
                    tiempo.show(getFragmentManager(), "time_inicio_evento");
                }
            }
        });

        //FECHA Y TIEMPO FINAL
        EditText dateFinal = (EditText)findViewById(R.id.fecha_final_evento_info_general);
        dateFinal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerFragment fecha = new DatePickerFragment();
                    fecha.show(getFragmentManager(), "date_final_evento");
                }
            }
        });

        EditText timeFinal = (EditText)findViewById(R.id.hora_final_evento_info_general);
        timeFinal.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerFragment tiempo = new TimePickerFragment();
                    tiempo.show(getFragmentManager(), "time_final_evento");
                }
            }
        });
    }

    @Override
    public void onTimePicked(String time) {
        EditText timeET = null;

        if(getWindow().getCurrentFocus().getId() == R.id.hora_inicio_evento_info_general) {
            timeET = (EditText) findViewById(R.id.hora_inicio_evento_info_general);
        }else if(getWindow().getCurrentFocus().getId() == R.id.hora_final_evento_info_general) {
            timeET = (EditText) findViewById(R.id.hora_final_evento_info_general);
        }
        if(timeET != null)
            timeET.setText(time);
    }

    @Override
    public void onDatePicked(String selectedDate) {
        EditText date = null;

        if(getWindow().getCurrentFocus().getId() == R.id.fecha_inicio_evento_info_general) {
            date = (EditText) findViewById(R.id.fecha_inicio_evento_info_general);
        }else if(getWindow().getCurrentFocus().getId() == R.id.fecha_final_evento_info_general){
            date = (EditText) findViewById(R.id.fecha_final_evento_info_general);
        }
        if(date != null)
            date.setText(selectedDate);
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
    public String getTituloSpinner() {
        return getResources().getString(R.string.spinner_deporte_evento);
    }

    @Override
    public void onItemSelectedSpinnerBD(KeyValueItem seleccionado, String tagFragmento) {
        //RESUELVE PROBLEMA
        this.deporte = (Deporte) seleccionado.getValue();
    }

    @Override
    public void onNothingSelectedSpinnerBD(String tagFragmento) {}

    @Override
    public String getAtributoMostradoSpinner(){
        return "nombre";
    }

    public void volcarDatosObjetos(){
        //EVENTO
        Editable editable = ((EditText) (findViewById(R.id.nombre_evento_info_general))).getText();
        final Calendar c = Calendar.getInstance();
        if(editable.length() > 0) {
            evento.setNombre(editable.toString());
        }else{
            evento.setNombre(null);
        }
        editable = ((EditText) (findViewById(R.id.descripcion_evento_info_general))).getText();
        if(editable.length() > 0) {
            evento.setDescripcion(editable.toString());
        }else{
            evento.setDescripcion(null);
        }
        editable = ((EditText) (findViewById(R.id.fecha_inicio_evento_info_general))).getText();
        if(editable.length() > 0) {
            evento.setFechaInicio(editable.toString());
        }else{
            evento.setFechaInicio(null);
        }
        editable = ((EditText) (findViewById(R.id.fecha_final_evento_info_general))).getText();
        if(editable.length() > 0) {
            evento.setFechaFinal(editable.toString());
        }else{
            evento.setFechaFinal(null);
        }
        editable = ((EditText) (findViewById(R.id.hora_inicio_evento_info_general))).getText();
        if(editable.length() > 0) {
            evento.setHoraInicio(editable.toString());
        }else{
            evento.setHoraInicio(null);
        }
        editable = ((EditText) (findViewById(R.id.hora_final_evento_info_general))).getText();
        if(editable.length() > 0) {
            evento.setHoraFinal(editable.toString());
        }else{
            evento.setHoraFinal(null);
        }
        if(getIntent().getExtras().getBundle(Constants.DATOS_FUNCIONALIDAD).
                getString(Constants.FUNCIONALIDAD).equals(ConstantesEvento.CREAR_EVENTO)) {
            evento.setFechaCreacion(null);
            evento.setActivo(null);
            try {
                if(!evento.equals(new ProductorFactoryEvento().
                                 producirFacObjetoSNS(getIntent().getExtras().getBundle(
                                         Constants.DATOS_FUNCIONALIDAD).
                                         getString(ConstantesEvento.TIPO_EVENTO)).getObjetoSNS().
                                setNullObject())) {
                    evento.setFechaCreacion(String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "/" +
                            String.format("%02d", c.get(Calendar.MONTH)) + "/" +
                            String.format("%04d", c.get(Calendar.YEAR)));
                    evento.setActivo(true);
                }else{
                    evento.setFechaCreacion(null);
                    evento.setActivo(null);
                }
            } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                productorFactoryExcepcion.printStackTrace();
            }
        }else{
            evento.setFechaCreacion(((EditText) findViewById(R.id.fecha_creacion_evento)).
                    getText().toString());
        }

    }

    public void setUpDatosActividad(){
        //EVENTO
        if(evento.getNombre() != null){
            ((EditText) (findViewById(R.id.nombre_evento_info_general))).setText(evento.getNombre());
        }
        if(evento.getDescripcion() != null){
            ((EditText) (findViewById(R.id.descripcion_evento_info_general))).
                    setText(evento.getDescripcion());
        }
        if(evento.getFechaInicio() != null){
            ((EditText) (findViewById(R.id.fecha_inicio_evento_info_general))).
                    setText(evento.getFechaInicio());
        }
        if(evento.getFechaFinal() != null){
            ((EditText) (findViewById(R.id.fecha_final_evento_info_general))).setText(
                    evento.getFechaFinal());
        }
        if(evento.getHoraInicio() != null){
            ((EditText) (findViewById(R.id.hora_inicio_evento_info_general))).
                    setText(evento.getHoraInicio());
        }
        if(evento.getHoraFinal() != null){
            ((EditText) (findViewById(R.id.hora_final_evento_info_general))).
                    setText(evento.getHoraFinal());
        }
        if(evento.getFechaCreacion() != null){
            ((EditText) (findViewById(R.id.fecha_creacion_evento))).setText(
                    evento.getFechaCreacion());
        }
        //!*!*!*!*! PENDIENTE
        //evento.setActivo(true);

    }

    public void setUpObjetos(){
        //EVENTO
        if(getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                ConstantesEvento.EVENTO_MANEJADO) != null){
            try {
                this.evento = (Evento)new ProductorFactoryEvento().producirFacObjetoSNS(getIntent().getExtras().
                        getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                        ConstantesEvento.TIPO_EVENTO)).getObjetoSNS();
                this.evento.deserializarJson(JsonUtils.JsonStringToObject(getIntent().getExtras().
                        getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                        ConstantesEvento.EVENTO_MANEJADO)));
            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                excepcionJsonDeserializacion.printStackTrace();
            } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                productorFactoryExcepcion.printStackTrace();
            }
        }

        //DEPORTE
        this.deporte = new Deporte();
        if(getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                ConstantesEvento.DEPORTE_MANEJADO) != null){
            try {
                this.deporte.deserializarJson(JsonUtils.JsonStringToObject(getIntent().getExtras().
                        getBundle(Constants.DATOS_FUNCIONALIDAD).getString(
                        ConstantesEvento.DEPORTE_MANEJADO)));
            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                excepcionJsonDeserializacion.printStackTrace();
            }
        }
    }

    @Override
    public void onPostExcecute() {
        //DEPORTE
        if(this.deporte.getNombre() != null){
            ((SpinnerDesdeBD)getFragmentManager().
                    findFragmentById(R.id.fragment_tipo_deporte)).
                    setSeleccionado(this.deporte);
        }
    }

}

package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.FactoryDeporte;
import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.gui.DatePickerFragment;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.OnDatePickedListener;
import sportsallaround.utils.ServiceUtils;
import sportsallaround.utils.gui.SpinnerDesdeBD;
import sportsallaround.utils.gui.TimePickerFragment;
import sportsallaround.utils.gui.TituloActividad;

public class InformacionGeneralEvento extends Activity
        implements OnDatePickedListener,TimePickerFragment.OnTimePickedListener,SpinnerDesdeBD.InitializerSpinnerBD,
        TituloActividad.InitializerTituloActividad {

    private class CrearEvento extends AsyncTask <JSONObject,Object,String>{

        private Context contexto;

        public CrearEvento(Context contexto){
            this.contexto = contexto;
        }

        @Override
        protected String doInBackground(JSONObject... params) {
            try{
                return ServiceUtils.invokeService(params[0],
                                                  Constants.SERVICES_PATH_EVENTOS +
                                                          //CAMBIAR POR EL QUE VENGA DE LA TAREA ANTERIOR. HAY QUE MIGRAR A FRAGMENTO
                                                  "practicas_libres/",
                                                  "POST");
            }catch(Exception e){
                Log.e("Nick:Error",e.getMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result){
            if(result != null && result.length() != 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this.contexto);
                builder.setMessage("Agregado evento con exito, verificar en Neo4j: " + result).
                        setNegativeButton("OK",null);
                builder.create().show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_general_evento);
        this.setUpListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_informacion_general_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUpListeners(){

        final Context contexto = this;

        EditText dateInicio = (EditText)findViewById(R.id.fecha_inicio_evento_info_general);
        dateInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DatePickerFragment fecha = new DatePickerFragment();
                    fecha.show(getFragmentManager(), "date_inicio_evento");
                }
            }
        });

        EditText timeInicio = (EditText)findViewById(R.id.hora_inicio_evento_info_general);
        timeInicio.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    TimePickerFragment tiempo = new TimePickerFragment();
                    tiempo.show(getFragmentManager(),"time_inicio_evento");
                }
            }
        });

        EditText dateFinal = (EditText)findViewById(R.id.fecha_final_evento_info_general);
        dateFinal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DatePickerFragment fecha = new DatePickerFragment();
                    fecha.show(getFragmentManager(),"date_final_evento");
                }
            }
        });

        EditText timeFinal = (EditText)findViewById(R.id.hora_final_evento_info_general);
        timeFinal.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    TimePickerFragment tiempo = new TimePickerFragment();
                    tiempo.show(getFragmentManager(),"time_final_evento");
                }
            }
        });

        Button crearEvento = (Button)findViewById(R.id.crear_evento_boton);
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject parametros = new JSONObject();
                try {
                    final Calendar c = Calendar.getInstance();
                    parametros.put("nombre",((EditText)(findViewById(R.id.nombre_evento_info_general))).getText().toString());
                    parametros.put("descripcion",((EditText)(findViewById(R.id.descripcion_evento_info_general))).getText().toString());
                    parametros.put("fechaCreacion",String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "/" +
                                                   String.format("%02d", c.get(Calendar.MONTH)) + "/" +
                                                   String.format("%04d", c.get(Calendar.YEAR)));
                    parametros.put("fechaInicio",((EditText)(findViewById(R.id.fecha_inicio_evento_info_general))).getText().toString());
                    parametros.put("fechaFinal",((EditText)(findViewById(R.id.fecha_final_evento_info_general))).getText().toString());
                    parametros.put("horaInicio",((EditText)(findViewById(R.id.hora_inicio_evento_info_general))).getText().toString());
                    parametros.put("horaFinal",((EditText)(findViewById(R.id.hora_final_evento_info_general))).getText().toString());
                    parametros.put("numMaxParticipantes",((EditText)(findViewById(R.id.numero_participantes_evento_info_general))).getText().toString());
                    parametros.put("rangoMaxEdad",((EditText)(findViewById(R.id.rango_maximo_edad_evento_info_general))).getText().toString());
                    parametros.put("rangoMinEdad",((EditText)(findViewById(R.id.rango_minimo_edad_evento_info_general))).getText().toString());
                    parametros.put("activo",true);
                    new CrearEvento(contexto).execute(parametros);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onTimePicked(String time) {
        EditText timeET = null;

        Log.d("Nick:id", getWindow().getCurrentFocus().getId() + " Time");

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

        Log.d("Nick:id",getWindow().getCurrentFocus().getId()+" Date");

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
        return "Deporte";
    }

    @Override
    public void onItemSelectedSpinnerBD(KeyValueItem seleccionado) {

        Log.d("Nick:Deporte", "Se selecciona deporte " + ((Deporte) seleccionado.getValue()).toString());
    }

    @Override
    public void onNothingSelectedSpinnerBD() {}

    @Override
    public String getAtributoMostradoSpinner(){
        return "nombre";
    }

    @Override
    public int getIdTituloActividad() {
        return R.string.title_activity_informacion_general_evento;
    }

}

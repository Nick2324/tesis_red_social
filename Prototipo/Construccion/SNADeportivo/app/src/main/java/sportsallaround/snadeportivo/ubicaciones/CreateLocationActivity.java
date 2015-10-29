package sportsallaround.snadeportivo.ubicaciones;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.deportes.pojos.Deporte;
import sportsallaround.snadeportivo.deportes.pojos.DeportePracticadoUbicacion;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ciudad;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.ubicaciones.pojos.Pais;
import sportsallaround.snadeportivo.ubicaciones.pojos.TipoUbicacion;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.snadeportivo.usuarios.tasks.RetreiveSports;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainSports;
import sportsallaround.utils.generales.ServiceUtils;

public class CreateLocationActivity extends AppCompatActivity implements ObtainSports, LocationListener {

    private EditText nombreLugar;
    private EditText observacionesLugar;
    private Spinner tipoLugar;
    private LinearLayout deportesPracticadosLugar;
    private Deporte[] deportesPosibles;
    private ArrayList<Deporte> deportes;
    private float latitud, longitud;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_location);

        setTitle(getResources().getString(R.string.title_activity_crear_ubicacion));

        nombreLugar = (EditText)findViewById(R.id.new_location_name);
        observacionesLugar = (EditText)findViewById(R.id.new_location_observations);
        tipoLugar = (Spinner) findViewById(R.id.new_location_type);
        deportesPracticadosLugar = (LinearLayout) findViewById(R.id.new_location_practiced_sports);

        ArrayList<String> tiposUbicacion = new ArrayList<>();

        for(TipoUbicacion ubicacion : Constants.TIPOS_UBICACION ){
            tiposUbicacion.add(ubicacion.getNombre());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_black_item,
                tiposUbicacion);
        tipoLugar.setAdapter(spinnerArrayAdapter);

        try {
            new RetreiveSports(this).execute((Void) null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            Toast.makeText(this,"Provider " + provider + " has been selected.",Toast.LENGTH_LONG).show();;
            onLocationChanged(location);
        } else {
            latitud = (float) 100.0;
            longitud = (float) 100.0;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_location, menu);
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

    @Override
    public void setSports(Deporte[] deportesExistentes) {
        this.deportesPosibles = deportesExistentes;
        if (deportesPosibles != null) {
            this.deportesPracticadosLugar.removeAllViews();
            CheckBox checkDeporte;
            for(Deporte deporte : deportesPosibles){
                checkDeporte = new CheckBox(getApplicationContext());
                checkDeporte.setTextColor(Color.BLACK);
                checkDeporte.setText(deporte.getNombre());
                checkDeporte.setTag(deporte);
                deportesPracticadosLugar.addView(checkDeporte,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    public Deporte[] getSports() {
        return this.deportesPosibles;
    }

    public boolean validarDatosIngresados(){
        boolean retorno = true;
        if(nombreLugar.getText().toString().equals(""))
            retorno = false;

        boolean deporteRequerido = true;

        for(TipoUbicacion ubicacion : Constants.TIPOS_UBICACION ){
            if(ubicacion.getNombre().equals(tipoLugar.getSelectedItem().toString())) {
                deporteRequerido = ubicacion.isRequereDeporte();
                break;
            }
        }


        CheckBox check;
        deportes = new ArrayList<>();
        for (int i = 0; i < deportesPracticadosLugar.getChildCount(); i++) {
            check = (CheckBox) deportesPracticadosLugar.getChildAt(i);
            if (check.isChecked()) {
                deportes.add((Deporte) check.getTag());
            }
        }

        if (deportes.size() == 0 && deporteRequerido) {
            retorno = false;
        }

        return retorno;
    }

    public void crearLugar(View view) {
        if(latitud == 100.0 || longitud == 100.0) {
            Toast.makeText(getApplicationContext(), "No hemos podido obtener su ubicacion acutal. Por favor vuelva a intentarlo mas tarde.", Toast.LENGTH_LONG).show();
        }
        else if (validarDatosIngresados()){
            Pais pais = new Pais();
            pais.setId(1);
            Ciudad ciudad = new Ciudad();
            ciudad.setId(1);
            LugarPractica lugar = null;
            lugar = new LugarPractica();
            lugar.setNombre(nombreLugar.getText().toString());
            lugar.setLatitud(latitud);
            lugar.setLongitud(longitud);
            Ubicacion nuevaUbicacion = new Ubicacion();
            nuevaUbicacion.setPais(pais);
            nuevaUbicacion.setCiudad(ciudad);
            nuevaUbicacion.setLugar(lugar);
            CreateLocation service = new CreateLocation(this);
            if(deportes.size()>0){
                Deporte[] arregloDeportes = new Deporte[deportes.size()];
                int j = 0;
                for (Deporte deporte : deportes){
                    arregloDeportes[j++] = deporte;
                }
                service.setDeportes(arregloDeportes);
            }
            service.execute(nuevaUbicacion);
        }
        else if (deportes.size() == 0)
            Toast.makeText(getApplicationContext(), "Debe seleccionar por lo menos un deporte.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Verifique que todos los campos esten debidamente diligenciados.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationChanged(Location location) {
        latitud = (float) location.getLatitude();
        longitud = (float) location.getLongitude();

        Toast.makeText(this,"Latitud: " + latitud + " - Longitud: " + longitud,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this,"Provider " + provider + " has been added.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this,"Provider " + provider + " has been removed.",Toast.LENGTH_LONG).show();
    }

    public class CreateLocation extends AsyncTask<Ubicacion, Void, Boolean> {

        private Context context;
        private String mensajeRespuesta;
        private Deporte[] deportes;

        public CreateLocation(Context context) {
            this.context = context;
        }

        public void setDeportes(Deporte[] deportes){
            this.deportes = deportes;
        }

        @Override
        protected Boolean doInBackground(Ubicacion... nuevaUbicacion) {
            boolean retorno = true;
            try {
                JSONObject parametros;
                parametros = new JSONObject(nuevaUbicacion[0].toJSONObject());

                String resultadoString = ServiceUtils.invokeService_(parametros, Constants.SERVICES_CREAR_UBICACION, "POST");
                JSONObject response = new JSONObject(resultadoString);
                mensajeRespuesta = response.getString("mensajeRespuesta");
                if(!response.getString("caracterAceptacion").equals("B")){
                    retorno = false;
                }
                if(retorno && this.deportes != null){
                    JSONArray deportesUbicacion = new JSONArray();
                    int i = 0;
                    DeportePracticadoUbicacion temp;
                    String[] desc = response.getString("datosExtra").split("/");
                    nuevaUbicacion[0].getLugar().setId(Integer.valueOf(desc[desc.length-1])*-1);
                    for (Deporte deporteUbicacion : deportes){
                        temp = new DeportePracticadoUbicacion();
                        temp.setDeporte(deporteUbicacion);
                        temp.setUbicacion(nuevaUbicacion[0]);
                        deportesUbicacion.put(new JSONObject(temp.toJSONObject()));
                    }

                    resultadoString = ServiceUtils.invokeService(deportesUbicacion, Constants.SERVICES_ASIGNAR_DEPORTE_UBICACION, "POST");
                    response = new JSONObject(resultadoString);
                    mensajeRespuesta = response.getString("mensajeRespuesta");
                    if(!response.getString("caracterAceptacion").equals("B")){
                        retorno = false;
                    }
                }
            } catch (JSONException e) {
                retorno = false;
                e.printStackTrace();
            }
            return retorno;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean)
                Toast.makeText(context,"Lugar creado exitosamente",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"Ha ocurrido un error creando su ubicación. Por favor, intentelo mas tarde.",Toast.LENGTH_LONG).show();
        }
    }
}

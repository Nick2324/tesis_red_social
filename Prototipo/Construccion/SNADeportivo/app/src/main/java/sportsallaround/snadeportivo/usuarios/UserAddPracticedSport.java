package sportsallaround.snadeportivo.usuarios;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.deportes.pojos.Deporte;
import sportsallaround.snadeportivo.deportes.pojos.DeportePracticado;
import sportsallaround.snadeportivo.deportes.pojos.DeportePracticadoUsuario;
import sportsallaround.snadeportivo.deportes.pojos.PosicionDeporte;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.snadeportivo.usuarios.tasks.RetreiveSports;
import sportsallaround.snadeportivo.usuarios.tasks.RetreiveSportsPositions;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainSportPositions;
import sportsallaround.utils.generales.ServiceUtils;

public class UserAddPracticedSport extends ActionBarActivity implements ObtainSportPositions {

    private Usuario user;
    private Spinner availableSports;
    private Spinner availableLevels;
    private PosicionDeporte[] posiciones;
    private RetreiveSportsPositions positionsTask;
    private LinearLayout layoutPosiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_practiced_sport);
        user = (Usuario) getIntent().getExtras().get("user");

        availableSports = (Spinner) findViewById(R.id.available_sports);

        layoutPosiciones = (LinearLayout) findViewById(R.id.available_sport_positions);

        try {
            new RetreiveSports(availableSports, this).execute((Void) null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        positionsTask = new RetreiveSportsPositions(this);

        availableSports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ObtainSportPositions activity = positionsTask.getActivity();
                positionsTask = new RetreiveSportsPositions(activity);
                Deporte[] deportes = (Deporte[]) availableSports.getTag();
                String selectedSport = deportes[position].getId() + "";
                positionsTask.setSportId(selectedSport);
                positionsTask.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] levels = {"Aficionado","Semi-profesional","profesional"};

        availableLevels = (Spinner) findViewById(R.id.availavle_levels);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                levels);
        availableLevels.setAdapter(spinnerArrayAdapter);
        availableLevels.setTag(levels);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_add_practiced_sport, menu);
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
    public void setSportPositions(PosicionDeporte[] posiciones) {
        this.posiciones = posiciones;
        this.layoutPosiciones.removeAllViews();
        CheckBox checkPosicion;
        for(int i=0;i<posiciones.length;i++){
            checkPosicion = new CheckBox(getApplicationContext());
            checkPosicion.setTextColor(Color.BLACK);
            checkPosicion.setText(posiciones[i].getNombre());
            checkPosicion.setTag(posiciones[i]);
            layoutPosiciones.addView(checkPosicion,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public PosicionDeporte[] getSportPositions() {
        return posiciones;
    }

    public void anadirDeporte(View view) {
        Deporte deporte = ((Deporte[]) availableSports.getTag())[availableSports.getSelectedItemPosition()];
        String nivel = ((String[])availableLevels.getTag())[availableLevels.getSelectedItemPosition()];
        ArrayList<PosicionDeporte> posiciones = new ArrayList<PosicionDeporte>();
        CheckBox check;
        for(int i=0;i<layoutPosiciones.getChildCount();i++){
            check = (CheckBox)layoutPosiciones.getChildAt(i);
            if (check.isChecked()){
                posiciones.add((PosicionDeporte)check.getTag());
            }
        }
        if(posiciones.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe seleccionar por lo menos una posicion.",Toast.LENGTH_LONG).show();
            return;
        }

        DeportePracticado nuevoDeporte = new DeportePracticado();
        nuevoDeporte.setDeporte(deporte);
        nuevoDeporte.setNivel(nivel);
        PosicionDeporte[] posicionesArreglo = new PosicionDeporte[posiciones.size()];
        for(int i=0;i<posiciones.size();i++)
            posicionesArreglo[i] = posiciones.get(i);
        nuevoDeporte.setPosiciones(posicionesArreglo);

        DeportePracticadoUsuario nuevoDeporteUsuario = new DeportePracticadoUsuario();

        nuevoDeporteUsuario.setDeportePracticado(nuevoDeporte);
        nuevoDeporteUsuario.setUser(user.getUsuario());

        new AddPracticedSportTask(getApplicationContext()).execute(nuevoDeporteUsuario);
    }

    public class AddPracticedSportTask extends AsyncTask<DeportePracticadoUsuario, Void, Boolean>{

        private Context context;
        private String mensajeRespuesta;

        public AddPracticedSportTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(DeportePracticadoUsuario... nuevoDeporte) {
            boolean retorno = true;
            try {
                JSONObject parametros;
                parametros = new JSONObject(nuevoDeporte[0].toJSONObject());

                String resultadoString = ServiceUtils.invokeService(parametros, Constants.SERVICES_ADICIONAR_DEPORTE_PRACTICADO, "POST");
                JSONObject response = new JSONObject(resultadoString);
                if(!response.getString("caracterAceptacion").equals("B")){
                    mensajeRespuesta = response.getString("mensajeRespuesta");
                    retorno = false;
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
                Toast.makeText(context,"Deporte añadido satisfactoriamente",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,mensajeRespuesta,Toast.LENGTH_LONG).show();
        }
    }
}

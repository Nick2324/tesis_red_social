package sportsallaround.snadeportivo.usuarios.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.deportes.pojos.Deporte;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ServiceUtils;

/**
 * Created by luis on 7/21/15.
 */
public class RetreiveSports extends AsyncTask<Void, Void, Deporte[]> {
    private Spinner availableSports;
    private Context context;

    public RetreiveSports(Spinner availableSports, Context context) {
        this.availableSports = availableSports;
        this.context = context;
    }

    @Override
    protected Deporte[] doInBackground(Void... params) {
        String responseRoles = ServiceUtils.invokeService(null, Constants.SERVICES_OBTENER_DEPORTES, "GET");

        try {
            JSONArray jsonRoles = new JSONArray(responseRoles);
            Deporte[] deportes = new Deporte[jsonRoles.length()];
            JSONObject deporte;
            for (int i = 0; i < jsonRoles.length(); i++) {
                deporte = jsonRoles.getJSONObject(i);
                deportes[i] = new Deporte(deporte);
            }
            return deportes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Deporte[] result) {
        if (result != null) {
            String[] deportes = new String[result.length];
            for(int i=0;i<result.length;i++)
                deportes[i] = result[i].getNombre();
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item,
                    deportes);
            availableSports.setAdapter(spinnerArrayAdapter);
            availableSports.setTag(result);
        }
    }
}

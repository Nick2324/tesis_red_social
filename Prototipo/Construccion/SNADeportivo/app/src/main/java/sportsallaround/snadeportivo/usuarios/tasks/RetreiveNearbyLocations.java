package sportsallaround.snadeportivo.usuarios.tasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.deportes.pojos.Deporte;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ObtainNearbyLocations;
import sportsallaround.utils.ObtainSports;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by luis on 9/30/15.
 */
public class RetreiveNearbyLocations extends AsyncTask<Void, Void, LugarPractica[]> {

    private ObtainNearbyLocations activity;

    public RetreiveNearbyLocations(ObtainNearbyLocations activity) {
        this.activity = activity;
    }

    @Override
    protected LugarPractica[] doInBackground(Void... params) {
        String responseLocations = ServiceUtils.invokeService(null, Constants.SERVICES_OBTENER_UBICACIONES, "GET");
        LugarPractica[] ubicaciones = null;
        try {
            JSONArray jsonRoles = new JSONArray(responseLocations);
            ubicaciones = new LugarPractica[jsonRoles.length()];
            JSONObject ubicacion;
            for (int i = 0; i < jsonRoles.length(); i++) {
                ubicacion = jsonRoles.getJSONObject(i);
                ubicaciones[i] = new LugarPractica(ubicacion);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ubicaciones;
    }

    @Override
    protected void onPostExecute(LugarPractica[] ubicaciones) {
        activity.setNearbyLocations(ubicaciones);
    }
}

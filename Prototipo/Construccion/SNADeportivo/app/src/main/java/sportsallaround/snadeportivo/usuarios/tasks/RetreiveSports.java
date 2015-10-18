package sportsallaround.snadeportivo.usuarios.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.deportes.pojos.Deporte;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainSports;
import sportsallaround.utils.generales.ServiceUtils;

/**
 * Created by luis on 7/21/15.
 */
public class RetreiveSports extends AsyncTask<Void, Void, Deporte[]> {
    private ObtainSports activity;

    public RetreiveSports(ObtainSports activity) {
        this.activity = activity;
    }

    @Override
    protected Deporte[] doInBackground(Void... params) {
        String responseRoles = ServiceUtils.invokeService(null, Constants.SERVICES_OBTENER_DEPORTES, "GET");
        Deporte[] deportes = null;
        try {
            JSONArray jsonRoles = new JSONArray(responseRoles);
            deportes = new Deporte[jsonRoles.length()];
            JSONObject deporte;
            for (int i = 0; i < jsonRoles.length(); i++) {
                deporte = jsonRoles.getJSONObject(i);
                deportes[i] = new Deporte(deporte);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deportes;
    }

    @Override
    protected void onPostExecute(Deporte[] deportes) {
        activity.setSports(deportes);
    }
}

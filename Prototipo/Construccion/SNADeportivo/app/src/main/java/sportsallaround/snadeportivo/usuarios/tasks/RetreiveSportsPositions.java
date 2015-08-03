package sportsallaround.snadeportivo.usuarios.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.deportes.pojos.PosicionDeporte;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ObtainSportPositions;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by luis on 7/26/15.
 */
public class RetreiveSportsPositions extends AsyncTask<Void, Void, PosicionDeporte[]> {
    private ObtainSportPositions activity;
    private String sportId;

    public RetreiveSportsPositions(ObtainSportPositions activity) {
        this.activity = activity;
    }

    @Override
    protected PosicionDeporte[] doInBackground(Void... params) {

        JSONObject parametros = new JSONObject();

        try {
            parametros.put("sportId",sportId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String responseRoles = ServiceUtils.invokeService(parametros, Constants.SERVICES_OBTENER_POSICIONES_DEPORTE, "GET");

        try {
            JSONArray jsonPosiciones = new JSONArray(responseRoles);
            PosicionDeporte[] posiciones = new PosicionDeporte[jsonPosiciones.length()];
            JSONObject posicion;
            for (int i = 0; i < jsonPosiciones.length(); i++) {
                posicion = jsonPosiciones.getJSONObject(i);
                posiciones[i] = new PosicionDeporte(posicion);
            }
            return posiciones;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(PosicionDeporte[] result) {
        if (result != null) {
            activity.setSportPositions(result);
        }
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public ObtainSportPositions getActivity() {
        return activity;
    }
}


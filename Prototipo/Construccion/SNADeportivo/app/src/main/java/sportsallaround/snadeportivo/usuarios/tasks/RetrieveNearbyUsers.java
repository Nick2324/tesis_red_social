package sportsallaround.snadeportivo.usuarios.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.ubicaciones.pojos.Lugar;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarUsuario;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainNearbyLocations;
import sportsallaround.utils.generales.ObtainNearbyUsers;
import sportsallaround.utils.generales.ServiceUtils;

/**
 * Created by luis on 9/30/15.
 */
public class RetrieveNearbyUsers extends AsyncTask<Void, Void, Lugar[]> {

    private ObtainNearbyUsers activity;

    public RetrieveNearbyUsers(ObtainNearbyUsers activity) {
        this.activity = activity;
    }

    @Override
    protected Lugar[] doInBackground(Void... params) {
        String responseLocations = ServiceUtils.invokeService_((JSONObject) null, Constants.SERVICES_OBTENER_UBICACIONES_USUARIOS, "GET");
        LugarPractica[] usuarios = null;
        try {
            JSONArray jsonRoles = new JSONArray(responseLocations);
            usuarios = new LugarPractica[jsonRoles.length()];
            JSONObject usuario;
            for (int i = 0; i < jsonRoles.length(); i++) {
                usuario = jsonRoles.getJSONObject(i);
                usuarios[i] = new LugarPractica(usuario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    protected void onPostExecute(Lugar[] ubicaciones) {
        activity.setNearbyUsers((LugarUsuario[]) ubicaciones);
    }
}

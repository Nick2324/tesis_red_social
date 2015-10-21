package sportsallaround.snadeportivo.usuarios.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainNearbyEvents;
import sportsallaround.utils.generales.ObtainNearbyLocations;
import sportsallaround.utils.generales.ServiceUtils;

/**
 * Created by luis on 9/30/15.
 */
public class RetrieveNearbyEvents extends AsyncTask<Void, Void, LugarPractica[]> {

    private ObtainNearbyEvents activity;

    public RetrieveNearbyEvents(ObtainNearbyEvents activity) {
        this.activity = activity;
    }

    @Override
    protected LugarPractica[] doInBackground(Void... params) {
        String responseLocations = ServiceUtils.invokeService_((JSONObject) null, Constants.SERVICES_OBTENER_UBICACIONES, "GET");
        LugarPractica[] eventos = null;
        try {
            JSONArray jsonRoles = new JSONArray(responseLocations);
            eventos = new LugarPractica[jsonRoles.length()];
            JSONObject evento;
            for (int i = 0; i < jsonRoles.length(); i++) {
                evento = jsonRoles.getJSONObject(i);
                eventos[i] = new LugarPractica(evento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    @Override
    protected void onPostExecute(LugarPractica[] ubicaciones) {
        activity.setNearbyEvents(ubicaciones);
    }
}

package sportsallaround.snadeportivo.usuarios.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.ubicaciones.pojos.EventoLocalizado;
import sportsallaround.snadeportivo.ubicaciones.pojos.Lugar;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarEvento;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainNearbyEvents;
import sportsallaround.utils.generales.ObtainNearbyLocations;
import sportsallaround.utils.generales.ServiceUtils;

/**
 * Created by luis on 9/30/15.
 */
public class RetrieveNearbyEvents extends AsyncTask<Void, Void, Lugar[]> {

    private ObtainNearbyEvents activity;

    public RetrieveNearbyEvents(ObtainNearbyEvents activity) {
        this.activity = activity;
    }

    @Override
    protected Lugar[] doInBackground(Void... params) {
        String responseLocations = ServiceUtils.invokeService_((JSONObject) null, Constants.SERVICES_OBTENER_UBICACIONES_EVENTOS, "GET");
        LugarEvento[] eventos = null;
        try {
            JSONArray jsonRoles = new JSONArray(responseLocations);
            eventos = new LugarEvento[jsonRoles.length()];
            EventoLocalizado tmp;
            JSONObject evento;
            for (int i = 0; i < jsonRoles.length(); i++) {
                evento = jsonRoles.getJSONObject(i);
                tmp = new EventoLocalizado(evento);
                eventos[i] = new LugarEvento();
                eventos[i].setLatitud(tmp.getUbicacion().getLatitud());
                eventos[i].setLongitud(tmp.getUbicacion().getLongitud());
                eventos[i].setNombre(tmp.getUbicacion().getNombre());
                eventos[i].setId(tmp.getUbicacion().getId());
                ((LugarEvento)eventos[i]).setNombreEvento(tmp.getEvento().getNombre());
                ((LugarEvento)eventos[i]).setDescripcionEvento(tmp.getEvento().getDescripcion());
                ((LugarEvento)eventos[i]).setFechaInicio(tmp.getEvento().getFechaInicio());
                ((LugarEvento)eventos[i]).setFechaFin(tmp.getEvento().getFechaFinal());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    @Override
    protected void onPostExecute(Lugar[] ubicaciones) {
        activity.setNearbyEvents((LugarEvento[]) ubicaciones);
    }
}

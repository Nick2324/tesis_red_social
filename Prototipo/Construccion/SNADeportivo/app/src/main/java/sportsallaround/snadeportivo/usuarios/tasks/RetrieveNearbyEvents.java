package sportsallaround.snadeportivo.usuarios.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        ArrayList<LugarEvento> eventos = new ArrayList<>();
        LugarEvento eventoTmp;
        try {
            JSONArray jsonRoles = new JSONArray(responseLocations);
            eventoTmp = new LugarEvento();
            EventoLocalizado tmp;
            JSONObject evento;
            for (int i = 0; i < jsonRoles.length(); i++) {
                try {
                    evento = jsonRoles.getJSONObject(i);
                    tmp = new EventoLocalizado(evento);
                    eventoTmp = new LugarEvento();
                    eventoTmp.setLatitud(tmp.getUbicacion().getLatitud());
                    eventoTmp.setLongitud(tmp.getUbicacion().getLongitud());
                    eventoTmp.setNombre(tmp.getUbicacion().getNombre());
                    eventoTmp.setId(tmp.getUbicacion().getId());
                    ((LugarEvento) eventoTmp).setNombreEvento(tmp.getEvento().getNombre());
                    ((LugarEvento) eventoTmp).setDescripcionEvento(tmp.getEvento().getDescripcion());
                    ((LugarEvento) eventoTmp).setFechaInicio(tmp.getEvento().getFechaInicio());
                    ((LugarEvento) eventoTmp).setFechaFin(tmp.getEvento().getFechaFinal());
                    eventos.add(eventoTmp);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LugarEvento[] eventosValidos = new LugarEvento[eventos.size()];
        int i=0;
        for(LugarEvento lugar : eventos){
            eventosValidos[i++] = lugar;
        }
        return eventos.size() == 0 ? null : eventosValidos;
    }

    @Override
    protected void onPostExecute(Lugar[] ubicaciones) {
        activity.setNearbyEvents((LugarEvento[]) ubicaciones);
    }
}

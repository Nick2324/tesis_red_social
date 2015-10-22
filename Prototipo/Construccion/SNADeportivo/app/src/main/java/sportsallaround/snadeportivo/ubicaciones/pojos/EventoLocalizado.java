package sportsallaround.snadeportivo.ubicaciones.pojos;

import com.sna_deportivo.pojo.evento.Evento;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 10/21/15.
 */
public class EventoLocalizado {

    private Evento evento;
    private LugarPractica ubicacion;

    public EventoLocalizado(){}

    public EventoLocalizado(JSONObject objeto) throws JSONException {
        JSONObject eventoJson = objeto.getJSONObject("evento");
        JSONObject ubicacionJson = objeto.getJSONObject("ubicacion");
        ubicacion = new LugarPractica(ubicacionJson);
        evento = new Evento();
        evento.setNombre(eventoJson.getString("nombre"));
        evento.setDescripcion(eventoJson.getString("descripcion"));
        evento.setFechaInicio(eventoJson.getString("fechaInicio"));
        evento.setFechaFinal(eventoJson.getString("fechaFinal"));
    }

    public Evento getEvento() {
        return evento;
    }
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    public LugarPractica getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(LugarPractica ubicacion) {
        this.ubicacion = ubicacion;
    }



}
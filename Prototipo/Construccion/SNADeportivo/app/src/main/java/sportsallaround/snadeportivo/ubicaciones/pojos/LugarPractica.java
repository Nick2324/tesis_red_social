package sportsallaround.snadeportivo.ubicaciones.pojos;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.MediaTypeTranslator;

/**
 * Created by luis on 9/25/15.
 */
public class LugarPractica implements MediaTypeTranslator {

    private int id;
    private String nombre;
    private float latitud;
    private float longitud;

    public LugarPractica(JSONObject ubicacion) throws JSONException {
        id = ubicacion.getInt("id");
        nombre = ubicacion.getString("nombre");
        latitud = (float) ubicacion.getDouble("latitud");
        longitud = (float) ubicacion.getDouble("longitud");
    }

    public LugarPractica() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toJSONObject() {
        String retorno = "";
        retorno = "{";
        retorno = retorno + "\"id\":" + id + ",";
        retorno = retorno + "\"nombre\":\"" + nombre + "\",";
        retorno = retorno + "\"latitud\":" + latitud + ",";
        retorno = retorno + "\"longitud\":" + longitud;
        retorno = retorno + "}";
        return retorno;
    }
}

package sportsallaround.snadeportivo.deportes.pojos;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 7/17/15.
 */
public class PosicionDeporte implements MediaTypeTranslator {

    private String nombre;
    private String descripcion;
    private int id;

    public PosicionDeporte(JSONObject jsonObject) throws JSONException {
        this.nombre = jsonObject.getString("nombre");
        this.descripcion = jsonObject.getString("descripcion");
        this.id = jsonObject.getInt("id");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toJSONObject() {
        return "{" + "\"nombre\":" + "\"" + nombre + "\",\"descripcion\":" + "\"" + descripcion + "\",\"id\":" + id + "}";
    }
}

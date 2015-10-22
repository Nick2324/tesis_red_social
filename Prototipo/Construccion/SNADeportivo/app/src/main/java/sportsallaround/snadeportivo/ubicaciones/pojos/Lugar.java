package sportsallaround.snadeportivo.ubicaciones.pojos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 10/21/15.
 */
public abstract class Lugar {

    protected int id;
    protected String nombre;
    protected float latitud;
    protected float longitud;

    public Lugar() {

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

    public abstract String getDescripcion();

    public abstract int getBitmap();

}

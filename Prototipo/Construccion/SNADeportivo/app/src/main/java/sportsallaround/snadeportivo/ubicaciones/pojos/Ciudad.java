package sportsallaround.snadeportivo.ubicaciones.pojos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 9/25/15.
 */
public class Ciudad implements MediaTypeTranslator {

    private int id;
    private String nombre;
    private ArrayList<LugarPractica> ubicaciones;
    private float latitud;
    private float longitud;

    public Ciudad(){}

    public Ciudad(JSONObject ciudad) throws JSONException {
        id = ciudad.getInt("id");
        nombre = ciudad.getString("nombre");
        latitud = Float.parseFloat(ciudad.getString("latitud"));
        longitud = Float.parseFloat(ciudad.getString("longitud"));
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

    public ArrayList<LugarPractica> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(ArrayList<LugarPractica> ubicaciones) {
        this.ubicaciones = ubicaciones;
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
        retorno = retorno + "\"ubicaciones\":[";
        if(ubicaciones != null){
            for(LugarPractica lugar : ubicaciones) {
                retorno = retorno + lugar.toJSONObject() + ",";
            }
            retorno = retorno.substring(0,retorno.length()-2);
        }
        retorno = retorno + "],";
        retorno = retorno + "\"latitud\":" + latitud + ",";
        retorno = retorno + "\"longitud\":" + longitud;
        retorno = retorno + "}";
        return retorno;
    }
}

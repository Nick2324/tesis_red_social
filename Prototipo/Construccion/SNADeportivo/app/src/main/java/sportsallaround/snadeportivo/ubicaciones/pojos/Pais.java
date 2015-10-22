package sportsallaround.snadeportivo.ubicaciones.pojos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 9/25/15.
 */
public class Pais implements MediaTypeTranslator {

    private int id;
    private String nombre;
    private ArrayList<Ciudad> ciudades;
    private float latitud;
    private float longitud;

    public Pais(){}

    public Pais(JSONObject pais) throws JSONException {
        id = pais.getInt("id");
        nombre = pais.getString("nombre");
        latitud = Float.parseFloat(pais.getString("latitud"));
        longitud = Float.parseFloat(pais.getString("longitud"));
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

    public ArrayList<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(ArrayList<Ciudad> ciudades) {
        this.ciudades = ciudades;
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
        retorno = retorno + "\"ciudades\":[";
        if(ciudades != null){
            for(Ciudad ciudad : ciudades){
                retorno = retorno + ciudad.toJSONObject() + ",";
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

package sportsallaround.snadeportivo.ubicaciones.pojos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.deportes.pojos.Deporte;
import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 9/25/15.
 */
public class LugarPractica extends Lugar implements MediaTypeTranslator {

    private Deporte[] deportesPracticados;

    public LugarPractica(){}

    public LugarPractica(JSONObject ubicacion) throws JSONException {
        id = ubicacion.getInt("id");
        nombre = ubicacion.getString("nombre");
        latitud = (float) ubicacion.getDouble("latitud");
        longitud = (float) ubicacion.getDouble("longitud");
        if(ubicacion.has("deportesPracticados") && ubicacion.get("deportesPracticados") != null &&
                !ubicacion.getString("deportesPracticados").equals("null")) {
            JSONArray arreglo = ubicacion.getJSONArray("deportesPracticados");
            try {
                deportesPracticados = new Deporte[arreglo.length()];
                for (int i = 0; i < arreglo.length(); i++) {
                    deportesPracticados[i] = new Deporte(arreglo.getJSONObject(i));
                }
            } catch (Exception e) {
                deportesPracticados = null;
            }
        }
    }

    public Deporte[] getDeportesPracticados() {
        return deportesPracticados;
    }

    public void setDeportesPracticados(Deporte[] deportesPracticados) {
        this.deportesPracticados = deportesPracticados;
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

    @Override
    public String getDescripcion() {
        String descripcion = "Deportes practicados: ";
        if (deportesPracticados != null)
            for(Deporte deporte : deportesPracticados)
                descripcion = descripcion.equals("Deportes practicados: ") ? descripcion + deporte.getNombre() : descripcion + ", " + deporte.getNombre();
        else
            descripcion = descripcion + "N/A";
        return descripcion;
    }

    @Override
    public int getBitmap() {
        return R.drawable.sport_marker;
    }
}

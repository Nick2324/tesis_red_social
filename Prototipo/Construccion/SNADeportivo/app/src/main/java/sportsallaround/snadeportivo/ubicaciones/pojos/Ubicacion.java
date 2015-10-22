package sportsallaround.snadeportivo.ubicaciones.pojos;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 9/25/15.
 */
public class Ubicacion implements MediaTypeTranslator {

    private Integer id;
    private Pais pais;
    private Ciudad ciudad;
    private LugarPractica lugar;

    public Ubicacion() {}

    public Ubicacion(JSONObject ubicacion) throws JSONException  {
        if(ubicacion.has("id")) {
            id = ubicacion.getInt("id");
        }
        if(ubicacion.has("pais")) {
            pais = new Pais(ubicacion.getJSONObject("pais"));
        }
        if(ubicacion.has("ciudad")){
            ciudad = new Ciudad(ubicacion.getJSONObject("ciudad"));
        }
        if(ubicacion.has("lugar")) {
            lugar = new LugarPractica(ubicacion.getJSONObject("lugar"));
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public LugarPractica getLugar() {
        return lugar;
    }

    public void setLugar(LugarPractica lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toJSONObject() {
        String retorno = "{";
        Object[] arregloPropiedades = new Object[4];
        arregloPropiedades[0] = this.id;
        arregloPropiedades[1] = this.pais;
        arregloPropiedades[2] = this.ciudad;
        arregloPropiedades[3] = this.lugar;
        String[] arregloNombresPropiedades = new String[4];
        arregloNombresPropiedades[0] = "\"id\"";
        arregloNombresPropiedades[1] = "\"pais\"";
        arregloNombresPropiedades[2] = "\"ciudad\"";
        arregloNombresPropiedades[3] = "\"lugar\"";
        for(int i = 0; i < arregloNombresPropiedades.length; i++){
            if(arregloPropiedades[i] != null) {
                for(int j = i - 1; j >= 0; j--) {
                    if(arregloPropiedades[j] != null) {
                        retorno += ",";
                        break;
                    }
                }
                retorno += arregloNombresPropiedades[i] + ":";
                if (arregloPropiedades[i] instanceof MediaTypeTranslator) {
                    retorno += ((MediaTypeTranslator) arregloPropiedades[i]).toJSONObject();
                } else {
                    retorno += arregloPropiedades[i].toString();
                }
            }
        }
        return retorno + "}";
    }
}

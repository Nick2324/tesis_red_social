package sportsallaround.snadeportivo.ubicaciones.pojos;

import org.json.JSONObject;

import sportsallaround.utils.MediaTypeTranslator;

/**
 * Created by luis on 9/25/15.
 */
public class Ubicacion implements MediaTypeTranslator {

    private Pais pais;
    private Ciudad ciudad;
    private LugarPractica lugar;

    public Ubicacion(JSONObject ubicacion) {

    }

    public Ubicacion() {

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
        String retorno = "";
        retorno = "{";
        retorno = retorno + "\"pais\":" + pais.toJSONObject() + ",";
        retorno = retorno + "\"ciudad\":" + ciudad.toJSONObject() + ",";
        retorno = retorno + "\"lugar\":" + lugar.toJSONObject();
        retorno = retorno + "}";
        return retorno;
    }
}

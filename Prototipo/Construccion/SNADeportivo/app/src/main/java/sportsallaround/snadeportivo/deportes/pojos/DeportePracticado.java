package sportsallaround.snadeportivo.deportes.pojos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 7/17/15.
 */
public class DeportePracticado {

    private Deporte deporte;
    private PosicionDeporte[] posiciones;
    private String nivel;

    public DeportePracticado(JSONObject jsonObject) throws JSONException {
        this.deporte = new Deporte(jsonObject.getJSONObject("deporte"));
        JSONArray posiciones = jsonObject.getJSONArray("posiciones");
        this.posiciones = new PosicionDeporte[posiciones.length()];
        for(int i=0;i<posiciones.length();i++)
            this.posiciones[i] = new PosicionDeporte(posiciones.getJSONObject(i));
        this.nivel = jsonObject.getString("nivel");
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public PosicionDeporte[] getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(PosicionDeporte[] posiciones) {
        this.posiciones = posiciones;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}

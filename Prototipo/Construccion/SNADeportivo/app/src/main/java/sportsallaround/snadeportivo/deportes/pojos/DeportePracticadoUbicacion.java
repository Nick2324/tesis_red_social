package sportsallaround.snadeportivo.deportes.pojos;

import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 10/18/15.
 */
public class DeportePracticadoUbicacion implements MediaTypeTranslator {

    private Deporte deporte;
    private Ubicacion ubicacion;

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toJSONObject() {
        return "{" + "\"deporte\":" + deporte.toJSONObject() + ",\"ubicacion\":" + ubicacion.toJSONObject() + "}";
    }

}
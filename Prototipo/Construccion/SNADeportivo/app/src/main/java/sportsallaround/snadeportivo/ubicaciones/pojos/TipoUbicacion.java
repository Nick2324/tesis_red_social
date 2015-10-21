package sportsallaround.snadeportivo.ubicaciones.pojos;

/**
 * Created by luis on 10/17/15.
 */
public class TipoUbicacion {

    private String nombre;
    private boolean requereDeporte;

    public TipoUbicacion(String nombre, boolean requereDeporte) {
        this.requereDeporte = requereDeporte;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRequereDeporte() {
        return requereDeporte;
    }

    public void setRequereDeporte(boolean requereDeporte) {
        this.requereDeporte = requereDeporte;
    }
}

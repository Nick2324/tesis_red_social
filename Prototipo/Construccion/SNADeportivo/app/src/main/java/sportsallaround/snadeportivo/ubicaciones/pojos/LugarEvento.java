package sportsallaround.snadeportivo.ubicaciones.pojos;

import sportsallaround.snadeportivo.R;

/**
 * Created by luis on 10/21/15.
 */
public class LugarEvento extends Lugar {
    private String nombreEvento;
    private String descripcionEvento;
    private String fechaInicio;
    private String fechaFin;

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String getDescripcion() {
        String desc = "Evento: " + getNombreEvento() + " - Inicia: " + getFechaInicio() + " Termina: " + getFechaFin() + " | " + getDescripcion();
        return desc;
    }

    @Override
    public int getBitmap() {
        return R.drawable.event_marker;
    }
}

package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.json.JsonObject;

/**
 * Created by luis on 7/17/15.
 */
public class DeportePracticado {

    private Deporte deporte;
    private PosicionDeporte[] posiciones;
    private String nivel;
    
    public DeportePracticado() {

    }

    public DeportePracticado(JsonObject jsonObject) {

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

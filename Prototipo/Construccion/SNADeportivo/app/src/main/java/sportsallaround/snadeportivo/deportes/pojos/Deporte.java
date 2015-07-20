package sportsallaround.snadeportivo.deportes.pojos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 7/13/15.
 */
public class Deporte {

    private int id;
    private String nombre;
    private String descripcion;
    private String fechaCreacion;
    private String historia;
    private boolean esOlimpico;

    public Deporte(JSONObject object) throws JSONException {
        id = object.getInt("id");
        nombre = object.getString("nombre");
        descripcion = object.getString("descripcion");
        fechaCreacion = object.getString("fechaCreacion");
        historia = object.getString("historia");
        esOlimpico = object.getString("esOlimpico").equals("true");
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public boolean getEsOlimpico() {
        return esOlimpico;
    }

    public void setEsOlimpico(boolean esOlimpico) {
        this.esOlimpico = esOlimpico;
    }
}

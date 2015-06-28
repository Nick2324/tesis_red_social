package sportsallaround.snadeportivo.usuarios.pojos;

/**
 * Created by LuisFelipe on 21/06/2015.
 */
public class Permiso {

    private String nombre;
    private String descripcion;
    private String ruta;
    private int consecutivoPermiso;

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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getConsecutivoPermiso() {
        return consecutivoPermiso;
    }

    public void setConsecutivoPermiso(int consecutivoPermiso) {
        this.consecutivoPermiso = consecutivoPermiso;
    }
}

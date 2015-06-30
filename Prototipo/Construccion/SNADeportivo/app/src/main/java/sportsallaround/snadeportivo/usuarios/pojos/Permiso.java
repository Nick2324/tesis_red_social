package sportsallaround.snadeportivo.usuarios.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LuisFelipe on 21/06/2015.
 */
public class Permiso implements Parcelable {

    private String nombre;
    private String descripcion;
    private String ruta;
    private int consecutivoPermiso;

    public Permiso() {
    }

    public Permiso(JSONObject permiso) throws JSONException {
        nombre = permiso.getString("nombre");
        descripcion = permiso.getString("descripcion");
        ruta = permiso.getString("ruta");
        consecutivoPermiso = permiso.getInt("consecutivoPermiso");
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(ruta);
        dest.writeInt(consecutivoPermiso);
    }

    @Override
    public String toString() {
        StringBuilder object = new StringBuilder();
        object.append("{");
        object.append("nombre:\"" + nombre + "\",");
        object.append("descripcion:\"" + descripcion + "\",");
        object.append("ruta:\"" + ruta + "\",");
        object.append("consecutivoPermiso:" + consecutivoPermiso);
        object.append("}");
        return object.toString();
    }

    public Permiso(Parcel in) {
        nombre = in.readString();
        descripcion = in.readString();
        ruta = in.readString();
        consecutivoPermiso = in.readInt();
    }

    public static final Parcelable.Creator<Permiso> CREATOR = new Parcelable.Creator<Permiso>(){
        public Permiso createFromParcel(Parcel in) {
            return new Permiso(in);
        }

        @Override
        public Permiso[] newArray(int size) {
            return new Permiso[size];
        }
    };
}

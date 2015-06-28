package sportsallaround.snadeportivo.usuarios.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LuisFelipe on 21/06/2015.
 */
public class Rol implements Parcelable {

    private String nombre;
    private String descripcion;
    private int consecutivoRol;

    public Rol(JSONObject rol) throws JSONException {
        nombre = rol.getString("nombre");
        descripcion = rol.getString("descripcion");
        consecutivoRol = rol.getInt("consecutivoRol");
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
    public int getConsecutivoRol() {
        return consecutivoRol;
    }
    public void setConsecutivoRol(int consecutivoRol) {
        this.consecutivoRol = consecutivoRol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeInt(consecutivoRol);
    }

    public Rol(Parcel in) {
        nombre = in.readString();
        descripcion = in.readString();
        consecutivoRol = in.readInt();
    }

    public static final Parcelable.Creator<Rol> CREATOR = new Parcelable.Creator<Rol>(){
        public Rol createFromParcel(Parcel in) {
            return new Rol(in);
        }

        @Override
        public Rol[] newArray(int size) {
            return new Rol[size];
        }
    };
}

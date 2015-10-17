package sportsallaround.snadeportivo.deportes.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 7/17/15.
 */
public class PosicionDeporte implements MediaTypeTranslator, Parcelable {

    private String nombre;
    private String descripcion;
    private int id;

    public PosicionDeporte(JSONObject jsonObject) throws JSONException {
        this.nombre = jsonObject.getString("nombre");
        this.descripcion = jsonObject.getString("descripcion");
        this.id = jsonObject.getInt("id");
    }

    public PosicionDeporte(Parcel in){
        nombre = in.readString();
        descripcion = in.readString();
        id = in.readInt();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toJSONObject() {
        return "{" + "\"nombre\":" + "\"" + nombre + "\",\"descripcion\":" + "\"" + descripcion + "\",\"id\":" + id + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeInt(id);
    }

    public static final Parcelable.Creator<PosicionDeporte> CREATOR = new Parcelable.Creator<PosicionDeporte>(){
        public PosicionDeporte createFromParcel(Parcel in) {
            return new PosicionDeporte(in);
        }

        @Override
        public PosicionDeporte[] newArray(int size) {
            return new PosicionDeporte[size];
        }
    };
}

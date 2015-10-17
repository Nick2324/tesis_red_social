package sportsallaround.snadeportivo.deportes.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.generales.MediaTypeTranslator;

/**
 * Created by luis on 7/13/15.
 */
public class Deporte implements MediaTypeTranslator, Parcelable {

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

    public Deporte(Parcel in){
        id = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
        fechaCreacion = in.readString();
        historia = in.readString();
        esOlimpico = in.readByte() == 1;
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

    @Override
    public String toJSONObject() {
        return "{" + "\"id\":" + id + ",\"nombre\":" + "\"" + nombre + "\",\"descripcion\":" + "\"" + descripcion + "\",\"fechaCreacion\":" + "\"" + fechaCreacion + "\",\"historia\":" + "\"" + historia + "\",\"esOlimpico\":" + "\"" + (esOlimpico ? "true" : "false") + "\"}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(fechaCreacion);
        dest.writeString(historia);
        dest.writeByte((byte) (esOlimpico ? 1 : 0));
    }

    public static final Parcelable.Creator<Deporte> CREATOR = new Parcelable.Creator<Deporte>(){
        public Deporte createFromParcel(Parcel in) {
            return new Deporte(in);
        }

        @Override
        public Deporte[] newArray(int size) {
            return new Deporte[size];
        }
    };
}

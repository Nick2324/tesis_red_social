package sportsallaround.snadeportivo.deportes.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.MediaTypeTranslator;

/**
 * Created by luis on 7/17/15.
 */
public class DeportePracticado implements MediaTypeTranslator, Parcelable{

    private Deporte deporte;
    private PosicionDeporte[] posiciones;
    private String nivel;

    public DeportePracticado(){}

    public DeportePracticado(JSONObject jsonObject) throws JSONException {
        this.deporte = new Deporte(jsonObject.getJSONObject("deporte"));
        JSONArray posiciones = jsonObject.getJSONArray("posiciones");
        this.posiciones = new PosicionDeporte[posiciones.length()];
        for(int i=0;i<posiciones.length();i++)
            this.posiciones[i] = new PosicionDeporte(posiciones.getJSONObject(i));
        this.nivel = jsonObject.getString("nivel");
    }

    public DeportePracticado(Parcel in){
        deporte = in.readParcelable(Deporte.class.getClassLoader());
        posiciones = in.createTypedArray(PosicionDeporte.CREATOR);
        nivel = in.readString();
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

    @Override
    public String toJSONObject() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("{");
        retorno.append("\"deporte\":");
        retorno.append(deporte.toJSONObject());
        retorno.append(",\"posiciones\":");
        retorno.append("[");
        for(int i=0;i<posiciones.length;i++){
            retorno.append(posiciones[i].toJSONObject());
            if(i != posiciones.length-1)
                retorno.append(",");
        }
        retorno.append("],\"nivel\":");
        retorno.append("\"");
        retorno.append(nivel);
        retorno.append("\"");
        retorno.append("}");
        return retorno.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(deporte,0);
        dest.writeTypedArray(posiciones, 0);
        dest.writeString(nivel);
    }

    public static final Parcelable.Creator<DeportePracticado> CREATOR = new Parcelable.Creator<DeportePracticado>(){
        public DeportePracticado createFromParcel(Parcel in) {
            return new DeportePracticado(in);
        }

        @Override
        public DeportePracticado[] newArray(int size) {
            return new DeportePracticado[size];
        }
    };
}

package sportsallaround.snadeportivo.ubicaciones.peticiones;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.generales.PeticionObjectCallback;

/**
 * Created by nicolas on 21/10/15.
 */
public class TraerUbicaciones extends Peticion {

    private Context contexto;
    private Ubicacion ubicacion;
    private Ubicacion[] ubicaciones;
    private String identificadorCallback;

    public TraerUbicaciones(Context contexto, Ubicacion ubicacion) {
        this.contexto = contexto;
        this.ubicacion = ubicacion;
        this.identificadorCallback = getClass().getSimpleName();
    }

    public TraerUbicaciones(Context contexto, Ubicacion ubicacion,String identificadorCallback) {
        this.contexto = contexto;
        this.ubicacion = ubicacion;
        this.identificadorCallback = identificadorCallback;
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "GET";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_UBICACIONES +
                Constants.SERVICES_PATH_UBICACIONES_REST;
    }

    @Override
    public void calcularParams() {
        try {
            super.params = new JSONObject(ubicacion.toJSONObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doInBackground() {
        super.setPeticionBody(true);
    }

    @Override
    public void onPostExcecute(String resultadoPeticion) {
        if(resultadoPeticion != null){
            try {
                if(resultadoPeticion.equals("[null]")){
                    resultadoPeticion = "[]";
                }
                JSONArray arrayUbicaciones = new JSONArray(resultadoPeticion);
                if(arrayUbicaciones.length() > 0){
                    ubicaciones = new Ubicacion[arrayUbicaciones.length()];
                    for(int i = 0; i <arrayUbicaciones.length(); i++){
                        ubicaciones[i] = new Ubicacion(arrayUbicaciones.getJSONObject(i));
                    }
                    if(this.contexto instanceof PeticionObjectCallback){
                        //Solo se envia la primera ubicacion encontrada
                        if(ubicaciones.length > 0) {
                            ((PeticionObjectCallback) this.contexto).
                                    getObjetoPeticion(ubicaciones[0],
                                            this.identificadorCallback);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                this.alertError();
            }

        }else{
            this.alertError();
        }
    }

    private void alertError(){
        new AlertDialog.Builder(this.contexto).
                setTitle(this.contexto.getResources().
                        getString(R.string.alert_traer_ubicaciones_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.alert_traer_ubicaciones_err_msn)).
                setNeutralButton(this.contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL),null).
                create().show();
    }

}

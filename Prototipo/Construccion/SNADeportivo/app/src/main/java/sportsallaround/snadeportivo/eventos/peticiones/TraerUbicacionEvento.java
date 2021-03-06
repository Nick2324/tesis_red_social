package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.generales.PeticionObjectCallback;

/**
 * Created by nicolas on 18/10/15.
 */
public class TraerUbicacionEvento extends Peticion {

    private Context contexto;
    private Evento evento;
    private String tipoEvento;
    private String identificadorCallback;

    public TraerUbicacionEvento(Context contexto, Evento evento, String tipoEvento) {
        if(contexto instanceof PeticionObjectCallback) {
            this.contexto = contexto;
            this.evento = evento;
            this.tipoEvento = tipoEvento;
            this.identificadorCallback = getClass().getSimpleName();
        }else{
            throw new ClassCastException();
        }
    }

    public TraerUbicacionEvento(Context contexto,
                                Evento evento,
                                String tipoEvento,
                                String identificadorCallback) {
        if(contexto instanceof PeticionObjectCallback) {
            this.contexto = contexto;
            this.evento = evento;
            this.tipoEvento = tipoEvento;
            this.identificadorCallback = identificadorCallback;
        }else{
            throw new ClassCastException();
        }
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "GET";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                this.tipoEvento + "/" +
                this.evento.getId() + "/" +
                Constants.SERVICES_PATH_UBICACIONES_REST;
    }

    @Override
    public void calcularParams() {
        try {
            super.params = new JSONObject(this.evento.stringJson());
            //PONIENDO EVENTO
            /*JSONArray arrayEventos = new JSONArray();
            arrayEventos.put(new JSONObject(this.evento.stringJson()));
            JSONObject parametrosEvento = new JSONObject();
            parametrosEvento.put(this.evento.getClass().getSimpleName(),
                    arrayEventos);
            super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                    parametrosEvento);*/
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
                Ubicacion ubicacion = new Ubicacion(new JSONObject(resultadoPeticion));
                if(this.contexto instanceof PeticionObjectCallback) {
                    ((PeticionObjectCallback) this.contexto).getObjetoPeticion(
                            ubicacion,
                            this.identificadorCallback);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                this.alertError();
            }
        }
    }

    private void alertError(){
        new AlertDialog.Builder(this.contexto).
                setTitle(this.contexto.getResources().
                        getString(R.string.alert_traer_ubicacion_evento_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.alert_traer_ubicacion_evento_msn_err)).
                setNeutralButton(this.contexto.getResources().getString(R.string.BOTON_NEUTRAL),
                        null).
                create().show();
    }

}

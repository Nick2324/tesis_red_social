package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.ubicacion.ConstantesUbicaciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ubicaciones.general.ConstantesUbicacion;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.generales.PeticionObjectCallback;

/**
 * Created by nicolas on 21/10/15.
 */
public class CrearUbicacionEvento extends Peticion {

    private Context contexto;
    private Ubicacion ubicacion;
    private Evento evento;

    public CrearUbicacionEvento(Context contexto, Ubicacion ubicacion, Evento evento, String tipoEvento) {
        this.contexto = contexto;
        this.ubicacion = ubicacion;
        this.evento = evento;
        this.tipoEvento = tipoEvento;
    }

    private String tipoEvento;


    @Override
    public void calcularMetodo() {
        super.metodo = "POST";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                         this.tipoEvento + "/" + this.evento.getId() + "/" +
                         Constants.SERVICES_PATH_UBICACIONES_REST;
    }

    @Override
    public void calcularParams() {
        Ubicacion ubicacionSoloId = new Ubicacion();
        ubicacionSoloId.setId(this.ubicacion.getId());
        try {
            super.params = new JSONObject();
            //PONIENDO UBICACION
            JSONArray arrayUbicacion = new JSONArray();
            arrayUbicacion.put(new JSONObject(this.ubicacion.toJSONObject()));
            JSONObject parametrosUbicacion = new JSONObject();
            parametrosUbicacion.put(this.ubicacion.getClass().getSimpleName(),
                    arrayUbicacion);
            super.params.put(ConstantesUbicaciones.ELEMENTO_MENSAJE_SERVICIO_UBI,
                    parametrosUbicacion);

            //PONIENDO EVENTO
            JSONArray arrayEventos = new JSONArray();
            arrayEventos.put(new JSONObject(this.evento.stringJson()));
            JSONObject parametrosEvento = new JSONObject();
            parametrosEvento.put(this.evento.getClass().getSimpleName(),
                    arrayEventos);
            super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                    parametrosEvento);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doInBackground() {}

    @Override
    public void onPostExcecute(String resultadoPeticion) {
        if(resultadoPeticion != null){
            try {
                JSONObject resultado = new JSONObject(resultadoPeticion);
                if(resultado.getString("caracterAceptacion") != null &&
                   (resultado.getString("caracterAceptacion").equals("204")||
                    resultado.getString("caracterAceptacion").equals("200")||
                    resultado.getString("caracterAceptacion").equals("201"))){
                    if(this.contexto instanceof PeticionObjectCallback){
                        ((PeticionObjectCallback)this.contexto).getObjetoPeticion(
                                this.ubicacion,this.getClass().getSimpleName());
                    }
                    Toast.makeText(this.contexto,this.contexto.getString(
                            R.string.toast_crea_ubicacion_evento_exito),Toast.LENGTH_LONG).show();
                }else{
                    this.alertError();
                }
            } catch (JSONException e) {
                this.alertError();
                e.printStackTrace();
            }
        }else{
            this.alertError();
        }
    }

    private void alertError(){
        new AlertDialog.Builder(this.contexto).
                setTitle(this.contexto.getResources().
                        getString(R.string.alert_crea_ubicacion_evento_tit)).
                setMessage(this.contexto.getString(R.string.alert_crea_ubicacion_evento_err_msn)).
                setNeutralButton(this.contexto.getString(R.string.BOTON_NEUTRAL),null).
                create().show();
    }

}

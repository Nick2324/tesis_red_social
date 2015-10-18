package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sna_deportivo.pojo.deportes.ConstantesDeportes;
import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.entidadesEstaticas.ConstantesEntidadesGenerales;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;

/**
 * Created by nicolas on 17/10/15.
 */
public class ActualizarEvento extends Peticion {

    private Context contexto;
    private String tipoEvento;
    private Evento evento;
    private Deporte deporte;
    private Genero genero;

    public ActualizarEvento(Context contexto, String tipoEvento, Evento evento, Deporte deporte, Genero genero) {
        this.contexto = contexto;
        this.tipoEvento = tipoEvento;
        this.evento = evento;
        this.deporte = deporte;
        this.genero = genero;
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "PUT";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                this.tipoEvento + "/" +
                this.evento.getId();
    }

    @Override
    public void calcularParams() {
        try {
            super.params = new JSONObject();
            JSONObject objetoTemporal = null;
            JSONArray arrayTemporal = null;
            //Asignando datos de actualizacion
            //ARMANDO DATOS DE EVENTO
            objetoTemporal = new JSONObject();
            arrayTemporal = new JSONArray();
            arrayTemporal.put(new JSONObject(this.evento.stringJson()));
            objetoTemporal.put(this.evento.getClass().getSimpleName(),
                    arrayTemporal);
            super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                    objetoTemporal);

            //ARMANDO DATOS DE GENERO
            objetoTemporal = new JSONObject();
            arrayTemporal = new JSONArray();
            arrayTemporal.put(new JSONObject(this.genero.stringJson()));
            objetoTemporal.put(this.genero.getClass().getSimpleName(),
                    arrayTemporal);
            super.params.put(ConstantesEntidadesGenerales.ELEMENTO_MENSAJE_SERVICIO_GEN,
                    objetoTemporal);

            //ARMANDO DATOS DE DEPORTE
            objetoTemporal = new JSONObject();
            arrayTemporal = new JSONArray();
            arrayTemporal.put(new JSONObject(this.deporte.stringJson()));
            objetoTemporal.put(this.deporte.getClass().getSimpleName(),arrayTemporal);
            super.params.put(ConstantesDeportes.ELEMENTO_MENSAJE_SERVICIO_DEP, objetoTemporal);
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
                if("201".equals(resultado.getString("caracterAceptacion"))){
                    Toast.makeText(this.contexto,
                            this.contexto.getResources().
                                    getString(R.string.toast_actualiza_evento_exito),
                            Toast.LENGTH_LONG).show();
                }else{
                    this.alertError();
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
                        getString(R.string.alert_actualiza_evento_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.alert_actualiza_evento_err_msn)).
                setNeutralButton(this.contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL),null).
                create().show();
    }

}
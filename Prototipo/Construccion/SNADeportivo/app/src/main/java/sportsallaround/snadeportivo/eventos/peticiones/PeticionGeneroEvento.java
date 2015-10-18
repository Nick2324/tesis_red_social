package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.gui.InformacionGeneralEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.PeticionEncadenada;

/**
 * Created by nicolas on 18/10/15.
 */
public class PeticionGeneroEvento extends PeticionEncadenada {

    private Context contexto;
    private Evento evento;
    private String tipoEvento;
    private Bundle extras;

    public PeticionGeneroEvento(Context contexto,
                                Evento evento,
                                String tipoEvento,
                                Bundle extras) {
        super("Genero");
        this.contexto = contexto;
        this.evento = evento;
        this.tipoEvento = tipoEvento;
        this.extras = extras;
    }

    @Override
    public boolean onPostExecuteEncadenado(String resultadoPeticion) {
        if(resultadoPeticion != null) {
            try {
                JSONObject resultado = new JSONObject(resultadoPeticion);
                String caracterAceptacion =
                        resultado.getString("caracterAceptacion");
                if(caracterAceptacion != null &&
                        (caracterAceptacion.equals("200") ||
                                caracterAceptacion.equals("204"))){
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void ejecutaTareaUltimaPeticion() {
        //Preparando datos adicionales del intent
        if(super.peticionesEncadenadas != null) {
            try {
                if(peticionesEncadenadas.getString("Deporte") != null) {
                    //PONE DATOS DE DEPORTE
                    JSONObject respuesta = new JSONObject(peticionesEncadenadas.
                            getString("Deporte"));
                    if(respuesta.getString("datosExtra") != null) {
                        this.extras.putString(ConstantesEvento.DEPORTE_MANEJADO,
                                respuesta.getString("datosExtra"));
                    }
                }
                if(peticionesEncadenadas.getString("Genero") != null) {
                    //PONE DATOS DE GENERO
                    JSONObject respuesta = new JSONObject(peticionesEncadenadas.
                            getString("Genero"));
                    if(respuesta.getString("datosExtra") != null) {
                        this.extras.putString(ConstantesEvento.GENERO_MANEJADO,
                                respuesta.getString("datosExtra"));
                    }
                }
                Intent intent = new Intent(this.contexto, InformacionGeneralEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD, extras);
                ((Activity)this.contexto).startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
                this.alertError();
            }
        } else {
            this.alertError();
        }
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "GET";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                this.tipoEvento + "/" + this.evento.getId() + "/" +
                Constants.SERVICES_PATH_GENERO;
    }

    @Override
    public void calcularParams() {
        super.params = new JSONObject();
        try {
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
    public void doInBackground() {
        this.setPeticionBody(true);
    }

    private void alertError(){
        new AlertDialog.Builder(this.contexto).
                setTitle(this.contexto.getResources().
                        getString(R.string.descripcion_go_error_ir_eve_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.descripcion_go_error_ir_eve_msn)).
                setNeutralButton(this.contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL), null).
                create().show();
    }

}
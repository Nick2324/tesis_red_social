package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.gui.PerfilEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;

/**
 * Created by nicolas on 17/10/15.
 */
public class PeticionDejarEvento extends Peticion {

    private Context context;
    private String tipoEvento;
    private Evento evento;
    private Usuario usuario;
    private Bundle datosIntent;

    public PeticionDejarEvento(Context context,
                               String tipoEvento,
                               Evento evento,
                               Usuario usuario,
                               Bundle datosIntent) {
        this.context = context;
        this.tipoEvento = tipoEvento;
        this.evento = evento;
        this.usuario = usuario;
        this.datosIntent = datosIntent;
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "DELETE";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                this.tipoEvento + "/" + this.evento.getId() + "/" +
                Constants.SERVICES_PATH_EVE_PARTICIPANTES + this.usuario.getUsuario();
    }

    @Override
    public void calcularParams() {
        try {
            super.params = new JSONObject();
            //PONIENDO USUARIO
            JSONArray arrayUsuarios = new JSONArray();
            arrayUsuarios.put(new JSONObject(this.usuario.stringJson()));
            JSONObject parametrosUsuario = new JSONObject();
            parametrosUsuario.put(this.usuario.getClass().getSimpleName(),
                    arrayUsuarios);
            super.params.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                    parametrosUsuario);

            //PONIENDO EVENTO
            JSONArray arrayEventos = new JSONArray();
            arrayEventos.put(new JSONObject(this.evento.stringJson()));
            JSONObject parametrosEvento = new JSONObject();
            parametrosEvento.put(this.evento.getClass().getSimpleName(),
                    arrayEventos);
            super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                    parametrosEvento);
        }catch(JSONException e){
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
                        (resultado.getString("caracterAceptacion").equals("200") ||
                                resultado.getString("caracterAceptacion").equals("200"))) {
                    Toast.makeText(this.context,
                            this.context.getResources().getString(R.string.toast_deja_evento),
                            Toast.LENGTH_LONG).show();
                    datosIntent.putString(Constants.FUNCIONALIDAD,
                            ConstantesEvento.POSIBLE_PARTICIPANTE_EVENTO);
                    Intent intent = new Intent(this.context, PerfilEvento.class);
                    intent.putExtra(Constants.DATOS_FUNCIONALIDAD, this.datosIntent);
                    ((Activity) this.context).startActivity(intent);
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
        new AlertDialog.Builder(this.context).
                setTitle(this.context.getResources().
                        getString(R.string.alert_dejar_evento_tit_err)).
                setMessage(this.context.getResources().
                        getString(R.string.alert_dejar_evento_msn_err)).
                setNeutralButton(this.context.getResources().getString(
                        R.string.BOTON_NEUTRAL),null).create().show();
    }

}
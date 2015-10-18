package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;

/**
 * Created by nicolas on 17/10/15.
 */
public class CreaInvitadoEvento extends Peticion {


    private Context contexto;
    private String tipoEvento;
    private Evento evento;
    private Usuario usuario;

    public CreaInvitadoEvento(Context contexto,
                              String tipoEvento,
                              Evento evento,
                              Usuario usuario) {
        this.contexto = contexto;
        this.tipoEvento = tipoEvento;
        this.evento = evento;
        this.usuario = usuario;
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "POST";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS + this.tipoEvento + "/" +
                this.evento.getId() + "/" +
                Constants.SERVICES_PATH_EVE_INVITACIONES;
    }

    @Override
    public void calcularParams() {
        super.params = new JSONObject();
        try {
            //PONIENDO USUARIO
            JSONArray arrayUsuarios = new JSONArray();
            arrayUsuarios.put(new JSONObject(this.usuario.stringJson()));
            JSONObject parametrosUsuario = new JSONObject();
            parametrosUsuario.put(this.usuario.getClass().getSimpleName(),
                    arrayUsuarios);
            super.params.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                    parametrosUsuario);

            //PONIENDO EVENTO
            //Los eventos deben estar activos
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
                  ("200".equals(resultado.getString("caracterAceptacion")) ||
                   "204".equals(resultado.getString("caracterAceptacion")))){
                    Toast.makeText(this.contexto, this.contexto.getResources().
                                    getString(R.string.toast_invita_usu_eve),
                            Toast.LENGTH_SHORT).show();
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
                        getString(R.string.alert_invita_usu_eve_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.alert_error_invita_usu_eve_msn)).
                setNeutralButton(this.contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL), null).
                create().show();
    }

}

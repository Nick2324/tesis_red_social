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
import sportsallaround.utils.generales.PeticionListaCallback;
import sportsallaround.utils.gui.KeyValueItem;

/**
 * Created by nicolas on 18/10/15.
 */
public class EliminarInvitacion extends Peticion {

    private Context contexto;
    private Usuario usuario;
    private String tipoEvento;
    private Evento evento;
    private KeyValueItem seleccionado;

    public EliminarInvitacion(Context contexto,
                              Usuario usuario,
                              String tipoEvento,
                              KeyValueItem seleccionado) {
        if(contexto instanceof PeticionListaCallback) {
            this.contexto = contexto;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
            this.seleccionado = seleccionado;
            this.evento = (Evento) seleccionado.getValue();
        }else{
            throw new ClassCastException();
        }
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "DELETE";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                this.tipoEvento  + "/" + this.evento.getId() + "/" +
                Constants.SERVICES_PATH_EVE_INVITACIONES +
                this.usuario.getUsuario();
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
            Evento evento = (Evento)this.seleccionado.getValue();
            JSONArray arrayEventos = new JSONArray();
            arrayEventos.put(new JSONObject(evento.stringJson()));
            JSONObject parametrosEventos = new JSONObject();
            parametrosEventos.put(evento.getClass().getSimpleName(),
                    arrayEventos);
            super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                    parametrosEventos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doInBackground() {}

    @Override
    public void onPostExcecute(String resultadoPeticion){
        if(resultadoPeticion != null){
            try {
                JSONObject respuesta = new JSONObject(resultadoPeticion);
                if(respuesta.getString("caracterAceptacion") != null &&
                        (respuesta.getString("caracterAceptacion").equals("200") ||
                                respuesta.getString("caracterAceptacion").equals("204"))){
                    //ELIMINANDO ELEMENTO DE LA LISTA
                    ((PeticionListaCallback)this.contexto).eliminarItem(this.seleccionado);
                    Toast.makeText(this.contexto, this.contexto.getResources().
                                    getString(R.string.toast_invitacion_eliminada_exito),
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
                        getString(R.string.alert_elimina_invitacion_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.alert_elimina_invitacion_err_msn)).
                setNeutralButton(this.contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL), null).
                create().show();
    }
}

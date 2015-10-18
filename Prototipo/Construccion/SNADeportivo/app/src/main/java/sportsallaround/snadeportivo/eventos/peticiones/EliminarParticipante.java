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
public class EliminarParticipante extends Peticion {

    Context contexto;
    Evento evento;
    Usuario usuario;
    String tipoEvento;
    KeyValueItem itemSeleccionado;

    public EliminarParticipante(Context contexto,
                                Evento evento,
                                Usuario usuario,
                                String tipoEvento,
                                KeyValueItem itemSeleccionado) {
        if(contexto instanceof PeticionListaCallback) {
            this.contexto = contexto;
            this.evento = evento;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
            this.itemSeleccionado = itemSeleccionado;
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
                this.tipoEvento + "/" +
                this.evento.getId() + "/" +
                Constants.SERVICES_PATH_EVE_PARTICIPANTES +
                this.usuario.getUsuario();
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
    public void doInBackground() {
        super.setPeticionBody(true);
    }

    @Override
    public void onPostExcecute(String resultadoPeticion){
        if(resultadoPeticion != null){
            try {
                JSONObject resultado = new JSONObject(resultadoPeticion);
                if("200".equals(resultado.getString("caracterAceptacion"))){
                    ((PeticionListaCallback)this.contexto).eliminarItem(itemSeleccionado);
                    Toast.makeText(this.contexto,
                            this.contexto.getResources().
                                    getString(R.string.toast_elimina_participante),
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
                        getString(R.string.alert_elimina_participante_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.alert_error_elimina_participante_msn)).
                setNeutralButton(this.contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL), null).
                create().show();
    }

}
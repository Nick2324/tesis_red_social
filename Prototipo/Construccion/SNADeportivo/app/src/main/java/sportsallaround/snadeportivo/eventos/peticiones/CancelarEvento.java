package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.gui.GestionEventosLista;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;

/**
 * Created by nicolas on 17/10/15.
 */
public class CancelarEvento extends Peticion {

    private Context contexto;
    private Evento evento;
    private Usuario usuario;
    private String servicioEvento;
    private String owner;

    public CancelarEvento(Usuario usuario,
                          Evento evento,
                          String servicioEvento,
                          String owner,
                          Context contexto){
        this.contexto = contexto;
        this.evento = evento;
        this.usuario = usuario;
        this.servicioEvento = servicioEvento;
        this.owner = owner;
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "DELETE";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                this.servicioEvento +
                "/" + this.evento.getId();
    }

    @Override
    public void calcularParams() {
        try {
            super.params = new JSONObject(this.evento.stringJson());
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
                JSONObject respuesta = new JSONObject(resultadoPeticion);
                if(respuesta.getString("caracterAceptacion").equals("200")) {
                    Toast.makeText(contexto, contexto.getResources().
                            getString(R.string.alert_cancel_eve_exito), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(contexto,GestionEventosLista.class);
                    Bundle extra = new Bundle();
                    extra.putString(Constants.USUARIO,this.usuario.stringJson());
                    extra.putString(ConstantesEvento.OWNER_EVENTO,this.owner);
                    intent.putExtra(Constants.DATOS_FUNCIONALIDAD,extra);
                    ((Activity)contexto).startActivity(intent);
                }else{
                    Toast.makeText(contexto,this.contexto.getResources().
                            getString(R.string.alert_cancel_eve_no_exito),Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(contexto,this.contexto.getResources().
                        getString(R.string.alert_cancel_eve_no_exito),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }else{
            Toast.makeText(contexto,this.contexto.getResources().
                    getString(R.string.alert_cancel_eve_no_exito),Toast.LENGTH_LONG).show();
        }
    }
}
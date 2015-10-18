package sportsallaround.snadeportivo.eventos.peticiones;

import android.content.Context;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ConstructorArrObjSNS;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.generales.PeticionListaCallback;

/**
 * Created by nicolas on 18/10/15.
 */
public class TraerInvitacionesUsuario extends Peticion {

    private Context contexto;
    private Usuario usuario;
    private String tipoEvento;

    public TraerInvitacionesUsuario(Context contexto,
                                    Usuario usuario,
                                    String tipoEvento) {
        if(contexto instanceof PeticionListaCallback) {
            this.contexto = contexto;
            this.usuario = usuario;
            this.tipoEvento = tipoEvento;
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
        super.servicio = Constants.SERVICES_PATH_USUARIOS +
                this.usuario.getUsuario() + "/" +
                Constants.SERVICES_PATH_EVENTOS +
                Constants.SERVICES_PATH_EVE_INVITACIONES;
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doInBackground() {
        this.setPeticionBody(true);
    }

    @Override
    public void onPostExcecute(String resultadoPeticion) {
        if(resultadoPeticion != null){
            try {
                ArrayList<ObjectSNSDeportivo> invitaciones =
                        ConstructorArrObjSNS.producirArrayObjetoSNS(new ProductorFactoryEvento().
                                        producirFacObjetoSNS(this.tipoEvento),
                                new JSONArray(resultadoPeticion));
                ((PeticionListaCallback)this.contexto).
                        llenarListaDesdePeticion(invitaciones);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                productorFactoryExcepcion.printStackTrace();
            }
        }else{
            Toast.makeText(this.contexto, this.contexto.getResources().
                    getString(R.string.toast_listado_invitaciones_vacio), Toast.LENGTH_LONG).show();
        }
    }
}

package sportsallaround.snadeportivo.eventos.peticiones;

import android.content.Context;
import android.widget.Toast;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.FactoryUsuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

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
public class ObtenerParticipantes extends Peticion {

    private Context contexto;
    private Evento evento;
    private String tipoEvento;

    public ObtenerParticipantes(Context contexto,
                                Evento evento,
                                String tipoEvento){
        if(contexto instanceof PeticionListaCallback) {
            this.contexto = contexto;
            this.evento = evento;
            this.tipoEvento = tipoEvento;
        }else{
            throw new ClassCastException();
        }
    }

    @Override
    public void calcularMetodo() {
        this.metodo = "GET";
    }

    @Override
    public void calcularServicio() {
        if(this.evento != null && this.evento.getId() != null) {
            super.servicio = Constants.SERVICES_PATH_EVENTOS +
                    tipoEvento + "/" +
                    evento.getId() + "/" +
                    Constants.SERVICES_PATH_EVE_PARTICIPANTES;
        }
    }

    @Override
    public void calcularParams() {
        try {
            super.params = new JSONObject();
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
        this.setPeticionBody(true);
    }

    @Override
    public void onPostExcecute(String resultadoPeticion) {
        if(resultadoPeticion != null){
            try {
                ArrayList<ObjectSNSDeportivo> participantes =
                        ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                                new JSONArray(resultadoPeticion));
                ((PeticionListaCallback)this.contexto).
                        llenarListaDesdePeticion(participantes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this.contexto, this.contexto.getResources().
                    getString(R.string.toast_listado_participantes_vacio), Toast.LENGTH_LONG).show();
        }
    }
}
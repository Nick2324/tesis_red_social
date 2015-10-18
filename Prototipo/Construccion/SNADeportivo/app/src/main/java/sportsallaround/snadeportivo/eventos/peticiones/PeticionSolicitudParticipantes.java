package sportsallaround.snadeportivo.eventos.peticiones;

import android.content.Context;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.usuarios.FactoryUsuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ConstructorArrObjSNS;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.generales.PeticionListaCallback;

/**
 * Created by nicolas on 18/10/15.
 */
public class PeticionSolicitudParticipantes extends Peticion {

    private Context contexto;
    private Evento evento;
    private String tipoEvento;

    public PeticionSolicitudParticipantes(Context contexto,
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
        this.servicio = Constants.SERVICES_PATH_EVENTOS + this.tipoEvento + "/" +
                this.evento.getId() + "/" +
                Constants.SERVICES_PATH_EVE_SOLICITUDES;
    }

    @Override
    public void calcularParams(){
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
        super.setPeticionBody(true);
    }

    @Override
    public void onPostExcecute(String resultadoPeticion){
        if(resultadoPeticion != null) {
            try {
                ArrayList<ObjectSNSDeportivo> solicitudes =
                        ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                                new JSONArray(resultadoPeticion));
                ((PeticionListaCallback)this.contexto).
                        llenarListaDesdePeticion(solicitudes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

package sportsallaround.snadeportivo.eventos.peticiones;

import android.content.Context;

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
public class PeticionListaUsuarios extends Peticion {

    private Context contexto;

    public PeticionListaUsuarios(Context contexto){
        if(contexto instanceof PeticionListaCallback) {
            this.contexto = contexto;
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
        this.servicio = Constants.SERVICES_PATH_USUARIOS;
    }

    @Override
    public void calcularParams() {
        this.params = new JSONObject();
    }

    @Override
    public void doInBackground() {
        this.setPeticionBody(true);
    }

    @Override
    public void onPostExcecute(String resultadoPeticion){
        try{
            ArrayList<ObjectSNSDeportivo> solicitudParaParticipantes =
                    ConstructorArrObjSNS.producirArrayObjetoSNS(new FactoryUsuario(),
                            new JSONArray(resultadoPeticion));
            ((PeticionListaCallback)this.contexto).
                    llenarListaDesdePeticion(solicitudParaParticipantes);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


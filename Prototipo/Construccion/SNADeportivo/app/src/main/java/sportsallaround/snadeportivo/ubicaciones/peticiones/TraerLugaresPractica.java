package sportsallaround.snadeportivo.ubicaciones.peticiones;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.generales.PeticionListaCallback;
import com.sna_deportivo.pojo.ubicacion.LugarPractica;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import java.util.ArrayList;

/**
 * Created by nicolas on 21/10/15.
 */
public class TraerLugaresPractica extends Peticion{

    private Context contexto;
    private LugarPractica[] lugaresPractica;

    public TraerLugaresPractica(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public void calcularMetodo() {
        super.metodo = "GET";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_OBTENER_UBICACIONES;
    }

    @Override
    public void calcularParams() {}

    @Override
    public void doInBackground() {}

    @Override
    public void onPostExcecute(String resultadoPeticion) {
        if(resultadoPeticion != null){
            try {
                if(resultadoPeticion.equals("[null]")){
                    resultadoPeticion = "[]";
                }
                Log.d("Nick:resultadoPeticion",resultadoPeticion);
                JSONArray arrayLugaresPractica = new JSONArray(resultadoPeticion);
                this.lugaresPractica = new LugarPractica[arrayLugaresPractica.length()];
                for(int i = 0; i < this.lugaresPractica.length; i++){
                    arrayLugaresPractica.getJSONObject(i).remove("deportesPracticados");
                    this.lugaresPractica[i] = new LugarPractica();
                    this.lugaresPractica[i].deserializarJson((JsonObject)
                            JsonUtils.JsonStringToObject(arrayLugaresPractica.getString(i)));
                }
                if(this.contexto instanceof PeticionListaCallback){
                    ArrayList<ObjectSNSDeportivo> lugaresPracticaLD =
                            new ArrayList<ObjectSNSDeportivo>();
                    for(int i = 0; i < this.lugaresPractica.length; i++){
                        lugaresPracticaLD.add(this.lugaresPractica[i]);
                    }
                    ((PeticionListaCallback)this.contexto).
                            llenarListaDesdePeticion(lugaresPracticaLD);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                this.alertError();
            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                excepcionJsonDeserializacion.printStackTrace();
                this.alertError();
            }
        }
    }

    private void alertError(){
        new AlertDialog.Builder(this.contexto).
                setTitle(this.contexto.getResources().
                            getString(R.string.alert_traer_lugares_practica_tit)).
                setMessage(this.contexto.getResources().
                        getString(R.string.alert_traer_lugares_practica_err_msn)).
                setNeutralButton(this.contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL),null).
                create().show();
    }

}

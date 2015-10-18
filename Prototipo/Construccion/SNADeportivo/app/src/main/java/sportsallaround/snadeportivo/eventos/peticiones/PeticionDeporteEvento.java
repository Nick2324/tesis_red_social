package sportsallaround.snadeportivo.eventos.peticiones;

import android.content.Context;
import android.os.Bundle;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.PeticionEncadenada;

/**
 * Created by nicolas on 18/10/15.
 */
public class PeticionDeporteEvento extends PeticionEncadenada {

    private Context contexto;
    private Evento evento;
    private String tipoEvento;
    private Bundle extras;

    public PeticionDeporteEvento(Context contexto,
                                 Evento evento,
                                 String tipoEvento,
                                 Bundle extras) {
        super("Deporte");
        this.contexto = contexto;
        this.evento = evento;
        this.tipoEvento = tipoEvento;
        this.extras = extras;
    }

    @Override
    public boolean onPostExecuteEncadenado(String resultadoPeticion) {
        if(resultadoPeticion != null) {
            try {
                JSONObject resultado = new JSONObject(resultadoPeticion);
                String caracterAceptacion =
                        resultado.getString("caracterAceptacion");
                if(caracterAceptacion != null &&
                        (caracterAceptacion.equals("200") ||
                                caracterAceptacion.equals("204"))){
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void ejecutaTareaUltimaPeticion() {}

    @Override
    public void calcularMetodo() {
        super.metodo = "GET";
    }

    @Override
    public void calcularServicio() {
        super.servicio = Constants.SERVICES_PATH_EVENTOS +
                this.tipoEvento + "/" + this.evento.getId() + "/" +
                Constants.SERVICES_PATH_DEPORTES;
    }

    @Override
    public void calcularParams() {
        super.params = new JSONObject();
        try {
            //PONIENDO EVENTO
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
}
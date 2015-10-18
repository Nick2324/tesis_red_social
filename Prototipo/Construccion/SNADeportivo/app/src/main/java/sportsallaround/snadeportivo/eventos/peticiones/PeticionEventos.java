package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.evento.TiposEventos;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.Peticion;
import sportsallaround.utils.generales.PeticionListaCallback;

/**
 * Created by nicolas on 18/10/15.
 */
public class PeticionEventos extends Peticion {

    protected Context contexto;
    protected TiposEventos tipoEvento;
    protected Usuario usuario;

    public PeticionEventos(Context contexto,TiposEventos tipoEvento){
        this.contexto = contexto;
        this.tipoEvento = tipoEvento;
    }

    public PeticionEventos(Context contexto,
                           TiposEventos tipoEvento,
                           Usuario usuario){
        if(contexto instanceof PeticionListaCallback) {
            this.contexto = contexto;
            this.tipoEvento = tipoEvento;
            this.usuario = usuario;
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
        if(this.usuario != null){
            super.servicio = Constants.SERVICES_PATH_USUARIOS +
                    this.usuario.getUsuario() + "/" +
                    Constants.SERVICES_PATH_EVENTOS + tipoEvento.getServicio();
        }else{
            super.servicio = Constants.SERVICES_PATH_EVENTOS + tipoEvento.getServicio();
        }

    }

    @Override
    public void calcularParams() {
        try {
            super.params = new JSONObject();
            if(this.usuario != null) {
                //PONIENDO USUARIO
                JSONArray arrayUsuarios = new JSONArray();
                arrayUsuarios.put(new JSONObject(this.usuario.stringJson()));
                JSONObject parametrosUsuario = new JSONObject();
                parametrosUsuario.put(this.usuario.getClass().getSimpleName(),
                        arrayUsuarios);
                super.params.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                        parametrosUsuario);

                //PONIENDO EVENTO
                Evento e = new ProductorFactoryEvento().
                        getEventosFactory(this.tipoEvento.getServicio()).
                        crearEvento();
                //Los eventos deben estar activos
                e.setActivo(true);
                JSONArray arrayEventos = new JSONArray();
                arrayEventos.put(new JSONObject(e.stringJson()));
                JSONObject parametrosEvento = new JSONObject();
                parametrosEvento.put(e.getClass().getSimpleName(),
                        arrayEventos);
                super.params.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
                        parametrosEvento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
            productorFactoryExcepcion.printStackTrace();
        }
    }

    @Override
    public void doInBackground() {
        super.setPeticionBody(true);
    }

    @Override
    public void onPostExcecute(String resultadoPeticion) {
        if(this.usuario != null && params == null){
            new AlertDialog.Builder(this.contexto).
                    setTitle(this.contexto.getResources().
                            getString(R.string.alert_lista_eventos_err_tit)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.alert_lista_eventos_err_msn)).
                    setNeutralButton(this.contexto.getResources().
                            getString(R.string.BOTON_NEUTRAL),null).
                    create().show();
        }else {
            if (resultadoPeticion != null && !resultadoPeticion.equals("")) {
                try {
                    JSONArray responseJson = new JSONArray(resultadoPeticion);
                    ArrayList<ObjectSNSDeportivo> eventos =
                            new ArrayList<ObjectSNSDeportivo>();
                    for (int i = 0; i < responseJson.length(); i++) {
                        eventos.add(new ProductorFactoryEvento().
                                getEventosFactory(this.tipoEvento.getServicio()).
                                crearEvento());
                        eventos.get(i).deserializarJson(
                                JsonUtils.JsonStringToObject(responseJson.getString(i)));
                    }
                    ((PeticionListaCallback)this.contexto).llenarListaDesdePeticion(eventos);
                } catch (JSONException e) {
                    ((PeticionListaCallback)this.contexto).limpiarListaDesdePeticion();
                    Log.w("SNA:JSONException", e.getMessage());
                } catch (ExcepcionJsonDeserializacion e) {
                    ((PeticionListaCallback)this.contexto).limpiarListaDesdePeticion();
                    Log.w("SNA:ExcepcionJsonDes", e.getMessage());
                } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                    productorFactoryExcepcion.printStackTrace();
                }
            } else {
                ((PeticionListaCallback)this.contexto).limpiarListaDesdePeticion();
            }
        }
    }
}

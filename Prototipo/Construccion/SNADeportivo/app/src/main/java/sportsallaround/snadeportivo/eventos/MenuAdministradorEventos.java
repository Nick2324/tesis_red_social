package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sna_deportivo.pojo.deportes.ConstantesDeportes;
import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.entidadesEstaticas.ConstantesEntidadesGenerales;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.StringUtils;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.Peticion;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by nicolas on 12/09/15.
 */
public class MenuAdministradorEventos implements MenuEventos{

    private class CrearEvento extends AsyncTask<JSONObject,Object,String> {

        private Context contexto;
        private Bundle datosIntent;

        public CrearEvento(Context contexto,
                           Bundle datosIntent){
            this.contexto = contexto;
            this.datosIntent = datosIntent;
        }

        @Override
        protected String doInBackground(JSONObject... params) {
            try{
                return ServiceUtils.invokeService(params[0],
                        Constants.SERVICES_PATH_EVENTOS +
                                params[1].getString(ConstantesEvento.SERVICIO_EVENTO)+"/",
                        "POST");
            }catch(Exception e){
                Log.e("Nick:Error", e.getMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result){
            if(result != null && result.length() != 0){
                Toast.makeText(contexto, this.contexto.getResources().
                        getString(R.string.toast_creacion_exitosa_evento), Toast.LENGTH_LONG).show();
                this.datosIntent.putString(ConstantesEvento.EVENTO_MANEJADO, result);
                this.datosIntent.putString(Constants.FUNCIONALIDAD, ConstantesEvento.ACTUALIZAR_EVENTO);
                Intent intent = new Intent(this.contexto,InformacionGeneralEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD, this.datosIntent);
                ((Activity)this.contexto).startActivity(intent);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle(this.contexto.getResources().
                        getString(R.string.alert_crear_eve_err_titulo)).
                        setMessage(this.contexto.getResources().
                                getString(R.string.alert_crear_eve_err_mensaje)).
                        setNeutralButton(this.contexto.getResources().
                                getString(R.string.BOTON_NEUTRAL), null);
                builder.create().show();
            }
        }

    }

    private class CancelarEvento extends Peticion{

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
            //SUPUESTAMENTE DELETE NO SOPORTA REQUEST BODY
            /*try {
                super.params = new JSONObject(this.evento.stringJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
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

    public MenuAdministradorEventos(){}

    public int getMenuId(){
        return R.menu.menu_accion_administrador_evento;
    }

    @Override
    public void esconderSegunFuncionalidadRol(Context contexto,String concepto, Menu menu) {
        //CAMBIAR POR LECTURA DESDE LA BD
        if (ConstantesEvento.CREAR_EVENTO.equals(concepto)) {
            MenuItem item = menu.findItem(R.id.item_gestionar_involucrados_eve_admin);
            item.setVisible(false);
            MenuItem item1 = menu.findItem(R.id.item_perfil_evento_admin);
            item1.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.item_cancelar_evento_admin);
            item2.setVisible(false);
        } else if (ConstantesEvento.ACTUALIZAR_EVENTO.equals(concepto)) {
            MenuItem item1 = menu.findItem(R.id.item_crear_evento);
            item1.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.item_info_participantes);
            item2.setVisible(false);
        }
    }

    public void comportamiento(final Context contexto,int idSeleccionado, final Bundle datosIntent){
        //SI ES POSIBLE, CAMBIAR POR LECTURA EN LA BD
        //CHAIN OF RESPONSIBILITY
        Intent intent = null;
        AlertDialog confirmacion = null;
        switch (idSeleccionado){
            case R.id.item_info_participantes:
                intent = new Intent(contexto,InformacionParticipantes.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_perfil_evento_admin:
                intent = new Intent(contexto,PerfilEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_info_general_evento_admin:
                intent = new Intent(contexto,InformacionGeneralEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_gestionar_involucrados_eve_admin:
                intent = new Intent(contexto,MenuAdministrarInvolucrados.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_gestion_ubicaciones_eve_admin:
                //contexto.startActivity(new Intent(contexto,InformacionGeneralEvento.class));
                break;
            case R.id.item_crear_evento:
                new AlertDialog.Builder(contexto).
                        setTitle(contexto.getResources().
                                getString(R.string.alert_crear_eve_titulo)).
                        setMessage(contexto.getResources().
                                getString(R.string.alert_crear_eve_mensaje)).
                        setPositiveButton(contexto.getResources().
                                getString(R.string.BOTON_AFIRMATIVO),
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (datosIntent.getString(ConstantesEvento.DEPORTE_MANEJADO) != null &&
                                    datosIntent.getString(ConstantesEvento.EVENTO_MANEJADO) != null &&
                                    datosIntent.getString(ConstantesEvento.TIPO_EVENTO) != null &&
                                    datosIntent.getString(ConstantesEvento.GENERO_MANEJADO) != null) {
                                    JSONObject objetoTemporal;
                                    JSONArray arrayTemporal;
                                    JSONObject tipoEventoJson = new JSONObject();
                                    JSONObject mensaje = new JSONObject();
                                    try {
                                        //ARMANDO DATOS DE EVENTO
                                        objetoTemporal = new JSONObject();
                                        arrayTemporal = new JSONArray();
                                        arrayTemporal.put(new JSONObject(datosIntent.getString(
                                                ConstantesEvento.EVENTO_MANEJADO).replace("\n", StringUtils.NEWLINE)));
                                        objetoTemporal.put(datosIntent.getString(ConstantesEvento.TIPO_EVENTO),
                                                arrayTemporal);
                                        mensaje.put(ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,objetoTemporal);

                                        //ARMANDO DATOS DE GENERO
                                        objetoTemporal = new JSONObject();
                                        arrayTemporal = new JSONArray();
                                        arrayTemporal.put(new JSONObject(datosIntent.getString(
                                                ConstantesEvento.GENERO_MANEJADO)));
                                        objetoTemporal.put(Genero.class.getSimpleName(),
                                                arrayTemporal);
                                        mensaje.put(ConstantesEntidadesGenerales.ELEMENTO_MENSAJE_SERVICIO_GEN,
                                                objetoTemporal);

                                        //ARMANDO DATOS DE DEPORTE
                                        objetoTemporal = new JSONObject();
                                        arrayTemporal = new JSONArray();
                                        arrayTemporal.put(new JSONObject(datosIntent.getString(
                                                ConstantesEvento.DEPORTE_MANEJADO)));
                                        objetoTemporal.put(Deporte.class.getSimpleName(),
                                                arrayTemporal);
                                        mensaje.put(ConstantesDeportes.ELEMENTO_MENSAJE_SERVICIO_DEP,
                                                objetoTemporal);

                                        //ARMANDO DATOS DE USUARIO
                                        objetoTemporal = new JSONObject();
                                        arrayTemporal = new JSONArray();
                                        arrayTemporal.put(new JSONObject(datosIntent.getString(
                                                Constants.USUARIO)));
                                        objetoTemporal.put(Usuario.class.getSimpleName(),
                                                arrayTemporal);
                                        mensaje.put(ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
                                                objetoTemporal);

                                        //ARMANDO TIPO DE EVENTO A ENVIAR
                                        tipoEventoJson.put(ConstantesEvento.SERVICIO_EVENTO,
                                                datosIntent.getString(ConstantesEvento.SERVICIO_EVENTO));

                                        new CrearEvento(contexto,datosIntent).execute(mensaje,tipoEventoJson);
                                    } catch (JSONException e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                                        builder.setTitle(contexto.getResources().
                                                getString(R.string.alert_crear_eve_err_titulo)).
                                                setMessage(contexto.getResources().
                                                        getString(R.string.alert_crear_eve_err_mensaje)).
                                                setNeutralButton(contexto.getResources().
                                                        getString(R.string.BOTON_NEUTRAL), null);
                                        builder.create().show();
                                        e.printStackTrace();
                                    }
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                                    builder.setTitle(contexto.getResources().
                                            getString(R.string.alert_crear_eve_err_titulo)).
                                            setMessage(contexto.getResources().
                                                    getString(R.string.alert_datos_faltantes_crea_eve_tit)).
                                            setNeutralButton(contexto.getResources().
                                                    getString(R.string.BOTON_NEUTRAL), null);
                                    builder.create().show();
                                }
                            }
                        }).setNegativeButton(contexto.getResources().
                        getString(R.string.BOTON_NEGATIVO),null).create().show();
                break;
            case R.id.item_cancelar_evento_admin:
                new AlertDialog.Builder(contexto).
                        setTitle(contexto.getResources().
                                getString(R.string.alert_cancelando_evento_tit)).
                        setMessage(contexto.getResources().
                                getString(R.string.alert_cancelando_evento_msn)).
                        setPositiveButton(contexto.getResources().
                                        getString(R.string.BOTON_AFIRMATIVO),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(datosIntent.getString(ConstantesEvento.EVENTO_MANEJADO)
                                                != null &&
                                           datosIntent.getString(ConstantesEvento.SERVICIO_EVENTO)
                                                   != null &&
                                           datosIntent.getString(Constants.USUARIO)
                                                   != null){
                                            Evento e = new ProductorFactoryEvento().
                                                    getEventosFactory(datosIntent.
                                                            getString(ConstantesEvento.SERVICIO_EVENTO)).
                                                    crearEvento();
                                            try {
                                                e.deserializarJson((JsonObject)JsonUtils.JsonStringToObject(
                                                        datosIntent.getString(ConstantesEvento.EVENTO_MANEJADO)));
                                                if(e.getId() != null){
                                                    Usuario u = new Usuario();
                                                    try {
                                                        u.deserializarJson((JsonObject)JsonUtils.JsonStringToObject(
                                                                datosIntent.getString(Constants.USUARIO)));
                                                        new CancelarEvento(u,e,
                                                                datosIntent.getString(ConstantesEvento.SERVICIO_EVENTO),
                                                                datosIntent.getString(ConstantesEvento.OWNER_EVENTO),
                                                                contexto).ejecutarPeticion();
                                                    } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                                                        despliegaAlertCanEveDatIncomp(contexto);
                                                        excepcionJsonDeserializacion.printStackTrace();
                                                    }
                                                }else{
                                                    new AlertDialog.Builder(contexto).
                                                            setTitle(contexto.getResources().
                                                                    getString(R.string.alert_cancelando_evento_tit)).
                                                            setMessage(contexto.getResources().
                                                                    getString(R.string.alert_cancel_eve_no_existe)).
                                                            setNeutralButton(contexto.getResources().
                                                                    getString(R.string.BOTON_NEUTRAL),null).
                                                            create().show();
                                                }
                                            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                                                despliegaAlertCanEveDatIncomp(contexto);
                                                excepcionJsonDeserializacion.printStackTrace();
                                            }
                                        }else{
                                            despliegaAlertCanEveDatIncomp(contexto);
                                        }
                                    }
                                }).
                        setNegativeButton(contexto.getResources().
                                getString(R.string.BOTON_NEGATIVO), null).create().show();
                break;
        }
    }

    private void despliegaAlertCanEveDatIncomp(final Context contexto){
        new AlertDialog.Builder(contexto).
                setTitle(contexto.getResources().
                        getString(R.string.alert_cancelando_evento_tit)).
                setMessage(contexto.getResources().
                        getString(R.string.alert_cancel_eve_dat_incomp)).
                setNeutralButton(contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL),null).
                create().show();
    }

}

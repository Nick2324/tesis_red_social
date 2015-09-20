package sportsallaround.snadeportivo.eventos;

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

import com.sna_deportivo.utils.gr.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by nicolas on 12/09/15.
 */
public class MenuAdministradorEventos implements MenuEventos{

    private class CrearEvento extends AsyncTask<JSONObject,Object,String> {

        private Context contexto;

        public CrearEvento(Context contexto){
            this.contexto = contexto;
        }

        @Override
        protected String doInBackground(JSONObject... params) {
            try{
                return ServiceUtils.invokeService(params[0],
                        Constants.SERVICES_PATH_EVENTOS +
                                //CAMBIAR POR EL QUE VENGA DE LA TAREA ANTERIOR. HAY QUE MIGRAR A FRAGMENTO
                                params[1].getString(ConstantesEvento.TIPO_EVENTO)+"/",
                        "POST");
            }catch(Exception e){
                Log.e("Nick:Error", e.getMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result){
            if(result != null && result.length() != 0){
                Toast.makeText(contexto, "Evento creado con éxito", Toast.LENGTH_LONG).show();
                //MANDAR A MODIFICACION Y MODIFICAR ID DEL EVENTO
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle("Error al guardar el evento").
                        setMessage("No se ha podido crear el evento debido a datos erroneos o " +
                                "inexistentes. Por favor verificar").
                        setNegativeButton("OK", null);
                builder.create().show();
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
            MenuItem item = menu.findItem(R.id.item_info_participantes);
            item.setVisible(false);
            MenuItem item1 = menu.findItem(R.id.item_crear_evento);
            item1.setVisible(false);
        }
    }

    public void comportamiento(final Context contexto,int idSeleccionado, final Bundle datosIntent){
        //SI ES POSIBLE, CAMBIAR POR LECTURA EN LA BD
        Intent intent = null;
        AlertDialog confirmacion = null;
        switch (idSeleccionado){
            case R.id.item_info_participantes:
                intent = new Intent(contexto,InformacionParticipantes.class);
                intent.putExtra("datos_funcionalidad",datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_perfil_evento_admin:
                //contexto.startActivity(new Intent());
                break;
            case R.id.item_info_general_evento_admin:
                intent = new Intent(contexto,InformacionGeneralEvento.class);
                intent.putExtra("datos_funcionalidad",datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_gestionar_involucrados_eve_admin:
                intent = new Intent(contexto,MenuAdministrarInvolucrados.class);
                intent.putExtra("datos_funcionalidad",datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_gestion_ubicaciones_eve_admin:
                //contexto.startActivity(new Intent(contexto,InformacionGeneralEvento.class));
                break;
            case R.id.item_crear_evento:
                confirmacion = new AlertDialog.Builder(contexto).
                        setTitle("Creación del evento").setMessage("¿Desea crear el evento?").
                        setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (datosIntent.getString(ConstantesEvento.DEPORTE_MANEJADO) != null &&
                                    datosIntent.getString(ConstantesEvento.EVENTO_MANEJADO) != null &&
                                    datosIntent.getString(ConstantesEvento.TIPO_EVENTO) != null) {
                                    JSONObject eventoACrear;
                                    JSONObject tipoEventoJson = new JSONObject();
                                    try {
                                        Log.d("What!=",datosIntent.getString(
                                                ConstantesEvento.EVENTO_MANEJADO).replace("\n",StringUtils.NEWLINE));
                                        eventoACrear = new JSONObject(datosIntent.getString(
                                                ConstantesEvento.EVENTO_MANEJADO).replace("\n",StringUtils.NEWLINE));
                                        /*eventoACrear.put(ConstantesEvento.DEPORTE_MANEJADO,
                                                datosIntent.getString(ConstantesEvento.DEPORTE_MANEJADO));*/
                                        tipoEventoJson.put(ConstantesEvento.TIPO_EVENTO,
                                                datosIntent.getString(ConstantesEvento.TIPO_EVENTO));
                                        new CrearEvento(contexto).execute(eventoACrear,tipoEventoJson);
                                    } catch (JSONException e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                                        builder.setTitle("Error al guardar el evento").
                                                setMessage("El evento no pudo ser guardado").
                                                setNegativeButton("OK", null);
                                        builder.create().show();
                                        e.printStackTrace();
                                    }
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                                    builder.setTitle("Error al guardar el evento").
                                            setMessage("Necesita llenar los campos del evento " +
                                                    "para poder continuar").
                                            setNegativeButton("OK", null);
                                    builder.create().show();
                                }
                            }
                        }).setNegativeButton("No",null).create();
                confirmacion.show();
                break;
            case R.id.item_cancelar_evento_admin:
                confirmacion = new AlertDialog.Builder(contexto).
                        setTitle("Cancelación de evento").setMessage("¿Desea cancelar el evento?").
                        setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).
                        setNegativeButton("No",null).create();
                confirmacion.show();
                break;
        }
    }

}

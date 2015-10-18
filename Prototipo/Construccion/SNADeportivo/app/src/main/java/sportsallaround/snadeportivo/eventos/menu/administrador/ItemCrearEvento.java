package sportsallaround.snadeportivo.eventos.menu.administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sna_deportivo.pojo.deportes.ConstantesDeportes;
import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.entidadesEstaticas.ConstantesEntidadesGenerales;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.peticiones.CrearEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ItemMenuSNS;

/**
 * Created by nicolas on 17/10/15.
 */
public final class ItemCrearEvento extends ItemMenuSNS{

    public ItemCrearEvento(int idItem){
        super(idItem);
    }

    @Override
    public void ejecutaAccionItem(final Context contexto, final Bundle datosIntent) {
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
    }
}

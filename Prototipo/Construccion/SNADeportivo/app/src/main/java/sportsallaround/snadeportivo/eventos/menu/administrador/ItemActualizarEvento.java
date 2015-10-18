package sportsallaround.snadeportivo.eventos.menu.administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.peticiones.ActualizarEvento;
import sportsallaround.utils.generales.ItemMenuSNS;

/**
 * Created by nicolas on 17/10/15.
 */
public final class ItemActualizarEvento extends ItemMenuSNS{

    public ItemActualizarEvento(int idItem){
        super(idItem);
    }

    @Override
    public void ejecutaAccionItem(final Context contexto, final Bundle datosIntent) {
        new AlertDialog.Builder(contexto).
                setTitle(contexto.getResources().
                        getString(R.string.alert_actualiza_evento_tit)).
                setMessage(contexto.getResources().
                        getString(R.string.alert_actualiza_evento_msn)).
                setPositiveButton(contexto.getResources().
                        getString(R.string.BOTON_AFIRMATIVO), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Evento evento = new ProductorFactoryEvento().
                                    getEventosFactory(datosIntent.
                                            getString(ConstantesEvento.SERVICIO_EVENTO)).
                                    crearEvento();
                            evento.deserializarJson((JsonObject)
                                    JsonUtils.JsonStringToObject(datosIntent.
                                            getString(ConstantesEvento.EVENTO_MANEJADO)));
                            //COMO ABSTRAER?
                            Deporte deporte = new Deporte();
                            deporte.deserializarJson((JsonObject)
                                    JsonUtils.JsonStringToObject(datosIntent.
                                            getString(ConstantesEvento.DEPORTE_MANEJADO)));
                            Genero genero = new Genero();
                            genero.deserializarJson((JsonObject)
                                    JsonUtils.JsonStringToObject(datosIntent.
                                            getString(ConstantesEvento.GENERO_MANEJADO)));
                            new ActualizarEvento(contexto,
                                    datosIntent.getString(ConstantesEvento.SERVICIO_EVENTO),
                                    evento,
                                    deporte,
                                    genero).
                                    ejecutarPeticion();
                        }catch(ExcepcionJsonDeserializacion e){
                            e.printStackTrace();
                            new AlertDialog.Builder(contexto).
                                    setTitle(contexto.getResources().
                                            getString(R.string.alert_actualiza_evento_tit)).
                                    setMessage(contexto.getResources().
                                            getString(R.string.alert_actualiza_evento_err_msn)).
                                    setNeutralButton(contexto.getResources().
                                            getString(R.string.BOTON_NEUTRAL), null).
                                    create().show();
                        } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                            productorFactoryExcepcion.printStackTrace();
                        }
                    }
                }).
                setNegativeButton(contexto.getResources().
                        getString(R.string.BOTON_NEGATIVO), null).
                create().show();
    }
}

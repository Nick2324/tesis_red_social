package sportsallaround.snadeportivo.eventos.menu.administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.peticiones.CancelarEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ItemMenuSNS;

/**
 * Created by nicolas on 17/10/15.
 */
public final class ItemCancelarEvento extends ItemMenuSNS{

    public ItemCancelarEvento(int idItem){
        super(idItem);
    }

    @Override
    public void ejecutaAccionItem(final Context contexto, final Bundle datosIntent) {
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
                                    Evento e = null;
                                    try {
                                        e = new ProductorFactoryEvento().
                                                getEventosFactory(datosIntent.
                                                        getString(ConstantesEvento.SERVICIO_EVENTO)).
                                                crearEvento();
                                    } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                                        productorFactoryExcepcion.printStackTrace();
                                    }
                                    try {
                                        e.deserializarJson((JsonObject) JsonUtils.JsonStringToObject(
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
    }

    private void despliegaAlertCanEveDatIncomp(final Context contexto){
        new AlertDialog.Builder(contexto).
                setTitle(contexto.getResources().
                        getString(R.string.alert_cancelando_evento_tit)).
                setMessage(contexto.getResources().
                        getString(R.string.alert_cancel_eve_dat_incomp)).
                setNeutralButton(contexto.getResources().
                        getString(R.string.BOTON_NEUTRAL), null).
                create().show();
    }

}

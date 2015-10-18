package sportsallaround.snadeportivo.eventos.menu.participante;

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
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.peticiones.PeticionUnirseEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ItemMenuSNS;

/**
 * Created by nicolas on 17/10/15.
 */
public class ItemUnirseEventoParticipante extends ItemMenuSNS{

    public ItemUnirseEventoParticipante(int idItem){
        super(idItem);
    }

    @Override
    public void ejecutaAccionItem(final Context contexto, final Bundle datosIntent) {
        new AlertDialog.Builder(contexto).setTitle(contexto.getResources().
                getString(R.string.alert_unir_evento_titulo)).
                setMessage(contexto.getResources().
                        getString(R.string.alert_unir_evento_mensaje)).
                setPositiveButton(contexto.getResources().
                                getString(R.string.BOTON_AFIRMATIVO),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Usuario usuario = new Usuario();
                                    usuario.deserializarJson((JsonObject) JsonUtils.
                                            JsonStringToObject(datosIntent.getString(
                                                    Constants.USUARIO)));
                                    Evento evento = (Evento)new ProductorFactoryEvento().
                                            producirFacObjetoSNS(datosIntent.
                                                    getString(ConstantesEvento.TIPO_EVENTO)).
                                            getObjetoSNS();
                                    evento.deserializarJson((JsonObject)JsonUtils.
                                            JsonStringToObject(datosIntent.getString(
                                                    ConstantesEvento.EVENTO_MANEJADO)));
                                    new PeticionUnirseEvento(contexto,
                                            datosIntent.getString(ConstantesEvento.SERVICIO_EVENTO),
                                            evento,
                                            usuario,
                                            datosIntent).ejecutarPeticion();
                                } catch (ExcepcionJsonDeserializacion e) {
                                    e.printStackTrace();
                                } catch (ProductorFactoryExcepcion productorFactoryExcepcion) {
                                    productorFactoryExcepcion.printStackTrace();
                                }
                            }
                        }).
                setNegativeButton(contexto.getResources().
                        getString(R.string.BOTON_NEGATIVO), null).create().show();
    }
}

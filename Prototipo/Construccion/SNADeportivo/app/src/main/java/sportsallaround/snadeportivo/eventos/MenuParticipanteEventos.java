package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.Peticion;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by nicolas on 12/09/15.
 */
public class MenuParticipanteEventos implements MenuEventos{

    private class PeticionDejarEvento extends Peticion{

        private Context context;
        private Bundle datosIntent;

        public PeticionDejarEvento(Context context,
                                   Bundle datosIntent){
            this.context = context;
            this.datosIntent = datosIntent;
        }

        @Override
        public void calcularMetodo() {

        }

        @Override
        public void calcularServicio() {

        }

        @Override
        public void calcularParams() {

        }

        @Override
        public void doInBackground() {

        }

        @Override
        public void onPostExcecute(String resultadoPeticion) {
            if(ServiceUtils.STATUS == 200){
                Intent intent = new Intent(this.context,PerfilEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,this.datosIntent);
                ((Activity)this.context).startActivity(intent);
            }else{
                new AlertDialog.Builder(this.context).
                        setTitle(this.context.getResources().
                                getString(R.string.alert_dejar_evento_tit_err)).
                        setMessage(this.context.getResources().
                                getString(R.string.alert_dejar_evento_msn_err)).
                        setNeutralButton(this.context.getResources().getString(
                                R.string.BOTON_NEUTRAL),null).create().show();
            }
        }
    }

    private class PeticionUnirseEvento extends Peticion{

        private Context context;
        private Bundle datosIntent;

        public PeticionUnirseEvento(Context context,
                                    Bundle datosIntent){
            this.context = context;
            this.datosIntent = datosIntent;
        }

        @Override
        public void calcularMetodo() {

        }

        @Override
        public void calcularServicio() {

        }

        @Override
        public void calcularParams() {

        }

        @Override
        public void doInBackground() {

        }

        @Override
        public void onPostExcecute(String resultadoPeticion) {
            if(ServiceUtils.STATUS == 200){
                Intent intent = new Intent(this.context, PerfilEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,this.datosIntent);
                ((Activity)this.context).startActivity(intent);
            }else{
                new AlertDialog.Builder(this.context).
                        setTitle(this.context.getResources().
                                getString(R.string.alert_unir_evento_tit_err)).
                        setMessage(this.context.getResources().getString(
                                R.string.alert_unir_evento_msn_err)).
                        setNeutralButton(this.context.getResources().
                                         getString(R.string.BOTON_NEUTRAL),
                                null).create().show();
            }
        }
    }

    public MenuParticipanteEventos(){}

    @Override
    public int getMenuId() {
        return R.menu.menu_participante_evento;
    }

    @Override
    public void esconderSegunFuncionalidadRol(Context contexto, String concepto, Menu menu) {}

    @Override
    public void comportamiento(final Context contexto, int idSeleccionado, final Bundle datosIntent) {
        Intent intent = null;
        switch (idSeleccionado){
            case R.id.item_info_general_evento:
                intent = new Intent(contexto,InformacionGeneralEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_perfil_evento:
                intent = new Intent(contexto,PerfilEvento.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_dejar_evento_participante:
                new AlertDialog.Builder(contexto).setTitle(contexto.getResources().
                        getString(R.string.alert_dejar_evento_titulo)).
                        setMessage(contexto.getResources().
                                getString(R.string.alert_dejar_evento_mensaje)).
                        setPositiveButton(contexto.getResources().
                                getString(R.string.BOTON_AFIRMATIVO),
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //new PeticionDejarEvento(contexto,datosIntent).ejecutarPeticion();
                                Toast.makeText(contexto,"Dejando evento",Toast.LENGTH_LONG).show();
                            }
                        }).
                        setNegativeButton(contexto.getResources().
                                getString(R.string.BOTON_NEGATIVO), null).create().show();
                break;
            case R.id.item_informacion_participantes:
                intent = new Intent(contexto,InformacionParticipantes.class);
                intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
                contexto.startActivity(intent);
                break;
            case R.id.item_unirse_evento_participante:
                new AlertDialog.Builder(contexto).setTitle(contexto.getResources().
                        getString(R.string.alert_unir_evento_titulo)).
                        setMessage(contexto.getResources().
                                getString(R.string.alert_unir_evento_mensaje)).
                        setPositiveButton(contexto.getResources().
                                getString(R.string.BOTON_AFIRMATIVO),
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //new PeticionUnirseEvento(contexto,datosIntent).ejecutarPeticion();
                                Toast.makeText(contexto,"Uniendose a evento",Toast.LENGTH_LONG).show();
                            }
                        }).
                        setNegativeButton(contexto.getResources().
                                getString(R.string.BOTON_NEGATIVO), null).create().show();
                break;
            case R.id.item_ver_ubicaciones_evento:
                Toast.makeText(contexto,"No implementado",Toast.LENGTH_LONG).show();
                //contexto.startActivity(new Intent(contexto,MenuAdministrarInvolucrados.class));
                break;
        }
    }
}

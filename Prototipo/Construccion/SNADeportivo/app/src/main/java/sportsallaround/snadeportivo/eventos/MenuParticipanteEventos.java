package sportsallaround.snadeportivo.eventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import sportsallaround.snadeportivo.R;

/**
 * Created by nicolas on 12/09/15.
 */
public class MenuParticipanteEventos implements MenuEventos{


    public MenuParticipanteEventos(){}

    @Override
    public int getMenuId() {
        return R.menu.menu_participante_evento;
    }

    @Override
    public void esconderSegunFuncionalidadRol(Context contexto, String concepto, Menu menu) {

    }

    @Override
    public void comportamiento(final Context contexto, int idSeleccionado, final Bundle datosIntent) {
        switch (idSeleccionado){
            case R.id.item_info_general_evento:
                contexto.startActivity(new Intent(contexto,InformacionGeneralEvento.class));
                break;
            case R.id.item_perfil_evento:
                //contexto.startActivity(new Intent(contexto,MenuAdministrarInvolucrados.class));
                break;
            case R.id.item_dejar_evento_participante:
                //contexto.startActivity(new Intent(contexto,MenuAdministrarInvolucrados.class));
                break;
            case R.id.item_informacion_participantes:
                contexto.startActivity(new Intent(contexto,MenuAdministrarInvolucrados.class));
                break;
            case R.id.item_unirse_evento_participante:
                //contexto.startActivity(new Intent(contexto,MenuAdministrarInvolucrados.class));
                break;
            case R.id.item_ver_ubicaciones_evento:
                //contexto.startActivity(new Intent(contexto,MenuAdministrarInvolucrados.class));
                break;
        }
    }
}

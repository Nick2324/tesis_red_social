package sportsallaround.snadeportivo.eventos.menu.administrador;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.gui.MenuAdministrarInvolucrados;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ItemMenuSNS;

/**
 * Created by nicolas on 17/10/15.
 */
public final class ItemGestionarInvolucradosEvento extends ItemMenuSNS{

    public ItemGestionarInvolucradosEvento(int idItem){
        super(idItem);
    }

    @Override
    public void ejecutaAccionItem(Context contexto, Bundle datosIntent) {
        Intent intent = new Intent(contexto,MenuAdministrarInvolucrados.class);
        intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
        contexto.startActivity(intent);
    }
}

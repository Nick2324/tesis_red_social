package sportsallaround.snadeportivo.eventos.menu.general;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.gui.UbicacionesEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ItemMenuSNS;

/**
 * Created by nicolas on 17/10/15.
 */
public final class ItemGestionUbicacionesEvento extends ItemMenuSNS{

    public ItemGestionUbicacionesEvento(int idItem){
        super(idItem);
    }

    @Override
    public void ejecutaAccionItem(Context contexto, Bundle datosIntent) {
        Intent intent = new Intent(contexto,UbicacionesEvento.class);
        intent.putExtra(Constants.DATOS_FUNCIONALIDAD,datosIntent);
        contexto.startActivity(intent);
    }
}

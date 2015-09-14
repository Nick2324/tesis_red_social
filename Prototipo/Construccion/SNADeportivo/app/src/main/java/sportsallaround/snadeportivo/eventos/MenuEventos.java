package sportsallaround.snadeportivo.eventos;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

/**
 * Created by nicolas on 12/09/15.
 */
public interface MenuEventos {

    public int getMenuId();
    public void esconderSegunFuncionalidadRol(Context contexto, String concepto, Menu menu);
    public void comportamiento(final Context contexto,int idSeleccionado, final Bundle datosIntent);

}

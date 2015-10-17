package sportsallaround.utils.generales;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by nicolas on 17/10/15.
 */
public abstract class ItemMenuSNS {

    private int idItem;

    public ItemMenuSNS(int idItem) {
        this.idItem = idItem;
    }

    public abstract void ejecutaAccionItem(final Context contexto, final Bundle datosIntent);

    public void esconder(Menu menu){
        MenuItem item = (MenuItem)menu.findItem(this.idItem);
        if(item != null){
            item.setVisible(false);
        }
    }

    public void renderizar(Menu menu){
        MenuItem item = (MenuItem)menu.findItem(this.idItem);
        if(item != null){
            item.setVisible(true);
        }
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdItem() {
        return idItem;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof ItemMenuSNS){
            if(((ItemMenuSNS)obj).
                getIdItem() == this.getIdItem()){
                return true;
            }
        }
        return false;
    }

}

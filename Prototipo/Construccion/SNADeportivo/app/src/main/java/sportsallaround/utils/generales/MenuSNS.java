package sportsallaround.utils.generales;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by nicolas on 12/09/15.
 */
public abstract class MenuSNS {

    private int idMenu;
    private ArrayList<ItemMenuSNS> itemMenuSNS;

    public MenuSNS(int idMenu) {
        this.itemMenuSNS = new ArrayList<ItemMenuSNS>();
        this.idMenu = idMenu;
    }

    protected void aniadirItem(ItemMenuSNS item){
        itemMenuSNS.add(item);
    }

    protected void eliminarItem(ItemMenuSNS item){
        itemMenuSNS.remove(item);
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public final void comportamiento(final Context contexto,int idSeleccionado, final Bundle datosIntent){
        for(ItemMenuSNS item:this.itemMenuSNS){
            if(item.getIdItem() == idSeleccionado){
                item.ejecutaAccionItem(contexto,datosIntent);
                break;
            }
        }
    }

    public void esconderItems(Menu menu, AnalizadorUsoItem analizador){
        ArrayList<Integer> itemsAEsconder = new ArrayList<Integer>();
        for(ItemMenuSNS item:this.itemMenuSNS){
            itemsAEsconder.add(item.getIdItem());
        }
        itemsAEsconder = analizador.analizarUsoItems(itemsAEsconder);
        for(Integer itemAEsconder:itemsAEsconder){
            for(ItemMenuSNS item:this.itemMenuSNS){
                if(item.getIdItem() == itemAEsconder){
                    item.esconder(menu);
                    break;
                }
            }
        }
    }


}

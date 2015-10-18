package sportsallaround.utils.generales;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

import java.util.ArrayList;

import sportsallaround.utils.gui.KeyValueItem;

/**
 * Created by nicolas on 18/10/15.
 */
public interface PeticionListaCallback {

    public void llenarListaDesdePeticion(ArrayList<ObjectSNSDeportivo> listaObtenida);
    public void limpiarListaDesdePeticion();
    public void eliminarItem(KeyValueItem aEliminar);

}

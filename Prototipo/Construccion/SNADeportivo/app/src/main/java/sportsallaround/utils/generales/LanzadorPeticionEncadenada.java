package sportsallaround.utils.generales;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by nicolas on 12/10/15.
 */
public class LanzadorPeticionEncadenada {

    private ArrayList<PeticionEncadenada> peticiones;

    public LanzadorPeticionEncadenada(){
        peticiones = new ArrayList<PeticionEncadenada>();
    }

    public void setSiguientePeticion(PeticionEncadenada peticion){
        peticiones.add(peticion);
    }

    public void ejecutarPeticion(){
        if(this.peticiones.size() > 0) {
            PeticionEncadenada pe;
            for (int i = this.peticiones.size() - 1; i > 0; i--) {
                pe = peticiones.remove(i);
                Log.d("Nick:clase", pe.getClass().getSimpleName());
                peticiones.get(i - 1).setSiguientePenticion(pe);
                Log.d("Nick:clase", peticiones.get(i - 1).getClass().getSimpleName());
            }
            peticiones.get(0).ejecutarPeticionEncadenada();
        }
    }

}

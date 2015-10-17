package sportsallaround.utils.generales;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nicolas on 12/10/15.
 */
public abstract class PeticionEncadenada extends Peticion{

    private PeticionEncadenada siguientePeticion;
    protected JSONObject peticionesEncadenadas;
    private String identificador;
    private Exception excepcionEjecucion;

    protected PeticionEncadenada(String identificador){
        this.identificador = identificador;
    }

    public void setSiguientePenticion(PeticionEncadenada siguientePeticion){
        this.siguientePeticion = siguientePeticion;
    }

    public abstract boolean onPostExecuteEncadenado(String resultadoPeticion);

    public abstract void ejecutaTareaUltimaPeticion();

    @Override
    public void onPostExcecute(String resultadoPeticion){
        if(this.onPostExecuteEncadenado(resultadoPeticion)) {
            try {
                this.peticionesEncadenadas.put(this.identificador, resultadoPeticion);
                if (this.siguientePeticion != null) {
                    this.siguientePeticion.ejecutarPeticionEncadenada(this.peticionesEncadenadas);
                }else{
                    this.ejecutaTareaUltimaPeticion();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                this.excepcionEjecucion = e;
            }
        }
    }

    public void ejecutarPeticionEncadenada(){
        this.ejecutarPeticionEncadenada(null);
    }

    private void ejecutarPeticionEncadenada(JSONObject peticionesEncadenadas){
        if(peticionesEncadenadas == null){
            this.peticionesEncadenadas = new JSONObject();
        }else{
            this.peticionesEncadenadas = peticionesEncadenadas;
        }
        this.ejecutarPeticion();
    }

    public Exception getExcepcionEjecucion(){
        return this.excepcionEjecucion;
    }

    public JSONObject getPeticionesEncadenadas() {
        return peticionesEncadenadas;
    }
}

package sportsallaround.utils.generales;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by nicolas on 22/08/15.
 */
public abstract class Peticion {

    protected String metodo;
    protected String servicio;
    protected JSONObject params;
    protected Object[] resultado;
    private boolean peticionBody;

    private class EjecucionPeticion extends AsyncTask<Peticion,Object,String>{

        private Peticion peticion;

        @Override
        protected String doInBackground(Peticion... params) {
            this.peticion = (Peticion)params[0];
            this.peticion.doInBackground();
            this.peticion.calcularServicio();
            this.peticion.calcularMetodo();
            this.peticion.calcularParams();
            if(peticionBody) {
                return ServiceUtils.invokeServiceBody(this.peticion.getParams(),
                        this.peticion.getServicio(),
                        this.peticion.getMetodo());
            }else{
                return ServiceUtils.invokeService(this.peticion.getParams(),
                        this.peticion.getServicio(),
                        this.peticion.getMetodo());
            }
        }

        @Override
        public void onPostExecute(String response){
            this.peticion.onPostExcecute(response);
        }

    }

    public abstract void calcularMetodo();
    public abstract void calcularServicio();
    public abstract void calcularParams();

    public void setPeticionBody(boolean peticionBody){
        this.peticionBody = peticionBody; 
    }

    public String getMetodo(){
        return this.metodo;
    }

    public String getServicio(){
        return this.servicio;
    }

    public JSONObject getParams(){
        return this.params;
    }

    public abstract void doInBackground();
    public abstract void onPostExcecute(String resultadoPeticion);

    public void ejecutarPeticion(){
        new EjecucionPeticion().execute(new Peticion[]{this});
    }

    public Object[] getResultado(){
        return this.resultado;
    }

}

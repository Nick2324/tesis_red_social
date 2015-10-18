package sportsallaround.snadeportivo.eventos.peticiones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.gui.InformacionGeneralEvento;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ServiceUtils;

/**
 * Created by nicolas on 17/10/15.
 */
public class CrearEvento extends AsyncTask<JSONObject,Object,String> {

    private Context contexto;
    private Bundle datosIntent;

    public CrearEvento(Context contexto,
                       Bundle datosIntent){
        this.contexto = contexto;
        this.datosIntent = datosIntent;
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        try{
            return ServiceUtils.invokeService(params[0],
                    Constants.SERVICES_PATH_EVENTOS +
                            params[1].getString(ConstantesEvento.SERVICIO_EVENTO) + "/",
                    "POST");
        }catch(Exception e){
            Log.e("Nick:Error", e.getMessage());
        }

        return null;

    }

    @Override
    protected void onPostExecute(String result){
        if(result != null && result.length() != 0){
            Toast.makeText(contexto, this.contexto.getResources().
                    getString(R.string.toast_creacion_exitosa_evento), Toast.LENGTH_LONG).show();
            this.datosIntent.putString(ConstantesEvento.EVENTO_MANEJADO, result);
            this.datosIntent.putString(Constants.FUNCIONALIDAD, ConstantesEvento.ACTUALIZAR_EVENTO);
            Intent intent = new Intent(this.contexto,InformacionGeneralEvento.class);
            intent.putExtra(Constants.DATOS_FUNCIONALIDAD, this.datosIntent);
            ((Activity)this.contexto).startActivity(intent);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
            builder.setTitle(this.contexto.getResources().
                    getString(R.string.alert_crear_eve_err_titulo)).
                    setMessage(this.contexto.getResources().
                            getString(R.string.alert_crear_eve_err_mensaje)).
                    setNeutralButton(this.contexto.getResources().
                            getString(R.string.BOTON_NEUTRAL), null);
            builder.create().show();
        }
    }

}

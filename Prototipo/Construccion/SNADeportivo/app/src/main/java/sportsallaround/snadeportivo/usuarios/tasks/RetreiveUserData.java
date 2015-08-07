package sportsallaround.snadeportivo.usuarios.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ObtainUserInfo;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by luis on 6/30/15.
 */
public class RetreiveUserData extends AsyncTask<Void, Void, Usuario> {

    private Usuario userInfo;
    private ObtainUserInfo activity;

    public RetreiveUserData(Usuario userInfo, ObtainUserInfo activity) {
        this.userInfo = userInfo;
        this.activity = activity;
    }

    @Override
    protected Usuario doInBackground(Void... params) {
        String responseUsuario = ServiceUtils.invokeService(null, Constants.SERVICES_OBTENER_INFO_USUARIO.replace("{user}",userInfo.getUsuario()), "GET");

        try {
            JSONObject jsonUsuario = new JSONObject(responseUsuario);
            Usuario usuario = new Usuario(jsonUsuario);

            if (usuario != null) {
                activity.setUserInfo(usuario);
            }

            return usuario;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Usuario result) {

    }
}
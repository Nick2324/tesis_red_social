package sportsallaround.snadeportivo.usuarios.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainUserInfo;
import sportsallaround.utils.generales.ServiceUtils;

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
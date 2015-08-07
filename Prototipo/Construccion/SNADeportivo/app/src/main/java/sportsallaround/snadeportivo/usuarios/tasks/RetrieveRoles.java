package sportsallaround.snadeportivo.usuarios.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sportsallaround.utils.Constants;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by luis on 6/30/15.
 */
public class RetrieveRoles extends AsyncTask<Void, Void, String[]> {

    private Spinner roles;
    private Context context;

    public RetrieveRoles(Spinner roles, Context context) {
        this.roles = roles;
        this.context = context;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String responseRoles = ServiceUtils.invokeService(null, Constants.SERVICES_OBTENER_ROLES, "GET");

        try {
            JSONArray jsonRoles = new JSONArray(responseRoles);
            String[] roles = new String[jsonRoles.length()];
            JSONObject rol;
            for (int i = 0; i < jsonRoles.length(); i++) {
                rol = jsonRoles.getJSONObject(i);
                roles[i] = (String) rol.get("nombre");
            }
            return roles;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item,
                    result);
            roles.setAdapter(spinnerArrayAdapter);
        }
    }
}
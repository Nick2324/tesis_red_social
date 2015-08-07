package sportsallaround.snadeportivo.usuarios.fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.ErrorActivity;
import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.usuarios.pojos.Permiso;
import sportsallaround.snadeportivo.usuarios.pojos.Rol;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ServiceUtils;

/**
 * Created by LuisFelipe on 21/06/2015.
 */
public class GenericMenuFragment extends Fragment{

    private Context context;
    private ArrayList<Permiso> permisos;
    private Usuario user;
    private Rol userRole;
    private Permiso permission;
    private LinearLayout layout;
    private View userTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        user = extras.getParcelable("user");
        userRole = extras.getParcelable("userRole");
        permission = extras.getParcelable("permission");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initializeMenu(inflater,container,savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        generateMenu();
    }

    public View initializeMenu(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        userTitle = inflater.inflate(R.layout.user_image_title, container, false);
        generateMenu();
        return layout;
    }

    public void generateMenu(){
        layout.removeAllViews();
        ((TextView)(userTitle.findViewById(R.id.title_user_name))).setText(user.getPrimerNombre().trim() + " " + user.getSegundoNombre().trim() + " " + user.getApellidos().trim());
        ((TextView)(userTitle.findViewById(R.id.title_user_role))).setText(userRole.getNombre());
        try {
            new GenericMenuTask(this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Button boton;
        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        for(final Permiso permiso : permisos){
            boton = new Button(context);
            boton.setText(permiso.getNombre());
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    try {
                        intent = new Intent(context,Class.forName(permiso.getRuta()));
                    } catch (ClassNotFoundException e) {
                        intent = new Intent(context,ErrorActivity.class);
                    }
                    intent.putExtra("user", user);
                    intent.putExtra("userRole", userRole);
                    startActivity(intent);
                }
            });
            buttonLayout.addView(boton,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

        layout.addView(userTitle,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        layout.addView(buttonLayout,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    public void setPermisos(ArrayList<Permiso> permisos) {
        this.permisos = permisos;
    }

    public class GenericMenuTask extends AsyncTask<Void, Void, Boolean>{

        private GenericMenuFragment activity;

        public GenericMenuTask(GenericMenuFragment activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean retorno = true;
            ArrayList<Permiso> permisos = new ArrayList<>();
            try {

                String resultadoString = ServiceUtils.invokeService(new JSONObject(permission.toString()),Constants.SERVICES_OBTENER_PERMISOS,"POST");

                JSONArray jsonPermisos = new JSONArray(resultadoString);
                JSONObject rol;
                Permiso temp;
                JSONObject tmpJson;
                for (int i = 0; i < jsonPermisos.length(); i++) {
                    temp = new Permiso();
                    tmpJson = jsonPermisos.getJSONObject(i);
                    temp.setNombre(tmpJson.getString("nombre"));
                    temp.setDescripcion(tmpJson.getString("descripcion"));
                    temp.setRuta(tmpJson.getString("ruta"));
                    temp.setConsecutivoPermiso(tmpJson.getInt("consecutivoPermiso"));
                    permisos.add(temp);
                }

                activity.setPermisos(permisos);
            } catch (JSONException e) {
                retorno = false;
                e.printStackTrace();
            }

            return retorno;
        }
            @Override
        protected void onPostExecute(final Boolean success) {
        }

        @Override
        protected void onCancelled() {
        }
    }
}

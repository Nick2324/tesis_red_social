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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.usuarios.pojos.Permiso;
import sportsallaround.snadeportivo.usuarios.pojos.Rol;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.Constants;
import sportsallaround.utils.Utils;

/**
 * Created by LuisFelipe on 21/06/2015.
 */
public class GenericMenuFragment extends Fragment{

    private Context context;
    private GenericMenuTask task;
    private ArrayList<Permiso> permisos;
    private Usuario user;
    private Rol userRole;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = new GenericMenuTask(this);
        Bundle extras = getArguments();
        user = extras.getParcelable("user");
        userRole = extras.getParcelable("userRole");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(context);
        View userTitle = inflater.inflate(R.layout.user_image_title, container, false);
        ((TextView)(userTitle.findViewById(R.id.title_user_name))).setText(user.getPrimerNombre().trim() + " " + user.getSegundoNombre().trim() + " " + user.getApellidos().trim());
        ((TextView)(userTitle.findViewById(R.id.title_user_role))).setText(userRole.getNombre());
        try {
            task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Button boton;
        LinearLayout buttonLayout = new LinearLayout(context);
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
                        intent = new Intent(context,Error.class);
                    }
                    startActivity(intent);
                }
            });
            buttonLayout.addView(boton,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

        layout.addView(userTitle,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        layout.addView(buttonLayout,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return layout;
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
            InputStream is;
            boolean retorno = true;

            //String parametros = "?";
            JSONObject parametros = new JSONObject();

            try {
                URL completeUrl = new URL(Constants.ROOT_URL + Constants.SERVICES_OBTENER_PERMISOS_NODO);

                HttpURLConnection conn = (HttpURLConnection) completeUrl.openConnection();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("GET");

                //conn.setDoOutput(true);
                //conn.setDoInput(true);
                // Starts the query

                //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                //wr.write(parametros.toString());
                //wr.flush();

                conn.connect();

                is = conn.getInputStream();

                JSONArray jsonPermisos = new JSONArray(Utils.convertStreamToString(is));

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

                conn.disconnect();
            } catch (MalformedURLException e) {
                retorno = false;
                e.printStackTrace();
            } catch (ProtocolException e) {
                retorno = false;
                e.printStackTrace();
            } catch (JSONException e) {
                retorno = false;
                e.printStackTrace();
            } catch (IOException e) {
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

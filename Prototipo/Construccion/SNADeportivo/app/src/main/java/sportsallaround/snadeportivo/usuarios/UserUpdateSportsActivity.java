package sportsallaround.snadeportivo.usuarios;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.deportes.pojos.DeportePracticado;
import sportsallaround.snadeportivo.deportes.pojos.DeportePracticadoUsuario;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ServiceUtils;

public class UserUpdateSportsActivity extends ActionBarActivity {

    private View progressView;
    private LinearLayout practicedSportsLayout;
    private Usuario user;
    private UserUpdateSportsActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_sports);

        setTitle(getResources().getString(R.string.title_activity_mis_deportes));

        progressView = findViewById(R.id.practiced_sports_progress);

        GetPracticedSportsTask sportsTask = new GetPracticedSportsTask(this);
        user = (Usuario) getIntent().getExtras().get("user");

        practicedSportsLayout = (LinearLayout)findViewById(R.id.user_practiced_sports);

        showProgress(true);
        sportsTask.execute((Void) null);
        activity=this;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            practicedSportsLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            practicedSportsLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    practicedSportsLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            practicedSportsLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress(true);
        new GetPracticedSportsTask(this).execute();
    }

    public LinearLayout getPracticedSportsLayout() {
        return practicedSportsLayout;
    }

    private View getPracticedSportView(final DeportePracticado deportePracticado) {
        LinearLayout root = new LinearLayout(getApplicationContext());
        root.setOrientation(LinearLayout.VERTICAL);

        TextView nombreDeporte = new TextView(getApplicationContext());
        nombreDeporte.setText(Html.fromHtml("<b>" + deportePracticado.getDeporte().getNombre() + "<b/>"));
        nombreDeporte.setTextColor(Color.WHITE);

        LinearLayout infoDeporte = new LinearLayout(getApplicationContext());
        infoDeporte.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imgDeporte = new ImageView(getApplicationContext());

        imgDeporte.setImageResource(R.drawable.no_image_available);
        Display display = getWindowManager().getDefaultDisplay();
        int width = (int) (display.getWidth()*0.2);
        int height = (int) (display.getWidth()*0.2);
        imgDeporte.setLayoutParams(new LinearLayout.LayoutParams(width,height));

        StringBuilder posiciones = new StringBuilder();
        posiciones.append("<b>" + (posiciones.length() > 1 ? "Posiciones" : "Posicion") +":</b> ");
        for(int i=0;i<deportePracticado.getPosiciones().length;i++){
            posiciones.append(deportePracticado.getPosiciones()[i].getNombre());
            posiciones.append(",");
        }

        posiciones = new StringBuilder(posiciones.toString().substring(0,posiciones.length()-1));

        LinearLayout datosDeporte = new LinearLayout(getApplicationContext());
        datosDeporte.setOrientation(LinearLayout.VERTICAL);

        TextView posicionesView = new TextView(getApplicationContext());
        TextView nivelView = new TextView(getApplicationContext());

        posicionesView.setText(Html.fromHtml(posiciones.toString()));
        posicionesView.setTextColor(Color.WHITE);
        nivelView.setText(Html.fromHtml("<b>Nivel: </b>" + deportePracticado.getNivel()));
        nivelView.setTextColor(Color.WHITE);

        LinearLayout optionsLayout = new LinearLayout(getApplicationContext());
        optionsLayout.setGravity(Gravity.END);
        ImageView edit = new ImageView(getApplicationContext());
        ImageView remove = new ImageView(getApplicationContext());

        edit.setImageResource(R.drawable.edit_pencil);
        remove.setImageResource(R.drawable.delete_x);

        width = (int) (display.getWidth()*0.08);
        height = (int) (display.getWidth()*0.08);
        edit.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        remove.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), UserUpdatePracticedSport.class);
                intent.putExtra("deporte",deportePracticado);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showProgress(true);
                new RemovePracticedSportTask(user, activity).execute(deportePracticado);
            }
        });

        optionsLayout.addView(edit,new LinearLayout.LayoutParams(width, height));
        optionsLayout.addView(remove,new LinearLayout.LayoutParams(width, height));

        datosDeporte.addView(posicionesView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        datosDeporte.addView(nivelView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        datosDeporte.addView(optionsLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        width = (int) (display.getWidth()*0.2);
        height = (int) (display.getWidth()*0.2);

        infoDeporte.addView(imgDeporte, new LinearLayout.LayoutParams(width, height));
        infoDeporte.addView(datosDeporte, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        root.addView(nombreDeporte, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        root.addView(infoDeporte, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        root.setBackgroundColor(Color.rgb(180, 180, 180));

        root.setPadding(10,10,10,10);

        return root;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_update_sports, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPracticedSportsLayout(LinearLayout practicedSportsLayout) {
        this.practicedSportsLayout = practicedSportsLayout;
    }

    public void anadirDeporte(View view) {
        Intent intent = new Intent(getApplicationContext(),UserAddPracticedSport.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public class GetPracticedSportsTask extends AsyncTask<Void, Void, Boolean> {
        private UserUpdateSportsActivity activity;
        private ArrayList<DeportePracticado> deportesPracticados;

        public GetPracticedSportsTask(UserUpdateSportsActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean retorno = true;
            deportesPracticados = new ArrayList<DeportePracticado>();
            try {

                JSONObject parametros = new JSONObject();
                try {
                    parametros.put("user", user.getUsuario());
                } catch (JSONException e) {
                    e.printStackTrace();
                    retorno = false;
                }

                String resultadoString = ServiceUtils.invokeService_(parametros, Constants.SERVICES_OBTENER_DEPORTES_PRACTICADOS, "GET");
                if(!resultadoString.equals("")){
                    JSONArray jsonDeportes = new JSONArray(resultadoString);
                    DeportePracticado tmp;
                    for(int i=0;i<jsonDeportes.length();i++){
                        tmp = new DeportePracticado(jsonDeportes.getJSONObject(i));
                        deportesPracticados.add(tmp);
                    }
                }
            } catch (JSONException e) {
                retorno = false;
                e.printStackTrace();
            }
            return retorno;
            }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                //Toast.makeText(activity.getApplicationContext(), "Se encontraron " + deportesPracticados.size() + " deportes.", Toast.LENGTH_LONG).show();
                LinearLayout practicedSportsLayout = activity.getPracticedSportsLayout();
                practicedSportsLayout.removeAllViews();
                if(deportesPracticados != null) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,20,0,0);

                    for (DeportePracticado deportePracticado : deportesPracticados)
                        practicedSportsLayout.addView(activity.getPracticedSportView(deportePracticado), params);
                    activity.setPracticedSportsLayout(practicedSportsLayout);
                }
                showProgress(false);
            } else {
                showProgress(false);
                Toast.makeText(activity.getApplicationContext(), "Ha ocurrido un error obteniendo sus deportes practicados", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }

    public class RemovePracticedSportTask extends AsyncTask<DeportePracticado,Void,Boolean>{

        private Usuario user;
        private UserUpdateSportsActivity activity;

        RemovePracticedSportTask(){}

        public RemovePracticedSportTask(Usuario user, UserUpdateSportsActivity activity) {
            this.user = user;
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(DeportePracticado... deportePracticado) {
            boolean retorno = true;
            try {
                DeportePracticadoUsuario deporte = new DeportePracticadoUsuario();
                deporte.setUser(user.getUsuario());
                deporte.setDeportePracticado(deportePracticado[0]);
                JSONObject parametros = new JSONObject(deporte.toJSONObject());

                String resultadoString = ServiceUtils.invokeService_(parametros, Constants.SERVICES_ELIMINAR_DEPORTE_PRACTICADO, "POST");
                JSONObject jsonRespuesta = new JSONObject(resultadoString);

            } catch (JSONException e) {
                retorno = false;
                e.printStackTrace();
            }
            return retorno;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            new GetPracticedSportsTask(activity).execute();
        }
    }
}

package sportsallaround.snadeportivo.usuarios;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.ErrorFragment;
import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ErrorActivity;
import sportsallaround.snadeportivo.usuarios.pojos.Permiso;
import sportsallaround.snadeportivo.usuarios.pojos.Rol;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.utils.Constants;
import sportsallaround.utils.ServiceUtils;

public class UserMainDrawer extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Usuario user;
    private Rol userRole;
    private MainDrawerTask drawerTask;
    private Permiso[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra("user");

        drawerTask = new MainDrawerTask(this);
        try {
            drawerTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_user_main_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        String[] drawerElements = new String[permissions.length];

        for(int i=0;i<permissions.length;i++)
            drawerElements[i] = permissions[i].getNombre();

        mNavigationDrawerFragment.updateDrawerListAdapter(drawerElements);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(user, userRole, permissions, position))
                .commit();
    }

    public void onSectionAttached(int number) {
        mTitle = permissions[number].getNombre();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.user_main_drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    public void setUserRole(Rol userRole) {
        this.userRole = userRole;
    }

    public Rol getUserRole() {
        return userRole;
    }

    public void setPermissions(Permiso[] permissions) {
        this.permissions = permissions;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(Usuario user, Rol userRole, Permiso[] permisos,int sectionNumber) {
            Fragment fragment = null;
            try {
                fragment = (Fragment) Class.forName(permisos[sectionNumber].getRuta()).newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
                fragment = new ErrorFragment();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                fragment = new ErrorFragment();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                fragment = new ErrorFragment();
            } catch (NullPointerException e){

            }
            Bundle extras = new Bundle();
            extras.putParcelable("user", user);
            extras.putParcelable("userRole", userRole);
            extras.putParcelable("permission", permisos[sectionNumber]);
            fragment.setArguments(extras);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_user_main_drawer, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
    }

    public class MainDrawerTask extends AsyncTask<Void, Void, Boolean> {

        private UserMainDrawer activity;

        public MainDrawerTask(UserMainDrawer activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean retorno = true;


            retorno = getUserRole();
            if(retorno)
                retorno = getRolePermissions();

            return retorno;
        }

        private Boolean getRolePermissions() {
            boolean retorno = true;
            Permiso[] permisos = null;
            try {
                JSONObject parametros = new JSONObject(activity.getUserRole().toString());
                String responseString = ServiceUtils.invokeService(parametros, Constants.SERVICES_OBTENER_PERMISOS_ROL, "POST");
                JSONArray response = new JSONArray(responseString);
                if(response.getJSONObject(0) != null)
                    permisos = new Permiso[response.length()+1];
                else
                    permisos = new Permiso[response.length()];
                    for (int i = 1; i <= response.length(); i++){
                        permisos[i] = new Permiso(response.getJSONObject(i - 1));
                    }
                activity.setPermissions(permisos);
            } catch (JSONException e) {
                e.printStackTrace();
                retorno = false;
                permisos = new Permiso[1];
            } finally {
                permisos[0] = new Permiso();
                permisos[0].setConsecutivoPermiso(-1);
                permisos[0].setNombre("Yo");
                permisos[0].setDescripcion("Página principal del usuario");
                permisos[0].setRuta("sportsallaround.snadeportivo.usuarios.fragmentos.UserMainFragment");
                activity.setPermissions(permisos);
            }
            return retorno;
        }

        private Boolean getUserRole() {
            boolean retorno = true;
            try {
                JSONObject parametros = new JSONObject();
                parametros.put("userName",user.getUsuario());

                String responseString = ServiceUtils.invokeService(parametros,Constants.SERVICES_OBTENER_ROL_USUARIO,"GET");

                JSONObject response = new JSONObject(responseString);
                Rol userRole = new Rol(response);
                activity.setUserRole(userRole);
            } catch (JSONException e) {
                e.printStackTrace();
                retorno = false;
            }
            return retorno;
        }
    }
}

package sportsallaround.snadeportivo;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import sportsallaround.utils.Constants;
import sportsallaround.utils.DatePickerFragment;
import sportsallaround.utils.OnDatePickedListener;
import sportsallaround.utils.ServiceUtils;
import sportsallaround.utils.Utils;


public class CreateUserActivity extends Activity implements OnDatePickedListener {

    private CreateUserTask mAuthTask = null;

    private EditText firstName;
    private EditText middleName;
    private EditText lastName;
    private EditText eMail;
    private EditText password;
    private EditText passwordConfirmation;
    private EditText birthDate;
    private Spinner gender;
    private Spinner userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        firstName = (EditText) findViewById(R.id.new_user_first_name);
        middleName = (EditText) findViewById(R.id.new_user_middle_name);
        lastName = (EditText) findViewById(R.id.new_user_last_name);
        eMail = (AutoCompleteTextView) findViewById(R.id.new_user_e_mail);
        password = (EditText) findViewById(R.id.new_user_password);
        passwordConfirmation = (EditText) findViewById(R.id.new_user_password_confirmation);
        birthDate = (EditText) findViewById(R.id.new_user_birth_date);
        gender = (Spinner) findViewById(R.id.new_user_gender);
        userType = (Spinner) findViewById(R.id.new_user_type);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender.setAdapter(genderAdapter);

        new RetrieveRoles(userType,this).execute((Void) null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
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

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void createUser(View view) {
        if (mAuthTask != null)
            return;


        if (!datosValidos())
            return;

        mAuthTask = new CreateUserTask(
                firstName.getText().toString(),
                middleName.getText().toString(),
                lastName.getText().toString(),
                eMail.getText().toString(),
                password.getText().toString(),
                birthDate.getText().toString(),
                gender.getSelectedItem().toString(),
                userType.getSelectedItem().toString(),
                this);
        mAuthTask.execute((Void) null);

    }

    private boolean datosValidos() {
        boolean resultado = true;

        if (!(firstName.getText().toString().length() > 0)) {
            resultado = false;
            firstName.setError("Este campo es obligatorio");
            firstName.requestFocus();
        }
        if (resultado && !(lastName.getText().toString().length() > 0)) {
            resultado = false;
            lastName.setError("Este campo es obligatorio");
            lastName.requestFocus();
        }
        if (resultado && !(eMail.getText().toString().length() > 0)) {
            resultado = false;
            eMail.setError("Este campo es obligatorio");
            eMail.requestFocus();
        }
        if (resultado && !(Utils.isValidEmail(eMail.getText().toString()))) {
            resultado = false;
            eMail.setError("Correo electronico no valido");
        }
        if (resultado && !(password.getText().toString().length() > 0)) {
            resultado = false;
            password.setError("Este campo es obligatorio");
            password.requestFocus();
        }
        if (resultado && !(passwordConfirmation.getText().toString().length() > 0)) {
            resultado = false;
            passwordConfirmation.setError("Este campo es obligatorio");
            passwordConfirmation.requestFocus();
        }
        if (resultado && !(password.getText().toString().equals(passwordConfirmation.getText().toString()))) {
            resultado = false;
            password.setError("Las contrasenas no concuerdan");
            password.requestFocus();
        }
        if (resultado && !(Utils.isValidDate(birthDate.getText().toString()))) {
            resultado = false;
            birthDate.setError("Fecha de nacimiento no valida");
            birthDate.requestFocus();
        }
        return resultado;
    }

    @Override
    public void onDatePicked(String selectedDate) {
        ((EditText) birthDate).setText(selectedDate);
    }

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

    public class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

        private ContentValues datosUsuario;
        private Context context;

        CreateUserTask(/*String userName,*/
                       String firstName,
                       String middleName,
                       String lastName,
                       String eMail,
                       String password,
                       String birthDate,
                       String gender,
                       String type,
                       Context context) {
            datosUsuario = new ContentValues();
            datosUsuario.put("usuario", eMail);
            datosUsuario.put("contrasena", Utils.StringToSHA1(password));
            datosUsuario.put("primerNombre", firstName);
            datosUsuario.put("segundoNombre", middleName);
            datosUsuario.put("apellidos", lastName);
            datosUsuario.put("fechaNacimiento", birthDate);
            datosUsuario.put("sexo", gender);
            datosUsuario.put("tipoUsuario", type);
            datosUsuario.put("eMail",eMail);
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            InputStream is;
            boolean retorno = true;

            JSONObject parametros = new JSONObject();
            try {
                parametros.put("usuario", datosUsuario.get("usuario"));
                parametros.put("contrasena", datosUsuario.get("contrasena"));
                parametros.put("primerNombre", datosUsuario.get("primerNombre"));
                parametros.put("segundoNombre", datosUsuario.get("segundoNombre"));
                parametros.put("apellidos", datosUsuario.get("apellidos"));
                parametros.put("fechaNacimiento", datosUsuario.get("fechaNacimiento"));
                parametros.put("sexo", datosUsuario.get("sexo"));
                parametros.put("tipoUsuario", datosUsuario.get("tipoUsuario"));
                parametros.put("eMail", datosUsuario.get("eMail"));
            } catch (JSONException e) {
                e.printStackTrace();
                retorno = false;
            }

            String responseString = ServiceUtils.invokeService(parametros, Constants.SERVICES_CREAR_USUARIO, "POST");

            try {
                JSONObject response = new JSONObject(responseString);
                if (response.get("caracterAceptacion").equals("M"))
                    retorno = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return retorno;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
            } else {
                Toast.makeText(context,"Un usuario con el mismo correo ya existe", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}

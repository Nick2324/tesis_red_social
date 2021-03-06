package sportsallaround.snadeportivo.usuarios;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.snadeportivo.usuarios.tasks.RetrieveRoles;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.gui.DatePickerFragment;
import sportsallaround.utils.gui.OnDatePickedListener;
import sportsallaround.utils.generales.ServiceUtils;
import sportsallaround.utils.generales.Utils;


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

        setTitle(getResources().getString(R.string.title_activity_crear_usuario));

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
                R.array.gender, R.layout.spinner_black_item);
        // Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(R.layout.spinner_black_item);
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
        birthDate.setText(selectedDate);
    }



    public class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

        private ContentValues datosUsuario;
        private Context context;
        private String rutaUsuario;

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

            String responseString = ServiceUtils.invokeService_(parametros, Constants.SERVICES_CREAR_USUARIO, "POST");

            try {
                JSONObject response = new JSONObject(responseString);
                if (response.get("caracterAceptacion").equals("M"))
                    retorno = false;
                else
                    rutaUsuario = response.getString("datosExtra");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return retorno;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                //todo: Invocar pagina inicial del usuario con la ruta retornada por la creacion del mismo.
                Intent intent = new Intent(getApplicationContext(), UserMainDrawer.class);
                Usuario usuarioCreado = new Usuario();
                usuarioCreado.setUsuario((String) datosUsuario.get("usuario"));
                usuarioCreado.setPrimerNombre((String) datosUsuario.get("primerNombre"));
                usuarioCreado.setSegundoNombre((String) datosUsuario.get("segundoNombre"));
                usuarioCreado.setApellidos((String) datosUsuario.get("apellidos"));
                intent.putExtra("user", usuarioCreado);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else
                Toast.makeText(context,"Un usuario con el mismo correo ya existe", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}

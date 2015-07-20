package sportsallaround.snadeportivo.usuarios;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.interfaces.DSAParams;
import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.usuarios.pojos.Rol;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;
import sportsallaround.snadeportivo.usuarios.tasks.RetreiveUserData;
import sportsallaround.snadeportivo.usuarios.tasks.RetrieveRoles;
import sportsallaround.utils.Constants;
import sportsallaround.utils.DatePickerFragment;
import sportsallaround.utils.ObtainUserInfo;
import sportsallaround.utils.OnDatePickedListener;
import sportsallaround.utils.ServiceUtils;
import sportsallaround.utils.Utils;

public class UserUpdatePersonalInfo extends ActionBarActivity implements OnDatePickedListener, ObtainUserInfo {

    private Usuario actualUserData;
    private Rol actualUserRole;

    private EditText firstName;
    private EditText middleName;
    private EditText lastName;
    private EditText newPassword;
    private EditText newPasswordConfirmation;
    private EditText birthDate;
    private Spinner userType;
    private Button updateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_personal_info);

        firstName = (EditText) findViewById(R.id.update_user_first_name);
        middleName = (EditText) findViewById(R.id.update_user_middle_name);
        lastName = (EditText) findViewById(R.id.update_user_last_name);
        newPassword = (EditText) findViewById(R.id.update_user_password);
        newPasswordConfirmation = (EditText) findViewById(R.id.update_user_password_confirmation);
        birthDate = (EditText) findViewById(R.id.update_user_birth_date);
        userType = (Spinner) findViewById(R.id.update_user_type);

        updateUser = (Button) findViewById(R.id.update_user_button);

        actualUserData = getIntent().getParcelableExtra("user");
        actualUserRole = getIntent().getParcelableExtra("userRole");

        try {
            new RetreiveUserData(actualUserData, this).execute((Void) null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        firstName.setText(actualUserData.getPrimerNombre());
        middleName.setText(actualUserData.getSegundoNombre());
        lastName.setText(actualUserData.getApellidos());
        birthDate.setText(actualUserData.getFechaNacimiento());

        new RetrieveRoles(userType,this).execute((Void) null);

        userType.setSelection(actualUserRole.getConsecutivoRol());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_update_personal_info, menu);
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

    @Override
    public void onDatePicked(String selectedDate) {
        birthDate.setText(selectedDate);
    }


    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void updateUser(View view){

        if(validateNewInfo()){
            UpdateUserTask uTask = new UpdateUserTask(
                    firstName.getText().toString(),
                    middleName.getText().toString(),
                    lastName.getText().toString(),
                    actualUserData.geteMail(),
                    newPassword.getText().toString().equals("") ? actualUserData.getContrasena() : Utils.StringToSHA1(newPassword.getText().toString()),
                    birthDate.getText().toString(),
                    actualUserData.getSexo(),
                    userType.getSelectedItem().toString(),
                    this
            );

        uTask.execute((Void) null);
        }

    }



    private boolean validateNewInfo(){
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
        if(!newPassword.getText().toString().equals("") && !newPasswordConfirmation.getText().toString().equals("")){
            if (resultado && !(newPassword.getText().toString().length() > 0)) {
                resultado = false;
                newPassword.setError("Este campo es obligatorio");
                newPassword.requestFocus();
            }
            if (resultado && !(newPasswordConfirmation.getText().toString().length() > 0)) {
                resultado = false;
                newPasswordConfirmation.setError("Este campo es obligatorio");
                newPasswordConfirmation.requestFocus();
            }
            if (resultado && !(newPassword.getText().toString().equals(newPasswordConfirmation.getText().toString()))) {
                resultado = false;
                newPassword.setError("Las contrasenas no concuerdan");
                newPassword.requestFocus();
            }
        }
        if (resultado && !(Utils.isValidDate(birthDate.getText().toString()))) {
            resultado = false;
            birthDate.setError("Fecha de nacimiento no valida");
            birthDate.requestFocus();
        }
        return resultado;
    }

    @Override
    public void setUserInfo(Usuario userInfo) {
        this.actualUserData = userInfo;
    }

    @Override
    public Usuario getActualUserData() {
        return this.actualUserData;
    }

    public class UpdateUserTask extends AsyncTask<Void, Void, Boolean> {

        private ContentValues datosUsuario;
        private Context context;

        UpdateUserTask(
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
            datosUsuario.put("contrasena", password);
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

            String responseString = ServiceUtils.invokeService(parametros, Constants.SERVICES_ACTUALIZAR_USUARIO, "POST");

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

            if (success) {
                Toast.makeText(context,"Datos de usuario actualizados correctamente.",Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(context,"Error", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onCancelled() {

        }
    }

}

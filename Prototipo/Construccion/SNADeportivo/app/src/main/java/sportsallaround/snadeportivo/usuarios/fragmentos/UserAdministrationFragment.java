package sportsallaround.snadeportivo.usuarios.fragmentos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import sportsallaround.snadeportivo.R;

/**
 * Created by LuisFelipe on 15/06/2015.
 */
public class UserAdministrationFragment extends Fragment{

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_user_administration, container, false);
        Button updatePersonalInfo = (Button) layout.findViewById(R.id.fragment_administration_update_personal_info);
        Button updatePracticedSports = (Button) layout.findViewById(R.id.fragment_administration_update_practiced_sports);
        Button closeAccount = (Button) layout.findViewById(R.id.fragment_administration_close_account);

        updatePersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Evento Boton", Toast.LENGTH_LONG).show();
            }
        });

        updatePracticedSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        closeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }
}

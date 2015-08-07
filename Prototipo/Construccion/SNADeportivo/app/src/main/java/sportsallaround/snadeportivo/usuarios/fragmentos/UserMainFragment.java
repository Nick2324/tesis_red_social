package sportsallaround.snadeportivo.usuarios.fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.usuarios.pojos.Rol;
import sportsallaround.snadeportivo.usuarios.pojos.Usuario;

/**
 * Created by LuisFelipe on 15/06/2015.
 */
public class UserMainFragment extends Fragment {

    private Usuario user;
    private Rol userRole;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        user = extras.getParcelable("user");
        userRole = extras.getParcelable("userRole");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_user_page, container, false);
        ((TextView)(layout.findViewById(R.id.title_user_name))).setText(user.getPrimerNombre().trim() + " " + user.getSegundoNombre().trim() + " " + user.getApellidos().trim());
        ((TextView)(layout.findViewById(R.id.title_user_role))).setText(userRole.getNombre());
        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}

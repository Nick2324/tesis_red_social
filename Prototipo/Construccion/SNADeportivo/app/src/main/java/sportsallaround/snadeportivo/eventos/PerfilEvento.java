package sportsallaround.snadeportivo.eventos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sna_deportivo.pojo.evento.Evento;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;

public class PerfilEvento extends Activity {

    private Evento evento;
    private MenuEventos menuEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_evento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String funcionalidad = getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).
                getString(Constants.FUNCIONALIDAD);
        String tipoMenu = getIntent().getExtras().
                getBundle(Constants.DATOS_FUNCIONALIDAD).
                getString(ConstantesEvento.TIPO_MENU);
        this.menuEventos = new ProductorMenuEventos().proveerMenuEventos(tipoMenu);
        getMenuInflater().inflate(menuEventos.getMenuId(), menu);
        this.menuEventos.esconderSegunFuncionalidadRol(this, funcionalidad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if(this.volcarDatosObjeto()) {
        if(true) {
            Bundle extras = getIntent().getExtras().
                    getBundle(Constants.DATOS_FUNCIONALIDAD);
            this.menuEventos.comportamiento(this, item.getItemId(),
                    extras);
        }
        return super.onOptionsItemSelected(item);
    }
}

package sportsallaround.snadeportivo.eventos;

import android.app.Activity;;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Constants;
import sportsallaround.utils.gui.TituloActividad;

public class MenuAdministrarInvolucrados extends Activity{

    private MenuEventos menuEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrar_involucrados);
        setTitle(getResources().getString(R.string.titulo_botones_admin_participantes));
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
        this.menuEventos.comportamiento(this, item.getItemId(),
                getIntent().getExtras().
                        getBundle(Constants.DATOS_FUNCIONALIDAD));
        return super.onOptionsItemSelected(item);
    }

    public void startAniadirPart(View v){
        startActivity(new Intent(this,ManejoParticipantes.class));
    }

    public void startConsultarPart(View v){
        startActivity(new Intent(this,ListadoParticipantes.class));
    }

    public void startCaracteristicasPart(View v){
        startActivity(new Intent(this,InformacionParticipantes.class));
    }

}

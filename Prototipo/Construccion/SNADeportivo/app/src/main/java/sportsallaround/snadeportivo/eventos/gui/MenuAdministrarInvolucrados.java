package sportsallaround.snadeportivo.eventos.gui;

import android.app.Activity;;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ProductorMenuEventos;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.utils.generales.Constants;

public class MenuAdministrarInvolucrados extends Activity{

    private MenuSNS menuSNS;

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
        this.menuSNS = new ProductorMenuEventos().proveerMenuEventos(tipoMenu);
        getMenuInflater().inflate(menuSNS.getIdMenu(), menu);
        this.menuSNS.esconderItems(menu, new ProductorMenuEventos().
                proveerAnalizadorItem(funcionalidad));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.menuSNS.comportamiento(this, item.getItemId(),
                getIntent().getExtras().
                        getBundle(Constants.DATOS_FUNCIONALIDAD));
        return super.onOptionsItemSelected(item);
    }

    private Intent construirIntent(Class clase){
        Intent intent = new Intent(this,clase);
        intent.putExtra(Constants.DATOS_FUNCIONALIDAD,
                getIntent().getExtras().getBundle(Constants.DATOS_FUNCIONALIDAD));
        return intent;
    }

    public void startAniadirPart(View v){
        startActivity(this.construirIntent(ListadoSolicitudes.class));
    }

    public void startInvitarPart(View v){
        startActivity(this.construirIntent(ListadoInvitarAParticipar.class));
    }

    public void startConsultarPart(View v){
        startActivity(this.construirIntent(ListadoParticipantes.class));
    }

    public void startCaracteristicasPart(View v){
        startActivity(this.construirIntent(InformacionParticipantes.class));
    }

}

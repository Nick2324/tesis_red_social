package sportsallaround.snadeportivo.eventos.menu.administrador;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.menu.general.ItemGestionUbicacionesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ItemInformacionGeneralEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ItemInformacionParticipantes;
import sportsallaround.snadeportivo.eventos.menu.general.ItemPerfilEvento;
import sportsallaround.utils.generales.MenuSNS;

/**
 * Created by nicolas on 12/09/15.
 */
public class MenuAdministradorEventos extends MenuSNS {

    public MenuAdministradorEventos(){
        super(R.menu.menu_accion_administrador_evento);
        this.construirItemsMenu();
    }

    private void construirItemsMenu(){
        super.aniadirItem(new ItemInformacionGeneralEvento(R.id.item_info_general_evento_admin));
        super.aniadirItem(new ItemInformacionParticipantes(R.id.item_info_participantes));
        super.aniadirItem(new ItemPerfilEvento(R.id.item_perfil_evento_admin));
        super.aniadirItem(new ItemGestionarInvolucradosEvento(R.id.item_gestionar_involucrados_eve_admin));
        super.aniadirItem(new ItemGestionUbicacionesEvento(R.id.item_gestion_ubicaciones_eve_admin));
        super.aniadirItem(new ItemCrearEvento(R.id.item_crear_evento));
        super.aniadirItem(new ItemActualizarEvento(R.id.item_actualizar_evento));
        super.aniadirItem(new ItemCancelarEvento(R.id.item_cancelar_evento_admin));
    }

}

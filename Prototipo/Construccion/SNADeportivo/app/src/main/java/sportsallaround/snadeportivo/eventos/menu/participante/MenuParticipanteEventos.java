package sportsallaround.snadeportivo.eventos.menu.participante;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.menu.general.ItemGestionUbicacionesEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ItemInformacionGeneralEvento;
import sportsallaround.snadeportivo.eventos.menu.general.ItemPerfilEvento;
import sportsallaround.utils.generales.MenuSNS;

/**
 * Created by nicolas on 12/09/15.
 */
public class MenuParticipanteEventos extends MenuSNS {

    public MenuParticipanteEventos(){
        super(R.menu.menu_participante_evento);
        this.construirMenu();
    }

    private void construirMenu(){
        super.aniadirItem(new ItemInformacionGeneralEvento(R.id.item_info_general_evento));
        super.aniadirItem(new ItemPerfilEvento(R.id.item_perfil_evento));
        super.aniadirItem(new ItemGestionUbicacionesEvento(R.id.item_ver_ubicaciones_evento));
        super.aniadirItem(new ItemDejarEventoParticipante(R.id.item_dejar_evento_participante));
        super.aniadirItem(new ItemInformacionParticipantes(R.id.item_informacion_participantes));
        super.aniadirItem(new ItemUnirseEventoParticipante(R.id.item_unirse_evento_participante));
    }

}

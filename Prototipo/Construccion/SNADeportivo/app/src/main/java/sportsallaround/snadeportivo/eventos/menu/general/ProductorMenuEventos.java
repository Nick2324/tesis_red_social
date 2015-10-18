package sportsallaround.snadeportivo.eventos.menu.general;

import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.snadeportivo.eventos.menu.administrador.AnalizadorItemAdminEvento;
import sportsallaround.snadeportivo.eventos.menu.administrador.MenuAdministradorEventos;
import sportsallaround.snadeportivo.eventos.menu.participante.AnalizadorItemParticipaEve;
import sportsallaround.utils.generales.AnalizadorUsoItem;
import sportsallaround.utils.generales.MenuSNS;
import sportsallaround.snadeportivo.eventos.menu.participante.MenuParticipanteEventos;

/**
 * Created by nicolas on 13/09/15.
 */
public class ProductorMenuEventos {

    public ProductorMenuEventos(){}

    public MenuSNS proveerMenuEventos(String menu){
        if(ConstantesEvento.MENU_ADMIN_EVENTOS.equals(menu)){
            return new MenuAdministradorEventos();
        }else if(ConstantesEvento.MENU_PART_EVENTOS.equals(menu)){
            return new MenuParticipanteEventos();
        }

        return null;

    }

    public AnalizadorUsoItem proveerAnalizadorItem(String funcionalidad){
        if(funcionalidad.equals(ConstantesEvento.ACTUALIZAR_EVENTO) ||
           funcionalidad.equals(ConstantesEvento.CREAR_EVENTO)){
            return new AnalizadorItemAdminEvento(funcionalidad);
        }else if(funcionalidad.equals(ConstantesEvento.PARTICIPANTE_EVENTO)||
                funcionalidad.equals(ConstantesEvento.POSIBLE_PARTICIPANTE_EVENTO)){
            return new AnalizadorItemParticipaEve(funcionalidad);
        }

        return null;

    }

}

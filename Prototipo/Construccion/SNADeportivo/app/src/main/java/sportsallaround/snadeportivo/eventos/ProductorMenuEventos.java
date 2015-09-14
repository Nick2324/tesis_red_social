package sportsallaround.snadeportivo.eventos;

/**
 * Created by nicolas on 13/09/15.
 */
public class ProductorMenuEventos {

    public ProductorMenuEventos(){}

    public MenuEventos proveerMenuEventos(String menu){
        if(ConstantesEvento.MENU_ADMIN_EVENTOS.equals(menu)){
            return new MenuAdministradorEventos();
        }else if(ConstantesEvento.MENU_PART_EVENTOS.equals(menu)){
            return new MenuParticipanteEventos();
        }

        return null;

    }

}

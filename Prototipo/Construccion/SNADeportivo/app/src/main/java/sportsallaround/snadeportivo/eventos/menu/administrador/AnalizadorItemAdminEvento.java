package sportsallaround.snadeportivo.eventos.menu.administrador;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.ConstantesEvento;
import sportsallaround.utils.generales.AnalizadorUsoItem;

/**
 * Created by nicolas on 17/10/15.
 */
public class AnalizadorItemAdminEvento implements AnalizadorUsoItem{

    private String funcionalidad;

    public AnalizadorItemAdminEvento(String funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    @Override
    public ArrayList<Integer> analizarUsoItems(ArrayList<Integer> items) {
        ArrayList<Integer> analizados = new ArrayList<Integer>();
        if(this.funcionalidad.equals(ConstantesEvento.CREAR_EVENTO)){
            if(items.contains(R.id.item_gestionar_involucrados_eve_admin)){
                analizados.add(R.id.item_gestionar_involucrados_eve_admin);
            }
            if(items.contains(R.id.item_perfil_evento_admin)){
                analizados.add(R.id.item_perfil_evento_admin);
            }
            if(items.contains(R.id.item_cancelar_evento_admin)){
                analizados.add(R.id.item_cancelar_evento_admin);
            }
            if(items.contains(R.id.item_actualizar_evento)){
                analizados.add(R.id.item_actualizar_evento);
            }
        }else if(this.funcionalidad.equals(ConstantesEvento.ACTUALIZAR_EVENTO)){
            if(items.contains(R.id.item_crear_evento)){
                analizados.add(R.id.item_crear_evento);
            }
            if(items.contains(R.id.item_info_participantes)){
                analizados.add(R.id.item_info_participantes);
            }
        }
        return analizados;
    }
}

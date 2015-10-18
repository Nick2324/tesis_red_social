package sportsallaround.snadeportivo.eventos.menu.participante;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.eventos.general.ConstantesEvento;
import sportsallaround.utils.generales.AnalizadorUsoItem;

/**
 * Created by nicolas on 17/10/15.
 */
public class AnalizadorItemParticipaEve implements AnalizadorUsoItem {

    private String funcionalidad;

    public AnalizadorItemParticipaEve(String funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    @Override
    public ArrayList<Integer> analizarUsoItems(ArrayList<Integer> items) {
        ArrayList<Integer> analizados = new ArrayList<Integer>();
        if(this.funcionalidad.equals(ConstantesEvento.POSIBLE_PARTICIPANTE_EVENTO)) {
            if(items.contains(R.id.item_dejar_evento_participante)){
                analizados.add(R.id.item_dejar_evento_participante);
            }
        }else if(this.funcionalidad.equals(ConstantesEvento.PARTICIPANTE_EVENTO)){
            if (items.contains(R.id.item_unirse_evento_participante)) {
                analizados.add(R.id.item_unirse_evento_participante);
            }
        }
        return analizados;
    }
}

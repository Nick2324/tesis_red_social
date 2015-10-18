package sportsallaround.snadeportivo.eventos.peticiones;

import android.content.Context;

import com.sna_deportivo.pojo.evento.TiposEventos;
import com.sna_deportivo.pojo.usuarios.Usuario;

import sportsallaround.utils.generales.Constants;

/**
 * Created by nicolas on 18/10/15.
 */
public class PeticionEventosAsistente extends PeticionEventos{


    public PeticionEventosAsistente(Context contexto, TiposEventos tipoEvento, Usuario usuario) {
        super(contexto, tipoEvento, usuario);
    }

    @Override
    public void calcularServicio(){
        if(super.usuario != null){
            super.servicio = Constants.SERVICES_PATH_USUARIOS+
                    super.usuario.getUsuario() + "/" +
                    Constants.SERVICES_PATH_EVENTOS +
                    super.tipoEvento.getServicio() + "?propietario=N";
        }
    }
}

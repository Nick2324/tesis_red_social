package sportsallaround.snadeportivo.deportes.pojos;

import sportsallaround.utils.MediaTypeTranslator;

/**
 * Created by luis on 7/29/15.
 */
public class DeportePracticadoUsuario implements MediaTypeTranslator{

    private DeportePracticado deportePracticado;
    private String user;
    public DeportePracticado getDeportePracticado() {
        return deportePracticado;
    }
    public void setDeportePracticado(DeportePracticado deportePracticado) {
        this.deportePracticado = deportePracticado;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toJSONObject() {
        return "{" + "\"deportePracticado\":" + deportePracticado.toJSONObject() + ",\"user\":\"" + user + "\"" + "}";
    }
}

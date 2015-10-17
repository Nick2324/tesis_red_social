package sportsallaround.utils.generales;

import sportsallaround.snadeportivo.deportes.pojos.PosicionDeporte;

/**
 * Created by luis on 7/28/15.
 */
public interface ObtainSportPositions {

    public void setSportPositions (PosicionDeporte[] posiciones);

    public PosicionDeporte[] getSportPositions();
}

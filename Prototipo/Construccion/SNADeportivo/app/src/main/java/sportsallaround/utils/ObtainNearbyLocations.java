package sportsallaround.utils;

import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;

/**
 * Created by luis on 9/30/15.
 */
public interface ObtainNearbyLocations {

    public LugarPractica[] getNearbyLocations();
    public void setNearbyLocations(LugarPractica[] locations);
}

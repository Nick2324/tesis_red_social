package sportsallaround.snadeportivo.ubicaciones;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ubicaciones.general.ConstantesUbicacion;
import sportsallaround.snadeportivo.ubicaciones.pojos.Lugar;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarEvento;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarUsuario;
import sportsallaround.snadeportivo.ubicaciones.pojos.Ubicacion;
import sportsallaround.snadeportivo.usuarios.tasks.RetreiveNearbyLocations;
import sportsallaround.snadeportivo.usuarios.tasks.RetrieveNearbyEvents;
import sportsallaround.snadeportivo.usuarios.tasks.RetrieveNearbyUsers;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.generales.ObtainNearbyEvents;
import sportsallaround.utils.generales.ObtainNearbyLocations;
import sportsallaround.utils.generales.ObtainNearbyUsers;

public class NearbyLocationsActivity extends FragmentActivity implements ObtainNearbyLocations,ObtainNearbyUsers,ObtainNearbyEvents,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private Location mLastLocation;
    private LatLng ubicacion;
    private ArrayList<ArrayList<Lugar>> lugares = new ArrayList<>();
    private boolean lugaresPracticaCargados = false;
    private boolean lugaresEventosCargados = true;
    private boolean lugaresUsuariosCargados = true;
    private Ubicacion ubicacionInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            String foco = getIntent().getBundleExtra(Constants.DATOS_FUNCIONALIDAD)
                    .getString(ConstantesUbicacion.UBICACION_FOCO);
            ubicacionInicial = new Ubicacion(new JSONObject(foco));
        } catch (JSONException e) {
            ubicacionInicial = null;
            e.printStackTrace();
        } catch (NullPointerException e){
            ubicacionInicial = null;
            e.printStackTrace();
        }

        buildGoogleApiClient();
        setContentView(R.layout.activity_nearby_locations);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {

        this.mMap = mMap;
        if(ubicacionInicial==null){
            if(ubicacion == null)
                obtenerUbicacion();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
            mMap.addCircle(new CircleOptions().center(ubicacion).radius(2).fillColor(Color.BLUE).strokeColor(Color.BLUE));
        }else
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubicacionInicial.getLugar().getLatitud(),ubicacionInicial.getLugar().getLongitud()), 15));
        new RetreiveNearbyLocations(this).execute();
        new RetrieveNearbyEvents(this).execute();
        new RetrieveNearbyUsers(this).execute();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void obtenerUbicacion(){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            ubicacion = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }else
            ubicacion = new LatLng(4.753702, -74.100071);
    }

    @Override
    public void onConnected(Bundle bundle) {
        obtenerUbicacion();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public LugarPractica[] getNearbyLocations() {
        return null;
    }

    @Override
    public void setNearbyLocations(LugarPractica[] locations) {
        lugaresPracticaCargados = true;
        if(locations != null)
            fusionarUbicaciones(locations);
    }

    @Override
    public void setNearbyUsers(LugarUsuario[] locations) {
        lugaresUsuariosCargados = true;
        if(locations != null)
            fusionarUbicaciones(locations);
    }

    @Override
    public void setNearbyEvents(LugarEvento[] locations) {
        lugaresEventosCargados = true;
        if(locations != null)
            fusionarUbicaciones(locations);
    }

    public void fusionarUbicaciones(Lugar[] lugaresRecibidos){
        boolean existente = false;
        ArrayList<Lugar> tmp;
        for (Lugar lugarRecibido : lugaresRecibidos){
            if (lugarRecibido != null) {
                for (ArrayList<Lugar> lugarExistente : lugares)
                    if (lugarExistente.get(0).getLatitud() == lugarRecibido.getLatitud() &&
                            lugarExistente.get(0).getLongitud() == lugarRecibido.getLongitud()) {
                        lugarExistente.add(lugarRecibido);
                        existente = true;
                        break;
                    }
                if (!existente) {
                    tmp = new ArrayList<>();
                    tmp.add(lugarRecibido);
                    lugares.add(tmp);
                    existente = false;
                }
            }
        }
        if(lugaresPracticaCargados && lugaresEventosCargados && lugaresUsuariosCargados )
            mostrarMarcadores();
    }

    public void mostrarMarcadores(){

        Bitmap marker = null;
        Bitmap marker_reduced = null;
        Lugar lugarMostrar = null;

        for(final ArrayList<Lugar> lugar : lugares){
            if(lugar.size() == 1) {
                lugarMostrar = new Lugar() {
                    @Override
                    public String getDescripcion() {
                        return lugar.get(0).getDescripcion();
                    }

                    @Override
                    public int getBitmap() {
                        return lugar.get(0).getBitmap();
                    }
                };
                lugarMostrar.setNombre(lugar.get(0).getNombre());
            }else{
                lugarMostrar = new Lugar() {
                    @Override
                    public String getDescripcion() {
                        String descripcion = "";
                        for (Lugar lugarCompartido : lugar)
                            descripcion = descripcion + "\n" + lugarCompartido.getNombre() + " - " + lugarCompartido.getDescripcion();
                        return descripcion;
                    }

                    @Override
                    public int getBitmap() {
                        return R.drawable.event_marker;
                    }
                };
                lugarMostrar.setNombre("Multiples resultados");
            }

            lugarMostrar.setLatitud(lugar.get(0).getLatitud());
            lugarMostrar.setLongitud(lugar.get(0).getLongitud());

            marker= BitmapFactory.decodeResource(getResources(), lugarMostrar.getBitmap());
            marker_reduced= Bitmap.createScaledBitmap(marker, marker.getWidth() / 6, marker.getHeight() / 6, false);
            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lugarMostrar.getLatitud(), lugarMostrar.getLongitud()))
                            .title(lugarMostrar.getNombre())
                            .snippet(lugarMostrar.getDescripcion())
                            .icon(BitmapDescriptorFactory.fromBitmap(marker_reduced))

            );
        }
    }
}
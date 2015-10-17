package sportsallaround.snadeportivo.ubicaciones;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.usuarios.tasks.RetreiveNearbyLocations;
import sportsallaround.utils.generales.ObtainNearbyLocations;

public class NearbyLocationsActivity extends FragmentActivity implements ObtainNearbyLocations,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LatLng ubicacion;
    private LugarPractica[] locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        try {
            new RetreiveNearbyLocations(this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_nearby_locations);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        if(ubicacion == null)
            obtenerUbicacion();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
        //mMap.moveCamera(CameraUpdateFactory.zoomBy(12));
        mMap.addCircle(new CircleOptions().center(ubicacion).radius(2).fillColor(Color.BLUE).strokeColor(Color.BLUE));
        for(LugarPractica lugar : locations){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lugar.getLatitud(),lugar.getLongitud()))
                    .title(lugar.getNombre())
                    .snippet("Deportes practicados: PENDIENTE" )
            );
        }
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
        return this.locations;
    }

    @Override
    public void setNearbyLocations(LugarPractica[] locations) {
        this.locations = locations;
    }
}
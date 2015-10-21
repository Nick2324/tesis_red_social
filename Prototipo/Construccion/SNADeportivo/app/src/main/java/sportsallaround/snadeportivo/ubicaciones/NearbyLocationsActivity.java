package sportsallaround.snadeportivo.ubicaciones;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import java.util.concurrent.ExecutionException;

import sportsallaround.snadeportivo.R;
import sportsallaround.snadeportivo.ubicaciones.pojos.LugarPractica;
import sportsallaround.snadeportivo.usuarios.tasks.RetreiveNearbyLocations;
import sportsallaround.snadeportivo.usuarios.tasks.RetrieveNearbyEvents;
import sportsallaround.snadeportivo.usuarios.tasks.RetrieveNearbyUsers;
import sportsallaround.utils.generales.ObtainNearbyEvents;
import sportsallaround.utils.generales.ObtainNearbyLocations;
import sportsallaround.utils.generales.ObtainNearbyUsers;

public class NearbyLocationsActivity extends FragmentActivity implements ObtainNearbyLocations,ObtainNearbyUsers,ObtainNearbyEvents,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private Location mLastLocation;
    private LatLng ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_nearby_locations);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        this.mMap = mMap;
        if(ubicacion == null)
            obtenerUbicacion();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
        mMap.addCircle(new CircleOptions().center(ubicacion).radius(2).fillColor(Color.BLUE).strokeColor(Color.BLUE));

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
        Bitmap sport_marker= BitmapFactory.decodeResource(getResources(), R.drawable.sport_marker);
        Bitmap sport_marker_reduced= Bitmap.createScaledBitmap(sport_marker, sport_marker.getWidth() / 8, sport_marker.getHeight() / 8, false);

        for(LugarPractica lugar : locations){
            if(lugar != null) {
                mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lugar.getLatitud(), lugar.getLongitud()))
                                .title(lugar.getNombre())
                                .snippet("Deportes practicados: ")
                                .icon(BitmapDescriptorFactory.fromBitmap(sport_marker_reduced))

                );
            }
        }
    }

    @Override
    public void setNearbyUsers(LugarPractica[] locations) {
        Bitmap user_marker= BitmapFactory.decodeResource(getResources(), R.drawable.user_marker);
        Bitmap user_marker_reduced= Bitmap.createScaledBitmap(user_marker, user_marker.getWidth() / 8, user_marker.getHeight() / 8, false);

        for(LugarPractica lugar : locations){
            if(lugar != null) {
                mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lugar.getLatitud(), lugar.getLongitud()))
                                .title(lugar.getNombre())
                                .snippet("Practica: ")
                                .icon(BitmapDescriptorFactory.fromBitmap(user_marker_reduced))

                );
            }
        }
    }

    @Override
    public void setNearbyEvents(LugarPractica[] locations) {
        Bitmap event_marker= BitmapFactory.decodeResource(getResources(), R.drawable.event_marker);
        Bitmap event_marker_reduced= Bitmap.createScaledBitmap(event_marker, event_marker.getWidth() / 8, event_marker.getHeight() / 8, false);

        for(LugarPractica lugar : locations){
            if(lugar != null) {
                mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lugar.getLatitud(), lugar.getLongitud()))
                                .title(lugar.getNombre())
                                .snippet("Fecha: ")
                                .icon(BitmapDescriptorFactory.fromBitmap(event_marker_reduced))

                );
            }
        }
    }
}
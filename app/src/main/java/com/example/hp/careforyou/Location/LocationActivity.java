package com.example.hp.careforyou.Location;

import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.careforyou.R;
import com.example.hp.careforyou.ScanItemsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks ,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener,OnMapReadyCallback {


    private final String Log_Tag = LocationActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates = false;
    private LocationCallback mLocationCallback;
    private Location mLastLocation;
    private Double mlat,mlog;
    GoogleMap m_map;
    boolean mapReady = false;
    private Marker mMarker;

    private static final int MY_PERMISSION_REQUEST_CODE =7171;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST =7172;

    private static int UPDATE_INTERVAL =5000;
    private static int FASTEST_INTERVAL =3000;
    private static int DISPLACEMENT =10;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(CheckPlayServices())
                        buildGoogleApiClient();
                }
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaction);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
        }

         MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);
    }

//    private void DisplayLocation()
//    {
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )
//                !=PackageManager.PERMISSION_GRANTED) {
//
//                return;
//        }
//
////        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
////        if(mLastLocation!=null)
////        {
////            double latitute = mLastLocation.getLatitude();
////            double longitude = mLastLocation.getLongitude();
////            //textoutput.setText(latitute + "  / "  + longitude);
////            mlat =latitute;
////            mlog =longitude;
////
////        }
//
////        else
////            //textoutput.setText("could  not get loction . Make Sure your Location is enabled in the device");
//    }

    private void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    private synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

    }


    private boolean CheckPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS)
        {
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"This device is not supported",Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }


    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )
                !=PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createLocationRequest();
        //DisplayLocation();
        startLocationUpdates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);

        if(mGoogleApiClient != null)
             mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation =location;

        if(mMarker!=null)
            mMarker.remove();
        double latitute = mLastLocation.getLatitude();
        double longitude = mLastLocation.getLongitude();
        mlat =latitute;
        mlog =longitude;

        LatLng userLocation = new LatLng(mlat,mlog);
        MarkerOptions markerOptions = new MarkerOptions()
                                       .position(userLocation)
                                       .title("Your Location");

        mMarker = m_map.addMarker(markerOptions);

        CameraPosition target = CameraPosition.builder().target(userLocation).zoom(14).build();
        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));

    }


    @Override
    public void onConnectionSuspended(int i) {

        Log.d(Log_Tag,"Location Is Suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(Log_Tag,"Location Is failed");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
       mapReady = true;
       m_map =googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
        }

        else
        {
            if(CheckPlayServices())
            {
                buildGoogleApiClient();
//                createLocationRequest();
            }
        }

    }
}

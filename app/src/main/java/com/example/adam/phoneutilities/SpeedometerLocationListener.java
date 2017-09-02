package com.example.adam.phoneutilities;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by adam on 8/7/17.
 */

public class SpeedometerLocationListener {

    private long requestInterval = 1000;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private double latitude, longitude;
    private float speed;

    public SpeedometerLocationListener(Context context) {
        setupLocationRequest(context);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                setLatitude(location.getLatitude());
                setLongitude(location.getLongitude());

                if (location.hasSpeed()) {
                    setSpeed(location.getSpeed());
                }
            }
        }
    };

    protected synchronized void setupLocationRequest(Context context) {
        // Set the parameters for the location request.
        locationRequest = new LocationRequest();
        locationRequest.setInterval(requestInterval);
        locationRequest.setFastestInterval(requestInterval);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the parameters for the Google API client.
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {

        @Override
        public void onConnected(Bundle bundle) {
            Log.i("onConnected()", "start");
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
            } catch (SecurityException e) {
                Log.i("onConnected()", "SecurityException: " + e.getMessage());
            }
        }

        @Override
        public void onConnectionSuspended(int i) {
        }
    };

    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

            Log.i("onConnected()", "SecurityException: " + connectionResult.toString());
        }
    };

    // Get and set methods for latitude.
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double lat) {
        this.latitude = lat;
    }

    // Get and set methods for longitude.
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double lon) {
        this.longitude = lon;
    }

    // Get and set methods for speed.
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float sp) {
        this.speed = sp;
    }

    // Get and set methods for the request interval.
    public long getRequestInterval() {
        return requestInterval;
    }

    public void setRequestInterval(long ri) {
        this.requestInterval = ri;
    }
}

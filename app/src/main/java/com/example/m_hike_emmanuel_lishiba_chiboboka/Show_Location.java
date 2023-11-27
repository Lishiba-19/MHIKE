package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.Manifest;
import android.app.LocaleManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Show_Location extends AppCompatActivity {

    // Variable declarations
    private TextView currentLocation;
    private Button findLocation, back;

    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_location);

        // Method to store all id assignment of components
        setIds();

        // Method to store all the click events of the buttons
        buttonClicks();

        //Method that checks if the phone location has been enabled
        isGPSEnabled();

    }

    //A method created to store the click events of all the buttons.
    private void buttonClicks() {

        //This button event basically will prompt the user to allow the app to track their current location
        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(Show_Location.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(IsgpsOn()){
                            LocationServices.getFusedLocationProviderClient(Show_Location.this)
                                    .requestLocationUpdates(locationRequest, new LocationCallback() {
                                        @Override
                                        public void onLocationResult(@NonNull LocationResult locationResult) {
                                            super.onLocationResult(locationResult);

                                                LocationServices.getFusedLocationProviderClient(Show_Location.this)
                                                        .removeLocationUpdates(this);

                                                if (locationResult != null && locationResult.getLocations().size() >0){
                                                    int index = locationResult.getLocations().size() - 1;
                                                    double latitude = locationResult.getLocations().get(index).getLatitude();
                                                    double longitude = locationResult.getLocations().get(index).getLongitude();

                                                    //This will display the current device's location using longitude and latitude
                                                    currentLocation.setText("Latitude:  "+latitude+" °" + "\n\n" + "Longitude:  "+longitude+" °");
                                                }
                                        }
                                    }, Looper.getMainLooper());
                        }
                        else {
                            // Will turn on the location of the device if it is not on
                            initiateGPS();
                        }
                    }
                    else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
                else{
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        });

    }

    //The initiateGPS is a block of code borrowed from the github user, "Pritish-git"
    //It basically validates and checks if the phone gps has been turned on.
    private void initiateGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(Show_Location.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Show_Location.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

        // Takes the user back to the main menu of the application
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Show_Location.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    // Returns a boolean if the location of the device has been turned on (true) or not (false)
    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    //When called, this method will return true or false depending on the gps's status being on or off
    private boolean IsgpsOn() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // Casts the ids of all components from the show_location xml to the declared components of this activity
    private void setIds() {
        currentLocation = findViewById(R.id.liveLocationId);
        findLocation = findViewById(R.id.revealLocation);
        back = findViewById(R.id.liveBackId);

        // The locationRequest Variable used by the application to request access to the device's location
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
    }
}

package com.example.longsor_app_001;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class splash extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 4000; // Splash screen timeout in milliseconds
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        requestLocationPermission();

        // Delayed execution of the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);

                // Close this activity
                finish();
            }
        }, SPLASH_TIMEOUT);
    }

    private void requestLocationPermission() {
        // Request the ACCESS_FINE_LOCATION permission
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE
        );
    }
}

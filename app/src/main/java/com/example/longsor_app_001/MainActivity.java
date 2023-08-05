package com.example.longsor_app_001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ImageView buttonmenu;
    private ImageView map1view;
    private ImageView map2view;

    private Double currentUserLatitude;
    private Double currentUserLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocation();

        this.map1view = (ImageView) findViewById(R.id.icon_petakerawanan);
        this.map2view = (ImageView) findViewById(R.id.icon_petariwayat);
        this.buttonmenu = (ImageView) findViewById(R.id.menu_drawer);
        this.map1view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();

                Intent intent = new Intent(MainActivity.this, map1_view.class);
                intent.putExtra("CURRENT_USER_LATITUDE", currentUserLatitude);
                intent.putExtra("CURRENT_USER_LONGITUDE", currentUserLongitude);
                startActivity(intent);
                finish();
            }
        });

        this.map2view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();

                Intent intent = new Intent(MainActivity.this, map2_view.class);
                startActivity(intent);
                finish();
            }
        });
        this.buttonmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, buttonmenu);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popup.setForceShowIcon(false);
                } else {
                }
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.menu_bantuan) {
                            Intent bantuanIntent = new Intent(MainActivity.this, Bantuan.class);
                            startActivity(bantuanIntent);
                            return true;
                        } else if (itemId == R.id.menu_tentang) {
                            Intent bantuanIntent = new Intent(MainActivity.this, Tentang.class);
                            startActivity(bantuanIntent);
                            return true;
                        } else if (itemId == R.id.menu_setting) {
                            Intent bantuanIntent = new Intent(MainActivity.this, Pengaturan.class);
                            startActivity(bantuanIntent);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void getLocation() {
        // Check if the location provider is enabled
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Location provider is not enabled, show an alert or take appropriate action
            Toast.makeText(this, "Please enable GPS to get location updates.", Toast.LENGTH_SHORT).show();
        } else {
            // Start receiving location updates
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            // Latitude and longitude of the user's current location
                            currentUserLatitude = location.getLatitude();
                            currentUserLongitude = location.getLongitude();
                        } else {
                            // Location is null, handle the case if location is not available
                            Toast.makeText(this, "Location not available.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle location retrieval failure
                        Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
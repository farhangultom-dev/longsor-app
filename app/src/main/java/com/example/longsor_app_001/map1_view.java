package com.example.longsor_app_001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.longsor_app_001.helper.NotificationHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class map1_view extends AppCompatActivity
        implements OnMapReadyCallback {

    private ImageView buttonback;

    private double currentUserLongitude;

    private double currentUserLatitude;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1_view);
        currentUserLatitude = getIntent().getDoubleExtra("CURRENT_USER_LATITUDE", 0.0);
        currentUserLongitude = getIntent().getDoubleExtra("CURRENT_USER_LONGITUDE", 0.0);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        this.buttonback = (ImageView) findViewById(R.id.back);
        this.buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map1_view.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadGeoJson(R.raw.rendah, Color.parseColor("#FFFF00")); // yellow
        loadGeoJson(R.raw.sangat_rendah, Color.parseColor("#FFFFE0")); // light yellow
        loadGeoJsonWithGeoFencing(R.raw.sangat_tinggi, Color.parseColor("#8B0000")); // old red
        loadGeoJsonWithGeoFencing(R.raw.tinggi, Color.parseColor("#FF0000")); // red
        loadGeoJsonWithGeoFencing(R.raw.sedang, Color.parseColor("#FF6600")); // orange

    }

    private void loadGeoJson(int dataGeoJson, int color) {
        try {
            InputStream inputStream = getResources().openRawResource(dataGeoJson);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String geoJsonString = new String(buffer, "UTF-8");

            GeoJsonLayer geoJsonLayer = new GeoJsonLayer(mMap, new JSONObject(geoJsonString));

            for (GeoJsonFeature feature : geoJsonLayer.getFeatures()) {
                if (feature.hasGeometry() && feature.getGeometry().getGeometryType().equals("Point")) {
                    GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                    LatLng latLng = new LatLng(point.getCoordinates().latitude, point.getCoordinates().longitude);

                    double circleRadius = 100;
                    CircleOptions circleOptions = new CircleOptions()
                            .center(latLng)
                            .radius(circleRadius)
                            .strokeWidth(2f)
                            .strokeColor(Color.BLACK)
                            .fillColor(color);

                    mMap.addCircle(circleOptions);
                }
            }

            mMap.setOnMarkerClickListener(marker -> {
                return false;
            });

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (GeoJsonFeature feature : geoJsonLayer.getFeatures()) {
                if (feature.hasGeometry() && feature.getGeometry().getGeometryType().equals("Point")) {
                    GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                    LatLng latLng = new LatLng(point.getCoordinates().latitude, point.getCoordinates().longitude);
                    builder.include(latLng);
                }
            }
            LatLngBounds bounds = builder.build();

            int mapWidth = getResources().getDisplayMetrics().widthPixels;
            int mapHeight = getResources().getDisplayMetrics().heightPixels;

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, mapWidth, mapHeight, 100));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadGeoJsonWithGeoFencing(int dataGeoJson, int color) {
        try {
            InputStream inputStream = getResources().openRawResource(dataGeoJson);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String geoJsonString = new String(buffer, "UTF-8");

            GeoJsonLayer geoJsonLayer = new GeoJsonLayer(mMap, new JSONObject(geoJsonString));

            for (GeoJsonFeature feature : geoJsonLayer.getFeatures()) {
                if (feature.hasGeometry() && feature.getGeometry().getGeometryType().equals("Point")) {
                    GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                    LatLng latLng = new LatLng(point.getCoordinates().latitude, point.getCoordinates().longitude);

                    double circleRadius = 100;
                    CircleOptions circleOptions = new CircleOptions()
                            .center(latLng)
                            .radius(circleRadius)
                            .strokeWidth(2f)
                            .strokeColor(Color.BLACK)
                            .fillColor(color);

                    mMap.addCircle(circleOptions);
                }
            }

            mMap.setOnMarkerClickListener(marker -> {
                return false;
            });

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (GeoJsonFeature feature : geoJsonLayer.getFeatures()) {
                if (feature.hasGeometry() && feature.getGeometry().getGeometryType().equals("Point")) {
                    GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                    LatLng latLng = new LatLng(point.getCoordinates().latitude, point.getCoordinates().longitude);
                    builder.include(latLng);
                }
            }
            LatLngBounds bounds = builder.build();

            int mapWidth = getResources().getDisplayMetrics().widthPixels;
            int mapHeight = getResources().getDisplayMetrics().heightPixels;

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, mapWidth, mapHeight, 100));

            checkUserLocation(geoJsonLayer);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkUserLocation(GeoJsonLayer geoJsonLayer) {
        LatLng userLocation = new LatLng(currentUserLatitude, currentUserLongitude); // Replace with user's current location

        for (GeoJsonFeature feature : geoJsonLayer.getFeatures()) {
            if (feature.hasGeometry() && feature.getGeometry().getGeometryType().equals("Point")) {
                GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                LatLng pointLatLng = new LatLng(point.getCoordinates().latitude, point.getCoordinates().longitude);

                float distance = calculateDistance(userLocation, pointLatLng);

                if (distance <= 100.0f) {
                    showNotification("Peringatan! ", "Kamu berada di wilayah rawan longsor!");
                    break;
                }
            }
        }
    }

    private float calculateDistance(LatLng point1, LatLng point2) {
        float[] distanceResults = new float[1];
        Location.distanceBetween(point1.latitude, point1.longitude, point2.latitude, point2.longitude, distanceResults);
        return distanceResults[0];
    }

    private void showNotification(String title, String message) {
        NotificationHelper.showNotification(this, title, message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location permission not granted.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable GPS to get location updates.", Toast.LENGTH_SHORT).show();
        } else {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            currentUserLatitude = location.getLatitude();
                            currentUserLongitude = location.getLongitude();

                            LatLng userLatLng = new LatLng(currentUserLatitude,currentUserLongitude);
                            mMap.addMarker(new MarkerOptions()
                                    .position(userLatLng)
                                    .title("User Location"));
                        } else {
                            Toast.makeText(this, "Location not available.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}

package com.example.longsor_app_001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.longsor_app_001.model.DataRiwyatLongsorModelItem;
import com.google.android.gms.maps.CameraUpdate;
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
import com.google.maps.android.data.geojson.GeoJsonPolygon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;

public class map2_view extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView buttonback;

    private Spinner spinner;

    private int geoJson;

    private GoogleMap mMap;

    private ArrayList<DataRiwyatLongsorModelItem> riwayatLongsorByTahuns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2_view);
        spinner = findViewById(R.id.spinner);

        geoJson = R.raw.riwayat_longsor_2016;

        riwayatLongsorByTahuns = new ArrayList<>();
        riwayatLongsorByTahuns.add(new DataRiwyatLongsorModelItem(R.raw.riwayat_longsor_2016,"2016"));
        riwayatLongsorByTahuns.add(new DataRiwyatLongsorModelItem(R.raw.riwayat_longsor_2017,"2017"));
        riwayatLongsorByTahuns.add(new DataRiwyatLongsorModelItem(R.raw.riwayat_longsor_2018,"2018"));
        riwayatLongsorByTahuns.add(new DataRiwyatLongsorModelItem(R.raw.riwayat_longsor_2019,"2019"));
        riwayatLongsorByTahuns.add(new DataRiwyatLongsorModelItem(R.raw.riwayat_longsor_2020,"2020"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView2);
        mapFragment.getMapAsync(this);

        ArrayList<String> spinnerDataList = new ArrayList<>();
        spinnerDataList.add("2016");
        spinnerDataList.add("2017");
        spinnerDataList.add("2018");
        spinnerDataList.add("2019");
        spinnerDataList.add("2020");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                spinnerDataList
        );

        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geoJson = riwayatLongsorByTahuns.get(position).getGeoJsonId();
                refreshMap();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.buttonback = (ImageView) findViewById(R.id.back2);
        this.buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map2_view.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Read GeoJSON data from the file in the assets folder
            InputStream inputStream = getResources().openRawResource(geoJson);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String geoJsonString = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(geoJsonString);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                double latitude = jsonObject.getDouble("Latitude (Y)");
                double longitude = jsonObject.getDouble("Longitude (X)");
                String dusun = jsonObject.getString("Dusun");
                String tglKejadian = jsonObject.getString("Tanggal Kejadian");


                LatLng latLng = new LatLng(latitude, longitude);

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title("titik longsor di dusun "+ dusun + " pada " + tglKejadian);

                googleMap.addMarker(markerOptions);
            }

            LatLng latLngCamera = new LatLng(-7.8546511565849935, 110.3318536769938);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngCamera, 10.0f);
            googleMap.animateCamera(cameraUpdate);

            googleMap.setOnMarkerClickListener(marker -> {
                return false;
            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void refreshMap() {
        if (mMap != null) {
            // Step 1: Clear the map of all markers, overlays, etc.
            mMap.clear();

            try {
                // Read GeoJSON data from the file in the assets folder
                InputStream inputStream = getResources().openRawResource(geoJson);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                String geoJsonString = new String(buffer, "UTF-8");

                JSONArray jsonArray = new JSONArray(geoJsonString);

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    double latitude = jsonObject.getDouble("Latitude (Y)");
                    double longitude = jsonObject.getDouble("Longitude (X)");
                    String dusun = jsonObject.getString("Dusun");
                    String tglKejadian = jsonObject.getString("Tanggal Kejadian");


                    LatLng latLng = new LatLng(latitude, longitude);

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng)
                            .title("titik longsor di dusun "+ dusun + " pada " + tglKejadian);

                    mMap.addMarker(markerOptions);
                }

                LatLng latLngCamera = new LatLng(-7.8546511565849935, 110.3318536769938);

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngCamera, 10.0f);
                mMap.animateCamera(cameraUpdate);

                mMap.setOnMarkerClickListener(marker -> {
                    return false;
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
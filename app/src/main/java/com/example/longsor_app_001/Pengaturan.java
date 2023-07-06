package com.example.longsor_app_001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.maps.LocationSource;

public class Pengaturan extends AppCompatActivity {

    private ImageView buttonback;
    private Button button_notifikasi;
    private Button button_lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        this.buttonback = (ImageView) findViewById(R.id.back);
        this.buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pengaturan.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.button_notifikasi = (Button) findViewById(R.id.notificationSwitch);
        button_notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);
            }
        });

        this.button_lokasi = (Button) findViewById(R.id.locationswitch);
        button_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

    }
}
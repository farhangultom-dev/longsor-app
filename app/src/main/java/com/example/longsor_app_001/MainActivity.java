package com.example.longsor_app_001;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private ImageView buttonmenu;
    private ImageView map1view;
    private ImageView map2view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.map1view = (ImageView) findViewById(R.id.icon_petakerawanan);
        this.map2view = (ImageView) findViewById(R.id.icon_petariwayat);
        this.buttonmenu = (ImageView) findViewById(R.id.menu_drawer);
        this.map1view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, map1_view.class);
                startActivity(intent);
                finish();
            }
        });

        this.map2view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
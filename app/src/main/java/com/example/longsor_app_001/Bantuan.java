package com.example.longsor_app_001;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bantuan extends AppCompatActivity {

    private ImageView buttonback;
    private LinearLayout expandableLayout1;
    private LinearLayout expandableLayout2;
    private LinearLayout expandableLayout3;
    private LinearLayout expandableLayout4;
    private LinearLayout expandableContent1;
    private LinearLayout expandableContent2;
    private LinearLayout expandableContent3;
    private LinearLayout expandableContent4;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        this.buttonback = (ImageView) findViewById(R.id.back);
        this.buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bantuan.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        expandableLayout1 = findViewById(R.id.expandableLayout1);
        expandableLayout2 = findViewById(R.id.expandableLayout2);
        expandableLayout3 = findViewById(R.id.expandableLayout3);
        expandableLayout4 = findViewById(R.id.expandableLayout4);

        expandableContent1 = findViewById(R.id.expandableContent1);
        expandableContent2 = findViewById(R.id.expandableContent2);
        expandableContent3 = findViewById(R.id.expandableContent3);
        expandableContent4 = findViewById(R.id.expandableContent4);
        expandableLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleExpandableContent(expandableLayout1, expandableContent1);
            }
        });

        expandableLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleExpandableContent(expandableLayout2, expandableContent2);
            }
        });

        expandableLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleExpandableContent(expandableLayout3, expandableContent3);
            }
        });

        expandableLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleExpandableContent(expandableLayout4, expandableContent4);
            }
        });
    }

    private void toggleExpandableContent(LinearLayout expandableLayout, LinearLayout expandableContent) {

        if (expandableContent.getVisibility() == View.VISIBLE) {
            expandableContent.setVisibility(View.GONE);
        } else {
            expandableContent.setVisibility(View.VISIBLE);
        }
    }

}
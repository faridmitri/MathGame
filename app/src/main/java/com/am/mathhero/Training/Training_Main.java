package com.am.mathhero.Training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.am.mathhero.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Training_Main extends AppCompatActivity {

    Button easy;
    Button medium;
    Button hard;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training__main);
        setTitle("Train");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Training_Main.this, Training_Game.class);
                intent.putExtra("level", "easy");
                startActivity(intent);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Training_Main.this, Training_Game.class);
                intent.putExtra("level", "medium");
                startActivity(intent);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Training_Main.this, Training_Game.class);
                intent.putExtra("level", "hard");
                startActivity(intent);
            }
        });


    }
}
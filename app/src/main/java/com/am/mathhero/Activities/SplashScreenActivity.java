package com.am.mathhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


import com.am.mathhero.R;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreenActivity extends AppCompatActivity {

    TextView textViewMathHero,textViewFarid;
    String TAG;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try{
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            Log.d(TAG,FirebaseDatabase.getInstance().toString());
        }catch (Exception e){
            Log.w(TAG,"SetPresistenceEnabled:Fail"+FirebaseDatabase.getInstance().toString());
            e.printStackTrace();
        }


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);



        textViewMathHero = findViewById(R.id.textMathHero);
        textViewFarid = findViewById(R.id.textFarid);


      //  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash);
        //textViewMathHero.startAnimation(animation);
        //textViewFarid.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        },4500);
    }
}
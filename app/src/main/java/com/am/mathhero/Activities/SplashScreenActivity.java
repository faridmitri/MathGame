package com.am.mathhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.mathhero.R;

public class SplashScreenActivity extends AppCompatActivity {

    TextView textViewMathHero,textViewFarid;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textViewMathHero = findViewById(R.id.textMathHero);
        textViewFarid = findViewById(R.id.textFarid);
        imageView= findViewById(R.id.imageView);

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
        },5000);
    }
}
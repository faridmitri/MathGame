package com.am.mathhero.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.mathhero.R;
import com.am.mathhero.Training.Training_Main;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    Button start,results,buy_wisdom,train;
    TextView wisdom,hightScore;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    long diamons;
    static String userCountry,name,user;
    ProgressBar progressBar;
    private InterstitialAd mInterstitialAd,mInterstitialAd1;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
       // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-8469263715026322/1317679859");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd1 = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd1.setAdUnitId("ca-app-pub-8469263715026322/9090801044");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());

        start = findViewById(R.id.start);
        wisdom = findViewById(R.id.wisdom_coin);
        buy_wisdom = findViewById(R.id.buy_wisdom);
        hightScore = findViewById(R.id.hightscore);
        results = findViewById(R.id.results);
        train = findViewById(R.id.train);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        progressBar = findViewById(R.id.progressBar5);
        getUserInfo();


        start.setEnabled(false);
        buy_wisdom.setEnabled(false);
        buy_wisdom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,DiamondsActivity.class);
                intent.putExtra("diamond", diamons);
                startActivity(intent);

            }
        });

        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    Intent i = new Intent(MainActivity.this, Training_Main.class);
                    startActivity(i);
                    mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                }

                mInterstitialAd1.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        Intent i = new Intent(MainActivity.this, Training_Main.class);
                        startActivity(i);
                        mInterstitialAd1.loadAd(new AdRequest.Builder().build());
                    }

                });



            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,QuizActivity.class);
                intent.putExtra("diamond", diamons);
                startActivity(intent);

            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Intent i = new Intent(MainActivity.this, LeaderBoardA.class);
                    startActivity(i);
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        Intent i = new Intent(MainActivity.this, LeaderBoardA.class);
                        startActivity(i);
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.usermenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_profile)
        {
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        }
        if (item.getItemId() == R.id.action_signout)
        {
            auth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        if (item.getItemId() == R.id.support)
        {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"andro.app@yahoo.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Math Quiz");
            i.putExtra(Intent.EXTRA_TEXT   , "");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }



        }


        return super.onOptionsItemSelected(item);
    }


    public void getUserInfo() {

        try {
        reference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                diamons = (long) snapshot.child("diamons").getValue();
                wisdom.setText(""+diamons);
                String hightscore = snapshot.child("score").getValue().toString();
                hightScore.setText(hightscore);
                userCountry = snapshot.child("country").getValue().toString();
                progressBar.setVisibility(View.INVISIBLE);
                start.setEnabled(true);
                buy_wisdom.setEnabled(true);
                user = snapshot.getKey();
                name = snapshot.child("userName").getValue().toString();
                setTitle("Welcome " + name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
} catch (Exception e) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            auth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

    }

    public static String countries(){

        String c = userCountry;
        return c;
    }

    public static String user(){

        String n = user;
        return n;
    }


}
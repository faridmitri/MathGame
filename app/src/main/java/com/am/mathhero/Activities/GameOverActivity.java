package com.am.mathhero.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.am.mathhero.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GameOverActivity extends AppCompatActivity {
    private RewardedAd rewardedAd;

    TextView score;
    Button chance, leader, buttonad;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;
    boolean flag = false;


    int myNum;
    long setcountryScore;
    String setcountry;
    long setScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

       loadad();

        final MediaPlayer gameover = MediaPlayer.create(this, R.raw.gameover);
        gameover.start();

        leader = findViewById(R.id.leaderBoard);
        buttonad = findViewById(R.id.buttonad);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        score = findViewById(R.id.textViewScore);
        score.setText("You score is: " + getIntent().getStringExtra("scor"));
        String fbscore = getIntent().getStringExtra("scor");
        long diamond = getIntent().getLongExtra("wisdom", 0);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        chance = findViewById(R.id.buttonad);
        leader = findViewById(R.id.leaderBoard);

        boolean chanceflag = getIntent().getBooleanExtra("chanceflag", false);
        if (chanceflag) {
            buttonad.setEnabled(false);
        }

        buttonad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showad();

            }
        });

        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);


                reference.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        setScore = (long) snapshot.child("score").getValue();
                       setcountry = (String) snapshot.child("country").getValue();
                        myNum = Integer.parseInt(fbscore);

                    //    setcountryScore += myNum;

                      //  reference.child("Users").child(auth.getUid()).child("countryScore").setValue(setcountryScore);
                        if (setScore < myNum) {
                            reference.child("Users").child(auth.getUid()).child("score").setValue(myNum);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                reference.child("Countries").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        myNum = Integer.parseInt(fbscore);
                        setcountryScore = (long) snapshot.child(setcountry).getValue();
                        setcountryScore += myNum;

                        reference.child("Countries").child(setcountry).setValue(setcountryScore);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });







                Intent i = new Intent(GameOverActivity.this, LeaderBoardA.class);
                startActivity(i);
                finish();

            }
        });

    }

    public void showad() {
        if (rewardedAd.isLoaded()) {
            Activity activityContext =GameOverActivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {

                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    // Ad closed.
                    loadad();
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                    Intent intent = new Intent(GameOverActivity.this, QuizActivity.class);
                    flag = true;
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    // Ad failed to display.

                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }

public RewardedAd loadad(){
   // rewardedAd = new RewardedAd(this,"ca-app-pub-3940256099942544/5224354917");
     rewardedAd = new RewardedAd(this,"ca-app-pub-8469263715026322/3266892639");
    RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
        @Override
        public void onRewardedAdLoaded() {
            // Ad successfully loaded.
        }

        @Override
        public void onRewardedAdFailedToLoad(LoadAdError adError) {
            // Ad failed to load.
        }
    };
    rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    return rewardedAd;
}

}
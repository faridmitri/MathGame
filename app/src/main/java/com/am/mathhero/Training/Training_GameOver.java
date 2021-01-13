package com.am.mathhero.Training;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.am.mathhero.Activities.GameOverActivity;
import com.am.mathhero.Activities.LeaderBoardA;
import com.am.mathhero.Activities.MainActivity;
import com.am.mathhero.Activities.QuizActivity;
import com.am.mathhero.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class Training_GameOver extends AppCompatActivity {

    private RewardedAd rewardedAd;

    TextView score;
    Button plusfive,plusten,onediamond,fivediamonds,retry;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String but;
    long hightscore;
    ConstraintLayout constraintLayout;
    private InterstitialAd mInterstitialAd;

    long installTimeInMilliseconds,diamons; // install time is conveniently provided in milliseconds
    private ReviewManager reviewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training__game_over);

        setTitle("Rewards");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        getUserInfo();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        loadad();

        mInterstitialAd = new InterstitialAd(this);
        // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-8469263715026322/9090801044");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        final MediaPlayer gameover = MediaPlayer.create(this, R.raw.gameover);
        gameover.start();

        plusfive = findViewById(R.id.plusfive);
        plusten = findViewById(R.id.plusten);
        onediamond = findViewById(R.id.onediamond);
        fivediamonds = findViewById(R.id.fivediamonds);
        constraintLayout = findViewById(R.id.constraint);
        retry = findViewById(R.id.retry);

        score = findViewById(R.id.textViewScore);
        score.setText("You score is: " + getIntent().getStringExtra("scor"));
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        retry.setVisibility(View.GONE);
        plusten.setEnabled(false);
        plusfive.setEnabled(false);
        onediamond.setEnabled(false);
        fivediamonds.setEnabled(false);

        long scor = Long.parseLong(getIntent().getStringExtra("scor"));
        if (scor <= 200){ retry.setVisibility(View.VISIBLE);}
        if (scor > 200 && scor < 400){plusfive.setEnabled(true);}
        if (scor > 400 && scor < 500){plusten.setEnabled(true);}
        if (scor > 500 && scor < 600){onediamond.setEnabled(true);}
        if (scor > 600){fivediamonds.setEnabled(true);}


        reviewManager = ReviewManagerFactory.create(this);
        getInstallDate();

        long l = currentTimeMillis();
        if (installTimeInMilliseconds + (86400000 * 3) < l) {
            showRateApp();
        }


        plusfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                but = "plusfive";
                showad();
            }
        });


     plusten.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            but = "plusten";
            showad();
        }
    });

        onediamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                but = "one";
                showad();
            }
        });

        fivediamonds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                but = "five";
                showad();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Intent i = new Intent(Training_GameOver.this, MainActivity.class);
                    startActivity(i);
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        Intent i = new Intent(Training_GameOver.this, MainActivity.class);
                        startActivity(i);
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });


            }
        });
}



    public void showad() {
        if (rewardedAd.isLoaded()) {
            Activity activityContext =Training_GameOver.this;
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
                    if (but.equals("one")){
                    diamons += 1;
                    reference.child("Users").child(auth.getUid()).child("diamons").setValue(diamons); snakebar();}
                    else if (but.equals("five")) {
                        diamons += 5;
                        reference.child("Users").child(auth.getUid()).child("diamons").setValue(diamons); snakebar();}
                    else if (but.equals("plusfive")) {
                        hightscore += 5;
                        reference.child("Users").child(auth.getUid()).child("score").setValue(hightscore); snakebar();}
                    else if  (but.equals("plusten")) {
                        hightscore += 10;
                        reference.child("Users").child(auth.getUid()).child("score").setValue(hightscore); snakebar();}

                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    // Ad failed to display.

                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            Toast.makeText(Training_GameOver.this, "Check your internet connection, and repress the buton", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }

    public RewardedAd loadad(){
        // rewardedAd = new RewardedAd(this,"ca-app-pub-3940256099942544/5224354917");
        rewardedAd = new RewardedAd(this,"ca-app-pub-8469263715026322/6407785624");
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

    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                //   showRateAppFallbackDialog();
            }
        });
    }


    private String getInstallDate() {
        // get app installation date
        PackageManager packageManager =  this.getPackageManager();

        Date installDate = null;
        String installDateString = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            installTimeInMilliseconds = packageInfo.firstInstallTime;


        }
        catch (PackageManager.NameNotFoundException e) {
            // an error occurred, so display the Unix epoch
            installDate = new Date(0);
            installDateString = installDate.toString();
        }
        return installDateString;
    }

    public void getUserInfo() {


        reference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                diamons = (long) snapshot.child("diamons").getValue();
                hightscore = (long) snapshot.child("score").getValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void snakebar(){
        Snackbar snackbar = Snackbar.make(constraintLayout,"Congratulations, you earn your rewards",Snackbar.LENGTH_INDEFINITE)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Training_GameOver.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                ;
        snackbar.show();
    }
}
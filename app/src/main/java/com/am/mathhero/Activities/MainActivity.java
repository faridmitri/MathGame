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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
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

import java.io.File;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {
    Button start,results,buy_wisdom;
    TextView wisdom,hightScore;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    long diamons;
    static String userCountry,name,user;
    ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;

    long installTimeInMilliseconds; // install time is conveniently provided in milliseconds
    private ReviewManager reviewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reviewManager = ReviewManagerFactory.create(this);

        getInstallDate();

        long l =currentTimeMillis();
        if (  installTimeInMilliseconds + (86400000 * 3) < l)
        {
            showRateApp();
        }


        mInterstitialAd = new InterstitialAd(this);
       // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-8469263715026322/1317679859");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        start = findViewById(R.id.start);
        wisdom = findViewById(R.id.wisdom_coin);
        buy_wisdom = findViewById(R.id.buy_wisdom);
        hightScore = findViewById(R.id.hightscore);
        results = findViewById(R.id.results);
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
            auth.signOut();


            startActivity(new Intent(MainActivity.this,LoginActivity.class));
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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public static String countries(){

        String c = userCountry;
        return c;
    }

    public static String user(){

        String n = user;
        return n;
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

}
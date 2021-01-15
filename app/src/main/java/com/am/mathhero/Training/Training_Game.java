package com.am.mathhero.Training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.TextView;


import com.am.mathhero.Activities.LeaderBoardA;
import com.am.mathhero.Activities.MainActivity;
import com.am.mathhero.Activities.Operations;

import com.am.mathhero.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;


import java.util.Random;

public class Training_Game extends AppCompatActivity {

    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnDel,ok;
    TextView answer,time,chance,score,question,lvl,mathhero;
    String number = null;

    private static FirebaseAnalytics firebaseAnalytics;
    CountDownTimer countDownTimer;
    private static long TOTAL_TIME ;

    private InterstitialAd mInterstitialAd;
    int useranswer;
    int realanswer;
    int userscore;
    int levelscore;
    int userlife = 5;

    int r;
    Random random = new Random();
    String operator1;

    Operations operations;
    String firstnum,level;
    String secnum;

    MediaPlayer timeup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training__game);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mInterstitialAd = new InterstitialAd(this);
        // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-8469263715026322/1870431354");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnDel = findViewById(R.id.btnDel);
        ok = findViewById(R.id.okid);
        answer = findViewById(R.id.answerid);
        btnDel.setEnabled(false);
        score = findViewById(R.id.scorenum);
        time = findViewById(R.id.timenum);
        chance = findViewById(R.id.lifenum);
        question=findViewById(R.id.questid);
        lvl = findViewById(R.id.lvl);

        Intent intent = getIntent();
        level= intent.getStringExtra("level");

        lvl.setText(level + "\n level");
        mathhero = findViewById(R.id.mathhero);
        Animation animFadein = AnimationUtils.loadAnimation(this,R.anim.splash);
        animFadein.setRepeatCount(Animation.INFINITE);
        mathhero.startAnimation(animFadein);



        final MediaPlayer correctsound = MediaPlayer.create(this, R.raw.level_up);
        final MediaPlayer wrongsound = MediaPlayer.create(this, R.raw.fail);
        timeup = MediaPlayer.create(this, R.raw.timesup);


        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        operations = new Operations();

        gameContinue();



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok.setEnabled(false);
                String text = answer.getText().toString();
                try {
                    useranswer = Integer.valueOf(text);
                } catch (NumberFormatException e) {
                    useranswer = 0;
                }
                // useranswer = Integer.valueOf(answer.getText().toString());

                if (useranswer == realanswer) {
                    userscore = userscore + levelscore;
                    correctsound.start();
                    score.setText(String.format("%d", userscore));

                } else {
                    userlife = userlife - 1;
                    wrongsound.start();
                    chance.setText(String.format("%d", userlife));
                }
                //   time.setText("20");
                countDownTimer.cancel();
                gameover();

            }

        });



        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("9");
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number.length() == 0) {
                    btnDel.setEnabled(false);
                } else {
                    number = number.substring(0, number.length() - 1);

                    answer.setText(number);
                }

            }
        });

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CHARACTER, "training");
        
        long levelsf = 0;
        if (level == "easy") {levelsf = 1;}
        if (level == "medium") {levelsf = 2;}
        if (level == "hard") {levelsf = 3;}
        
        bundle.putLong(FirebaseAnalytics.Param.LEVEL, levelsf);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_START, bundle);

    }

    public void numberClick(String view)
    {
        btnDel.setEnabled(true);
        if (number == null)
        {
            number = view;
            answer.setText(number);
        }
        else {
            number = number + view;
            answer.setText(number);

        }
    }

    public void startTimer() {
        countDownTimer=new CountDownTimer(TOTAL_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                userlife = userlife - 1;
                timeup.start();
                question.setText("Time's up");
                gameover();
                chance.setText(String.format("%d", userlife));
            }

        }.start();
    }

    public void nexttimer() {
        countDownTimer=new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (useranswer == realanswer){
                    question.setText(correctAnswer());}
                else {question.setText("Wrong, Correct answer is: " + realanswer);answer.setText(null);}
            }

            public void onFinish() {

                gameContinue();
            }

        }.start();
    }


    public void gameContinue() {
        btnDel.setEnabled(false);
        ok.setEnabled(true);
        number = null;
        answer.setText(number);

        TOTAL_TIME= 20000;
        startTimer();

        switch (level) {
            case "easy":
                levelscore = 1;

                r = random.nextInt(2);
                if(r==0){operations.addition(100,0);}
                else if(r==1){operations.subtraction(100,1);}
                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;


            case "medium":
                levelscore = 2;
                r = random.nextInt(3);
                if(r==0){operations.addition(500,100);}
                else if(r==1){operations.subtraction(500,100);}
                else if(r==2){operations.multiplication(30,1);}

                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;



            case "hard":
                levelscore = 3;
                TOTAL_TIME= 15000;

                r = random.nextInt(4);
                if(r==0){operations.addition(1000,500);}
                else if(r==1){operations.subtraction(1000,500);}
                else if(r==2){operations.multiplication(50,1);}
                else if(r==3){operations.division(200,1);}

                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;


        }

    }

    public String correctAnswer(){
        Random random = new Random(1);
        String[] correct = {"You found a really \n good way to do it!", "You seem to really \n understand this!", "I can tell you’ve \n been practicing!",
                "Good thinking!","Great answer!","Awesome!","You are \n unique!", "Fantastic!","You're a \n problem solver!", "You learn \n quickly!","Brilliant!",
                "You're winner!","So proud of you!","Excellent!"};
        int n = random.nextInt(correct.length);
        return correct[n];
    }

    public void pauseTimer()
    {
        this.countDownTimer.cancel();

    }

    public void savedata(){
        SharedPreferences.Editor editor = getSharedPreferences("saved", MODE_PRIVATE).edit();
        editor.putInt("score", userscore);
        editor.apply();
    }



    public void gameover(){
        if (userlife == 0)
        {
            savedata();
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Intent i = new Intent(Training_Game.this, Training_GameOver.class);
                i.putExtra("scor","" +userscore);
                startActivity(i);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // Load the next interstitial.
                    Intent i = new Intent(Training_Game.this, Training_GameOver.class);
                    i.putExtra("scor","" +userscore);
                    startActivity(i);
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

            });


        } else  nexttimer();


    }


  /*  public void savefirebase() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("diamons").setValue(wisdoms);
    } */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Training_Main.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pauseTimer();
        startActivity(intent);
        finish();

    }
}
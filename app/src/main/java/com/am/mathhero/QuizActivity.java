package com.am.mathhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.media.MediaPlayer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnDel,ok;
    private TextView answer,time,chance,score,wisdom,question;
    private String number = null;


    CountDownTimer countDownTimer;
    private static long TOTAL_TIME ;
    Boolean timerContinue;
    //long timeLeft = TOTAL_TIME;



    int useranswer;
    int realanswer;
    int userscore = 0;
    int levelscore;
    int userlife = 3;
    int level = 1;
    int p = 0;
    int progresslevel;
    String op;
    int r;
    Random random = new Random();
    String operator1;
    ProgressBar progressBar;
    Operations operations;
    String firstnum;
    String secnum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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
        wisdom = findViewById(R.id.wisdom);
        question=findViewById(R.id.questid);
        final MediaPlayer correctsound = MediaPlayer.create(this, R.raw.level_up);
        final MediaPlayer wrongsound = MediaPlayer.create(this, R.raw.fail);
        progressBar = findViewById(R.id.progressBar1);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        operations = new Operations();

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    if (p == 100){p = 0;}
                    p = p+progresslevel;
                        progressBar.setProgress(p);


                } else {
                    userlife = userlife - 1;
                    wrongsound.start();
                    chance.setText(String.format("%d", userlife));
                }
             //   time.setText("20");
                countDownTimer.cancel();
                if (userlife == 0)
                {
                    Intent intent = new Intent(QuizActivity.this,GameOverActivity.class);
                    intent.putExtra("scor",userscore);
                    startActivity(intent);
                    finish();
                } else  nexttimer();

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

                number = number.substring(0, number.length() - 1);

                if (number.length() == 0) {
                    btnDel.setEnabled(false);
                }

                answer.setText(number);


            }
        });
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
                question.setText("Time's up");
                gameContinue();
                chance.setText(String.format("%d", userlife));
            }

        }.start();
    }

    public void nexttimer() {
        countDownTimer=new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (useranswer == realanswer){
                question.setText(correctAnswer());}
                else {question.setText("Hmmm it is wrong");}
            }

            public void onFinish() {

                gameContinue();
            }

        }.start();
    }


    public void gameContinue() {
        ok.setEnabled(true);
        number = null;
        answer.setText(number);

        if (userscore < 5){level = 1;TOTAL_TIME= 20000;levelscore =1;progresslevel=20;} else
        if (userscore < 15){level = 2;TOTAL_TIME= 20000;levelscore =2;progresslevel=20;} else
        if (userscore < 45){level = 3;TOTAL_TIME= 20000;levelscore =3;progresslevel=10;} else
        if (userscore < 85){level = 4;TOTAL_TIME= 20000;levelscore =4;progresslevel=10;} else
        if (userscore < 135){level = 5;TOTAL_TIME= 15000;levelscore =5;progresslevel=10;} else
        if (userscore < 195){level = 6;TOTAL_TIME= 15000;levelscore =6;progresslevel=10;} else
        if (userscore < 265){level = 7;TOTAL_TIME= 15000;levelscore =7;progresslevel=10;} else
        if (userscore < 345){level = 8;TOTAL_TIME= 15000;levelscore =8;progresslevel=10;} else
        if (userscore < 435){level = 9;TOTAL_TIME= 15000;levelscore =9;progresslevel=10;} else
        {level = 10;TOTAL_TIME= 10000;levelscore =10;}


        startTimer();

        switch (level) {
            case 1:
                operations.addition(100);
                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;


             case 2:
                 r = random.nextInt(2);
                 if(r==0){operations.addition(100);}
                 else if(r==1){operations.subtraction(100);}

                 firstnum = String.valueOf(Operations.getFirstNumber());
                 secnum = String.valueOf(Operations.getSecondNumber());
                 operator1 = String.valueOf(Operations.getOperator());
                 question.setText(firstnum + operator1 + secnum);
                 realanswer = Operations.getResult();
                 break;

            case 3:
                r = random.nextInt(3);
                if(r==0){operations.addition(300);}
                else if(r==1){operations.subtraction(300);}
                else if(r==2){operations.multiplication(20);}

                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

            case 4:
                r = random.nextInt(3);
                if(r==0){operations.addition(500);}
                else if(r==1){operations.subtraction(500);}
                else if(r==2){operations.multiplication(30);}

                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

             case 5:
                 r = random.nextInt(4);
                 if(r==0){operations.addition(500);}
                 else if(r==1){operations.subtraction(500);}
                 else if(r==2){operations.multiplication(40);}
                 else if(r==3){operations.division(20);}

                  firstnum = String.valueOf(Operations.getFirstNumber());
                 secnum = String.valueOf(Operations.getSecondNumber());
                 operator1 = String.valueOf(Operations.getOperator());
                 question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

            case 6:
                r = random.nextInt(4);
                if(r==0){operations.addition(500);}
                else if(r==1){operations.subtraction(500);}
                else if(r==2){operations.multiplication(40);}
                else if(r==3){operations.division(20);}
                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

            case 7:
                r = random.nextInt(4);
                if(r==0){operations.addition(500);}
                else if(r==1){operations.subtraction(500);}
                else if(r==2){operations.multiplication(50);}
                else if(r==3){operations.division(20);}
                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

            case 8:
                r = random.nextInt(4);
                if(r==0){operations.addition(800);}
                else if(r==1){operations.subtraction(800);}
                else if(r==2){operations.multiplication(50);}
                else if(r==3){operations.division(50);}
                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

            case 9:
                r = random.nextInt(4);
                if(r==0){operations.addition(1000);}
                else if(r==1){operations.subtraction(1000);}
                else if(r==2){operations.multiplication(600);}
                else if(r==3){operations.division(100);}
                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

            case 10:
                r = random.nextInt(4);
                if(r==0){operations.addition(1000);}
                else if(r==1){operations.subtraction(1000);}
                else if(r==2){operations.multiplication(1000);}
                else if(r==3){operations.division(1000);}
                firstnum = String.valueOf(Operations.getFirstNumber());
                secnum = String.valueOf(Operations.getSecondNumber());
                operator1 = String.valueOf(Operations.getOperator());
                question.setText(firstnum + operator1 + secnum);
                realanswer = Operations.getResult();
                break;

        }

    }

    public String correctAnswer(){
        Random random = new Random();
        String[] correct = {"You found a really \n good way to do it!", "You seem to really \n understand this!", "I can tell you’ve \n been practicing!",
                "Good thinking!","Great answer!","Awesome!","You are \n unique!", "Fantastic!","You're a \n problem solver!", "You learn \n quickly!","Brilliant!",
                "You're winner!","So proud of you!","Excellent!"};
        int n = random.nextInt(correct.length);
        return correct[n];
    }




}
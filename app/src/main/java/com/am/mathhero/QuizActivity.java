package com.am.mathhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnDel,ok;
    private TextView answer,time,chance,score,wisdom,question;
    private String number = null;


    CountDownTimer countDownTimer;
   // private static final long TOTAL_TIME = 20000;
    Boolean timerContinue;
    //long timeLeft = TOTAL_TIME;


    int number1;
    int number2;
    int useranswer;
    int realanswer;
    int userscore = 0;
    int userlife = 2;
    int level = 1;
    Random random = new Random();



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
                    userscore = userscore + 1;
                    correctsound.start();
                    score.setText(String.format("%d", userscore));
                } else {
                    userlife = userlife - 1;
                    wrongsound.start();
                    chance.setText(String.format("%d", userlife));
                }
                time.setText("20");
                countDownTimer.cancel();
                nexttimer();

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
        countDownTimer=new CountDownTimer(20000, 1000) {
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
        countDownTimer=new CountDownTimer(2000, 1000) {
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

        startTimer();
        switch (level) {
            case 1:
                number1 = random.nextInt(100);
                number2 = random.nextInt(100);
                realanswer = number1 + number2;
                question.setText(number1 + "+" + number2);
                break;

  /*          case "minus":
                number1 = random.nextInt(100);
                number2 = random.nextInt(100);
                if (number1 > number2) {
                    realanswer = number1 - number2;
                    question.setText(number1 + "-" + number2);
                } else {
                    realanswer = number2 - number1;
                    question.setText(number2 + "-" + number1);
                }
                break;
            case "mult":
                number1 = random.nextInt(20);
                number2 = random.nextInt(20);
                realanswer = number1 * number2;
                question.setText(number1 + "*" + number2);
                break;
            case "div":
                number1 = random.nextInt(400)+1;
                number2 = random.nextInt(400)+1;
                if (number1 > number2) {
                    if (number1 % number2 == 0) {
                        realanswer = number1 / number2;
                        question.setText(number1 + "/" + number2);
                    } else {gameContinue();}

                } else {
                    if (number2 % number1 == 0) {
                        realanswer = number2 / number1;
                        question.setText(number2 + "/" + number1);
                    } else {gameContinue();}

                } */

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
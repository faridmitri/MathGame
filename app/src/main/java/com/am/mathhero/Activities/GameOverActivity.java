package com.am.mathhero.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.am.mathhero.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameOverActivity extends AppCompatActivity {

    TextView score;
    Button chance,leader,buttonad;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;
    boolean flag = false;


    int myNum;
    long setcountryScore;
   long setScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        final MediaPlayer gameover = MediaPlayer.create(this, R.raw.gameover);
        gameover.start();

        leader = findViewById(R.id.leaderBoard);
        buttonad    = findViewById(R.id.buttonad);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        score = findViewById(R.id.textViewScore);
        score.setText("You score is: "+getIntent().getStringExtra("scor"));
        String fbscore = getIntent().getStringExtra("scor");
        long diamond = getIntent().getLongExtra("wisdom",0);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        chance = findViewById(R.id.buttonad);
        leader = findViewById(R.id.leaderBoard);

        retreive();
        buttonad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this,QuizActivity.class);
                 flag = true;
                 savedata();
                intent.putExtra("flag",flag );
                startActivity(intent);
                finish();
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
                  setcountryScore = (long) snapshot.child("countryScore").getValue();
                  myNum = Integer.parseInt(fbscore);

                  setcountryScore += myNum;

                  reference.child("Users").child(auth.getUid()).child("countryScore").setValue(setcountryScore);
                  if (setScore < myNum) {
                      reference.child("Users").child(auth.getUid()).child("score").setValue(myNum);
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });




                Intent i = new Intent(GameOverActivity.this, LeaderBoardA.class);
                startActivity(i);

            }
        });

    }

    public void savedata(){
        SharedPreferences.Editor editor = getSharedPreferences("saved", MODE_PRIVATE).edit();
        editor.putBoolean("ads", flag);
        editor.apply();
    }

    public void retreive() {
        SharedPreferences prefs = getSharedPreferences("saved", MODE_PRIVATE);
        boolean flag1 = prefs.getBoolean("ads", false);
        if (flag1) {
            buttonad.setEnabled(false);
        }
    }

}
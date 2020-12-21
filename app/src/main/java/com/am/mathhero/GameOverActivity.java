package com.am.mathhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class GameOverActivity extends AppCompatActivity {

    TextView score;
    Button chance,leader;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;

    String getCountryScore;
    String getScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        final MediaPlayer gameover = MediaPlayer.create(this, R.raw.gameover);
        gameover.start();

        leader = findViewById(R.id.leaderBoard);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        score = findViewById(R.id.textViewScore);
        score.setText("You score is: "+getIntent().getStringExtra("scor"));
        String fbscore = getIntent().getStringExtra("scor");

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        chance = findViewById(R.id.buttonad);
        leader = findViewById(R.id.leaderBoard);

        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);


  /*              getInfo();

                int myNum = 0;
                int setcountryScore = 0;
                int setScore = 0;
                try {
                    myNum = Integer.parseInt(fbscore);
                    setScore = Integer.parseInt(getScore);
                    setcountryScore = Integer.parseInt(getCountryScore);
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " );
                }
                setcountryScore += myNum;
                reference.child("Users").child(auth.getUid()).child("country").child("countryScore").setValue(setcountryScore);
                if (setScore < myNum) {
                    reference.child("Users").child(auth.getUid()).child("score").setValue(fbscore);
                }
*/


              //  reference.child("Users").child(auth.getUid()).child("country").child("countryScore").setValue(countryfirebase);
                progressBar.setVisibility(View.INVISIBLE);

                Intent i = new Intent(GameOverActivity.this, LeaderBoardA.class);
                startActivity(i);

            }
        });

    }

    public void getInfo() {
        reference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 getCountryScore = snapshot.child("country").child("countryScore").getValue().toString();
                 getScore = snapshot.child("score").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
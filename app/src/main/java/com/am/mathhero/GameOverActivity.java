package com.am.mathhero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.media.MediaPlayer;
public class GameOverActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        final MediaPlayer gameover = MediaPlayer.create(this, R.raw.gameover);
        gameover.start();
    }
}
package com.am.mathhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.am.mathhero.R;
import com.squareup.picasso.Picasso;

public class RulesActivity extends AppCompatActivity {
ImageView points,levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        setTitle("Game Rules");
        points = findViewById(R.id.points);
        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdfcbPEm7FQQsd-ZM9ccrYKMZjYjFI4iF2Og&usqp=CAU").into(points);

        levels = findViewById(R.id.levels1);
        Picasso.get().load("https://lh3.googleusercontent.com/proxy/7WvyT8i6vHN-TeRR766myDDboBypifnm5p_3vCarkhk-512x64gFslbvhVck3ONHKFyQAidGt90aeGP2KURwZZOSqaj6ixlHAiCTGkz6WgMMK8IwCS8Zcn9hBg").into(levels);

    }
}
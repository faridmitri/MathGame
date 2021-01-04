package com.am.mathhero.Activities;



import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.am.mathhero.Adapter.ViewPagerAdapter;
import com.am.mathhero.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LeaderBoardA extends AppCompatActivity {

    private TabLayout tabLayoutLeader;
    private ViewPager2 viewPagerLeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayoutLeader = findViewById(R.id.tabLayoutLeader);
        viewPagerLeader = findViewById(R.id.viewPagerLeader);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

        viewPagerLeader.setAdapter(adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayoutLeader, viewPagerLeader
                ,true , true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position)
                {
                    case 0:
                        tab.setText("World Wide");
                        break;
                   case 1:
                        tab.setText("By Country");
                        break;

                    case 2:
                        tab.setText("Countries");

                }

            }
        });

        tabLayoutMediator.attach();
    }
}
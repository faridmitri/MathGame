package com.am.mathhero.Activities;



import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.am.mathhero.Adapter.ViewPagerAdapter;
import com.am.mathhero.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LeaderBoardA extends AppCompatActivity {

    private TabLayout tabLayoutLeader;
    private ViewPager2 viewPagerLeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

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
                        tab.setText("Leader Board");
                        break;
                   case 1:
                        tab.setText("Countries Heros");
                        break;

                }

            }
        });

        tabLayoutMediator.attach();
    }
}
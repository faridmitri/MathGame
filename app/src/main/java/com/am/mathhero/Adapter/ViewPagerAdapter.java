package com.am.mathhero.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.am.mathhero.fragments.CountryScores;
import com.am.mathhero.fragments.UserCountryScores;
import com.am.mathhero.fragments.UserScores;


public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment;

        switch (position)
        {
            case 0:
                fragment = UserScores.newInstance();
                break;
            case 1:
                fragment = UserCountryScores.newInstance();
                break;

            case 2:
                fragment = CountryScores.newInstance();
                break;

            default:
                return null;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
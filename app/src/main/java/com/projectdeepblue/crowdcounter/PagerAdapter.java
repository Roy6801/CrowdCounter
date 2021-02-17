package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int behave;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        behave = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
            {
                return new Tab1();
            }
            case 1:
            {
                return new Tab2();
            }
            case 2:
            {
                return new Tab3();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return behave;
    }
}

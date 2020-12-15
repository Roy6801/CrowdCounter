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
        switch (position){
            case 0:{
                Tab1 tab1 = new Tab1();
                return tab1;
            }
            case 1:{
                Tab2 tab2 = new Tab2();
                return tab2;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return behave;
    }
}

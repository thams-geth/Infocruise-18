package com.tts.infocruise18;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Siva on 2/13/2018.
 */

class PagerViewAdapterDirection extends FragmentStatePagerAdapter {
    int tabCount;
    public PagerViewAdapterDirection(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                Fragment_brief fragment_teachers=new Fragment_brief();
                return fragment_teachers;

            case 1 :
                Fragment_waytokec fragment_notification=new Fragment_waytokec();
                return fragment_notification;

            case 2 :
                Fragment_contact fragment_profile=new Fragment_contact();
                return fragment_profile;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

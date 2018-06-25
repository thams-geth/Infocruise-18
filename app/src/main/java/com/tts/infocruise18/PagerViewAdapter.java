package com.tts.infocruise18;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

/**
 * Created by Siva on 2/13/2018.
 */

class PagerViewAdapter extends FragmentStatePagerAdapter {

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                Fragment_coders fragment_coders=new Fragment_coders();
                return fragment_coders;

            case 1 :
                Fragment_webwar fragment_webwar=new Fragment_webwar();
                return fragment_webwar;

            case 2 :
                Fragment_paper fragment_paper=new Fragment_paper();
                return fragment_paper;
            case 3 :
                Fragment_project fragment_project=new Fragment_project();
                return fragment_project;

            case 4 :
                Fragment_google fragment_google=new Fragment_google();
                return fragment_google;

            case 5 :
                Fragment_techno fragment_techno=new Fragment_techno();
                return fragment_techno;
            case 6 :
                Fragment_search fragment_search=new Fragment_search();
                return fragment_search;

            case 7 :
                Fragment_zam fragment_zam=new Fragment_zam();
                return fragment_zam;

            case 8 :
                Fragment_solostar fragment_solostar=new Fragment_solostar();
                return fragment_solostar;
            case 9 :
                Fragment_gamenotime fragment_gamenotime=new Fragment_gamenotime();
                return fragment_gamenotime;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 10;
    }
}

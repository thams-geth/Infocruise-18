package com.tts.infocruise18;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DirectionActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerViewAdapterDirection pagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        tabLayout=(TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("BRIEF"));
        tabLayout.addTab(tabLayout.newTab().setText("WAY TO KEC"));
        tabLayout.addTab(tabLayout.newTab().setText("CONTACT"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pagerViewAdapter=new PagerViewAdapterDirection(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerViewAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

package com.framgia.music;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by trant on 22/01/2018.
 */

public class Search extends AppCompatActivity implements OnFragmentInteractionListener {
    private ViewPager mViewPaper;
    private TabLayout mTabLayout;
    private static final String SONG = "SONG";
    private static final String ALBUM = "AlBUM";
    private static final String FAVORITE = "FAVORITE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_search);
        init();
        addTab(mTabLayout);
        AdapterViewPaper mAdapterView =
                new AdapterViewPaper(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPaper.setAdapter(mAdapterView);
        mViewPaper.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPaper.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void init() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPaper = (ViewPager) findViewById(R.id.viewPager);
    }

    private void addTab(TabLayout tabLayout) {
        tabLayout.addTab(mTabLayout.newTab().setText(SONG).setIcon(R.drawable.ic_song));
        tabLayout.addTab(mTabLayout.newTab().setText(ALBUM).setIcon(R.drawable.ic_album));
        tabLayout.addTab(mTabLayout.newTab().setText(FAVORITE).setIcon(R.drawable.ic_favorite));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuHome:
                Intent intentMain = new Intent(this, MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.menuIntentSearch:
                break;
            case R.id.menuFeedback:
                Intent intentFeedback = new Intent(this, Feedback.class);
                startActivity(intentFeedback);
                break;
            case R.id.menuExit:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}



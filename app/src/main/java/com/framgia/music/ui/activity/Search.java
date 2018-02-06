package com.framgia.music.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.music.util.OnFragmentInteractionListener;
import com.framgia.music.R;
import com.framgia.music.ui.adapter.AdapterViewPaper;

/**
 * Created by trant on 22/01/2018.
 */

public class Search extends AppCompatActivity implements OnFragmentInteractionListener {
    private ViewPager mViewPaper;
    private TabLayout mTabLayout;
    private static final String SONG = "SONG";
    private static final String ALBUM = "AlBUM";
    private static final String FAVORITE = "FAVORITE";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_search);
        setDrawerLayout();
        setClickNavigationView();
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

    private void setDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutSearch);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(true);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setClickNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.naviViewSearch);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuHome:
                                Intent intentHome = new Intent(Search.this, MainActivity.class);
                                startActivity(intentHome);
                                break;
                            case R.id.menuIntentSearch:
                                break;
                            case R.id.menuFeedback:
                                Intent intentFeedback = new Intent(Search.this, Feedback.class);
                                startActivity(intentFeedback);
                                break;
                            case R.id.menuExit:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                }
                                break;
                        }

                        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}



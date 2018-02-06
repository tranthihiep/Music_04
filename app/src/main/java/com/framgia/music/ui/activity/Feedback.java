package com.framgia.music.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.framgia.music.R;

/**
 * Created by trant on 22/01/2018.
 */

public class Feedback extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private ImageView mbtnGoogle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_feedback);
        setDrawerLayout();
        setClickNavigationView();
        mbtnGoogle = (ImageView) findViewById(R.id.gg);
        mbtnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearch = new Intent(Feedback.this, SendFeedback.class);
                startActivity(intentSearch);

            }
        });
    }
    private void setDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutFeedback);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(true);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setClickNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.naviViewFeedback);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuHome:
                                Intent intentHome = new Intent(Feedback.this, MainActivity.class);
                                startActivity(intentHome);
                                break;
                            case R.id.menuIntentSearch:
                                Intent intentSearch = new Intent(Feedback.this, Search.class);
                                startActivity(intentSearch);
                                break;
                            case R.id.menuFeedback:
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}


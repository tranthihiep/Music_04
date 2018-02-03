package com.framgia.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private SongDataHelper mSongDataHelper;
    private static final int REQUEST_PERMISSION = 4;
    private Animation mAnimation;
    private ArrayList<SongMusic> arraySong;
    private TextView txtNameSong;
    private ImageView mBtnPlay, mBtnNext, mBtnPrevious, mImageView;
    private TextView mTxtTimeTotal, mTxtTimeCurrent;
    private SeekBar mSeekBar;
    private int mPos = 0;
    private MediaPlayer mMediaPlayer;
    private ImageView mbtnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);
        setDrawerLayout();
        initView();
        setClickNavigationView();
        addSong();
        setbtn();
        initMedia();
    }

    private void setDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(true);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setClickNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.naviView);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuHome:
                                break;
                            case R.id.menuIntentSearch:
                                Intent intentSearch = new Intent(MainActivity.this, Search.class);
                                startActivity(intentSearch);
                                break;
                            case R.id.menuFeedback:
                                Intent intentFeedback =
                                        new Intent(MainActivity.this, Feedback.class);
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

    private void setbtn() {
        if (arraySong.get(mPos).getFavorite() == 0) {
            mbtnFavorite.setImageResource(R.drawable.ic_unfavorite);
        } else {
            mbtnFavorite.setImageResource(R.drawable.ic_favorite);
        }
    }

    private void addSong() {
        arraySong = new ArrayList<>();
        arraySong = mSongDataHelper.getListSongFinal();
    }

    private void initView() {
        txtNameSong = (TextView) findViewById(R.id.txtNameSong);
        mBtnNext = (ImageView) findViewById(R.id.btnNext);
        mBtnPlay = (ImageView) findViewById(R.id.btnPlay);
        mBtnPrevious = (ImageView) findViewById(R.id.btnPre);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTxtTimeTotal = (TextView) findViewById(R.id.txtTotal);
        mTxtTimeCurrent = (TextView) findViewById(R.id.txtTimeCur);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mbtnFavorite = (ImageView) findViewById(R.id.btnFavorite);
        mbtnFavorite.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnPrevious.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
    }

    private void setSeekBar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(mSeekBar.getProgress());
            }
        });
    }

    @Override
    public void onClick(View view) {
        setSeekBar();
        switch (view.getId()) {
            case R.id.btnPlay:
                onPlayMusic();
                setbtn();
                break;
            case R.id.btnNext:
                onNextSongs();
                setbtn();
                break;
            case R.id.btnPre:
                onPeriousSongs();
                setbtn();
                break;
            case R.id.btnFavorite:
                break;
        }
    }

    private void onPeriousSongs() {
        mPos--;
        if (mPos < 0) {
            mPos = arraySong.size() - 1;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        initMedia();
        mMediaPlayer.start();
        mBtnPlay.setImageResource(R.drawable.ic_pause);
        setTimeTotal();
        updateTimeSong();
    }

    private void onNextSongs() {
        mPos++;
        if (mPos > arraySong.size() - 1) {
            mPos = 0;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        initMedia();
        mMediaPlayer.start();
        mBtnPlay.setImageResource(R.drawable.ic_pause);
        setTimeTotal();
        updateTimeSong();
    }

    private void onPlayMusic() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mBtnPlay.setImageResource(R.drawable.ic_play);
        } else {
            mMediaPlayer.start();
            mBtnPlay.setImageResource(R.drawable.ic_pause);
            setTimeTotal();
            updateTimeSong();
        }
    }

    private void initMedia() {
        mMediaPlayer =
                MediaPlayer.create(MainActivity.this, Uri.parse(arraySong.get(mPos).getPath()));
        txtNameSong.setText(arraySong.get(mPos).getName());
        mImageView.startAnimation(mAnimation);
    }

    private void setTimeTotal() {
        SimpleDateFormat formatHours = new SimpleDateFormat("mm:ss", Locale.US);
        mTxtTimeTotal.setText(formatHours.format(mMediaPlayer.getDuration()));
        mSeekBar.setMax(mMediaPlayer.getDuration());
    }

    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat formatHours = new SimpleDateFormat("mm:ss", Locale.US);
                mTxtTimeCurrent.setText(formatHours.format(mMediaPlayer.getCurrentPosition()));
                mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        onNextSongs();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            initPermission();
        }
    }

    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    REQUEST_PERMISSION);
        } else {
            mSongDataHelper = new SongDataHelper(this);
            mSongDataHelper.initListSong();
        }
    }
}




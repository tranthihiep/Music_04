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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SongDataHelper mSongDataHelper;
    private Animation mAnimation;
    private ArrayList<SongMusic> arraySong;
    private TextView txtNameSong;
    private ImageView mBtnPlay, mBtnNext, mBtnPrevious, mImageView;
    private TextView mTxtTimeTotal, mTxtTimeCurrent;
    private SeekBar mSeekBar;
    private int mPos = 0;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_cd);
        mSongDataHelper = new SongDataHelper(this, "Data.db", null, 1);
        mSongDataHelper.initListSong();
        initView();
        addSong();
        initMedia();
        mSongDataHelper.close();
    }

    private void addSong() {
        arraySong = new ArrayList<>();
        arraySong = mSongDataHelper.getListSongFromDatabase();
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
        mBtnPlay.setOnClickListener(this);
        mBtnPrevious.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
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
                break;
            case R.id.menuIntentSearch:
                Intent intentSearch = new Intent(this, Search.class);
                startActivity(intentSearch);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, R.string.permission_granted, Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, R.string.permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, R.string.permission_show_again,
                            Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
            }
        }
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
                break;
            case R.id.btnNext:
                onNextSongs();
                break;
            case R.id.btnPre:
                onPeriousSongs();
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
        mBtnPlay.setImageResource(R.drawable.pause);
        setTimeEnd();
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
        mBtnPlay.setImageResource(R.drawable.pause);
        setTimeEnd();
        updateTimeSong();
    }

    private void onPlayMusic() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mBtnPlay.setImageResource(R.drawable.play);
        } else {
            initMedia();
            mImageView.startAnimation(mAnimation);
            mMediaPlayer.start();
            mBtnPlay.setImageResource(R.drawable.pause);
            setTimeEnd();
            updateTimeSong();
        }
    }

    private void initMedia() {
        mMediaPlayer =
                MediaPlayer.create(MainActivity.this, Uri.parse(arraySong.get(mPos).getPath()));
        txtNameSong.setText(arraySong.get(mPos).getName());
    }

    private void setTimeEnd() {
        SimpleDateFormat formatHout = new SimpleDateFormat("mm:ss");
        mTxtTimeTotal.setText(formatHout.format(mMediaPlayer.getDuration()));
        mSeekBar.setMax(mMediaPlayer.getDuration());

    }

    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat fomatHout = new SimpleDateFormat("mm:ss");
                mTxtTimeCurrent.setText(fomatHout.format(mMediaPlayer.getCurrentPosition()));
                mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        onNextSongs();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 1000);
    }
}


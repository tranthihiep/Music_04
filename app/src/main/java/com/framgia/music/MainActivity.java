package com.framgia.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private SongDataHelper mSongDataHelper;
    private static final int REQUEST_PERMISSION = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
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
            mSongDataHelper.close();
        }
    }
}


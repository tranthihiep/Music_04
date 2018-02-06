package com.framgia.music.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.framgia.music.ui.fragment.FragmentAlbum;
import com.framgia.music.ui.fragment.FragmentFavorite;
import com.framgia.music.ui.fragment.FragmentSong;

/**
 * Created by trant on 22/01/2018.
 */

public class AdapterViewPaper extends FragmentStatePagerAdapter {

    private int mTab;

    public AdapterViewPaper(FragmentManager fm, int mTab) {
        super(fm);
        this.mTab = mTab;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new FragmentSong();
                break;
            case 1:
                frag = new FragmentAlbum();
                break;
            case 2:
                frag = new FragmentFavorite();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return mTab;
    }
}

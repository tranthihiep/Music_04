package com.framgia.music.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music.util.OnItemClickListenner;
import com.framgia.music.R;
import com.framgia.music.data.local.SongDataHelper;
import com.framgia.music.data.model.SongMusic;
import com.framgia.music.ui.adapter.AdapterFavorite;
import java.util.ArrayList;

/**
 * Created by trant on 20/01/2018.
 */

public class FragmentFavorite extends Fragment implements OnItemClickListenner {
    private RecyclerView mRecyclerFavorite;
    private ArrayList<SongMusic> mArraySong;
    private AdapterFavorite mAdapterFavorite;
    private SongDataHelper mSongDataHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        mRecyclerFavorite = (RecyclerView) view.findViewById(R.id.recyclerFavorite);
        mSongDataHelper = new SongDataHelper(getContext());
        mArraySong = new ArrayList<>();
        setRecyclerSong();
        fillListSongToList();
        return view;
    }

    private void setRecyclerSong() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerFavorite.setLayoutManager(layoutManager);
        mRecyclerFavorite.setItemAnimator(new DefaultItemAnimator());
    }

    private void fillListSongToList() {
        mArraySong = mSongDataHelper.getListSongFavorite();
        mAdapterFavorite = new AdapterFavorite(mArraySong, getContext(), this);
        mRecyclerFavorite.setAdapter(mAdapterFavorite);
        mAdapterFavorite.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFavRowFavorite:
                mAdapterFavorite.notifyDataSetChanged();
                break;
            case R.id.btnUnFavRowFavorite:
                mAdapterFavorite.notifyDataSetChanged();
                break;
        }
    }
}


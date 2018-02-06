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
import com.framgia.music.ui.adapter.AdapterSong;
import java.util.ArrayList;

/**
 * Created by trant on 20/01/2018.
 */

public class FragmentSong extends Fragment implements OnItemClickListenner {
    private RecyclerView mRecyclerSong;
    private ArrayList<SongMusic> mArraySong;
    private AdapterSong mAdapterSong;
    private SongDataHelper mSongDataHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        mRecyclerSong = (RecyclerView) view.findViewById(R.id.recyclerSong);
        mSongDataHelper = new SongDataHelper(getContext());
        mArraySong = new ArrayList<>();
        setRecyclerSong();
        fillListSongToList();
        return view;
    }

    private void setRecyclerSong() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerSong.setLayoutManager(layoutManager);
        mRecyclerSong.setItemAnimator(new DefaultItemAnimator());
    }

    private void fillListSongToList() {
        mArraySong = mSongDataHelper.getListSongFinal();
        mAdapterSong = new AdapterSong(mArraySong, getContext(), this);
        mRecyclerSong.setAdapter(mAdapterSong);
        mAdapterSong.notifyDataSetChanged();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btndelRowSong:
                fillListSongToList();
                //TODO
                break;
            case R.id.btnAddRowSong:
                //TODO
                break;
            case R.id.btnUnFavRowSong:

                break;
            case R.id.btnFavRowSong:

                break;
        }
    }
}


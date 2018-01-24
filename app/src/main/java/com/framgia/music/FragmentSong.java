package com.framgia.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;

/**
 * Created by trant on 20/01/2018.
 */

public class FragmentSong extends Fragment {
    private RecyclerView mRecyclerSong;
    private ArrayList<SongMusic> mArraySong;
    private AdapterSong mAdapterSong;
    private DataSong mDataSong;
    private EditText edtfagSearchSong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        mDataSong = new DataSong(getContext(), "Data.db", null, 1);
        mDataSong.insertSongIntoTableSong();
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
        mArraySong = mDataSong.querySong();
        mAdapterSong = new AdapterSong(mArraySong, getContext());
        mRecyclerSong.setAdapter(mAdapterSong);
        mAdapterSong.notifyDataSetChanged();
    }
}

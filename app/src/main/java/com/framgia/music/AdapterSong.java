package com.framgia.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by trant on 24/01/2018.
 */

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolder> {
    private ArrayList<SongMusic> mSongs;
    private Context context;

    public AdapterSong(ArrayList<SongMusic> songMusics, Context context) {
        this.mSongs = songMusics;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_song, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtNameSong;
        ImageView mImgFavorite, mImgDelete, mImgAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtNameSong = (TextView) itemView.findViewById(R.id.txtNameRowSong);
            mImgFavorite = (ImageView) itemView.findViewById(R.id.btnFavRowSong);
            mImgDelete = (ImageView) itemView.findViewById(R.id.btndelRowSong);
            mImgAdd = (ImageView)itemView.findViewById(R.id.btnAddRowSong);
        }

        public void setData(int pos) {
            mTxtNameSong.setText(mSongs.get(pos).getName());
            mImgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mImgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

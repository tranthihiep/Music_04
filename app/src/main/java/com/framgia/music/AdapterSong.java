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
 * Created by trant on 29/01/2018.
 */
public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolder> {
    private Context mContext;
    private ArrayList<SongMusic> mSongs;
    private OnItemClickListenner mListenner;

    public AdapterSong(ArrayList<SongMusic> songMusics, Context context,
            OnItemClickListenner onItem) {
        mSongs = songMusics;
        mContext = context;
        mListenner = onItem;
    }

    public void setListenner(OnItemClickListenner listenner) {
        mListenner = listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_song, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position, mListenner);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtNameSong;
        private ImageView mImgFavorite, mUnImgFavorite, mImgDelete, mImgAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtNameSong = (TextView) itemView.findViewById(R.id.txtNameRowSong);
            mImgFavorite = (ImageView) itemView.findViewById(R.id.btnFavRowSong);
            mUnImgFavorite = (ImageView) itemView.findViewById(R.id.btnUnFavRowSong);
            mImgDelete = (ImageView) itemView.findViewById(R.id.btndelRowSong);
            mImgAdd = (ImageView) itemView.findViewById(R.id.btnAddRowSong);
        }

        public void setData(final int pos, OnItemClickListenner listenner) {
            mListenner = listenner;
            mTxtNameSong.setText(mSongs.get(pos).getName());
            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(getAdapterPosition(), view);
                }
            });
            mImgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(getAdapterPosition(), view);
                }
            });
            mUnImgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(getAdapterPosition(), view);
                }
            });
            mImgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(getAdapterPosition(), view);
                }
            });
        }
    }
}

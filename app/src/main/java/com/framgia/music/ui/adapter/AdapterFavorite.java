package com.framgia.music.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.framgia.music.util.OnItemClickListenner;
import com.framgia.music.R;
import com.framgia.music.data.local.SongDataHelper;
import com.framgia.music.data.model.SongMusic;
import java.util.ArrayList;

/**
 * Created by trant on 05/02/2018.
 */

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {
    private Context mContext;
    private ArrayList<SongMusic> mSongs;
    private OnItemClickListenner mListenner;
    private SongDataHelper mSongDataHelper;

    public AdapterFavorite(ArrayList<SongMusic> songMusics, Context context,
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
        View itemView = layoutInflater.inflate(R.layout.row_favorite, parent, false);
        mSongDataHelper = new SongDataHelper(mContext);
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
        private TextView mTxtNameSongFavorite;
        private ImageView mImgFavFavorite, mUnImgFavFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtNameSongFavorite = (TextView) itemView.findViewById(R.id.txtNameRowFavorite);
            mImgFavFavorite = (ImageView) itemView.findViewById(R.id.btnFavRowFavorite);
            mUnImgFavFavorite = (ImageView) itemView.findViewById(R.id.btnUnFavRowFavorite);
        }

        public void setData(final int pos, OnItemClickListenner listenner) {
            mListenner = listenner;
            mTxtNameSongFavorite.setText(mSongs.get(pos).getName());
            int favorite = mSongs.get(pos).getFavorite();
            if (favorite == 0) {
                mImgFavFavorite.setVisibility(View.INVISIBLE);
                mUnImgFavFavorite.setVisibility(View.VISIBLE);
            } else {
                mUnImgFavFavorite.setVisibility(View.INVISIBLE);
                mImgFavFavorite.setVisibility(View.VISIBLE);
            }

            mImgFavFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSongDataHelper.update(0, mSongs.get(getAdapterPosition()).getId());
                    mUnImgFavFavorite.setImageResource(R.drawable.ic_unfavorite);
                    //mImgFavFavorite.setVisibility(View.INVISIBLE);
                    mListenner.onClick(view);
                }
            });
        }
    }
}
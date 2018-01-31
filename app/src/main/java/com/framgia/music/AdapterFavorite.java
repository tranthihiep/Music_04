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
 * Created by trant on 31/01/2018.
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
            if (favorite == 0){
                mImgFavFavorite.setVisibility(View.INVISIBLE);
                mUnImgFavFavorite.setVisibility(View.VISIBLE);
            }else {
                mUnImgFavFavorite.setVisibility(View.INVISIBLE);
                mImgFavFavorite.setVisibility(View.VISIBLE);
            }
            mUnImgFavFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mSongDataHelper.update(1,mSongs.get(pos).getId());
                    mImgFavFavorite.setVisibility(View.INVISIBLE);
                    mUnImgFavFavorite.setVisibility(View.VISIBLE);
                    mListenner.onClick(getAdapterPosition(), view);
                }
            });
            mImgFavFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mSongDataHelper.update(0,mSongs.get(pos).getId());
                    mImgFavFavorite.setVisibility(View.VISIBLE);
                    mUnImgFavFavorite.setVisibility(View.INVISIBLE);

                    mListenner.onClick(getAdapterPosition(), view);
                }
            });


        }
    }
}
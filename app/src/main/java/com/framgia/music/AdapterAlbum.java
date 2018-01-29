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
 * Created by trant on 25/01/2018.
 */

public class AdapterAlbum extends RecyclerView.Adapter<AdapterAlbum.ViewHolder> {
    private ArrayList<Album> mAlbums;
    private Context mContext;
    private OnItemClickListenner mListenner;

    public AdapterAlbum(ArrayList<Album> albums, Context context, OnItemClickListenner onItem) {
        this.mContext = context;
        this.mAlbums = albums;
        this.mListenner = onItem;
    }

    public void setListenner(OnItemClickListenner listenner) {
        mListenner = listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_album, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position, mListenner);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtNameAlbum;
        private ImageView mImgEdit, mImgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtNameAlbum = (TextView) itemView.findViewById(R.id.txtNameRowAlbum);
            mImgEdit = (ImageView) itemView.findViewById(R.id.btnEditRowAlbum);
            mImgDelete = (ImageView) itemView.findViewById(R.id.btnDeleteRowAlbum);
        }

        public void setData(final int pos, OnItemClickListenner listenner) {
            mListenner = listenner;
            mTxtNameAlbum.setText(mAlbums.get(pos).getNameAlbum());
            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(getAdapterPosition(), view);
                }
            });
            mImgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(getAdapterPosition(), view);
                }
            });
        }
    }
}


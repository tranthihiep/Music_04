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

    public AdapterAlbum(ArrayList<Album> albums, Context context) {
        this.mAlbums = albums;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_album, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTxtNameAlbum;
        private ImageView mImgEdit, mImgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtNameAlbum = (TextView) itemView.findViewById(R.id.txtNameRowAlbum);
            mImgEdit = (ImageView) itemView.findViewById(R.id.btnEditRowAlbum);
            mImgDelete = (ImageView) itemView.findViewById(R.id.btnDeleteRowAlbum);
        }

        public void setData(int pos) {
            mTxtNameAlbum.setText(mAlbums.get(pos).getNameAlbum());
            mImgEdit.setOnClickListener(this);
            mImgDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //TODO
        }
    }
}

package com.framgia.music.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.framgia.music.R;
import com.framgia.music.data.local.SongDataHelper;
import com.framgia.music.data.model.Album;
import com.framgia.music.data.model.SongMusic;
import com.framgia.music.util.OnItemClickListenner;
import java.util.ArrayList;

/**
 * Created by trant on 25/01/2018.
 */

public class AdapterAlbum extends RecyclerView.Adapter<AdapterAlbum.ViewHolder> {
    private ArrayList<Album> mAlbums;
    private Context mContext;
    private OnItemClickListenner mListenner;
    private SongDataHelper mSongDataHelper;

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
        mSongDataHelper = new SongDataHelper(mContext);
        return new ViewHolder(itemView, mListenner);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtNameAlbum;
        private ImageView mImgEdit, mImgDelete;

        public ViewHolder(View itemView, OnItemClickListenner listenner) {
            super(itemView);
            mListenner = listenner;
            mTxtNameAlbum = (TextView) itemView.findViewById(R.id.txtNameRowAlbum);
            mImgDelete = (ImageView) itemView.findViewById(R.id.btnDeleteRowAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogListView(mAlbums.get(getAdapterPosition()).getIdAlbum());
                }
            });
        }

        public void setData(int pos) {
            mTxtNameAlbum.setText(mAlbums.get(pos).getNameAlbum());
            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSongDataHelper.deleteAlbum(mAlbums.get(getAdapterPosition()).getIdAlbum());
                    mListenner.onClick(view);
                }
            });
        }
    }

    public void showDialogListView(final int idalbum) {
        final Dialog mDialog;
        mDialog = new Dialog(mContext);
        mDialog.setTitle(R.string.danh_sach_bai_hat);
        mDialog.setContentView(R.layout.list_song_in_album_dialog);
        ArrayList<SongMusic> arrayList;
        AdapterSongDialogAlbum adapter;
        ListView list = (ListView) mDialog.findViewById(R.id.lvSong);
        arrayList = new ArrayList<>();
        arrayList = mSongDataHelper.getListSongFromAlbum(idalbum);
        adapter = new AdapterSongDialogAlbum(mContext, R.layout.row_dialog_album, arrayList);
        list.setAdapter(adapter);
        mDialog.show();
    }
}


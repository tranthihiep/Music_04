package com.framgia.music.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Created by trant on 29/01/2018.
 */
public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolder>
        implements OnItemClickListenner {
    private Context mContext;
    private ArrayList<SongMusic> mSongs;
    private OnItemClickListenner mListenner;
    private SongDataHelper mSongDataHelper;

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
        mSongDataHelper = new SongDataHelper(mContext);
        return new ViewHolder(itemView, mListenner);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtNameSong;
        private ImageView mImgFavorite, mUnImgFavorite, mImgDelete, mImgAdd;

        public ViewHolder(View itemView, OnItemClickListenner listenner) {
            super(itemView);
            mListenner = listenner;
            mTxtNameSong = (TextView) itemView.findViewById(R.id.txtNameRowSong);
            mImgFavorite = (ImageView) itemView.findViewById(R.id.btnFavRowSong);
            mUnImgFavorite = (ImageView) itemView.findViewById(R.id.btnUnFavRowSong);
            mImgDelete = (ImageView) itemView.findViewById(R.id.btndelRowSong);
            mImgAdd = (ImageView) itemView.findViewById(R.id.btnAddRowSong);
        }

        public void setData(int pos) {

            mTxtNameSong.setText(mSongs.get(pos).getName());
            int favorite = mSongs.get(pos).getFavorite();
            if (favorite == 0) {
                mImgFavorite.setVisibility(View.INVISIBLE);
                mUnImgFavorite.setVisibility(View.VISIBLE);
            } else {
                mUnImgFavorite.setVisibility(View.INVISIBLE);
                mImgFavorite.setVisibility(View.VISIBLE);
            }
            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mSongDataHelper.deleteSong(mSongs.get(getAdapterPosition()).getId());
                    mListenner.onClick(view);
                }
            });
            mImgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(view);
                    showDialogListView(mSongs.get(getAdapterPosition()).getId());
                }
            });
            mUnImgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(view);
                    mSongDataHelper.update(1, mSongs.get(getAdapterPosition()).getId());
                    mImgFavorite.setVisibility(View.VISIBLE);
                    mUnImgFavorite.setVisibility(View.INVISIBLE);
                }
            });
            mImgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListenner.onClick(view);
                    mSongDataHelper.update(0, mSongs.get(getAdapterPosition()).getId());
                    mImgFavorite.setVisibility(View.INVISIBLE);
                    mUnImgFavorite.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void showDialogListView(final int idsong) {

        final Dialog mDialog;
        mDialog = new Dialog(mContext);
        mDialog.setTitle(R.string.danh_sach_album);
        mDialog.setContentView(R.layout.list_item_dialog);
        SongDataHelper albumDataHelper = new SongDataHelper(mContext);
        final ArrayList<Album> arrayList = albumDataHelper.queryAlbum();
        ;
        AdapterAlbumDialog adapter;
        ListView list = (ListView) mDialog.findViewById(R.id.lv);
        adapter = new AdapterAlbumDialog(mContext, R.layout.row_dialog_album, arrayList);
        list.setAdapter(adapter);
        final SongDataHelper detailAlbumDataHelper = new SongDataHelper(mContext);
        //final ArrayList<Album> finalArrayList = arrayList;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                detailAlbumDataHelper.insertSongInAlbum(arrayList.get(i).getIdAlbum(), idsong);
            }
        });
        mDialog.show();
    }
}

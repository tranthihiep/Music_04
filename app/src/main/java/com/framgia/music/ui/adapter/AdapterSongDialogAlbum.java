package com.framgia.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.framgia.music.R;
import com.framgia.music.data.model.SongMusic;
import java.util.List;

/**
 * Created by trant on 05/02/2018.
 */

public class AdapterSongDialogAlbum extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SongMusic> mSongMusics;

    public AdapterSongDialogAlbum(Context context, int layout, List<SongMusic> songMusics) {
        this.context = context;
        this.layout = layout;
        this.mSongMusics = songMusics;
    }

    @Override
    public int getCount() {
        return mSongMusics.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtten;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.txtten = (TextView) view.findViewById(R.id.txtNameRowAlbum);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        SongMusic songMusic = mSongMusics.get(i);
        holder.txtten.setText(songMusic.getName());
        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}

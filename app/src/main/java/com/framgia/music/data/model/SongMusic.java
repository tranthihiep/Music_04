package com.framgia.music.data.model;

/**
 * Created by trant on 22/01/2018.
 */

public class SongMusic {
    private int mId;
    private String mName;
    private String mPath;
    private int mFavorite;

    public SongMusic(int id, String name, String path, int favorite) {
        mId = id;
        mName = name;
        mPath = path;
        mFavorite = favorite;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(int favorite) {
        mFavorite = favorite;
    }
}



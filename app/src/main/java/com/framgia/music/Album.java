package com.framgia.music;

/**
 * Created by trant on 25/01/2018.
 */
public class Album {
    private int mIdAlbum;
    private String mNameAlbum;

    public Album(int idAlbum, String nameAlbum) {
        this.mIdAlbum = idAlbum;
        this.mNameAlbum = nameAlbum;
    }

    public int getIdAlbum() {
        return mIdAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.mIdAlbum = idAlbum;
    }

    public String getNameAlbum() {
        return mNameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.mNameAlbum = nameAlbum;
    }
}

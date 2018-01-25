package com.framgia.music;

/**
 * Created by trant on 25/01/2018.
 */
public class Album {
    private int idAlbum;
    private String nameAlbum;

    public Album(int idAlbum, String nameAlbum) {
        this.idAlbum = idAlbum;
        this.nameAlbum = nameAlbum;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }
}

package com.framgia.music;

/**
 * Created by trant on 22/01/2018.
 */

public class SongMusic {
    private int id;
    private String name;
    private String album;
    private int favorite;

    public SongMusic(int id, String name, String album, int favorite) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}

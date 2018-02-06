package com.framgia.music.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import com.framgia.music.data.model.Album;
import com.framgia.music.data.model.SongMusic;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by trant on 22/01/2018.
 */

public class SongDataHelper extends SQLiteOpenHelper {
    public static final String TABLE_SONG = "TableSong";
    public static final String KEY_ID_SONG = "idSong";
    public static final String KEY_NAME_SONG = "nameSong";
    public static final String KEY_PATH_SONG = "pathSong";
    public static final String KEY_FAVORITE = "ic_favorite";
    public static final String DATA_NAME = "DataFramgia.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_DETAIL = "TableDetail";
    public static final String KEY_DETAIL = "idDetail";
    public static final String KEY_ID_SONG_DETAIL = "idSongDetail";
    public static final String KEY_ID_ALBUM_DETAIL = "idAlbumDetail";
    public static final String TABLE_ALBUM = "TableAlbum";
    public static final String KEY_ID_ALBUM = "idAlbum";
    public static final String KEY_NAME_ALBUM = "nameAlbum";

    public SongDataHelper(Context context) {
        super(context, DATA_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1 = "CREATE TABLE "
                + TABLE_SONG
                + "("
                + KEY_ID_SONG
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME_SONG
                + " TEXT,"
                + KEY_PATH_SONG
                + " TEXT,"
                + KEY_FAVORITE
                + " INTEGER"
                + ")";

        String sql2 = "CREATE TABLE "
                + TABLE_ALBUM
                + "("
                + KEY_ID_ALBUM
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME_ALBUM
                + " TEXT"
                + ")";

        String sql3 = "CREATE TABLE "
                + TABLE_DETAIL
                + "("
                + KEY_DETAIL
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ID_SONG_DETAIL
                + " INTEGER,"
                + KEY_ID_ALBUM_DETAIL
                + " INTEGER,"
                + " FOREIGN KEY ("
                + KEY_ID_SONG_DETAIL
                + ") REFERENCES "
                + TABLE_SONG
                + "("
                + KEY_ID_SONG
                + "), "
                + " FOREIGN KEY ("
                + KEY_ID_ALBUM_DETAIL
                + ") REFERENCES "
                + TABLE_ALBUM
                + "("
                + KEY_ID_ALBUM
                + "));";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void initListSong() {
        insertListSongFromStorage();
        getListSongFromDatabase();
    }

    public ArrayList<SongMusic> getListSongFinal() {
        return getListSongFromDatabase();
    }

    private void insertListSongFromStorage() {
        File file = Environment.getExternalStorageDirectory();
        ArrayList<SongMusic> songListDatabase;
        songListDatabase = getListSongFromDatabase();
        ArrayList<HashMap<String, String>> songListStorage = getPlayList(file.getPath());
        for (int i = 0; i < songListStorage.size(); i++) {
            if (songListDatabase.isEmpty()) {
                insertSongToDatabase(songListStorage.get(i).get("file_name"),
                        songListStorage.get(i).get("file_path"));
            } else {
                boolean isSongNew = true;
                String pathSong = songListStorage.get(i).get("file_path");
                for (int j = 0; j < songListDatabase.size(); j++) {
                    if (pathSong.equals(songListDatabase.get(j).getPath())) {
                        isSongNew = false;
                    }
                }
                if (isSongNew) {
                    insertSongToDatabase(songListStorage.get(i).get("file_name"), pathSong);
                }
            }
        }
    }

    public void insertSongToDatabase(String fileName, String pathSong) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_SONG, fileName);
        values.put(KEY_PATH_SONG, pathSong);
        values.put(KEY_FAVORITE, 0);
        db.insert(TABLE_SONG, null, values);
        db.close();
    }

    private ArrayList<HashMap<String, String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String, String>> fileList = new ArrayList<>();
        File rootFolder = new File(rootPath);
        File[] files = rootFolder.listFiles();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            return fileList;
        }
    }

    public ArrayList<SongMusic> getListSongFromDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<SongMusic> songMusics = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(TABLE_SONG, null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID_SONG));
            String name = c.getString(c.getColumnIndex(KEY_NAME_SONG));
            String path = c.getString(c.getColumnIndex(KEY_PATH_SONG));
            int favorite = c.getInt(c.getColumnIndex(KEY_FAVORITE));
            songMusics.add(new SongMusic(id, name, path, favorite));
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return songMusics;
    }

    public void update(int fav, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITE, fav);
        sqLiteDatabase.update(TABLE_SONG, values, KEY_ID_SONG + " = " + id, null);
    }

    public void deleteSong(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_SONG, KEY_ID_SONG + " = " + id, null);
    }

    public void deleteAlbum(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_ALBUM, KEY_ID_ALBUM + " = " + id, null);
    }

    public ArrayList<SongMusic> getListSongFavorite() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<SongMusic> songSearchs = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + TABLE_SONG + " WHERE " + KEY_FAVORITE + " = 1 ", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID_SONG));
            String name = c.getString(c.getColumnIndex(KEY_NAME_SONG));
            String path = c.getString(c.getColumnIndex(KEY_PATH_SONG));
            int favorite = c.getInt(c.getColumnIndex(KEY_FAVORITE));
            songSearchs.add(new SongMusic(id, name, path, favorite));
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return songSearchs;
    }

    public void insertAlbumIntoTableAlbum(String nameAlbum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_ALBUM, nameAlbum);
        db.insert(TABLE_ALBUM, null, values);
        db.close();
    }

    public ArrayList<Album> queryAlbum() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Album> albums = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(TABLE_ALBUM, null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID_ALBUM));
            String name = c.getString(c.getColumnIndex(KEY_NAME_ALBUM));
            albums.add(new Album(id, name));
            c.moveToNext();
        }
        c.close();
        return albums;
    }

    public void insertSongInAlbum(int idSong, int idAlbum) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_SONG_DETAIL, idSong);
        values.put(KEY_ID_ALBUM_DETAIL, idAlbum);
        db.insert(TABLE_DETAIL, null, values);
        db.close();
    }

    public ArrayList<SongMusic> getListSongFromAlbum(int idAlbum) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<SongMusic> songSearchs = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(" SELECT "
                + TABLE_SONG
                + "."
                + KEY_ID_SONG
                + ", "
                + TABLE_SONG
                + "."
                + KEY_NAME_SONG
                + " FROM "
                + TABLE_SONG
                + " , "
                + TABLE_DETAIL
                + " , "
                + TABLE_ALBUM
                + " WHERE "
                + TABLE_SONG
                + "."
                + KEY_ID_SONG
                + " = "
                + TABLE_DETAIL
                + "."
                + KEY_ID_SONG_DETAIL
                + " AND "
                + TABLE_ALBUM
                + "."
                + KEY_ID_ALBUM
                + " = "
                + idAlbum, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID_SONG));
            String name = c.getString(c.getColumnIndex(KEY_NAME_SONG));
            songSearchs.add(new SongMusic(id, name, "", 0));
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return songSearchs;
    }
}


package com.framgia.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
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
    public static final String KEY_ID_ALBUM_TB_SONG = "idAlbum";
    public static final String DATA_NAME = "Data.db";
    private static final int DATABASE_VERSION = 1;

    public SongDataHelper(Context context) {
        super(context, DATA_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "
                + TABLE_SONG
                + "("
                + KEY_ID_SONG
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME_SONG
                + " TEXT,"
                + KEY_PATH_SONG
                + " TEXT,"
                + KEY_FAVORITE
                + " INTEGER,"
                + KEY_ID_ALBUM_TB_SONG
                + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(sql);
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
        //values.put(KEY_ID_ALBUM_TB_SONG, "");
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
            int idAlbum = c.getInt(c.getColumnIndex(KEY_ID_ALBUM_TB_SONG));
            songMusics.add(new SongMusic(id, name, path, favorite, idAlbum));
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
    public ArrayList<SongMusic> getListSongFavorite() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<SongMusic> songSearchs = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SONG + " WHERE "+ KEY_FAVORITE + " = 1 ",null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID_SONG));
            String name = c.getString(c.getColumnIndex(KEY_NAME_SONG));
            String path = c.getString(c.getColumnIndex(KEY_PATH_SONG));
            int favorite = c.getInt(c.getColumnIndex(KEY_FAVORITE));
            int idAlbum = c.getInt(c.getColumnIndex(KEY_ID_ALBUM_TB_SONG));
            songSearchs.add(new SongMusic(id, name, path, favorite, idAlbum));
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return songSearchs;
    }
}

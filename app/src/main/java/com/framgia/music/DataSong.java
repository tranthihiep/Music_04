package com.framgia.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by trant on 22/01/2018.
 */

public class DataSong extends SQLiteOpenHelper {

    public DataSong(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql2 = "CREATE TABLE TableSong ("
                + "idSong INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nameSong TEXT,"
                + "favorite INTEGER,"
                + "idAlbum INTEGER NOT NULL CONSTRAINT idAlbum "
                + "REFERENCES TableAlbum(idAlbum) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertSongIntoTableSong() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<HashMap<String, String>> songList = getPlayList("/storage/emulated/0");
        if (songList != null) {
            for (int i = 0; i < songList.size(); i++) {
                ContentValues values = new ContentValues();
                String fileName = songList.get(i).get("file_name");
                values.put("nameSong", fileName);
                values.put("idAlbum", "");
                values.put("favorite", 0);
                db.insert("TableSong", null, values);
            }
        }
    }

    public ArrayList<SongMusic> querySong() {
        ArrayList<SongMusic> songMusics = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.query("TableSong", null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            songMusics.add(new SongMusic(id, name, null, 0));
            c.moveToNext();
        }
        c.close();
        return songMusics;
    }

    private ArrayList<HashMap<String, String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String, String>> fileList = new ArrayList<>();

        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    HashMap<String, String> song = new HashMap<>();
                    //song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e) {
            throw new RuntimeException("Error of service !!!");
        }
    }
}



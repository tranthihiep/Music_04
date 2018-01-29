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
    public SongDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    private static ArrayList<SongMusic> songListFinal;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE TableSong ("
                + "idSong INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nameSong TEXT,"
                + "pathSong TEXT,"
                + "favorite INTEGER,"
                + "idAlbum INTEGER NOT NULL CONSTRAINT idAlbum "
                + "REFERENCES TableAlbum(idAlbum) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void initListSong() {
        insertListSongFromStorage();
        songListFinal = new ArrayList<>();
        songListFinal = getListSongFromDatabase();
    }

    public static ArrayList<SongMusic> getListSongFinal() {
        return songListFinal;
    }

    private void insertListSongFromStorage() {
        File file = Environment.getExternalStorageDirectory();
        ArrayList<SongMusic> songListDatabase = new ArrayList<SongMusic>();
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
        values.put("nameSong", fileName);
        values.put("pathSong", pathSong);
        values.put("idAlbum", "");
        values.put("favorite", 0);
        db.insert("TableSong", null, values);
        db.close();
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
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e) {
            throw new RuntimeException("Error of service !!!");
        }
    }

    public ArrayList<SongMusic> getListSongFromDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<SongMusic> songMusics = new ArrayList<>();
        Cursor c = sqLiteDatabase.query("TableSong", null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("idSong"));
            String name = c.getString(c.getColumnIndex("nameSong"));
            String path = c.getString(c.getColumnIndex("pathSong"));
            int favorite = c.getInt(c.getColumnIndex("favorite"));
            int idAlbum = c.getInt(c.getColumnIndex("idAlbum"));
            songMusics.add(new SongMusic(id, name, path, favorite, idAlbum));
            c.moveToNext();
        }
        c.close();
        return songMusics;
    }
}

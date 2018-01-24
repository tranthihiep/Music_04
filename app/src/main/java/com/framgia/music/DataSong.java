package com.framgia.music;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by trant on 22/01/2018.
 */

public class DataSong extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SongList";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Songs";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ALBUM = "album";
    private static final String KEY_FAVORITE = "favorite";

    public DataSong(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME
                + " ( "
                + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT "
                + ","
                + KEY_NAME
                + " VARCHAR"
                + ","
                + KEY_FAVORITE
                + " INT "
                + ","
                + KEY_ALBUM
                + " VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

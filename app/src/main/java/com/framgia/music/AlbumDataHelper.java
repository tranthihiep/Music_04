package com.framgia.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/**
 * Created by trant on 25/01/2018.
 */

public class AlbumDataHelper extends SQLiteOpenHelper {
    public static final String TABLE_ALBUM = "TableAlbum";
    public static final String KEY_ID_ALBUM = "idAlbum";
    public static final String KEY_NAME_ALBUM = "nameAlbum";
    public static final String DATA_ALBUM = "Album.db";
    private static final int DATABASE_VERSION = 1;
    public AlbumDataHelper(Context context) {
        super(context, DATA_ALBUM, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1 = "CREATE TABLE "
                + TABLE_ALBUM
                + "("
                + KEY_ID_ALBUM
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME_ALBUM
                + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUM);
        onCreate(sqLiteDatabase);
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
}

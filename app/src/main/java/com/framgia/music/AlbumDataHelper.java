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
    public AlbumDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1 = "CREATE TABLE IF NOT EXISTS TableAlbum ("
                + "idAlbum INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nameAlbum TEXT)";
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TableAlbum");
        onCreate(sqLiteDatabase);
    }

    public void insertAlbumIntoTableAlbum(String nameAlbum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nameAlbum", nameAlbum);
        db.insert("TableAlbum", null, values);

    }

    public ArrayList<Album> queryAlbum() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Album> albums = new ArrayList<>();
        Cursor c = sqLiteDatabase.query("TableAlbum", null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            //c.getString(2);
            albums.add(new Album(id, name));
            c.moveToNext();
        }
        c.close();
        return albums;
    }
}

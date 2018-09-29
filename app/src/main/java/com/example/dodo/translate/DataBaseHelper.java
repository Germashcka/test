package com.example.dodo.translate;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "translate.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "translateTable";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_TRANSLATE = "translate";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE " + TABLE + " ("+
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
        COLUMN_WORD + " TEXT NOT NULL, "+
        COLUMN_TRANSLATE + " TEXT NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

}

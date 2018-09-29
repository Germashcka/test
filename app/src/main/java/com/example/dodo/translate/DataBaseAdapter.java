package com.example.dodo.translate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public DataBaseAdapter(Context context){
        dbHelper = new DataBaseHelper(context.getApplicationContext());
    }

    public DataBaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_WORD, DataBaseHelper.COLUMN_TRANSLATE};
        return  database.query(DataBaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Translate> getTranslates(){
        ArrayList<Translate> translates = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                String word = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_WORD));
                String translate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TRANSLATE));
                translates.add(new Translate(id,word,translate));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  translates;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DataBaseHelper.TABLE);
    }
    public long delete(long userId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DataBaseHelper.TABLE, whereClause, whereArgs);
    }

    public Translate getTranslate(long id){
        Translate translate_ = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DataBaseHelper.TABLE, DataBaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String word = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_WORD));
            String translate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TRANSLATE));
            translate_ = new Translate(id,word,translate);
        }
        cursor.close();
        return  translate_;
    }

    public long insert(Translate translate){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_WORD, translate.getWord());
        cv.put(DataBaseHelper.COLUMN_TRANSLATE, translate.getTranslate());

        return  database.insert(DataBaseHelper.TABLE, null, cv);
    }

    public long update(Translate translate){
        String whereClause = DataBaseHelper.COLUMN_ID + "=" + String.valueOf(translate.getId());
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_WORD, translate.getWord());
        cv.put(DataBaseHelper.COLUMN_TRANSLATE, translate.getTranslate());

        return  database.update(DataBaseHelper.TABLE, cv, whereClause, null);
    }

}

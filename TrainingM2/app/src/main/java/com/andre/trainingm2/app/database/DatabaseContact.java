package com.andre.trainingm2.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Andree on 1/5/2015.
 */
public class DatabaseContact extends SQLiteOpenHelper{
    //private final Context context;
    //private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public static final String ROW_ID = "id";
    public static final String ROW_NAME = "Nama";
    public static final String ROW_PHONE_NUMBER = "Number";
    public static final String KEY_IMAGE = "Gambar";
    public static final String ROW_FAV = "favorite";

    public static final String DATABASE_NAME = "DBContact";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CONTACT = "TableContact";

    private static final String CREATE_TABLE = "create table "
            + TABLE_CONTACT + "("
            + ROW_ID + " integer primary key autoincrement,"
            + ROW_NAME + " text,"
            + ROW_PHONE_NUMBER + " text,"
            + KEY_IMAGE + " text,"
            + ROW_FAV+" Boolean INTEGER default 0);";



        public DatabaseContact(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + DATABASE_NAME);
            onCreate(sqLiteDatabase);
        }
    }





/*    public DatabaseContact(Context context) {
        this.context = context;
        dbHelper = new DatabaseOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addRow(ModelData modeldata) {
        ContentValues values = new ContentValues();
        values.put(ROW_NAME, modeldata.getName());
        values.put(ROW_PHONE_NUMBER, modeldata.getNumber());
        values.put(KEY_IMAGE, modeldata.getPict());
        try{
        db.insert(TABLE_CONTACT, null, values);
        }catch (Exception e){
            Log.e("DB Error",e.toString());
            e.printStackTrace();
        }
    }

    public ModelData getFirstDataFromDatabase() throws SQLException {
        Cursor cursor = db.query(true, TABLE_CONTACT, new String[]{ROW_NAME, ROW_PHONE_NUMBER,KEY_IMAGE}, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            ModelData modelData=new ModelData();
            modelData.setId(cursor.getInt(cursor.getColumnIndex(ROW_ID)));
            modelData.setPict(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
            modelData.setName(cursor.getString(cursor.getColumnIndex(ROW_NAME)));
            modelData.setNumber(cursor.getString(cursor.getColumnIndex(ROW_PHONE_NUMBER)));
            cursor.close();
            return modelData;
        }
        cursor.close();
        return null;
    }


    public boolean update(ModelData modelData){
    ContentValues values=new ContentValues();
        values.put(ROW_NAME,modelData.getName());
        values.put(ROW_PHONE_NUMBER,modelData.getNumber());
        values.put(KEY_IMAGE,modelData.getPict());
        return db.update(TABLE_CONTACT,values,ROW_ID+" = "+modelData.getId(),null)>0;
    }

    public void deleteRow(String point) {
        try {
            db.delete(TABLE_CONTACT, ROW_ID, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelData> getAllData() {
        ArrayList<ModelData> dataArray = new ArrayList<ModelData>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_CONTACT, new String[]{ROW_ID, ROW_NAME, ROW_PHONE_NUMBER,KEY_IMAGE}, null, null, null, null, null);
            cursor.moveToFirst();
            try{
            if (!cursor.isAfterLast()) {
                do {
                    ModelData modelData = new ModelData();
                    modelData.setId(cursor.getInt(cursor.getColumnIndex(ROW_ID)));
                    modelData.setName(cursor.getString(cursor.getColumnIndex(ROW_NAME)));
                    modelData.setNumber(cursor.getString(cursor.getColumnIndex(ROW_PHONE_NUMBER)));
                    modelData.setPict(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
                    dataArray.add(modelData);
                } while (cursor.moveToNext());
            }
            }finally {
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DB error", e.toString());
            e.printStackTrace();
        }
        return dataArray;
    }

}*/

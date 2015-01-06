/*package com.andre.trainingm2.app.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.andre.trainingm2.app.models.ModelData;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Andree on 1/6/2015.
 */
/*public class AdapterContact {
    private final Context context;
    private DatabaseContact dbHelper;
    private SQLiteDatabase db;

    public AdapterContact(Context context) {
        this.context = context;
    }

    public AdapterContact openToWrite() throws SQLException {
        dbHelper = new DatabaseContact(context, dbHelper.DATABASE_NAME, null, dbHelper.DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public AdapterContact openToRead() throws SQLException {
        dbHelper = new DatabaseContact(context, dbHelper.DATABASE_NAME, null, dbHelper.DATABASE_VERSION);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long addRow(ModelData modeldata) throws SQLException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        modeldata.getPict().compress(Bitmap.CompressFormat.PNG, 100, output);
        ContentValues values = new ContentValues();
        values.put(dbHelper.ROW_NAMA, modeldata.getName());
        values.put(dbHelper.ROW_PHONE_NUMBER, modeldata.getNumber());
        values.put(dbHelper.KEY_IMAGE, output.toByteArray());
        openToWrite();
        long value = db.insert(dbHelper.TABLE_NAME, null, values);
        close();
        return value;
    }

    public Cursor queryData() throws SQLException {
        String[] forData = {dbHelper.ROW_ID, dbHelper.ROW_NAMA, dbHelper.ROW_PHONE_NUMBER, dbHelper.KEY_IMAGE};
        openToWrite();
        Cursor cursor = db.query(dbHelper.TABLE_NAME, forData, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryAll(int nameId) throws SQLException {
        String[] forAll = {dbHelper.ROW_ID, dbHelper.ROW_NAMA, dbHelper.ROW_PHONE_NUMBER, dbHelper.KEY_IMAGE};
        openToWrite();
        Cursor cursor = db.query(dbHelper.TABLE_NAME, forAll, dbHelper.ROW_ID + " = " + nameId, null, null, null, null);
        return cursor;
    }

/*    public ModelData getFirstModelDataFromDatabase() throws SQLException{
        Cursor cursor=db.query(true,dbHelper.TABLE_NAME,new String[]{dbHelper.ROW_NAMA,dbHelper.ROW_PHONE_NUMBER,dbHelper.KEY_IMAGE},null,null,null,null,null,null);
        if (cursor.moveToLast()){
            byte[] blob=cursor.getBlob(cursor.getColumnIndex(dbHelper.KEY_IMAGE));
            Bitmap bitmap= BitmapFactory.decodeByteArray(blob, 0, blob.length);
            String Name=cursor.getString(cursor.getColumnIndex(dbHelper.ROW_NAMA));
            String Number=cursor.getString(cursor.getColumnIndex(dbHelper.ROW_PHONE_NUMBER));
            cursor.close();
            return new ModelData(bitmap,Name,Number);
        }
        cursor.close();
        return null;
    }*/

   /* public long UpdateDetail(int rowId, ModelData modelData) throws SQLException {
        ContentValues values = new ContentValues();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        modelData.getPict().compress(Bitmap.CompressFormat.PNG, 100, output);
        values.put(dbHelper.KEY_IMAGE, output.toByteArray());
        values.put(dbHelper.ROW_NAMA, modelData.getName());
        values.put(dbHelper.ROW_PHONE_NUMBER, modelData.getNumber());
        openToWrite();
        long value = db.update(dbHelper.TABLE_NAME, values, dbHelper.ROW_ID + " = " + rowId, null);
        close();
        return value;
    }

    public int deleteRow(String rowId) throws SQLException {
        openToWrite();
        int value = db.delete(dbHelper.TABLE_NAME, dbHelper.ROW_ID + " = " + rowId, null);
        close();
        return value;
    }

/*    public ArrayList<Object> getAllData(){
        ArrayList<Object> dataArray=new ArrayList<ArrayList<Object>>();
        Cursor cursor;
        try{cursor=db.query(TABLE_NAME,new String[]{ROW_ID,ROW_NAMA,ROW_PHONE_NUMBER,KEY_IMAGE},null,null,null,null,null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()){
                do {
                    ArrayList<Object> datalist=new ArrayList<Object>();
                    datalist.add(cursor.getLong(0));
                    datalist.add(cursor.getString(1));
                    datalist.add(cursor.getString(2));
                    datalist.add(cursor.getString(3));

                    dataArray.add(datalist);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataArray;
    }*/

//}

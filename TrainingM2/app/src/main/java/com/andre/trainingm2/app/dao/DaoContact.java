package com.andre.trainingm2.app.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.andre.trainingm2.app.database.DatabaseContact;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Andree on 1/8/2015.
 */
public class DaoContact {
    private SQLiteDatabase database;
    private DatabaseContact dbHelper;
    private String[] allColumn= {
            DatabaseContact.ROW_ID,
            DatabaseContact.ROW_NAME,
            DatabaseContact.ROW_PHONE_NUMBER,
            DatabaseContact.KEY_IMAGE,
            DatabaseContact.ROW_FAV};

    public DaoContact(Context context){
        dbHelper = new DatabaseContact(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void CreateContact(ModelData modelData){
        ContentValues values = new ContentValues();

        values.put(dbHelper.ROW_NAME,modelData.getName());
        values.put(dbHelper.ROW_PHONE_NUMBER,modelData.getNumber());
        values.put(dbHelper.KEY_IMAGE,modelData.getPict());

        try {
            database.insert(dbHelper.TABLE_CONTACT, null, values);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ModelData cursorContact(Cursor cursor){
        ModelData modelData = new ModelData();

        modelData.setId(cursor.getInt(cursor.getColumnIndex(DatabaseContact.ROW_ID)));
        modelData.setName(cursor.getString(cursor.getColumnIndex(DatabaseContact.ROW_NAME)));
        modelData.setNumber(cursor.getString(cursor.getColumnIndex(DatabaseContact.ROW_PHONE_NUMBER)));
        modelData.setPict(cursor.getString(cursor.getColumnIndex(DatabaseContact.KEY_IMAGE)));
        modelData.setFavorite(cursor.getInt(cursor.getColumnIndex(DatabaseContact.ROW_FAV)));

        return modelData;
    }

    public ArrayList<ModelData> getAllContact(){
    ArrayList<ModelData> listContact=new ArrayList<ModelData>();

        Cursor cursor;

        try {
            cursor = database.query(DatabaseContact.TABLE_CONTACT, allColumn, null, null, null, null, null);
            cursor.moveToFirst();
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ModelData modelData=cursorContact(cursor);

                        listContact.add(modelData);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }catch (Exception e) {
            Log.e("DB error",e.toString());
            e.printStackTrace();
        }
        return listContact;
    }

    public ModelData getContact(int id){
        ModelData modelData = new ModelData();
        Cursor cursor = database.query(DatabaseContact.TABLE_CONTACT,allColumn,"id = "+id,null,null,null,null);

        cursor.moveToFirst();
        modelData = cursorContact(cursor);
        cursor.close();

        return modelData;
    }

    public void update(ModelData modelData){
        String updateId="id = " + modelData.getId();
        ContentValues values = new ContentValues();

        values.put(DatabaseContact.ROW_NAME,modelData.getName());
        values.put(DatabaseContact.ROW_PHONE_NUMBER,modelData.getNumber());
        values.put(DatabaseContact.KEY_IMAGE,modelData.getPict());

        database.update(DatabaseContact.TABLE_CONTACT,values,updateId,null);
    }

    public void toFavorites(ModelData modelData){
        String updateId="id = " + modelData.getId();
        ContentValues values = new ContentValues();

        values.put(DatabaseContact.ROW_FAV,modelData.isFavorite());

        database.update(DatabaseContact.TABLE_CONTACT,values,updateId,null);
    }

    public ArrayList<ModelData> getFavorite(){
        ArrayList<ModelData> listFav = new ArrayList<ModelData>();
        Cursor cursor;
        try{
            cursor=database.query(DatabaseContact.TABLE_CONTACT, allColumn, DatabaseContact.ROW_FAV+ " = ?", new String[]{"1"}, null, null, null);
            cursor.moveToFirst();
            try{
               if (cursor != null && cursor.moveToFirst()){
                do {
                    ModelData modelData = cursorContact(cursor);
                    listFav.add(modelData);
                }while (cursor.moveToNext());
               }else System.out.println("no data");
            }finally {
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listFav;
    }

    public void deleteRow(ModelData modelData){
        try{
            database.delete(DatabaseContact.TABLE_CONTACT,DatabaseContact.ROW_ID+ " = "+modelData.getId(),null);
        }catch (Exception e){
           Log.e("error",e.toString());
        }
    }
}

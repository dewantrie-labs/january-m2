package com.andre.trainingm2.app.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.andre.trainingm2.app.models.ModelData;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

/**
 * Created by Andree on 1/5/2015.
 */
public class DatabaseContact {
    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    private static final String ROW_ID="id";
    private static final String ROW_NAMA="Nama";
    private static final String ROW_PHONE_NUMBER="Number";
    private static final String KEY_IMAGE="Gambar";

    private static final String DATABASE_NAME="DBContact";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_NAME="TableContact";

    private static final String CREATE_TABLE="create table "+TABLE_NAME+"("+ROW_ID+" integer primary key autoincrement,"+ROW_NAMA+" text,"+ROW_PHONE_NUMBER+" text,"+KEY_IMAGE+" blob);";




    public DatabaseContact(Context context) {
        this.context = context;
        dbHelper=new DatabaseOpenHelper(context);
    }
    public DatabaseContact open()throws SQLException{
        db=dbHelper.getWritableDatabase();
        return this;
    }


    private static class DatabaseOpenHelper extends SQLiteOpenHelper{

        public DatabaseOpenHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+DATABASE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    public void close(){
        dbHelper.close();
    }

    public void addRow(ModelData modeldata){
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        modeldata.getPict().compress(Bitmap.CompressFormat.JPEG,100,output);
        ContentValues values=new ContentValues();
        values.put(ROW_NAMA, modeldata.getName());
        values.put(ROW_PHONE_NUMBER,modeldata.getNumber());
        values.put(KEY_IMAGE,output.toByteArray());
            db.insert(TABLE_NAME,null,values);
    }

    public ModelData getFirstModelDataFromDatabase() throws SQLException{
        Cursor cursor=db.query(true,TABLE_NAME,new String[]{ROW_NAMA,ROW_PHONE_NUMBER,KEY_IMAGE},null,null,null,null,null,null);
        if (cursor.moveToLast()){
            byte[] blob=cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
            Bitmap bitmap= BitmapFactory.decodeByteArray(blob,0,blob.length);
            String Name=cursor.getString(cursor.getColumnIndex(ROW_NAMA));
            String Number=cursor.getString(cursor.getColumnIndex(ROW_PHONE_NUMBER));
            cursor.close();
            return new ModelData(bitmap,Name,Number);
        }
        cursor.close();
        return null;
    }
    public void deleteRow(String point){
        try{
            db.delete(TABLE_NAME, ROW_ID,null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

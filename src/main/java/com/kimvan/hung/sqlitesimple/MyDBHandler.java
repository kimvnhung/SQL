package com.kimvan.hung.sqlitesimple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by h on 10/08/2017.
 */

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1 ;
    private static final String DATABASE_NAME = "products.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS+ "("+
                COLUMN_ID +" INTERGER PRIMARY KEY, "+
                COLUMN_PRODUCTNAME+" TEXT"+
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // add  a new row to the database
    public void addProduct(Products products){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME,products.get_productname());

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PRODUCTS+" WHERE 1";
        Cursor cursor = db.rawQuery(query,null);

        values.put(COLUMN_ID,cursor.getCount());

        db.insert(TABLE_PRODUCTS,null,values);
        db.close();
    }

    //Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT *FROM "+TABLE_PRODUCTS + " WHERE " +
                COLUMN_PRODUCTNAME +"=\""+productName+"\";";

        Cursor c = db.rawQuery(query,null);
        c.moveToLast();
        String id = c.getString(c.getColumnIndex(COLUMN_ID));

        try {
            if (id==null){
                db.execSQL("DELETE FROM " +TABLE_PRODUCTS + " WHERE " +COLUMN_PRODUCTNAME + "=\""+productName+"\";");
            }else {
                db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE "+COLUMN_ID+"="+id+";"
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        resetId();
    }

    //reset ID
    public void resetId(){
        String query = "SELECT * FROM "+TABLE_PRODUCTS+" WHERE 1";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        int size = cursor.getCount();
        cursor.moveToFirst();

        while (size-->0){

        }
    }

    //Print out the databse as a string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PRODUCTS+" WHERE 1";

        //cursor point to a location in your resuits
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your resuits
        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("productname")) != null){
                dbString+=c.getString(c.getColumnIndex("productname"));
                dbString+="\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

}

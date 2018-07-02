package com.example.hp.offermagnet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by fci on 11/03/17.
 */

public class Database extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "OfferMagnet2";

    static final String TABLE_NAME = "user";
    static final String KEY_NAME = "username";
    static final String KEY_USER_ID = "user_id";
    static final String KEY_ID = "id";
    static final String KEY_GENDER = "gender";
    static final String KEY_BIRTHDATE = "birthDate";
    static final String KEY_CITY = "city";
    static final String KEY_PHONE = "phone";
    static final String KEY_PASSWORD = "password";
    static final String KEY_STATE = "state";
    static final String KEY_IMAGE = "image";

    static final int DATABASE_VERSION = 2;
    Context cont;

    // Database creation sql statement
    static final String DATABASE_CREATE = "create table if not exists " + TABLE_NAME +
            "( " + KEY_ID + " integer primary key , " + KEY_USER_ID + " varchar(255) not null, " + KEY_NAME + " varchar(200) not null , " + KEY_GENDER
            + " varchar(200) not null, "
            + KEY_BIRTHDATE + " varchar(200) not null, " + KEY_CITY + " varchar(200) not null, " + KEY_PHONE
            + " varchar(200) not null, " + KEY_PASSWORD + " varchar(200) not null, " + KEY_IMAGE + " varchar(255) null, " + KEY_STATE + " varchar(200) not null );";

    // Database Deletion
    static final String DATABASE_DROP = "drop table if exists " + TABLE_NAME + ";";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
        //Toast.makeText(context,"Datbase opened",Toast.LENGTH_SHORT).show();
        getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Toast.makeText(cont,"Database on create",Toast.LENGTH_SHORT).show();

        try {
            db.execSQL(DATABASE_CREATE);
            Toast.makeText(cont, "database created", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(cont, "database doesn't created " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        try {
            db.execSQL("insert into " + TABLE_NAME + " ( " + KEY_ID + ", " + KEY_USER_ID + ", " + KEY_NAME + ", " + KEY_GENDER + ", " + KEY_BIRTHDATE + ", " + KEY_CITY + ", " + KEY_PHONE + ", " + KEY_PASSWORD + ", " + KEY_IMAGE + ", " + KEY_STATE + ") values ( '1', 't', 't', 't', '0', '1', 't', 't', '0', '0');");

        }catch (SQLException e){
            Toast.makeText(cont, "not inserted " + e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DATABASE_DROP);
            onCreate(db);
            Toast.makeText(cont, "database upgraded", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(cont, "database doesn't upgraded " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor ShowData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " ;", null);
        return cursor;
    }

    public boolean UpdateData(String id, String user_id, String name,String phone, String pass, String city, String gender, String state, String date, String image) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_ID,user_id);
        contentValues.put(KEY_NAME,name);
        contentValues.put(KEY_PHONE,phone);
        contentValues.put(KEY_PASSWORD,pass);
        contentValues.put(KEY_BIRTHDATE,date);
        contentValues.put( KEY_GENDER,gender);
        contentValues.put(KEY_CITY,city);
        contentValues.put(KEY_STATE,state);
        contentValues.put(KEY_IMAGE,image);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id = " + Integer.parseInt(id), null);

        return true;
    }

    public int DeleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }


    public String getCity(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT  "+KEY_CITY+" FROM "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        return  cursor.getString(0);
    }
    public String getId(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT  "+KEY_USER_ID+" FROM "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        return  cursor.getString(0);
    }
    public String getGender(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT  "+KEY_GENDER+" FROM "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        return  cursor.getString(0);
    }
    public String getName(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT  "+KEY_NAME+" FROM "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        return  cursor.getString(0);
    }
    public String getImage(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT  "+KEY_IMAGE+" FROM "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        return  cursor.getString(0);
    }
    public String getPass(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT  "+KEY_PASSWORD+" FROM "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        return  cursor.getString(0);
    }
    public String getPhone(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT  "+KEY_PHONE+" FROM "+ TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        return  cursor.getString(0);
    }
}

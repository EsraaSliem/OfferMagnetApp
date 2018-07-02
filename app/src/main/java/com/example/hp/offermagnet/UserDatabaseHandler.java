package com.example.hp.offermagnet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 29/04/2018.
 */

public class UserDatabaseHandler extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "OfferMagnet";
    final String TABLE_NAME = "user";
    final String KEY_NAME = "username";
    final String KEY_USER_ID = "user_id";
    final String KEY_ID = "id";
    final String KEY_GENDER = "gender";
    final String KEY_BIRTHDATE = "birthDate";
    final String KEY_CITY = "city";
    final String KEY_PHONE = "phone";
    final String KEY_PASSWORD = "password";
    final String KEY_STATE = "state";

    public UserDatabaseHandler(Context context, String DATABASE_NAME, SQLiteDatabase.CursorFactory factory, int DATABASE_VERSION) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    String create = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_USER_ID + " VARCHAR ,  " + KEY_NAME
            + " VARCHAR , " + KEY_PHONE + " VARCHAR , " + KEY_PASSWORD + " VARCHAR , " + KEY_BIRTHDATE + " VARCHAR , " + KEY_GENDER
            + " VARCHAR ," + KEY_CITY + " VARCHAR , " + KEY_STATE
            + " VARCHAR ) ";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, user.getId());
        cv.put(KEY_NAME, user.getUserName());
        cv.put(KEY_PHONE, user.getPhone());
        cv.put(KEY_PASSWORD, user.getPassword());

        cv.put(KEY_BIRTHDATE, user.getBirthDate());

        cv.put(KEY_GENDER, user.getCity());
        cv.put(KEY_CITY, user.getCity());
        cv.put(KEY_STATE, user.getState());

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public int getId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT " + KEY_USER_ID + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User();
        user.setId((cursor.getString(0)));
        return Integer.parseInt(cursor.getString(0));


    }

    public User getForRequest() {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT " + KEY_USER_ID + " ," + KEY_BIRTHDATE + " ," + KEY_GENDER + ", " + KEY_CITY + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(Query, null);
        User user = new User((cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        return user;
    }

    public boolean login(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT " + KEY_PHONE + " ," + KEY_PASSWORD + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(Query, null);
        if (phone == cursor.getString(0) && password == cursor.getString(1))
            return true;
        else
            return false;
    }

    public void update(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, user.getId());
        cv.put(KEY_NAME, user.getUserName());
        cv.put(KEY_PASSWORD, user.getPassword());
        cv.put(KEY_PHONE, user.getPhone());
        cv.put(KEY_BIRTHDATE, user.getBirthDate());

        cv.put(KEY_GENDER, user.getCity());
        cv.put(KEY_CITY, user.getCity());
        cv.put(KEY_STATE, user.getState());

        String whereClause = "id=?";
        String whereArgs[] = {String.valueOf(user.getId())};
        db.update("category", cv, whereClause, whereArgs);
    }

    public void delete(User user) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {user.getId()};
        db.delete("category", whereClause, whereArgs);
    }
}

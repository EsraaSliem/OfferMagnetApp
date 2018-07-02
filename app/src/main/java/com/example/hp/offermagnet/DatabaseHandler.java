package com.example.hp.offermagnet;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler  extends SQLiteOpenHelper{



    static final int  DATABASE_VERSION =1;
    static final String DATABASE_NAME="OfferMagnet";
    final String TABLE_NAME ="category";
    final String CATEGORY_ID ="id";
    final String CATEGORY_NAME ="category_name";

public  DatabaseHandler(Context context ){
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "+CATEGORY_ID+" INTEGER PRIMARY KEY , "+CATEGORY_NAME+
                " VARCHAR )";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void insert(UserCategory category){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(CATEGORY_ID,category.getId());
        cv.put(CATEGORY_NAME,category.getName());
        db.insert(TABLE_NAME,null,cv);
        db.close();
    }
   public  void update(int id, String name){
       SQLiteDatabase db = getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("id", id);
       contentValues.put("name",name);
       String whereClause = "id=?";
       String whereArgs[] = {String.valueOf(id)};
       db.update("category", contentValues, whereClause, whereArgs);
   }
   public  void delete (int id){
       SQLiteDatabase db = getWritableDatabase();
       String whereClause = "id=?";
       String whereArgs[] = {String.valueOf(id)};
       db.delete("category", whereClause, whereArgs);
   }


}

package com.anioncode.drzewostan.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.anioncode.drzewostan.Model.Trees;

import java.util.ArrayList;

public class DatabaseTablenameHelper extends SQLiteOpenHelper {
    public DatabaseTablenameHelper(Context context) {
        super(context, "las2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE OAK(ID INTEGER PRIMARY KEY AUTOINCREMENT,SREDNICA TEXT UNIQUE,KLASA_1 INTEGER,KLASA_2 INTEGER,KLASA_3 INTEGER,KLASA_A " +
                "INTEGER,KLASA_B INTEGER,KLASA_C INTEGER,WYSOKOSC INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS OAK");
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String srednica,int klasa_1,int klasa_2,int klasa_3,int klasa_a,int klasa_b,int klasa_c,int wysokosc){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("SREDNICA",srednica);
        contentValues.put("KLASA_1",klasa_1);
        contentValues.put("KLASA_2",klasa_2);
        contentValues.put("KLASA_3",klasa_3);
        contentValues.put("KLASA_A",klasa_a);
        contentValues.put("KLASA_B",klasa_b);
        contentValues.put("KLASA_C",klasa_c);
        contentValues.put("WYSOKOSC",wysokosc);

        long result=db.insert("OAK",null,contentValues);
        if (result ==-1)return false;
        else return true;


    }
    public Cursor LasDane(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM OAK WHERE ID="+id;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public ArrayList<Trees> getAllData(){
        ArrayList<Trees> arrayList=new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor =db.rawQuery("Select * from OAK",null);
        while(cursor.moveToNext()){

            int id=cursor.getInt(0);
            String srednica= cursor.getString(1);
            int klasa_1=cursor.getInt(2);
            int klasa_2=cursor.getInt(3);
            int klasa_3=cursor.getInt(4);
            int klasa_a=cursor.getInt(5);
            int klasa_b=cursor.getInt(6);
            int klasa_c=cursor.getInt(7);
            int wysokosc=cursor.getInt(8);



            Trees trees1=new Trees(id,srednica,klasa_1,klasa_2,klasa_3,klasa_a,klasa_b,klasa_c,wysokosc);
            arrayList.add(trees1);


        }
        return arrayList;
    }
    public boolean updateData(String klasa,int id,int liczba) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ID",id);
        contentValues.put(klasa,liczba);
        db.update("OAK", contentValues, "ID = ?",new String[] { String.valueOf(id) });
        return true;
    }
    public boolean updateDatado0(int id,  int klasa_1, int klasa_2, int klasa_3, int klasa_a, int klasa_b, int klasa_c, int wysokosc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ID",id);
        contentValues.put("KLASA_1",klasa_1);
        contentValues.put("KLASA_2",klasa_2);
        contentValues.put("KLASA_3",klasa_3);
        contentValues.put("KLASA_A",klasa_a);
        contentValues.put("KLASA_B",klasa_b);
        contentValues.put("KLASA_C",klasa_c);
        contentValues.put("WYSOKOSC",wysokosc);
        db.update("OAK", contentValues, "ID = ?",new String[] { String.valueOf(id) });
        return true;
    }
    public Cursor AllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM OAK ";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void deleteName(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM OAK WHERE id>=0";
        db.execSQL(query);
    }
}

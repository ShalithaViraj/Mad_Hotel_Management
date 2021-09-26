package com.hotel.hotelpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HotelDatabase extends SQLiteOpenHelper {


    public HotelDatabase(@Nullable Context context) {
        super(context,"HotelDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table HotelDetails(name TEXT primary key, ratings TEXT, feedback TEXT, imageUrl TEXT, singleBed TEXT, doubleBed TEXT, kingBed TEXT)");
        db.execSQL("create Table purchaseHistory(hotelName TEXT primary key, roomType TEXT, roomNO TEXT, bedType TEXT, price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists HotelDetails");
        db.execSQL("drop Table if exists purchaseHistory");
    }

    public Boolean insertPurchaseDetails(String hotelName, String roomType, String roomNO, String bedType, String price ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("hotelName", hotelName);
        contentValues.put("roomType", roomType);
        contentValues.put("roomNO", roomNO);
        contentValues.put("bedType", bedType);
        contentValues.put("price", price);

        long result = db.insert("purchaseHistory", null, contentValues);
        if (result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public Cursor getPurchaseDetails () {

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select * from purchaseHistory", null);

        return cursor;
    }

    public Boolean deletePurchaseDetails (String hotelName) {

        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from purchaseHistory where hotelName = ?", new String[]{hotelName});

        if (cursor.getCount() > 0) {
            long result = DB.delete("purchaseHistory", "name=?", new String[]{hotelName});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean updatePurchaseDetails(String hotelName, String roomType, String roomNO, String bedType, String price){

        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("hotelName", hotelName);
        contentValues.put("roomType", roomType);
        contentValues.put("roomNO", roomNO);
        contentValues.put("bedType", bedType);
        contentValues.put("price", price);

        DB.update("purchaseHistory", contentValues, "hotelName=?", new String[]{hotelName});
        return true;
        }

        public Boolean insertHotelDetails(String name, String ratings, String feedback, String imageUrl, String singleBed ,String doubleBed ,
                                          String kingBed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("ratings", ratings);
        contentValues.put("feedback", feedback);
        contentValues.put("imageUrl", imageUrl);
        contentValues.put("singleBed", singleBed);
        contentValues.put("doubleBed", doubleBed);
        contentValues.put("kingBed", kingBed);

        long result = db.insert("HotelDetails", null, contentValues);
        if (result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public Cursor getHotelDetails () {

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select * from HotelDetails", null);

        return cursor;
    }

    public Boolean deleteHotelDetails (String hotelName) {

        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from HotelDetails where name = ?", new String[]{hotelName});

        if (cursor.getCount() > 0) {
            long result = DB.delete("HotelDetails", "name=?", new String[]{hotelName});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean updateHotelDetails(String name, String ratings, String feedback, String imageUrl, String singleBed , String doubleBed ,
                                      String kingBed){

        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("ratings", ratings);
        cv.put("feedback", feedback);
        cv.put("imageUrl", imageUrl);
        cv.put("singleBed", singleBed);
        cv.put("doubleBed", doubleBed);
        cv.put("kingBed", kingBed);

        DB.update("HotelDetails", cv, "name=?", new String[]{name});
        return true;
    }
}

package com.example.easywallet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ADMIN on 10/12/2560.
 */

public class DbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Wallet.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Easy_Wallet";
    public static final String COL_ID = "_id";
    public static final String COL_NAME= "_name";
    public static final String COL_MONEY = "_money";
    public static final String COL_TYPE = "_type";
    public static final String COL_PICTURE = "_picture";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, "
            + COL_MONEY + " TEXT, "
            + COL_TYPE + " TEXT, "
            + COL_PICTURE + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        insertInitialData(db);

    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME,"คุณพ่อให้เงิน");
        cv.put(COL_MONEY,"8,000");
        cv.put(COL_TYPE,"1");
        cv.put(COL_PICTURE,"ic_income.png");
        db.insert(TABLE_NAME,null,cv);

         cv = new ContentValues();
        cv.put(COL_NAME,"จ่ายค่าหอ");
        cv.put(COL_MONEY,"2,500");
        cv.put(COL_TYPE,"2");
        cv.put(COL_PICTURE,"ic_expense.png");
        db.insert(TABLE_NAME,null,cv);

        cv = new ContentValues();
        cv.put(COL_NAME,"ซื้อล็อตเตอรี่ 1 ชุด");
        cv.put(COL_MONEY,"700");
        cv.put(COL_TYPE,"2");
        cv.put(COL_PICTURE,"ic_expense.png");
        db.insert(TABLE_NAME,null,cv);

        cv = new ContentValues();
        cv.put(COL_NAME,"ถูกล็อตเตอรี่รางวัลที่ 1");
        cv.put(COL_MONEY,"30,000,000");
        cv.put(COL_TYPE,"1");
        cv.put(COL_PICTURE,"ic_income.png");
        db.insert(TABLE_NAME,null,cv);


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }

}

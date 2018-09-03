package com.example.bz.bz.Black_list;

/**
 * Created by ChenSiyuan on 2018/9/3.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Blackhelper extends SQLiteOpenHelper {
    public Blackhelper(Context context){
        super(context,"blacklist.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table black_number (_id integer primary key autoincrement,phone_number varchar)");

        Log.e("TAG","黑名单建立完成");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}

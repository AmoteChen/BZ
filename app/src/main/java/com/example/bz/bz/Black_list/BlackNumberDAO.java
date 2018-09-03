package com.example.bz.bz.Black_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenSiyuan on 2018/9/3.
 */

public class BlackNumberDAO {
    private Blackhelper blackhelper;
    public BlackNumberDAO(Context context){
        blackhelper = new Blackhelper(context);
    }
    //增加操作，直接将实体类导入
    public void insert(BlackNumber blackNumber){
        //获取数据库
        SQLiteDatabase sd = blackhelper.getWritableDatabase();
        //利用ContentValues完成键值对的映射
        ContentValues contentValues = new ContentValues();
        contentValues.put("remark",blackNumber.getRemark());
        contentValues.put("phone_number",blackNumber.getPhoneNumber());
        //数据添加后关闭数据库
        sd.insert("black_number",null,contentValues);
        sd.close();
        Log.e("TAG","黑名单添加完成");
    }

    //查询（基于具体实际考虑，查询时返回整个黑名单表，用List<实体类的实例>返回数据）
    public List<BlackNumber> getAll(){
        //List 接口 ArrayList 实现类
        List<BlackNumber> rtl=new ArrayList<BlackNumber>();
        //获取数据库
        SQLiteDatabase sd = blackhelper.getWritableDatabase();
        //光标处理
        //以_id为排序条件，降序返回，最新添加的在最前面显示
        Cursor cursor = sd.query("black_number",null,null,null,null,null,"_id desc");
        //遍历 移动光标以获取数据
        while (cursor.moveToNext()){
            //构造实体类的实例，放入List
            rtl.add(new BlackNumber(cursor.getLong(0),cursor.getString(1),cursor.getString(2)));
        }
        Log.e("TAG","数据总条数"+cursor.getCount());
        sd.close();
        return rtl;
    }
    //修改
    public void update(BlackNumber blackNumber){
        //获取数据库
        SQLiteDatabase sd = blackhelper.getWritableDatabase();
        //构建要修改的键值对
        ContentValues contentValues=new ContentValues();
        contentValues.put("remark",blackNumber.getRemark());
        contentValues.put("phone_number",blackNumber.getPhoneNumber());
        //数据修改
        sd.update("black_number",contentValues,"_id=?",new String[]{String.valueOf(blackNumber.getId())});
        sd.close();
        Log.e("TAG","黑名单修改成功");
    }
    public void delete(long id){
        //获取数据库
        SQLiteDatabase sd=blackhelper.getWritableDatabase();
        //数据删除
        sd.delete("black_number","_id=?",new String[]{String.valueOf(id)});
        //关闭数据库
        sd.close();
        Log.e("TAG","已移除黑名单");
    }
    public void deleteAll(){
        //获取数据库
        SQLiteDatabase sd =blackhelper.getWritableDatabase();
        sd.delete("black_number",null,null);
        sd.close();
        Log.e("TAG","黑名单已清空");
    }
}

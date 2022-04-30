package com.sk.ordinary.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.sk.ordinary.entity.AccountBean;
import com.sk.ordinary.entity.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库管理工具类  主要对表中的内容进行操作
 */
public class DBManager {

    private static SQLiteDatabase db ;
    /**
     * 初始化数据库对象
     */
    public static void initDB(Context context) {
        DBOpenHelper dboPenHelper = new DBOpenHelper(context);  //得到帮助类对象
        db = dboPenHelper.getWritableDatabase();  //获取数据库对象
    }

    /**
     * 读取数据库中的数据
     */
    public static List<TypeBean> getTypeList(int kind) {
        List<TypeBean> list = new ArrayList<>();

        //  读取typedb中的数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);
        //循环读取游标内容，并添加到对象中
        while(cursor.moveToNext()) {
            @SuppressLint("Range")
            String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));

            TypeBean typeBean = new TypeBean(id, typeName, imageId, sImageId, kind);
            list.add(typeBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 保存记账的操作
     */

    public static void insertMoneyRecord(AccountBean accountBean){
        String sql = "insert into recordtb(typeName, sImageId, descs, money, time, year, month, day, kind) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ContentValues values = new ContentValues();
        values.put("typeName", accountBean.getTypeName());
        values.put("sImageId", accountBean.getsImageId());
        values.put("descs", accountBean.getDescs());
        values.put("money", accountBean.getMoney());
        values.put("time", accountBean.getTime());
        values.put("year", accountBean.getYear());
        values.put("month", accountBean.getMonth());
        values.put("day", accountBean.getDay());
        values.put("kind", accountBean.getKind());
        db.insert("recordtb", null, values);
        Log.i("数据插入", "recordtb 插入一条数据成功");
    }

    /**
     * 查询记账数据
     */
    public static List<AccountBean> getListByDate(int year, int month, int day) {
        List<AccountBean> mDatas = new ArrayList<>();
        String sql = "select * from recordtb where year = ? and month = ? and day = ? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year +"", month + "", day + ""});

        while(cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            @SuppressLint("Range") String descs = cursor.getString(cursor.getColumnIndex("descs"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int years = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int months =  cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") int days = cursor.getInt(cursor.getColumnIndex("day"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typeName, sImageId, descs, money,time, years, months, days, kind);


            mDatas.add(accountBean);
        }
        cursor.close();
        Log.i("查询", "返回条数："+ mDatas.size() );
        return mDatas;
    }

    /**
     * 获取某一天得收入或者支出得总金额
     *
     */
    @SuppressLint("Range")
    public static float getTotalDayMoney(int year, int month, int day, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from recordtb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});

        while(cursor.moveToNext()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        Log.i("日总金额:", ""+total);
        return total;
    }

    /**
     * 按照某一个月进行查询总金额
     */
    @SuppressLint("Range")
    public static float getTotalMonthMoney(int year, int month, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from recordtb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});

        while(cursor.moveToNext()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        Log.i("月总金额:", ""+total);
        return total;
    }

    /**
     * 计算某一年得总金额
     * @param year
     *
     * @param kind
     * @return
     */
    @SuppressLint("Range")
    public static float getTotalYearMoney(int year, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from recordtb where year=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", kind + ""});

        while(cursor.moveToNext()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }


}

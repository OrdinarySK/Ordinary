package com.sk.ordinary.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sk.ordinary.config.ConfigManager;
import com.sk.ordinary.entity.BillRecord;
import com.sk.ordinary.entity.Cate;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库的操作的管理类  增删改查的操作
 */
public class DBManager {

    private static SQLiteDatabase db;

    /**
     * 初始化数据库对象
     */
    public static void initDB(Context context) {
        DBInitData dbInitData = new DBInitData(context);
        db = dbInitData.getWritableDatabase();   // 获取对数据库的读写权限
    }

    /**
     * 查询分类表的数据
     * @param
     * @param
     * @param
     */

    public static List<Cate> getCateList(int kind) {
        List<Cate> catelist = new ArrayList<>();
        String sql = "select * from catetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()) {
            @SuppressLint("Range") String cateName = cursor.getString(cursor.getColumnIndex("cateName"));
            @SuppressLint("Range") int cateId1 = cursor.getInt(cursor.getColumnIndex("cateId1"));
            @SuppressLint("Range") int cateId2 = cursor.getInt(cursor.getColumnIndex("cateId2"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));

            Cate cate = new Cate(id, cateName, cateId1, cateId2, kind, imageId);
            catelist.add(cate);
        }
        cursor.close();
        return catelist;
    }

    /**
     * 记账的数据的插入
     */
    public static void insertBillRecord(BillRecord billRecord){

        String sql = "insert into billtb(cateName, cateId1, cateId2, kind, time, year, month, day, remark, pay, account, money) values(" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//        System.out.println(billRecord.getCateName());
//        System.out.println(billRecord.getCateId1());
//        System.out.println(billRecord.getCateId2());
//        System.out.println(billRecord.getTime());
//        System.out.println(billRecord.getYear());
//        System.out.println(billRecord.getMonth());
//        System.out.println(billRecord.getDay());
//        System.out.println(billRecord.getAccount());
//        System.out.println(billRecord.getKind());
//        System.out.println(billRecord.getPay());
//        System.out.println(billRecord.getMoney());


        ContentValues values = new ContentValues();
        values.put("cateName", billRecord.getCateName());
        values.put("cateId1", billRecord.getCateId1());
        values.put("cateId2", billRecord.getCateId2());
        values.put("kind", billRecord.getKind());
        values.put("time", billRecord.getTime());
        values.put("year", billRecord.getYear());
        values.put("month", billRecord.getMonth());
        values.put("day", billRecord.getDay());
        values.put("remark", billRecord.getRemark());
        values.put("pay", billRecord.getPay());
        values.put("account", billRecord.getAccount());
        values.put("money", billRecord.getMoney());

        db.insert("billtb", null, values);
        Log.i("数据插入", "billtb 插入一条数据成功");

    }

    /**
     * 记账的数据的查询
     */

    public static List<BillRecord> getCurrentMonthBill(int year, int month, int day) {
        List<BillRecord> billRecordlist = new ArrayList<>();
        String sql = "select * from billtb where year = ? and month = ? and day <= ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});


        while(cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String cateName = cursor.getString(cursor.getColumnIndex("cateName"));
            @SuppressLint("Range") int cateId1 = cursor.getInt(cursor.getColumnIndex("cateId1"));
            @SuppressLint("Range") int cateId2 = cursor.getInt(cursor.getColumnIndex("cateId2"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int years = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int months = cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String pay = cursor.getString(cursor.getColumnIndex("pay"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int days = cursor.getInt(cursor.getColumnIndex("day"));
            @SuppressLint("Range") String account = cursor.getString(cursor.getColumnIndex("account"));

            BillRecord billRecord = new BillRecord(id, cateName, cateId1, cateId2, kind, time, years, months, days, remark, pay, account, money);
            billRecordlist.add(billRecord);
        }
        cursor.close();
        Log.i("查询一次", "chaxunyici");
        return billRecordlist;

    }


    /**
     * 按照某一个月进行查询总金额
     */
    @SuppressLint("Range")
    public static float getTotalMonthMoney(int year, int month, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from billtb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});

        while(cursor.moveToNext()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        Log.i("月总金额:", ""+total);
        return total;
    }
}

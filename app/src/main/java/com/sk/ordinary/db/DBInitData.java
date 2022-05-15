package com.sk.ordinary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sk.ordinary.R;

/**
 * 初始化创建数据库
 */
public class DBInitData extends SQLiteOpenHelper {


    public DBInitData(@Nullable Context context){
        super(context, "ordinary.db", null, 1);
    }

    /**
     * 创建相应的需要的表
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建存储分类的类型的表
        String sql = "create table catetb(id integer primary key autoincrement, cateName varchar(15), cateId1 integer, cateId2 integer, kind integer, imageId integer)";
        sqLiteDatabase.execSQL(sql);

        // 添加记账的表的创建
        String sql_bill = "create table billtb(id integer primary key autoincrement, " +
                "cateId1 integer," +
                "cateId2 integer," +
                "cateName varchar(15)," +
                "kind integer," +
                "time varchar(20)," +
                "year integer," +
                "month integer," +
                "day integer," +
                "remark varchar(50)," +
                "pay varchar(15)," +
                "account varchar(15)," +
                "money float)";
        sqLiteDatabase.execSQL(sql_bill);



        //执行分类的插入
        insertCate(sqLiteDatabase);
    }

    /**
     * 插入类型数据
     * @param sqLiteDatabase
     */
    private void insertCate(SQLiteDatabase sqLiteDatabase) {
        String sql = "insert into catetb(cateName, cateId1, cateId2, kind, imageId) values(?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(sql, new Object[]{"其他", 10, 0, 0, R.drawable.ic_qita});
        sqLiteDatabase.execSQL(sql, new Object[]{"三餐", 11, 0, 0, R.drawable.ic_sancan});
        sqLiteDatabase.execSQL(sql, new Object[]{"交通", 12, 0, 0, R.drawable.ic_jiaotong});
        sqLiteDatabase.execSQL(sql, new Object[]{"医疗", 13, 0, 0, R.drawable.ic_yiliao});
        sqLiteDatabase.execSQL(sql, new Object[]{"水电煤", 14, 0, 0, R.drawable.ic_shuidianmei});
        sqLiteDatabase.execSQL(sql, new Object[]{"零食", 15, 0, 0, R.drawable.ic_lingshi});
        sqLiteDatabase.execSQL(sql, new Object[]{"住房", 16, 0, 0, R.drawable.ic_zhufang});
        sqLiteDatabase.execSQL(sql, new Object[]{"发红包", 17, 0, 0, R.drawable.ic_hongbao});
        sqLiteDatabase.execSQL(sql, new Object[]{"运动", 18, 0, 0, R.drawable.ic_yundong});
        sqLiteDatabase.execSQL(sql, new Object[]{"学习", 19, 0, 0, R.drawable.ic_xuexi});
        sqLiteDatabase.execSQL(sql, new Object[]{"衣服", 20, 0, 0, R.drawable.ic_yifu});
        sqLiteDatabase.execSQL(sql, new Object[]{"送礼", 21, 0, 0, R.drawable.ic_songli});

        sqLiteDatabase.execSQL(sql, new Object[]{"其他", 50, 0, 1, R.drawable.in_qita});
        sqLiteDatabase.execSQL(sql, new Object[]{"工资", 51, 0, 1, R.drawable.in_gongzi});
        sqLiteDatabase.execSQL(sql, new Object[]{"收红包", 52, 0, 1, R.drawable.in_hongbao});
        sqLiteDatabase.execSQL(sql, new Object[]{"理财", 53, 0, 1, R.drawable.in_licai});
        sqLiteDatabase.execSQL(sql, new Object[]{"外快", 54, 0, 1, R.drawable.in_waikuai});
        sqLiteDatabase.execSQL(sql, new Object[]{"补助", 55, 0, 1, R.drawable.in_buzhu});
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

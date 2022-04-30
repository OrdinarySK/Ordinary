package com.sk.ordinary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sk.ordinary.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context){
        super(context, "ordinary.db", null, 1);
    }

    /**
     * 只有项目第一次运行时，才会被调用
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表示类型的表
        String sql = "create table typetb(id integer primary key autoincrement," +
                "typeName varchar(10)," +
                "imageId integer," +
                "sImageId integer," +
                "kind integer)";
        sqLiteDatabase.execSQL(sql);

        // 添加记账的表的创建
        String jz_sql = "create table recordtb (id integer primary key autoincrement," +
                "typeName varchar(10)," +
                "sImageId integer," +
                "descs varchar(50)," +
                "money float," +
                "time varchar(20)," +
                "year integer," +
                "month integer," +
                "day integer," +
                "kind integer)";
        sqLiteDatabase.execSQL(jz_sql);
        insertType(sqLiteDatabase);
    }

    private void insertType(SQLiteDatabase sqLiteDatabase) {
        //向typedb当中插入元素
        String sql = "insert into typetb(typeName, imageId, sImageId, kind) values(?, ?, ?, ?)";
       //支出
        sqLiteDatabase.execSQL(sql, new Object[]{"其他", R.mipmap.ic_qita, R.mipmap.ic_qita_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"餐饮", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"交通", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"购物", R.mipmap.ic_gouwu, R.mipmap.ic_gouwu_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"服饰", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"日用品", R.mipmap.ic_riyongpin, R.mipmap.ic_riyongpin_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"娱乐", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"零食", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"烟酒茶", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"学习", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"医疗", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"住宅", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"水电煤", R.mipmap.ic_shuidianfei, R.mipmap.ic_shuidianfei_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"通讯", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, 0});
        sqLiteDatabase.execSQL(sql, new Object[]{"人情", R.mipmap.ic_renqingwanglai, R.mipmap.ic_renqingwanglai_fs, 0});


        //收入
        sqLiteDatabase.execSQL(sql, new Object[]{"其他", R.mipmap.in_qt, R.mipmap.in_qt_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"薪资", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"奖金", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"借入", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"收债", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"利息收入", R.mipmap.in_lixifuji , R.mipmap.in_lixifuji_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"投资汇报", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"二手交易", R.mipmap.in_ershoushebei, R.mipmap.in_ershoushebei_fs, 1});
        sqLiteDatabase.execSQL(sql, new Object[]{"意外所得", R.mipmap.in_yiwai, R.mipmap.in_yiwai_fs, 1});

    }

    /**
     * 数据库版本发生改变时才会调用的方法
     */

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

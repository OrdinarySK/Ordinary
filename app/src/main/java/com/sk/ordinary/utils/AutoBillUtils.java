package com.sk.ordinary.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.sk.ordinary.R;
import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.entity.BillRecord;

public class AutoBillUtils {

    /**
     * 自动记账的传入的参数处理和入库方法
     * @param uri
     */
    public static void parseParams(Uri uri) {
        if (uri != null) {
            BillRecord billRecord = new BillRecord();
            billRecord.setCateName(uri.getQueryParameter("catename"));
            billRecord.setRemark(uri.getQueryParameter("remark"));
            billRecord.setPay(uri.getQueryParameter("accountname"));
            billRecord.setAccount(uri.getQueryParameter("rawAccount"));
            billRecord.setMoney(Float.parseFloat(uri.getQueryParameter("money")));
            billRecord.setKind(Integer.parseInt(uri.getQueryParameter("type")));
            billRecord.setTime(uri.getQueryParameter("time"));
            String bill_time = uri.getQueryParameter("time");

            Log.i("sdfnsldjflsd fk---fs-df-s-d-------", uri.getQuery());
            int years = TimeUtils.year();
            int months = TimeUtils.month();
            int days = TimeUtils.day();
            if (!TextUtils.isEmpty(bill_time)) {
                Log.i("yearsssss", bill_time.substring(0, 4));
                years = Integer.parseInt(bill_time.substring(0, 4));
                months = Integer.parseInt(bill_time.substring(5, 7));
                days = Integer.parseInt(bill_time.substring(8, 10));
            }
            Log.i("year---", "" + years + "---" + months + "---" + days);
            billRecord.setYear(years);
            billRecord.setMonth(months);
            billRecord.setDay(days);


            //分类id  暂不实现  之后可以建立枚举类来实现
            billRecord.setCateId1(10);
            billRecord.setCateId2(0);



            DBManager.insertBillRecord(billRecord);
        }
    }
}

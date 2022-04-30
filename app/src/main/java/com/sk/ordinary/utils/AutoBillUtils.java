package com.sk.ordinary.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.sk.ordinary.R;
import com.sk.ordinary.entity.AccountBean;
import com.sk.ordinary.db.DBManager;

/**
 * 自动结账的接口的数据处理 类
 */
public class AutoBillUtils {
    /**
     * 自动记账的传入的参数处理和入库方法
     * @param uri
     */
    public static void parseParams(Uri uri) {
        if (uri != null) {
            AccountBean accountBean = new AccountBean();
            Log.i("Tag", "" + uri.getQueryParameter("type"));
            accountBean.setKind(Integer.parseInt(uri.getQueryParameter("type")));
            accountBean.setDescs(uri.getQueryParameter("remark"));
            String bill_time = uri.getQueryParameter("time");
            accountBean.setTime(bill_time);
            int years = 0, months = 0, days = 0;
            if (!TextUtils.isEmpty(bill_time)) {
                Log.i("yearsssss", bill_time.substring(0, 4));
                years = Integer.parseInt(bill_time.substring(0, 4));
                months = Integer.parseInt(bill_time.substring(5, 7));
                days = Integer.parseInt(bill_time.substring(8, 10));
            }
            Log.i("year---", "" + years + "---" + months + "---" + days);
            accountBean.setYear(years);
            accountBean.setMonth(months);
            accountBean.setDay(days);
            accountBean.setMoney(Float.parseFloat(uri.getQueryParameter("money")));
            accountBean.setTypeName(uri.getQueryParameter("catename"));
            accountBean.setsImageId(R.mipmap.ic_qita_fs);
            DBManager.insertMoneyRecord(accountBean);
        }
    }
}

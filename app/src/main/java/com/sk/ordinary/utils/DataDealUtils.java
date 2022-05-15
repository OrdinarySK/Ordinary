package com.sk.ordinary.utils;

import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.entity.BillRecord;
import com.sk.ordinary.entity.ListBill;
import com.sk.ordinary.entity.ParenBilltRecord;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对查询的数据库的返回的数据进行处理的公共类
 */
public class DataDealUtils {

    public static List<ParenBilltRecord> getCurrentMonthBill(int year, int month, int day){

        double inCome = 0.0;
        double outCome = 0.0;

        List<ParenBilltRecord> parenBilltRecordList = new ArrayList<>();
        List<BillRecord> currentMonthBill = DBManager.getCurrentMonthBill(year, month, day);


        List<List<BillRecord>> groupList = new ArrayList<>();
        // 对数据库返回的结果进行分组筛选
        currentMonthBill.stream().collect(Collectors.groupingBy(BillRecord::getDay, Collectors.toList())).forEach((days, footListByDay)->{
            groupList.add(footListByDay);
        });

        // 然后对分组后的list进行处理
        for (List<BillRecord> list : groupList) {
            ParenBilltRecord parenBilltRecord = new ParenBilltRecord();
            //  日常支出和收入统计
            Map<Integer, Double> collect = list.stream().collect(Collectors.groupingBy(BillRecord::getKind, Collectors.summingDouble(BillRecord::getMoney)));
            if(collect.get(1) != null) {
                inCome =  collect.get(1);
            }
            if(collect.get(0) != null){
                outCome = collect.get(0);
            }


            int monthBill = TimeUtils.month();
            int dayBill = TimeUtils.day();
            String dates = "";
            List<ListBill> listBillList = new ArrayList<>();

            List<BillRecord> collect1 = list.stream().sorted(Comparator.comparing(BillRecord::getTime).reversed()).collect(Collectors.toList());

            for (BillRecord billRecord: collect1) {
                //子listview数据的添加
                ListBill listBill = new ListBill();
                listBill.setId(billRecord.getId());
                listBill.setCateName(billRecord.getCateName());
                listBill.setRemark(billRecord.getRemark());
                listBill.setPay(billRecord.getPay());
                listBill.setTimes(billRecord.getTime());
                listBill.setAccount(billRecord.getAccount());
                // 对于金额的加入  需要先判断其正负
                listBill.setKind(billRecord.getKind());
                listBill.setMoney(String.valueOf(billRecord.getMoney()));

                monthBill = billRecord.getMonth();
                dayBill = billRecord.getDay();
                dates = billRecord.getTime();
                listBillList.add(listBill);
            }


            // 父listview数据的添加
            parenBilltRecord.setDates(String.valueOf(monthBill)+"."+String.valueOf(dayBill));
            parenBilltRecord.setWeek(TimeUtils.getWeek(dates));
            DecimalFormat df = new DecimalFormat("#.00");
            parenBilltRecord.setOutcome(""+df.format(outCome));
            parenBilltRecord.setIncome(""+df.format(inCome));
            parenBilltRecord.setDay(dayBill);
            parenBilltRecord.setListBillList(listBillList);

            parenBilltRecordList.add(parenBilltRecord);
        }
        // 按照日期进行降序排列
        List<ParenBilltRecord> collect = parenBilltRecordList.stream().sorted(Comparator.comparing(ParenBilltRecord::getDay).reversed()).collect(Collectors.toList());

        return collect;
    }

}

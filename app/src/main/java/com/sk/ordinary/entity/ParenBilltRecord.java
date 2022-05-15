package com.sk.ordinary.entity;

import java.util.List;

/**
 * 外层记录的父类的数据类型
 */
public class ParenBilltRecord {
    String dates;  // 日期
    String week;   //星期
    String income;  //当天收入
    String outcome;  //当天支出
    int day;   //用来排序的字段
    List<ListBill> listBillList;  //每条的内容

    public ParenBilltRecord(String dates, String week, String income, int day, String outcome, List<ListBill> listBillList) {
        this.dates = dates;
        this.week = week;
        this.income = income;
        this.outcome = outcome;
        this.day = day;
        this.listBillList = listBillList;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public List<ListBill> getListBillList() {
        return listBillList;
    }

    public void setListBillList(List<ListBill> listBillList) {
        this.listBillList = listBillList;
    }

    public ParenBilltRecord() {
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

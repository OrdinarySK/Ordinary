package com.sk.ordinary.entity;

/**
 * 账单记录的实体类
 */
public class BillRecord {
    /**
     * id
     */
    int id;
    /**
     * 分类名字
     */
    String cateName;
    /**
     * 一级分类id
     */
    int cateId1;
    /**
     * 二级分类id
     */
    int cateId2;
    /**
     * 支出  还是收入   支出--0  收入--1
     * */
    int kind;
    /**
     * 记录时间
     */
    String time;
    /**
     * 记录年
     */
    int year;
    /**
     * 记录月
     */
    int month;
    /**
     * 记录日
     */
    int day;
    /**
     * 备注信息
     */
    String remark;
    /**
     * 支付手段 ---  支付宝  微信   京东等
     */
    String pay;
    /**
     * 支付账号  例如  微信的零钱  还是微信的银行卡等
     */
    String account;
    /**
     * 支付金额
     */
    float money;

    public BillRecord() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public int getCateId1() {
        return cateId1;
    }

    public void setCateId1(int cateId1) {
        this.cateId1 = cateId1;
    }

    public int getCateId2() {
        return cateId2;
    }

    public void setCateId2(int cateId2) {
        this.cateId2 = cateId2;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public BillRecord(int id, String cateName, int cateId1, int cateId2, int kind, String time, int year, int month, int day, String remark, String pay, String account, float money) {
        this.id = id;
        this.cateName = cateName;
        this.cateId1 = cateId1;
        this.cateId2 = cateId2;
        this.kind = kind;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.remark = remark;
        this.pay = pay;
        this.account = account;
        this.money = money;
    }
}

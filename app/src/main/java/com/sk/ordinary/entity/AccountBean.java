package com.sk.ordinary.entity;

/**
 * 记录手指的数据的相关内容类
 */
public class AccountBean {
    int id;
    String typeName; //类型
    int sImageId; //被选中类型图片
    String descs; //备注
    float money; // 金额
    String time; // 保存时间字符串
    int year;
    int month;
    int day;
    int kind;    //  收入  1   支出  0

    public AccountBean() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getsImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
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

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public AccountBean(int id, String typeName, int sImageId, String descs, float money, String time, int year, int month, int day, int kind) {
        this.id = id;
        this.typeName = typeName;
        this.sImageId = sImageId;
        this.descs = descs;
        this.money = money;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }
}

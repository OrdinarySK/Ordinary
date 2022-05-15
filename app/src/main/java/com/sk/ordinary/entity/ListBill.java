package com.sk.ordinary.entity;

/**
 * 每天的单独的项目的实体类
 */
public class ListBill {
    int id;   // 条目的id
    String cateName; //分类名称
    String remark;  //备注
    String money;  //支出收入的数目
    String pay;  //支付方式
    int kind;
    String times;
    String account;

    public ListBill() {
    }

    public ListBill(int id, String cateName, String remark, String money, String pay, int kind, String times, String account) {
        this.id = id;
        this.cateName = cateName;
        this.remark = remark;
        this.money = money;
        this.pay = pay;
        this.kind = kind;
        this.times = times;
        this.account = account;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

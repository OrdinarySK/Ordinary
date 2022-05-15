package com.sk.ordinary.entity;

/**
 * 分类类型的实体类
 */
public class Cate {
    int id; //id
    String cateName; //类型名称
    int cateId1;  //一级分类
    int cateId2; // 二级分类
    int kind;      //  支出 00  收入   1
    int imageId;   //图片存储位置

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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Cate(int id, String cateName, int cateId1, int cateId2, int kind, int imageId) {
        this.id = id;
        this.cateName = cateName;
        this.cateId1 = cateId1;
        this.cateId2 = cateId2;
        this.kind = kind;
        this.imageId = imageId;
    }
}

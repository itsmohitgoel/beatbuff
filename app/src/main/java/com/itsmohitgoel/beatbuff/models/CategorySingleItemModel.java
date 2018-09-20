package com.itsmohitgoel.beatbuff.models;

public class CategorySingleItemModel {
    private String mName;
    private int imageResId;

    public CategorySingleItemModel(String name, int imageResId) {
        mName = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImageResId() {
        return imageResId;
    }

}

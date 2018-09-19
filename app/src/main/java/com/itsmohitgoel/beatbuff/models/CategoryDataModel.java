package com.itsmohitgoel.beatbuff.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryDataModel {
    private String mHeaderTitle;
    private ArrayList<CategorySingleItemModel>  mAllItemsInCategory;

    public CategoryDataModel() {
    }

    public String getHeaderTitle() {
        return mHeaderTitle;
    }

    public ArrayList<CategorySingleItemModel> getAllItemsInCategory() {
        return mAllItemsInCategory;
    }

    public void setHeaderTitle(String headerTitle) {
        mHeaderTitle = headerTitle;
    }

    public void setAllItemsInCategory(ArrayList<CategorySingleItemModel> allItemsInCategory) {
        mAllItemsInCategory = allItemsInCategory;
    }
}

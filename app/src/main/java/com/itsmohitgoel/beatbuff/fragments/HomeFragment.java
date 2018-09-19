package com.itsmohitgoel.beatbuff.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itsmohitgoel.beatbuff.adapters.CategoriesDataAdapter;
import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.models.CategoryDataModel;
import com.itsmohitgoel.beatbuff.models.CategorySingleItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.all_categories_recycler_view)
    RecyclerView mAllCategoriesRecyclerView;

    private ArrayList<CategoryDataModel> mAllCategoriesData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAllCategoriesData = new ArrayList<CategoryDataModel>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, rootView);

        createDummyData();

        mAllCategoriesRecyclerView.setHasFixedSize(true);
        //todo: create and set adapter
        CategoriesDataAdapter outerListAdapter = new CategoriesDataAdapter(getActivity(), mAllCategoriesData);
        mAllCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false
        ));
        mAllCategoriesRecyclerView.setAdapter(outerListAdapter);
        return rootView;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private void createDummyData() {
        for (int i = 1; i <= 5; i++) {
            CategoryDataModel categoryDataModel = new CategoryDataModel();
            categoryDataModel.setHeaderTitle("Section " + i);

            ArrayList<CategorySingleItemModel> singleItemsList = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                singleItemsList.add(new CategorySingleItemModel("Item " + j, R.drawable.alan_walker));
            }

            categoryDataModel.setAllItemsInCategory(singleItemsList);

            mAllCategoriesData.add(categoryDataModel);
        }
    }

}


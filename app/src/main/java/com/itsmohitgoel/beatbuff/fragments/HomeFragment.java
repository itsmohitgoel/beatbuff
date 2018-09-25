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
import com.itsmohitgoel.beatbuff.utils.MusicManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getCanonicalName();

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

        prepareOuterListData();

        mAllCategoriesRecyclerView.setHasFixedSize(true);
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

    private void prepareOuterListData() {
        MusicManager musicManager = MusicManager.getInstance(getActivity());
        CategoryDataModel genreData = musicManager.getAllGenreData();
        CategoryDataModel artistData = musicManager.getAllArtistData();
        CategoryDataModel albumsData = musicManager.getAllAlbumsData();
        CategoryDataModel allSongsData = musicManager.getAllSongsData();

        mAllCategoriesData.add(genreData);
        mAllCategoriesData.add(artistData);
        mAllCategoriesData.add(albumsData);
        mAllCategoriesData.add(allSongsData);
    }

}


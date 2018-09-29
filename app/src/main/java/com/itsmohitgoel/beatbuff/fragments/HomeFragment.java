package com.itsmohitgoel.beatbuff.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itsmohitgoel.beatbuff.adapters.CategoriesDataAdapter;
import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.adapters.ImageSliderAdapter;
import com.itsmohitgoel.beatbuff.models.CategoryDataModel;
import com.itsmohitgoel.beatbuff.models.ImageModel;
import com.itsmohitgoel.beatbuff.utils.MusicManager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Home Screen Fragment Hosting :-
 * 1. Image Slider to display upcoming music events in nearby cities.
 * 2. 4 Horizontal recycler views to show music by Genre/Albums/Artists/All Songs
 * 3. Horizontal recycler views are nested inside single vertical RecylerView.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getCanonicalName();

    @BindView(R.id.all_categories_recycler_view)
    RecyclerView mAllCategoriesRecyclerView;
    @BindView(R.id.image_slider_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.circle_page_indicator)
    CirclePageIndicator mCirclePageIndicator;

    private ArrayList<CategoryDataModel> mAllCategoriesData;
    private int mCurrentPage = 0;
    private int NUM_PAGES = 0;
    private ArrayList<ImageModel> mImageModels;
    private int[] mSliderImages = new int[]{R.drawable.img_poster1, R.drawable.img_poster2,
            R.drawable.img_poster3, R.drawable.img_poster4, R.drawable.img_poster5};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAllCategoriesData = new ArrayList<CategoryDataModel>();
        mImageModels = new ArrayList<>();
        mImageModels = populateSliderImages();

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

        initializeImageSlider();
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

    private ArrayList<ImageModel> populateSliderImages() {
        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0 ; i < mSliderImages.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImageDrawable(mSliderImages[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void initializeImageSlider() {
        mViewPager.setAdapter(new ImageSliderAdapter(getActivity(), mImageModels));
        mCirclePageIndicator.setViewPager(mViewPager);

        final float density = getResources().getDisplayMetrics().density;

        mCirclePageIndicator.setRadius(5 * density);

        NUM_PAGES = mImageModels.size();

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (mCurrentPage == NUM_PAGES) {
                    mCurrentPage = 0;
                }
                mViewPager.setCurrentItem(mCurrentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 1000);

        mCirclePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCurrentPage = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}


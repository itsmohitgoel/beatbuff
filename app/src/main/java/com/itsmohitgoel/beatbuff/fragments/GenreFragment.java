package com.itsmohitgoel.beatbuff.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.adapters.PlaylistAdapter;
import com.itsmohitgoel.beatbuff.models.MusicItem;
import com.itsmohitgoel.beatbuff.utils.Blur;
import com.itsmohitgoel.beatbuff.utils.MusicManager;
import com.lopei.collageview.CollageView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreFragment extends Fragment {

    @Nullable
    @BindView(R.id.genre_fragment_text_button)
    Button mGenreTextButton;
    @BindView(R.id.playlist_recycler_view)
    RecyclerView mPlaylistRecyclerView;
    @BindView(R.id.collage_image_view)
    ImageView mcollageImageView;

    private Bitmap mPreviewBitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreviewBitmap = Blur.mergeMultiple(extractImagesFromMusic());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, rootView);

        mGenreTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = CategoryListFragment.newInstance();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        });

        mPlaylistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getActivity());
        mPlaylistRecyclerView.setAdapter(playlistAdapter);

        mcollageImageView.setImageBitmap(mPreviewBitmap);
        return rootView;
    }

    public static GenreFragment newInstance() {
        return new GenreFragment();
    }

    private ArrayList<Bitmap> extractImagesFromMusic() {
        ArrayList<MusicItem> musicItems = MusicManager.getInstance(getActivity()).getMusicItems();
        ArrayList<Bitmap> bitmaps = new ArrayList<>(5);
        for (int i = 7; i <11; i++) {
                bitmaps.add(musicItems.get(i).getCoverArt());
        }

        return bitmaps;
    }
}














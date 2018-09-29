package com.itsmohitgoel.beatbuff.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.activities.PlayerActivity;
import com.itsmohitgoel.beatbuff.adapters.PlaylistAdapter;
import com.itsmohitgoel.beatbuff.models.MusicItem;
import com.itsmohitgoel.beatbuff.utils.Blur;
import com.itsmohitgoel.beatbuff.utils.MusicManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Playlist Fragment which will display the playlist,
 * based on the user's seletion of music category.
 */
public class CategoryListFragment extends Fragment {
    private static final String ARG_CATEGORY = "category";
    public static final String TAG = CategoryListFragment.class.getCanonicalName();

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({MusicCategory.GENRE, MusicCategory.ARTIST, MusicCategory.ALBUMS,
            MusicCategory.ALL_SONGS})
    public @interface MusicCategory {
        String GENRE = "Available Genres";
        String ARTIST = "Popular Artists";
        String ALBUMS = "Top Albums";
        String ALL_SONGS = "All Songs";
    }

    @BindView(R.id.playlist_recycler_view)
    RecyclerView mPlaylistRecyclerView;
    @BindView(R.id.collage_image_view)
    ImageView mCollageImageView;
    @BindView(R.id.category_header_text_view)
    TextView mCategoryHeaderTextView;
    @BindView(R.id.play_all_songs_button)
    Button mPlayAllSongsButton;

    private Bitmap mPreviewBitmap;
    private String mHeaderTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderTitle = getArguments().getString(ARG_CATEGORY);
        mPreviewBitmap = Blur.mergeMultiple(extractImagesFromMusic(mHeaderTitle));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, rootView);


        mPlaylistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getActivity());
        mPlaylistRecyclerView.setAdapter(playlistAdapter);

        mCollageImageView.setImageBitmap(mPreviewBitmap);

        mCategoryHeaderTextView.setText(mHeaderTitle);

        mPlayAllSongsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayerActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        return rootView;
    }

    public static CategoryListFragment newInstance(@MusicCategory String categoryType) {
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, categoryType);

        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Extract the cover art from music and build collage image.
     * @param categoryType
     * @return
     */
    private ArrayList<Bitmap> extractImagesFromMusic(String categoryType) {
        int min = 0;
        int max = 5;

        switch (categoryType) {
            case MusicCategory.GENRE:
                min = 6; max = 10;
                break;
            case MusicCategory.ARTIST:
                min = 10; max = 14;
                break;
            case MusicCategory.ALBUMS:
                min = 8; max = 12;
                break;
            case MusicCategory.ALL_SONGS:
                min = 5 ; max = 9;
                break;
        }
        ArrayList<MusicItem> musicItems = MusicManager.getInstance(getActivity()).getMusicItems();
        ArrayList<Bitmap> bitmaps = new ArrayList<>(5);
        for (int i = min; i < max; i++) {
            bitmaps.add(musicItems.get(i).getCoverArt());
        }

        return bitmaps;
    }
}














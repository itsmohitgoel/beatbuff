package com.itsmohitgoel.beatbuff.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itsmohitgoel.beatbuff.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerFragment extends Fragment {
    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_player, container, false);

        CircleImageView profileImageView = (CircleImageView) view.findViewById(R.id
                .circle_image_view);

        profileImageView.setImageBitmap(getAlbumArtImage(R.raw.faded_by_alan_walker));
        return view;
    }


    /**
     * Extract the Album cover from the audio resource file itself
     */
    private Bitmap getAlbumArtImage(long resId) {
        Uri baseUriForAudioFiles = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw");
        Uri uri = ContentUris.withAppendedId(baseUriForAudioFiles, resId);
        Bitmap image;

        MediaMetadataRetriever mData = new MediaMetadataRetriever();
        mData.setDataSource(getActivity(), uri);
        byte art[] = mData.getEmbeddedPicture();
        image = BitmapFactory.decodeByteArray(art, 0, art.length);

        return image;
    }
}

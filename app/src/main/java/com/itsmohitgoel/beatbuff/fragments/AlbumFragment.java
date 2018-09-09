package com.itsmohitgoel.beatbuff.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itsmohitgoel.beatbuff.R;

public class AlbumFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_album, container, false);
        return rootView;
    }

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }
}

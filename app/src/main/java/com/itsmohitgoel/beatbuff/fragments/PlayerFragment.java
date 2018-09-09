package com.itsmohitgoel.beatbuff.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itsmohitgoel.beatbuff.R;

public class PlayerFragment extends Fragment {
    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_container, container, false);
        return view;
    }
}

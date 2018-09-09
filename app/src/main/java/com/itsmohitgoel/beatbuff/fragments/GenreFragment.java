package com.itsmohitgoel.beatbuff.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itsmohitgoel.beatbuff.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreFragment extends Fragment {

    @Nullable
    @BindView(R.id.genre_fragment_text_button)
    Button genreTextButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, rootView);

        genreTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = CategoryListFragment.newInstance();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        });
        return rootView;
    }

    public static GenreFragment newInstance() {
        return new GenreFragment();
    }
}

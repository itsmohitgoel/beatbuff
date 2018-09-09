package com.itsmohitgoel.beatbuff.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.activities.PlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListFragment extends Fragment {

    @BindView(R.id.dummy_textview3)
    Button genreSubFragmentButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        ButterKnife.bind(this, view);

        genreSubFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayerActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        return view;
    }

    public static CategoryListFragment newInstance() {
        return new CategoryListFragment();
    }
}

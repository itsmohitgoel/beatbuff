package com.itsmohitgoel.beatbuff.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.fragments.PlayerFragment;

public class PlayerActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_container;
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PlayerActivity.class);
        return intent;
    }
}

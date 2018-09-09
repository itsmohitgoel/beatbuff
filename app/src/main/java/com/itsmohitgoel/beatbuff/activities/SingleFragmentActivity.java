package com.itsmohitgoel.beatbuff.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.itsmohitgoel.beatbuff.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    public abstract Fragment createFragment();
    protected FragmentManager mFragmentManager;

    @LayoutRes
    protected abstract int getLayoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();

            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}

package com.itsmohitgoel.beatbuff.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.models.ImageModel;

import java.util.ArrayList;

public class ImageSliderAdapter extends PagerAdapter {

    private ArrayList<ImageModel> mImageModels;
    private LayoutInflater mInflater;
    private Context mContext;

    public ImageSliderAdapter(Context context, ArrayList<ImageModel> imageModels) {
        mImageModels = imageModels;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImageModels != null ? mImageModels.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageSliderLayout = mInflater.inflate(R.layout.image_slider_container,
                container, false);
        assert imageSliderLayout != null;
        final ImageView sliderImageView = (imageSliderLayout.findViewById(R.id.image_slider_image_view));
        sliderImageView.setImageResource(mImageModels.get(position).getImageDrawable());

        container.addView(imageSliderLayout, 0);
        return imageSliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

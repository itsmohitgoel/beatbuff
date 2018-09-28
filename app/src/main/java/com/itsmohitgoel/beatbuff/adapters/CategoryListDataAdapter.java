package com.itsmohitgoel.beatbuff.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.models.CategorySingleItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListDataAdapter extends RecyclerView.Adapter<CategoryListDataAdapter.SingleItemRowHolder> {
    private Context mContext;
    private ArrayList<CategorySingleItemModel> mAllItemsDataList;

    public CategoryListDataAdapter(Context context, ArrayList<CategorySingleItemModel> allItemsDataList) {
        mContext = context;
        mAllItemsDataList = allItemsDataList;
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.horizontal_single_card_list_item, parent, false);

        return new SingleItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, int position) {
        holder.bindCategorySingleItemData(mAllItemsDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (mAllItemsDataList != null ? mAllItemsDataList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail_image_view)
        ImageView mThumbnailImageView;
        @BindView(R.id.title_text_view)
        TextView mTitleTextView;

        public SingleItemRowHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCategorySingleItemData(CategorySingleItemModel singleItem) {
            mTitleTextView.setText(singleItem.getName());
            Drawable imageDrawable = mContext.getResources().getDrawable(singleItem.getImageResId());
            mThumbnailImageView.setImageDrawable(imageDrawable);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), )
                }
            });
        }
    }

}

package com.itsmohitgoel.beatbuff.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.fragments.CategoryListFragment;
import com.itsmohitgoel.beatbuff.models.CategoryDataModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class for the outer recycler_view in HomeFragment
 */

public class CategoriesDataAdapter extends RecyclerView.Adapter<CategoriesDataAdapter.CategoryRowHolder> {

    private Context mContext;
    private ArrayList<CategoryDataModel> mAllCategoriesDataList;

    public CategoriesDataAdapter(Context context, ArrayList<CategoryDataModel> categoryDataModelArrayList) {
        mContext = context;
        mAllCategoriesDataList = categoryDataModelArrayList;
    }

    @NonNull
    @Override
    public CategoryRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.categories_list_item, parent, false);

        return new CategoryRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRowHolder holder, int position) {
        holder.bindCategoryData(mAllCategoriesDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (mAllCategoriesDataList != null ? mAllCategoriesDataList.size() : 0);
    }

    /**
     * ViewHolder class for Categories
     */
    public class CategoryRowHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_name_text_view)
        TextView mBrowseByCategoryTextView;
        @BindView(R.id.see_all_items_text_view)
        TextView mSeeAllItemsTextView;
        @BindView(R.id.category_item_recycler_view)
        RecyclerView mCategoryItemRecyclerView;

        public CategoryRowHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCategoryData(final CategoryDataModel categoryData) {
            final String sectionName = categoryData.getHeaderTitle();
            ArrayList singleSectionItems = categoryData.getAllItemsInCategory();

            mBrowseByCategoryTextView.setText(sectionName);
            mSeeAllItemsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Click even on SeeAll, " + sectionName,
                            Toast.LENGTH_SHORT).show();
                    Fragment fragment = CategoryListFragment
                            .newInstance(categoryData.getMusicCategory());

                    FragmentManager fragmentManager = ((AppCompatActivity) mContext)
                            .getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            mCategoryItemRecyclerView.setHasFixedSize(true);
            mCategoryItemRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                    LinearLayoutManager.HORIZONTAL, false));
            CategoryListDataAdapter categoryListDataAdapter =
                    new CategoryListDataAdapter(mContext, singleSectionItems);
            mCategoryItemRecyclerView.setAdapter(categoryListDataAdapter);
        }
    }
}

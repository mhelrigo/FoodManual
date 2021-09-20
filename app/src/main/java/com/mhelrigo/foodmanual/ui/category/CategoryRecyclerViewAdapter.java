package com.mhelrigo.foodmanual.ui.category;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.model.category.CategoryModel;
import com.mhelrigo.foodmanual.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CategoryRecyclerViewAda";

    public interface OnCategoryClicked{
        void onClick(CategoryModel categoryModel);
    }

    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private OnCategoryClicked onCategoryClicked;

    public CategoryRecyclerViewAdapter(OnCategoryClicked onCategoryClicked) {
        this.onCategoryClicked = onCategoryClicked;
    }

    public void setData(List<CategoryModel> categoryModelList){
        Log.e("setData", "setData : " + categoryModelList.size());
        this.categoryModelList = categoryModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding =
                DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_category, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(categoryModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return (categoryModelList.size() == 0) ? 0 : categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding itemCategoryBinding;
        CategoryModel categoryModel;

        public ViewHolder(@NonNull ItemCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            this.itemCategoryBinding = itemCategoryBinding;

            itemCategoryBinding.getRoot().setOnClickListener(v -> {
                if(getAdapterPosition() > -1){
                    onCategoryClicked.onClick(categoryModel);
                }
            });
        }

        public void bind(CategoryModel categoryModel){
            this.categoryModel = categoryModel;
            itemCategoryBinding.setCategoryModel(categoryModel);
            itemCategoryBinding.executePendingBindings();
        }
    }
}

package com.mhelrigo.foodmanual.ui.category;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.data.model.Category;
import com.mhelrigo.foodmanual.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CategoryRecyclerViewAda";

    public interface OnCategoryClicked{
        void onClick(Category category);
    }

    private List<Category> categoryList = new ArrayList<>();
    private OnCategoryClicked onCategoryClicked;

    public CategoryRecyclerViewAdapter(OnCategoryClicked onCategoryClicked) {
        this.onCategoryClicked = onCategoryClicked;
    }

    public void setData(List<Category> categoryList){
        this.categoryList = categoryList;
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
        holder.bind(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return (categoryList.size() == 0) ? 0 : categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding itemCategoryBinding;
        Category category;

        public ViewHolder(@NonNull ItemCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            this.itemCategoryBinding = itemCategoryBinding;

            itemCategoryBinding.getRoot().setOnClickListener(v -> {
                if(getAdapterPosition() > -1){
                    onCategoryClicked.onClick(category);
                }
            });
        }

        public void bind(Category category){
            this.category = category;
            itemCategoryBinding.setCategory(category);
            itemCategoryBinding.executePendingBindings();
        }
    }
}

package com.mhelrigo.foodmanual.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhelrigo.foodmanual.databinding.ItemCategoryBinding;
import com.mhelrigo.foodmanual.model.category.CategoryModel;

import io.reactivex.subjects.PublishSubject;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private DiffUtil.ItemCallback itemCallback = new DiffUtil.ItemCallback<CategoryModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategoryModel oldItem, @NonNull CategoryModel newItem) {
            return oldItem.getIdCategory().equals(newItem.getIdCategory());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoryModel oldItem, @NonNull CategoryModel newItem) {
            return oldItem.getIdCategory().equals(newItem.getIdCategory());
        }
    };

    public AsyncListDiffer<CategoryModel> categories = new AsyncListDiffer<CategoryModel>(this, itemCallback);

    public PublishSubject<CategoryModel> expandCategoryDetail = PublishSubject.create();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categories.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return categories.getCurrentList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;

        public ViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(CategoryModel p0) {
            binding.textViewName.setText(p0.getStrCategory());
            Glide.with(binding.getRoot().getContext()).load(p0.getStrCategoryThumb()).into(binding.imageViewThumbnail);

            binding.getRoot().setOnClickListener(view -> expandCategoryDetail.onNext(p0));
        }
    }
}

package com.mhelrigo.foodmanual.ui.ingredient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mhelrigo.foodmanual.databinding.ItemIngredientBinding;
import com.mhelrigo.foodmanual.model.ingredient.IngredientModel;

import io.reactivex.subjects.PublishSubject;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {
    private DiffUtil.ItemCallback itemCallback = new DiffUtil.ItemCallback<IngredientModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull IngredientModel oldItem, @NonNull IngredientModel newItem) {
            return oldItem.getIdIngredient().equals(newItem.getIdIngredient());
        }

        @Override
        public boolean areContentsTheSame(@NonNull IngredientModel oldItem, @NonNull IngredientModel newItem) {
            return oldItem.getIdIngredient().equals(newItem.getIdIngredient());
        }
    };

    public AsyncListDiffer<IngredientModel> ingredients = new AsyncListDiffer<IngredientModel>(this, itemCallback);

    public PublishSubject<IngredientModel> expandIngredientDetails = PublishSubject.create();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ingredients.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.getCurrentList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemIngredientBinding binding;

        public ViewHolder(@NonNull ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(IngredientModel p0) {
            binding.textViewName.setText(p0.getStrIngredient());
            Glide.with(binding.getRoot().getContext()).load(p0.thumbnail()).diskCacheStrategy(
                    DiskCacheStrategy.ALL
            ).into(binding.imageViewThumbnail);

            binding.getRoot().setOnClickListener(view -> expandIngredientDetails.onNext(p0));
        }
    }
}

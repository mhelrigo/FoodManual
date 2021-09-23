package com.mhelrigo.foodmanual.ui.meal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mhelrigo.foodmanual.databinding.ItemMealBinding;
import com.mhelrigo.foodmanual.model.meal.MealModel;

import io.reactivex.Completable;
import io.reactivex.subjects.PublishSubject;

public class MealRecyclerViewAdapter extends RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder> {
    private DiffUtil.ItemCallback<MealModel> itemCallback = new DiffUtil.ItemCallback<MealModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MealModel oldItem, @NonNull MealModel newItem) {
            return Integer.parseInt(oldItem.getIdMeal()) == Integer.parseInt(newItem.getIdMeal());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MealModel oldItem, @NonNull MealModel newItem) {
            return Integer.parseInt(oldItem.getIdMeal()) == Integer.parseInt(newItem.getIdMeal()) &&
                    oldItem.isFavorite() == newItem.isFavorite();
        }
    };

    public AsyncListDiffer<MealModel> meals = new AsyncListDiffer<MealModel>(this, itemCallback);

    public PublishSubject<MealModel> toggleFavorite = PublishSubject.create();

    public PublishSubject<MealModel> expandDetail = PublishSubject.create();

    public Completable toggleFavoriteOfADrink(MealModel p0) {
        meals.getCurrentList().get(p0.getViewHolderIndex()).setFavorite(p0.isFavorite());
        notifyItemChanged(p0.getViewHolderIndex());

        return Completable.complete();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMealBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(meals.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return meals.getCurrentList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMealBinding binding;

        public ViewHolder(@NonNull ItemMealBinding itemMealBinding) {
            super(itemMealBinding.getRoot());

            this.binding = itemMealBinding;
        }

        public void bind(MealModel p0) {
            binding.textViewName.setText(p0.getStrMeal());
            binding.imageViewFavorite
                    .setImageDrawable(ResourcesCompat.getDrawable(binding.getRoot().getResources(), p0.returnIconForFavorite(), null));
            Glide.with(binding.getRoot().getContext()).load(p0.getStrMealThumb()).diskCacheStrategy(
                    DiskCacheStrategy.ALL
            ).into(binding.imageViewThumbnail);

            binding.imageViewFavorite.setOnClickListener(view -> {
                p0.setViewHolderIndex(getAdapterPosition());
                toggleFavorite.onNext(p0);
            });

            binding.getRoot().setOnClickListener(view -> expandDetail.onNext(p0));
        }
    }
}

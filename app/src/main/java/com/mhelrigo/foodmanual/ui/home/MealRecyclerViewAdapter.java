package com.mhelrigo.foodmanual.ui.home;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.databinding.ItemMealBinding;
import com.mhelrigo.foodmanual.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MealRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "LatestMealRecyclerViewA";

    public interface OnMealClicked {
        void onClicked(MealModel mealModel);

        void onAddedToFavorites(MealModel mealModel);

        void onRemovedFromFavorites(MealModel mealModel);
    }

    private List<MealModel> mealModels;
    private OnMealClicked onMealClicked;
    private boolean isWithPagination = false;
    private MealsType mealsType;

    public MealRecyclerViewAdapter(MealsType mealsType, OnMealClicked onMealClicked) {
        mealModels = new ArrayList<>();
        this.onMealClicked = onMealClicked;
        this.mealsType = mealsType;
    }

    public void setData(List<MealModel> newMealModels) {
        if (isWithPagination) {
            removeLoading();

            mealModels.addAll(newMealModels);
            notifyItemRangeChanged(((mealModels.size() - newMealModels.size()) + 1), newMealModels.size());
            isWithPagination = !isWithPagination;
            return;
        }

        mealModels = newMealModels;
        notifyDataSetChanged();
    }

    public void showLoading() {
        mealModels.add(null);
        notifyItemInserted(mealModels.size() - 1);
    }

    private void removeLoading() {
        mealModels.remove(mealModels.size() - 1);
        notifyItemRemoved(mealModels.size());
    }

    public void setIsWithPagination(boolean value) {
        isWithPagination = value;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.View.VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meals_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        ItemMealBinding binding =
                DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_meal, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bind(mealModels.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (mealModels.size() == 0) ? 0 : mealModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mealModels.get(position) == null ? Constants.View.VIEW_TYPE_LOADING : Constants.View.VIEW_TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MealModel mealModel;
        public ItemMealBinding itemMealBinding;
        private boolean isFavorite;
        private ObjectAnimator animator;

        public ViewHolder(@NonNull ItemMealBinding itemMealBinding) {
            super(itemMealBinding.getRoot());
            this.itemMealBinding = itemMealBinding;

            itemMealBinding.getRoot().setOnClickListener(v -> {
                if (getAdapterPosition() != -1) {
                    onMealClicked.onClicked(mealModel);
                }
            });

            itemMealBinding.imageViewFavorite.setOnClickListener(v -> {
                if (isFavorite) {
                    itemMealBinding.imageViewFavorite.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.ic_favorite_1));
                    onMealClicked.onRemovedFromFavorites(mealModel);
                    animator = ObjectAnimator.ofPropertyValuesHolder(itemMealBinding.imageViewFavorite, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f));
                    animator.setRepeatCount(1);
                    animator.setRepeatMode(ObjectAnimator.REVERSE);
                    animator.setDuration(100);
                    animator.start();
                    if (mealsType.equals(MealsType.FAVORITE)) {
                        mealModels.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                } else {
                    animator = ObjectAnimator.ofPropertyValuesHolder(itemMealBinding.imageViewFavorite, PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f));
                    animator.setRepeatCount(1);
                    animator.setRepeatMode(ObjectAnimator.REVERSE);
                    animator.setDuration(100);
                    animator.start();
                    mealModel.setFavorite(true);
                    itemMealBinding.imageViewFavorite.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.ic_favorite));
                    onMealClicked.onAddedToFavorites(mealModel);
                }

                isFavorite = !isFavorite;
            });
        }

        public void bind(MealModel mealModel) {
            this.mealModel = mealModel;
            itemMealBinding.setMealModel(mealModel);
            itemMealBinding.executePendingBindings();

            isFavorite = mealModel.isFavorite();
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ShimmerFrameLayout shimmerLayoutMealLoading;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerLayoutMealLoading = itemView.findViewById(R.id.shimmerLayoutMealLoading);

            shimmerLayoutMealLoading.startShimmer();
        }
    }
}

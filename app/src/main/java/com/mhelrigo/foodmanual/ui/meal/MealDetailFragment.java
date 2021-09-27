package com.mhelrigo.foodmanual.ui.meal;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentMealDetailBinding;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.base.ViewState;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class MealDetailFragment extends BaseFragment<FragmentMealDetailBinding> {
    private MealViewModel mealViewModel;

    public MealDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_meal_detail;
    }

    @Override
    public FragmentMealDetailBinding inflateLayout(@NonNull LayoutInflater inflater) {
        return FragmentMealDetailBinding.inflate(inflater);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealViewModel = new ViewModelProvider(getActivity()).get(MealViewModel.class);

        requestForExpandedMealDetail();
        handleMealData();
    }

    private void requestForExpandedMealDetail() {
        mealViewModel.requestForExpandedMealDetail(getArguments().getString(MealModel.ID));
    }

    private void handleMealData() {
        mealViewModel.meal().observe(getViewLifecycleOwner(), mealModelResultWrapper -> {
            if (mealModelResultWrapper.getViewState().equals(ViewState.LOADING)) {
                processLoadingState(mealModelResultWrapper.getViewState(), binding.imageViewLoading);

                binding.imageViewLoading.setVisibility(View.VISIBLE);
                binding.constraintLayoutRootSuccess.setVisibility(View.GONE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.GONE);
            } else if (mealModelResultWrapper.getViewState().equals(ViewState.SUCCESS)) {
                MealModel mealModel = mealModelResultWrapper.getResult();

                binding.imageViewLoading.setVisibility(View.GONE);
                binding.constraintLayoutRootSuccess.setVisibility(View.VISIBLE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.GONE);

                if (mealModel.getStrInstructions() == null) {
                    binding.constraintLayoutRootSuccess.setVisibility(View.GONE);
                    binding.textViewEmptyMeals.setVisibility(View.VISIBLE);
                    return;
                }

                binding.textViewName.setText(mealModel.getStrMeal());
                binding.textViewShortDesc.setText(mealModel.getStrCategory() + " and " + mealModel.getStrArea());
                binding.textViewMeasurements.setText(mealModel.doMeasurementAndIngredients());
                binding.textViewInstruction.setText(mealModel.getStrInstructions());

                Glide.with(getContext()).load(mealModel.getStrMealThumb()).into(binding.imageViewThumbnail);

                binding.imageViewFavorite
                        .setImageDrawable(ResourcesCompat.getDrawable(binding.getRoot().getResources(), mealModel.returnIconForFavorite(), null));

                binding.imageViewFavorite
                        .setOnClickListener(view -> mealViewModel.compositeDisposable.add(mealViewModel.toggleFavoriteOfAMeal(mealModel)
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnComplete(() -> {
                                    requestForExpandedMealDetail();
                                }).subscribe()));
            } else {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.constraintLayoutRootSuccess.setVisibility(View.GONE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.VISIBLE);
            }
        });
    }
}
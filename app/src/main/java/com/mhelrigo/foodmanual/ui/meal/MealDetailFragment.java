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
import com.mhelrigo.foodmanual.databinding.FragmentMealDetailBinding;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class MealDetailFragment extends BaseFragment {
    private FragmentMealDetailBinding binding;
    private MealViewModel mealViewModel;

    public MealDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMealDetailBinding.inflate(inflater);

        return binding.getRoot();
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
        mealViewModel.meal().observe(getViewLifecycleOwner(), mealModel -> {
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
        });
    }
}
package com.mhelrigo.foodmanual.ui.meal;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentMealDetailBinding;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.base.ViewState;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

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
        mealViewModel.mealIdToBeSearched()
                .observe(getViewLifecycleOwner(), s -> mealViewModel.requestForExpandedMealDetail(s));
    }

    private void handleMealData() {
        mealViewModel.meal().observe(getViewLifecycleOwner(), mealModelResultWrapper -> {
            if (mealModelResultWrapper.getViewState().equals(ViewState.LOADING)) {
                processLoadingState(mealModelResultWrapper.getViewState(), binding.imageViewLoading);

                binding.imageViewLoading.setVisibility(View.VISIBLE);
                binding.constraintLayoutRootSuccess.setVisibility(View.GONE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewEmptyDetails.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.GONE);
            } else if (mealModelResultWrapper.getViewState().equals(ViewState.SUCCESS)) {
                MealModel mealModel = mealModelResultWrapper.getResult();

                binding.imageViewLoading.setVisibility(View.GONE);
                binding.constraintLayoutRootSuccess.setVisibility(View.VISIBLE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewEmptyDetails.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.GONE);

                if (mealModel.getStrInstructions() == null) {
                    binding.constraintLayoutRootSuccess.setVisibility(View.GONE);
                    binding.textViewEmptyDetails.setVisibility(View.VISIBLE);
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
                                    refreshDataIfDeviceIsTablet(mealModel);
                                }).subscribe()));
            } else {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.constraintLayoutRootSuccess.setVisibility(View.GONE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewEmptyDetails.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Called to update the list of Meals on the left side of screen.
     * */
    private void refreshDataIfDeviceIsTablet(MealModel p0) {
        if (isTablet) {
            // For non-favorite screen
            mealViewModel.mealThatIsToggled.onNext(p0);

            // For favorites screen
            mealViewModel.requestForFavoriteMeals();
        }
    }
}
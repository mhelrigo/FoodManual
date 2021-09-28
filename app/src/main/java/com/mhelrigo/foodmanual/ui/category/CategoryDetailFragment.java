package com.mhelrigo.foodmanual.ui.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentCategoryDetailBinding;
import com.mhelrigo.foodmanual.ui.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.meal.MealDetailFragment;
import com.mhelrigo.foodmanual.ui.meal.MealNavigator;
import com.mhelrigo.foodmanual.ui.base.ViewState;
import com.mhelrigo.foodmanual.ui.meal.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.meal.MealViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@AndroidEntryPoint
public class CategoryDetailFragment extends BaseFragment<FragmentCategoryDetailBinding> implements MealNavigator {
    private CategoryViewModel categoryViewModel;
    private MealViewModel mealViewModel;

    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_category_detail;
    }

    @Override
    public FragmentCategoryDetailBinding inflateLayout(@NonNull LayoutInflater inflater) {
        return FragmentCategoryDetailBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryViewModel = new ViewModelProvider(getActivity()).get(CategoryViewModel.class);
        mealViewModel = new ViewModelProvider(getActivity()).get(MealViewModel.class);

        setUpRecyclerView();

        handleCategoryData();
        handleFilteredMeals();

        if (isTablet) {
            refreshMealsWhenItemToggled();
        }
    }

    private void setUpRecyclerView() {
        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();

        Disposable v0 = mealRecyclerViewAdapter.toggleFavorite
                .concatMapCompletable(mealModel -> mealViewModel.toggleFavoriteOfAMeal(mealModel)
                        .observeOn(AndroidSchedulers.mainThread())
                        .andThen(mealRecyclerViewAdapter.toggleFavoriteOfADrink(mealModel))
                        .doOnComplete(() -> {
                            if (isTablet) {
                                // Sync data on both screen
                                mealViewModel.setMealIdToBeSearched(mealModel.getIdMeal());
                            }
                        }))
                .subscribe();

        Disposable v1 = mealRecyclerViewAdapter.expandDetail.subscribe(mealModel -> {
            mealViewModel.setMealIdToBeSearched(mealModel.getIdMeal());
            navigateToMealDetail(R.id.action_categoryDetailFragment_to_mealDetailFragment,
                    null,
                    findNavController(this), isTablet);
        });

        mealViewModel.compositeDisposable.add(v0);
        mealViewModel.compositeDisposable.add(v1);

        binding.recyclerViewFilteredMeals.setAdapter(mealRecyclerViewAdapter);
        binding.recyclerViewFilteredMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void handleCategoryData() {
        categoryViewModel.category().observe(getViewLifecycleOwner(), categoryModel -> {
            binding.textViewCategory.setText(categoryModel.getStrCategory());

            Timber.e(categoryModel.getStrCategory());

            if (categoryModel.strDescriptionEmpty()) {
                binding.textViewDescription.setVisibility(View.GONE);
                binding.imageViewThumbnail.setVisibility(View.GONE);
            }

            binding.textViewDescription.setText(categoryModel.getStrCategoryDescription());
            Glide.with(getContext()).load(categoryModel.getStrCategoryThumb()).into(binding.imageViewThumbnail);

            mealViewModel.filterMealsByCategory(categoryModel.getStrCategory());
        });
    }

    public void handleFilteredMeals() {
        mealViewModel.mealsFilteredByCategory().observe(getViewLifecycleOwner(), listResultWrapper -> {
            if (listResultWrapper.getViewState().equals(ViewState.LOADING)) {
                processLoadingState(listResultWrapper.getViewState(), binding.imageViewLoading);
                binding.imageViewLoading.setVisibility(View.VISIBLE);
                binding.recyclerViewFilteredMeals.setVisibility(View.GONE);
            } else if (listResultWrapper.getViewState().equals(ViewState.SUCCESS)) {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.recyclerViewFilteredMeals.setVisibility(View.VISIBLE);
                mealRecyclerViewAdapter.meals.submitList(listResultWrapper.getResult());
            } else {

                binding.imageViewLoading.setVisibility(View.GONE);
                binding.recyclerViewFilteredMeals.setVisibility(View.GONE);
            }
        });
    }

    private void refreshMealsWhenItemToggled() {
        mealViewModel.mealThatIsToggled.subscribe(mealModel -> mealViewModel.filterMealsByCategory(categoryViewModel.category().getValue().getStrCategory()));
    }
}
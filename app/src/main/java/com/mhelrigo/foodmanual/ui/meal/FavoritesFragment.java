package com.mhelrigo.foodmanual.ui.meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentFavoritesBinding;
import com.mhelrigo.foodmanual.ui.commons.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.commons.base.ViewState;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class FavoritesFragment extends BaseFragment<FragmentFavoritesBinding> implements MealNavigator {
    private MealViewModel mealViewModel;

    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_favorites;
    }

    @Override
    public FragmentFavoritesBinding inflateLayout(@NonNull LayoutInflater inflater) {
        return FragmentFavoritesBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealViewModel = new ViewModelProvider(getActivity()).get(MealViewModel.class);

        setUpRecyclerView();

        mealViewModel.requestForFavoriteMeals();
        handleFavoriteMealsData();
    }

    private void setUpRecyclerView() {
        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();

        Disposable v0 = mealRecyclerViewAdapter.toggleFavorite
                .concatMapCompletable(mealModel -> mealViewModel.toggleFavoriteOfAMeal(mealModel)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(() -> {
                            mealViewModel.requestForFavoriteMeals();
                            if (isTablet) {
                                // Sync data on both screen
                                mealViewModel.requestForExpandedMealDetail(mealModel.getIdMeal());
                            }
                        }))
                .subscribe();

        Disposable v1 = mealRecyclerViewAdapter.expandDetail
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealModel -> {
                    mealViewModel.setMealIdToBeSearched(mealModel.getIdMeal());
                    navigateToMealDetail(R.id.action_favoritesFragment_to_mealDetailFragment,
                            null,
                            findNavController(this), isTablet);
                });

        mealViewModel.compositeDisposable.add(v0);
        mealViewModel.compositeDisposable.add(v1);

        binding.recyclerViewFavoriteMeals.setAdapter(mealRecyclerViewAdapter);
        binding.recyclerViewFavoriteMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void handleFavoriteMealsData() {
        mealViewModel.favoriteMeals().observe(getViewLifecycleOwner(), mealModels -> {
            if (mealModels.getViewState().equals(ViewState.LOADING)) {
                binding.imageViewLoading.setVisibility(View.VISIBLE);
                binding.recyclerViewFavoriteMeals.setVisibility(View.GONE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.GONE);
            } else if (mealModels.getViewState().equals(ViewState.SUCCESS)) {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.recyclerViewFavoriteMeals.setVisibility(View.VISIBLE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.GONE);

                mealRecyclerViewAdapter.submitList(mealModels.getResult());

                if (mealModels.getResult() == null || mealModels.getResult().isEmpty()) {
                    binding.textViewEmptyMeals.setVisibility(View.VISIBLE);
                    return;
                }
            } else {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.recyclerViewFavoriteMeals.setVisibility(View.GONE);
                binding.textViewEmptyMeals.setVisibility(View.GONE);
                binding.textViewErrorForMeals.setVisibility(View.VISIBLE);
            }
        });
    }
}
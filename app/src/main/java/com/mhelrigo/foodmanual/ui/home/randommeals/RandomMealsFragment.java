package com.mhelrigo.foodmanual.ui.home.randommeals;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.MealViewModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.databinding.FragmentRandomMealsBinding;
import com.mhelrigo.foodmanual.ui.home.HomeViewModel;
import com.mhelrigo.foodmanual.ui.home.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.home.MealsType;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;
import com.mhelrigo.foodmanual.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Todo - Summary of this class
 */
public class RandomMealsFragment extends DaggerFragment {
    private static final String TAG = "RandomMealsFragment";

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private FragmentRandomMealsBinding binding;

    private HomeViewModel mHomeViewModel;
    private MealViewModel mMealViewModel;

    private MealRecyclerViewAdapter mMealRecyclerViewAdapter;

    private boolean isTablet = false;
    private boolean isLoadingNetworkDataFetch = false;

    public RandomMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mHomeViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel.class);
        mMealViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(MealViewModel.class);

        mHomeViewModel.fetchRandomMeal();

        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =
                DataBindingUtil
                        .inflate(inflater, R.layout.fragment_random_meals, container, false);

        mMealRecyclerViewAdapter = new MealRecyclerViewAdapter(MealsType.RANDOM, new MealRecyclerViewAdapter.OnMealClicked() {
            @Override
            public void onClicked(Meal meal) {
                if (!isTablet) {
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragmentHome, new MealDetailsFragment())
                            .addToBackStack(MealDetailsFragment.class.getSimpleName())
                            .commit();
                }

                mMealViewModel.setSelectedMeal(meal);
            }

            @Override
            public void onAddedToFavorites(Meal meal) {
                mMealViewModel.addToFavorites(meal);
            }

            @Override
            public void onRemovedFromFavorites(Meal meal) {
                mMealViewModel.removeFromFavorites(meal);
            }
        });

        binding.setView(this);
        binding.setAdapter(mMealRecyclerViewAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.nestedScrollViewRandomMeals.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        if (!isLoadingNetworkDataFetch) {
                            mMealRecyclerViewAdapter.showLoading();

                            isLoadingNetworkDataFetch = true;
                            mMealRecyclerViewAdapter.setIsWithPagination(true);

                            mHomeViewModel.fetchRandomMeal();
                        }
                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.shimmerFrameLayoutMeals.startShimmer();
        binding.shimmerFrameLayoutMeals.setVisibility(View.VISIBLE);
        mHomeViewModel.getRandomMeals().observe(getViewLifecycleOwner(), meals -> {
            isLoadingNetworkDataFetch = false;

            if (meals == null) {
                return;
            }

            if (isTablet) {
                mMealViewModel.setSelectedMeal(mHomeViewModel.getRandomMeals().getValue().getMealList().get(0));
            }

            mMealRecyclerViewAdapter.setData(meals.getMealList());
            binding.shimmerFrameLayoutMeals.stopShimmer();
            binding.shimmerFrameLayoutMeals.setVisibility(View.GONE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHomeViewModel.getRandomMeals().getValue() != null) {
            if (isTablet) {
                mMealViewModel.setSelectedMeal(mHomeViewModel.getRandomMeals().getValue().getMealList().get(0));
            }
        }
    }

    public void goBack() {
        if (mHomeViewModel.getMealsData().getValue() != null) {
            if (isTablet) {
                mMealViewModel.setSelectedMeal(mHomeViewModel.getMealsData().getValue().getMealList().get(0));
            }
        }
        mHomeViewModel.fetchLatestMeals();
        getActivity().onBackPressed();
    }
}

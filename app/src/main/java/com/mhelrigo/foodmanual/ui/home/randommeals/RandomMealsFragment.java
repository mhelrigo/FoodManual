package com.mhelrigo.foodmanual.ui.home.randommeals;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.OldMealModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.databinding.FragmentRandomMealsBinding;
import com.mhelrigo.foodmanual.ui.home.HomeViewModel;
import com.mhelrigo.foodmanual.ui.home.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.home.MealsType;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Todo - Summary of this class
 */
@AndroidEntryPoint
public class RandomMealsFragment extends Fragment {
    private static final String TAG = "RandomMealsFragment";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    private FragmentRandomMealsBinding binding;

    private HomeViewModel mHomeViewModel;
    private OldMealModel mOldMealModel;

    private MealRecyclerViewAdapter mMealRecyclerViewAdapter;

    private boolean isTablet = false;
    private boolean isLoadingNetworkDataFetch = false;

    @Inject
    public RandomMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mHomeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        mOldMealModel = new ViewModelProvider(getActivity()).get(OldMealModel.class);

        mHomeViewModel.fetchRandomMeal(false);

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
            public void onClicked(MealModel mealModel) {
                if (!isTablet) {
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragmentHome, new MealDetailsFragment())
                            .addToBackStack(MealDetailsFragment.class.getSimpleName())
                            .commit();
                }

                mOldMealModel.setSelectedMeal(mealModel);
            }

            @Override
            public void onAddedToFavorites(MealModel mealModel) {
                mOldMealModel.addToFavorites(mealModel);
            }

            @Override
            public void onRemovedFromFavorites(MealModel mealModel) {
                mOldMealModel.removeFromFavorites(mealModel);
            }
        });

        binding.setView(this);
        binding.setAdapter(mMealRecyclerViewAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setViewModel(mHomeViewModel);

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

                            mHomeViewModel.fetchRandomMeal(false);
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
        mHomeViewModel.getRandomMeals(false).observe(getViewLifecycleOwner(), meals -> {
            isLoadingNetworkDataFetch = false;

            if (meals == null) {
                return;
            }

            if (isTablet) {
                mOldMealModel.setSelectedMeal(mHomeViewModel.getRandomMeals(false).getValue().getMealList().get(0));
            }

            mMealRecyclerViewAdapter.setData(meals.getMealList());
            binding.shimmerFrameLayoutMeals.stopShimmer();
            binding.shimmerFrameLayoutMeals.setVisibility(View.GONE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHomeViewModel.getRandomMeals(false).getValue() != null) {
            if (isTablet) {
                mOldMealModel.setSelectedMeal(mHomeViewModel.getRandomMeals(false).getValue().getMealList().get(0));
            }
        }
    }

    public void goBack() {
        if (mHomeViewModel.getMealsData().getValue() != null) {
            if (isTablet) {
                mOldMealModel.setSelectedMeal(mHomeViewModel.getMealsData().getValue().getMealList().get(0));
            }
        }
        getActivity().onBackPressed();
    }
}

package com.mhelrigo.foodmanual.ui.home.favoritemeals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.MealViewModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.databinding.FragmentFavoriteMealBinding;
import com.mhelrigo.foodmanual.ui.home.HomeViewModel;
import com.mhelrigo.foodmanual.ui.home.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.home.MealsType;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;
import com.mhelrigo.foodmanual.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteMealFragment#onCreate(Bundle)} factory method to
 * create an instance of this fragment.
 */
public class FavoriteMealFragment extends DaggerFragment {
    private static final String TAG = "FavoriteMealFragment";

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private FragmentFavoriteMealBinding binding;

    private HomeViewModel mHomeViewModel;
    private MealViewModel mMealViewModel;

    private MealRecyclerViewAdapter mMealRecyclerViewAdapter;

    private boolean isTablet = false;
    private boolean isLoadingNetworkDataFetch = false;

    public FavoriteMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mHomeViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel.class);
        mMealViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(MealViewModel.class);
        mMealViewModel.fetchFavorites();

        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_meal, container, false);

        mMealRecyclerViewAdapter = new MealRecyclerViewAdapter(MealsType.FAVORITE, new MealRecyclerViewAdapter.OnMealClicked() {
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
                if (mHomeViewModel.getMealsData().getValue().getMealList().contains(meal)){
                    Log.e(TAG, "COntainers");
                }
            }
        });

        binding.setView(this);
        binding.setAdapter(mMealRecyclerViewAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.shimmerFrameLayoutMeals.startShimmer();
        binding.shimmerFrameLayoutMeals.setVisibility(View.VISIBLE);
        mMealViewModel.getFavouriteMeals().observe(getViewLifecycleOwner(), meals -> {
            isLoadingNetworkDataFetch = false;

            if (meals == null) {
                return;
            }

            if (isTablet) {
                if (mMealViewModel.getFavouriteMeals().getValue().size() > 0)
                    mMealViewModel.setSelectedMeal(mMealViewModel.getFavouriteMeals().getValue().get(0));
            }

            mMealRecyclerViewAdapter.setData(meals);
            binding.shimmerFrameLayoutMeals.stopShimmer();
            binding.shimmerFrameLayoutMeals.setVisibility(View.GONE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMealViewModel.getFavouriteMeals().getValue() != null) {
            if (isTablet) {
                mMealViewModel.setSelectedMeal(mMealViewModel.getFavouriteMeals().getValue().get(0));
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
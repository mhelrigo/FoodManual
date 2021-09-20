package com.mhelrigo.foodmanual.ui.home.favoritemeals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.mhelrigo.foodmanual.databinding.FragmentFavoriteMealBinding;
import com.mhelrigo.foodmanual.ui.home.HomeViewModel;
import com.mhelrigo.foodmanual.ui.home.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.home.MealsType;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteMealFragment#onCreate(Bundle)} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class FavoriteMealFragment extends Fragment {
    private static final String TAG = "FavoriteMealFragment";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    private FragmentFavoriteMealBinding binding;

    private HomeViewModel mHomeViewModel;
    private OldMealModel mOldMealModel;

    private MealRecyclerViewAdapter mMealRecyclerViewAdapter;

    private boolean isTablet = false;
    private boolean isLoadingNetworkDataFetch = false;

    @Inject
    public FavoriteMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mHomeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        mOldMealModel = new ViewModelProvider(getActivity()).get(OldMealModel.class);
        mOldMealModel.fetchFavorites();

        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_meal, container, false);

        mMealRecyclerViewAdapter = new MealRecyclerViewAdapter(MealsType.FAVORITE, new MealRecyclerViewAdapter.OnMealClicked() {
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
                mHomeViewModel.manageFavorite(mealModel, true);
            }

            @Override
            public void onRemovedFromFavorites(MealModel mealModel) {
                mOldMealModel.removeFromFavorites(mealModel);
                mHomeViewModel.manageFavorite(mealModel, false);
            }
        });

        binding.setView(this);
        binding.setAdapter(mMealRecyclerViewAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setViewModel(mOldMealModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading();
        mOldMealModel.getFavouriteMeals().observe(getViewLifecycleOwner(), meals -> {
            hideLoading();

            isLoadingNetworkDataFetch = false;

            if (isTablet) {
                if (mOldMealModel.getFavouriteMeals().getValue().size() > 0)
                    mOldMealModel.setSelectedMeal(mOldMealModel.getFavouriteMeals().getValue().get(0));
            }

            mMealRecyclerViewAdapter.setData(meals);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mOldMealModel.getFavouriteMeals().getValue() != null) {
            if (isTablet) {
                mOldMealModel.setSelectedMeal(mOldMealModel.getFavouriteMeals().getValue().get(0));
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

    private void showLoading() {
        binding.shimmerFrameLayoutMeals.startShimmer();
        binding.shimmerFrameLayoutMeals.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.shimmerFrameLayoutMeals.stopShimmer();
        binding.shimmerFrameLayoutMeals.setVisibility(View.GONE);
    }
}
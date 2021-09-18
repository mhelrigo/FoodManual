package com.mhelrigo.foodmanual.ui.home.latestmeals;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.OldMealModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.databinding.FragmentLatestMealBinding;
import com.mhelrigo.foodmanual.ui.home.HomeViewModel;
import com.mhelrigo.foodmanual.ui.home.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.home.MealsType;
import com.mhelrigo.foodmanual.ui.home.NavigationDrawer;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 */
@AndroidEntryPoint
public class LatestMealFragment extends Fragment {
    private static final String TAG = "LatestMealFragment";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    private FragmentLatestMealBinding binding;
    private HomeViewModel mHomeViewModel;
    private OldMealModel mOldMealModel;
    public NavigationDrawer navigationDrawer;

    private MealRecyclerViewAdapter mMealRecyclerViewAdapter;

    private boolean isTablet = false;
    private boolean isLoadingNetworkDataFetch = false;

    @Inject
    public LatestMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mOldMealModel = new ViewModelProvider(this).get(OldMealModel.class);
        mHomeViewModel.fetchLatestMeals();

        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =
                DataBindingUtil
                        .inflate(inflater, R.layout.fragment_latest_meal, container, false);

        mMealRecyclerViewAdapter = new MealRecyclerViewAdapter(MealsType.LATEST, new MealRecyclerViewAdapter.OnMealClicked() {
            @Override
            public void onClicked(Meal meal) {
                if (!isTablet) {
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragmentHome, new MealDetailsFragment(), MealDetailsFragment.class.getSimpleName())
                            .addToBackStack(MealDetailsFragment.class.getSimpleName())
                            .commit();
                }

                mOldMealModel.setSelectedMeal(meal);
            }

            @Override
            public void onAddedToFavorites(Meal meal) {
                mOldMealModel.addToFavorites(meal);
            }

            @Override
            public void onRemovedFromFavorites(Meal meal) {
                Log.e(TAG, "onRemovedFromFavorites: " + meal.getStrMeal());
                mOldMealModel.removeFromFavorites(meal);
            }
        });

        binding.setViewModel(mHomeViewModel);
        binding.setAdapter(mMealRecyclerViewAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setView(this);

        binding.nestedScrollViewLatestMeals.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollX) {
                        if (!isLoadingNetworkDataFetch) {
                            mMealRecyclerViewAdapter.showLoading();

                            isLoadingNetworkDataFetch = true;
                            mMealRecyclerViewAdapter.setIsWithPagination(true);

                            mHomeViewModel.fetchRandomMeal(true);
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

        showLoading();

        mHomeViewModel.getMealsData().observe(getViewLifecycleOwner(), meals -> {
            if (isTablet) {
                mOldMealModel.setSelectedMeal(mHomeViewModel.getMealsData().getValue().getMealList().get(0));
            }
            mMealRecyclerViewAdapter.setData(meals.getMealList());
            hideLoading();
        });

        mHomeViewModel.getRandomMeals(true).observe(getViewLifecycleOwner(), meals -> {
            if (meals == null) {
                return;
            }

            isLoadingNetworkDataFetch = false;

            if (isTablet) {
                mOldMealModel.setSelectedMeal(mHomeViewModel.getRandomMeals(true).getValue().getMealList().get(0));
            }

            mMealRecyclerViewAdapter.setData(meals.getMealList());
        });

        /*binding.textInputLayoutSearch.setEndIconOnClickListener(v -> {
            showLoading();
            mHomeViewModel.fetchMealByCharacters(binding.textInputLayoutSearch.getEditText().getText().toString());
        });

        binding.textInputLayoutSearch.setStartIconOnClickListener(v -> {
            binding.textInputLayoutSearch.getEditText().setText(Constants.EMPTY);
            mHomeViewModel.fetchLatestMeals();
        });*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigationDrawer = (NavigationDrawer) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHomeViewModel.getMealsData().getValue() != null) {
            if (isTablet) {
                mOldMealModel.setSelectedMeal(mHomeViewModel.getMealsData().getValue().getMealList().get(0));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    /*public void onSearchFilterChanged(CharSequence s) {
        Log.e("tag", "onTextChanged " + s.toString());
        showLoading();
        mHomeViewModel.fetchMealByCharacters(s.toString());
    }*/

    private void showLoading() {
        binding.shimmerFrameLayoutMeals.startShimmer();
        binding.shimmerFrameLayoutMeals.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.shimmerFrameLayoutMeals.stopShimmer();
        binding.shimmerFrameLayoutMeals.setVisibility(View.GONE);
    }
}

package com.mhelrigo.foodmanual.ui.category.categorydetails;


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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.OldMealModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.databinding.FragmentCategoryDetailBinding;
import com.mhelrigo.foodmanual.ui.category.CategoriesViewModel;
import com.mhelrigo.foodmanual.ui.home.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.home.MealsType;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;
import com.mhelrigo.foodmanual.utils.Constants;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 */
@AndroidEntryPoint
public class CategoryDetailFragment extends Fragment {
    private static final String TAG = "CategoryDetailFragment";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private FragmentCategoryDetailBinding binding;

    CategoriesViewModel mCategoriesViewModel;
    private OldMealModel mOldMealModel;

    private MealRecyclerViewAdapter mMealRecyclerViewAdapter;

    private boolean isTablet = false;

    @Inject
    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mCategoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        mOldMealModel = new ViewModelProvider(this).get(OldMealModel.class);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(Constants.PACKAGE_NAME.concat(Constants.CATEGORY))){
                mCategoriesViewModel.setSelectedCategory(savedInstanceState.getParcelable(Constants.PACKAGE_NAME.concat(Constants.CATEGORY)));
            }
        }

        mCategoriesViewModel.fetchMealsByCategory();

        Log.e(TAG, "onCreate...");

        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =
                DataBindingUtil
                        .inflate(inflater, R.layout.fragment_category_detail, container, false);

        mMealRecyclerViewAdapter = new MealRecyclerViewAdapter(MealsType.LATEST, new MealRecyclerViewAdapter.OnMealClicked() {
            @Override
            public void onClicked(Meal meal) {
                Bundle bundle = new Bundle();
                bundle.putString("mealId", meal.getIdMeal());

                MealDetailsFragment mealDetailsFragment = new MealDetailsFragment();
                mealDetailsFragment.setArguments(bundle);

                if (isTablet){
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragmentCategoryDetails, mealDetailsFragment, MealDetailsFragment.class.getSimpleName())
                            .addToBackStack(mealDetailsFragment.getClass().getSimpleName())
                            .commit();
                }else{
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentCategory, mealDetailsFragment, MealDetailsFragment.class.getSimpleName())
                            .commit();
                }
            }

            @Override
            public void onAddedToFavorites(Meal meal) {
                mOldMealModel.addToFavorites(meal);
            }

            @Override
            public void onRemovedFromFavorites(Meal meal) {
                mOldMealModel.removeFromFavorites(meal);
            }
        });

        binding.setView(this);
        binding.setAdapter(mMealRecyclerViewAdapter);
        binding.setViewModel(mCategoriesViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.shimmerFrameLayoutMealsByCategory.startShimmer();
        binding.shimmerFrameLayoutMealsByCategory.setVisibility(View.VISIBLE);
        mCategoriesViewModel.getMealsByCategory().observe(getViewLifecycleOwner(), meals -> {
            if(meals == null){
                return;
            }
            mMealRecyclerViewAdapter.setData(meals.getMealList());
            binding.shimmerFrameLayoutMealsByCategory.setVisibility(View.GONE);
            binding.shimmerFrameLayoutMealsByCategory.stopShimmer();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isTablet){
            mCategoriesViewModel.reset();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCategoriesViewModel.getSelectedCategory() != null){
            outState.putParcelable(Constants.PACKAGE_NAME.concat(Constants.CATEGORY), mCategoriesViewModel.getSelectedCategory().getValue());
        }
    }

    public void goBack() {
        getActivity().onBackPressed();
    }
}

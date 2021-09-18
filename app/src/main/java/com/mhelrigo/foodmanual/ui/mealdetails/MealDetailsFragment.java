package com.mhelrigo.foodmanual.ui.mealdetails;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.mhelrigo.foodmanual.OldMealModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentMealDetailsBinding;
import com.mhelrigo.foodmanual.ui.home.HomeViewModel;
import com.mhelrigo.foodmanual.utils.Constants;
import com.mhelrigo.foodmanual.viewmodels.ViewModelProviderFactory;

import java.util.Arrays;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 */
@AndroidEntryPoint
public class MealDetailsFragment extends Fragment {
    private static final String TAG = "MealDetailsFragment";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    private OldMealModel mOldMealModel;
    private HomeViewModel mHomeViewModel;

    private TagsRecyclerViewAdapter tagsRecyclerViewAdapter;

    private String mWatchUrl, mSourceUrl;

    FragmentMealDetailsBinding binding;

    private ObjectAnimator animator;

    @Inject
    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mOldMealModel
                = new ViewModelProvider(this).get(OldMealModel.class);
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (getArguments() != null) {
            if (getArguments().containsKey("mealId")) {
                mOldMealModel.fetchMealById(getArguments().getString("mealId"));
            }
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.PACKAGE_NAME.concat(Constants.ID))) {
                mOldMealModel.fetchMealById(savedInstanceState.getString(Constants.PACKAGE_NAME.concat(Constants.ID)));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding
                = DataBindingUtil.inflate(inflater, R.layout.fragment_meal_details, container, false);

        tagsRecyclerViewAdapter = new TagsRecyclerViewAdapter();

        binding.setViewModel(mOldMealModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setAdapter(tagsRecyclerViewAdapter);
        binding.setView(this);
        binding.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startLoading();
        mOldMealModel.getSelectedMeal().observe(getViewLifecycleOwner(), meal -> {
            if (meal == null) {
                startLoading();
                return;
            }

            mOldMealModel.setSelectedMealId(meal.getIdMeal());

            if (meal.getStrTags() != null) {
                tagsRecyclerViewAdapter.setData(Arrays.asList(meal.getStrTags().split(",")));
            }

            mWatchUrl = meal.getStrYoutube();
            mSourceUrl = meal.getStrSource();

            stopLoading();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOldMealModel.setSelectedMeal(null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mOldMealModel.getSelectedMealId() != null) {
            outState.putString(Constants.PACKAGE_NAME.concat(Constants.ID), mOldMealModel.getSelectedMealId());
        }
    }

    private void startLoading() {
        binding.shimmerFrameLayoutMealDetail.startShimmer();
        binding.constraintLayoutShimmer.setVisibility(View.VISIBLE);
        binding.constraintLayoutMealDetail.setVisibility(View.GONE);
    }

    private void stopLoading() {
        binding.shimmerFrameLayoutMealDetail.stopShimmer();
        binding.constraintLayoutShimmer.setVisibility(View.GONE);
        binding.constraintLayoutMealDetail.setVisibility(View.VISIBLE);
    }

    public void watchManual() {
        Uri uri = Uri.parse(mWatchUrl); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void manualSauce() {
        Uri uri = Uri.parse(mSourceUrl); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void back() {
        getActivity().onBackPressed();
        mHomeViewModel.fetchLatestMeals();
    }

    public void favoriteToggle() {
        if (mOldMealModel.getSelectedMeal().getValue().isFavorite()) {
            removeFromFavorites();
            return;
        }

        addToFavorites();
    }

    public void addToFavorites() {
        binding.imageViewFavorite.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_favorite));
        animateFavoriteIcon(1.5f, 1.5f);
        mOldMealModel.getSelectedMeal().getValue().setFavorite(true);
        mOldMealModel.addToFavorites(mOldMealModel.getSelectedMeal().getValue());
    }

    public void removeFromFavorites() {
        binding.imageViewFavorite.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_favorite_1));
        animateFavoriteIcon(0.5f, 0.5f);
        mOldMealModel.getSelectedMeal().getValue().setFavorite(false);
        mOldMealModel.removeFromFavorites(mOldMealModel.getSelectedMeal().getValue());
    }

    public void animateFavoriteIcon(float xVal, float yVal) {
        animator = ObjectAnimator.ofPropertyValuesHolder(binding.imageViewFavorite, PropertyValuesHolder.ofFloat(View.SCALE_X, xVal), PropertyValuesHolder.ofFloat(View.SCALE_Y, yVal));
        animator.setRepeatCount(1);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.setDuration(100);
        animator.start();
    }
}

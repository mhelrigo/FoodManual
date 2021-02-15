package com.mhelrigo.foodmanual.ui.mealdetails;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.mhelrigo.foodmanual.BaseApplication;
import com.mhelrigo.foodmanual.MealViewModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentMealDetailsBinding;
import com.mhelrigo.foodmanual.ui.connectionreceiver.ConnectivityReceiver;
import com.mhelrigo.foodmanual.utils.Constants;
import com.mhelrigo.foodmanual.viewmodels.ViewModelProviderFactory;

import java.util.Arrays;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealDetailsFragment extends DaggerFragment {
    private static final String TAG = "MealDetailsFragment";

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    RequestManager requestManager;

    private MealViewModel mMealViewModel;
    private TagsRecyclerViewAdapter tagsRecyclerViewAdapter;

    private String mWatchUrl, mSourceUrl;

    FragmentMealDetailsBinding binding;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mMealViewModel
                = new ViewModelProvider(this, viewModelProviderFactory).get(MealViewModel.class);

        if (getArguments() != null) {
            if (getArguments().containsKey("mealId")) {
                mMealViewModel.fetchMealById(getArguments().getString("mealId"));
            }
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.PACKAGE_NAME.concat(Constants.ID))) {
                mMealViewModel.fetchMealById(savedInstanceState.getString(Constants.PACKAGE_NAME.concat(Constants.ID)));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding
                = DataBindingUtil.inflate(inflater, R.layout.fragment_meal_details, container, false);

        tagsRecyclerViewAdapter = new TagsRecyclerViewAdapter();

        binding.setViewModel(mMealViewModel);
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
        mMealViewModel.getSelectedMeal().observe(getViewLifecycleOwner(), meal -> {
            if (meal == null) {
                startLoading();
                return;
            }

            mMealViewModel.setSelectedMealId(meal.getIdMeal());

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
        mMealViewModel.setSelectedMeal(null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMealViewModel.getSelectedMealId() != null) {
            outState.putString(Constants.PACKAGE_NAME.concat(Constants.ID), mMealViewModel.getSelectedMealId());
        }
    }

    private void startLoading(){
        binding.shimmerFrameLayoutMealDetail.startShimmer();
        binding.constraintLayoutShimmer.setVisibility(View.VISIBLE);
        binding.constraintLayoutMealDetail.setVisibility(View.GONE);
    }

    private void stopLoading(){
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
    }
}

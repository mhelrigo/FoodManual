package com.mhelrigo.foodmanual.ui.meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentFavoritesBinding;
import com.mhelrigo.foodmanual.ui.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class FavoritesFragment extends BaseFragment {
    private FragmentFavoritesBinding binding;
    private MealViewModel mealViewModel;

    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater);

        return binding.getRoot();
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
                        .andThen(mealRecyclerViewAdapter.toggleFavoriteOfADrink(mealModel))).subscribe();

        mealViewModel.compositeDisposable.add(v0);

        binding.recyclerViewFavoriteMeals.setAdapter(mealRecyclerViewAdapter);
        binding.recyclerViewFavoriteMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void handleFavoriteMealsData() {
        mealViewModel.favoriteMeals().observe(getViewLifecycleOwner(), mealModels -> {
            mealRecyclerViewAdapter.meals.submitList(mealModels);
        });
    }
}
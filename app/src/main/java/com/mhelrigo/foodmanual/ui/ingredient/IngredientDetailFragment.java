package com.mhelrigo.foodmanual.ui.ingredient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentIngredientDetailBinding;
import com.mhelrigo.foodmanual.model.ingredient.IngredientModel;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.BaseFragment;
import com.mhelrigo.foodmanual.ui.meal.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.meal.MealViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class IngredientDetailFragment extends BaseFragment {
    private FragmentIngredientDetailBinding binding;
    private MealViewModel mealViewModel;

    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    public IngredientDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIngredientDetailBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealViewModel = new ViewModelProvider(getActivity()).get(MealViewModel.class);

        setUpRecyclerView();

        handleIngredientData();
        handleFilteredMeals();
    }

    private void setUpRecyclerView() {
        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();

        Disposable v0 = mealRecyclerViewAdapter.toggleFavorite
                .concatMapCompletable(mealModel -> mealViewModel.toggleFavoriteOfAMeal(mealModel)
                        .observeOn(AndroidSchedulers.mainThread())
                        .andThen(mealRecyclerViewAdapter.toggleFavoriteOfADrink(mealModel))).subscribe();

        Disposable v1 = mealRecyclerViewAdapter.expandDetail.subscribe(mealModel -> {
            Bundle v2 = new Bundle();
            v2.putString(MealModel.ID, mealModel.getIdMeal());
            findNavController(this).navigate(R.id.action_ingredientListFragment_to_ingredientDetailFragment, v2);
        });

        mealViewModel.compositeDisposable.add(v0);
        mealViewModel.compositeDisposable.add(v1);

        binding.recyclerViewFilteredMeals.setAdapter(mealRecyclerViewAdapter);
        binding.recyclerViewFilteredMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void handleIngredientData() {
        Bundle v0 = getArguments();
        if (v0.containsKey(IngredientModel.NAME)) {
            binding.textViewIngredient.setText(v0.getString(IngredientModel.NAME));
            binding.textViewDescription.setText(v0.getString(IngredientModel.DESCRIPTION));

            Glide.with(getContext()).load(v0.getString(IngredientModel.THUMBNAIL)).into(binding.imageViewThumbnail);

            mealViewModel.filterMealsByMainIngredient(v0.getString(IngredientModel.NAME));
        }
    }

    public void handleFilteredMeals() {
        mealViewModel.mealsFilteredByMainIngredient().observe(getViewLifecycleOwner(), mealModels -> {
            mealRecyclerViewAdapter.meals.submitList(mealModels);
        });
    }
}
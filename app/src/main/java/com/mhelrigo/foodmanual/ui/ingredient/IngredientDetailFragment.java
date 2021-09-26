package com.mhelrigo.foodmanual.ui.ingredient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.mhelrigo.foodmanual.ui.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.base.ViewState;
import com.mhelrigo.foodmanual.ui.meal.MealRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.meal.MealViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class IngredientDetailFragment extends BaseFragment<FragmentIngredientDetailBinding> {
    private MealViewModel mealViewModel;
    private IngredientViewModel ingredientViewModel;

    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    public IngredientDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_ingredient_detail;
    }

    @Override
    public FragmentIngredientDetailBinding inflateLayout(@NonNull LayoutInflater inflater) {
        return FragmentIngredientDetailBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealViewModel = new ViewModelProvider(getActivity()).get(MealViewModel.class);
        ingredientViewModel = new ViewModelProvider(getActivity()).get(IngredientViewModel.class);

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
            findNavController(this).navigate(R.id.action_ingredientDetailFragment_to_mealDetailFragment, v2);
        });

        mealViewModel.compositeDisposable.add(v0);
        mealViewModel.compositeDisposable.add(v1);

        binding.recyclerViewFilteredMeals.setAdapter(mealRecyclerViewAdapter);
        binding.recyclerViewFilteredMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void handleIngredientData() {
        ingredientViewModel.ingredient().observe(getViewLifecycleOwner(), ingredientModel -> {
            binding.textViewIngredient.setText(ingredientModel.getStrIngredient());

            if (ingredientModel.strDescriptionEmpty()) {
                binding.textViewDescription.setVisibility(View.GONE);
                binding.imageViewThumbnail.setVisibility(View.GONE);
            }

            binding.textViewDescription.setText(ingredientModel.getStrDescription());
            Glide.with(getContext()).load(ingredientModel.thumbnail()).into(binding.imageViewThumbnail);

            mealViewModel.filterMealsByMainIngredient(ingredientModel.getStrIngredient());
        });
    }

    public void handleFilteredMeals() {
        mealViewModel.mealsFilteredByMainIngredient().observe(getViewLifecycleOwner(), listResultWrapper -> {
            if (listResultWrapper.getViewState().equals(ViewState.LOADING)) {
                processLoadingState(listResultWrapper.getViewState(), binding.imageViewLoading);
                binding.imageViewLoading.setVisibility(View.VISIBLE);
                binding.recyclerViewFilteredMeals.setVisibility(View.GONE);
            } else if (listResultWrapper.getViewState().equals(ViewState.SUCCESS)) {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.recyclerViewFilteredMeals.setVisibility(View.VISIBLE);
                mealRecyclerViewAdapter.meals.submitList(listResultWrapper.getResult());
            } else {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.recyclerViewFilteredMeals.setVisibility(View.GONE);
            }
        });
    }
}
package com.mhelrigo.foodmanual.ui.meal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentMealListBinding;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.commons.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.commons.base.ViewState;
import com.mhelrigo.foodmanual.ui.category.CategoryRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.category.CategoryViewModel;

import java.util.List;

import javax.inject.Singleton;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@Singleton
@AndroidEntryPoint
public class MealListFragment extends BaseFragment<FragmentMealListBinding> implements MealNavigator {
    private MealViewModel mealViewModel;
    private CategoryViewModel categoryViewModel;

    private MealRecyclerViewAdapter mealRecyclerViewAdapter;
    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;

    private CharSequence searchedDrinkTemp = "";

    public MealListFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_meal_list;
    }

    @Override
    public FragmentMealListBinding inflateLayout(@NonNull LayoutInflater inflater) {
        return FragmentMealListBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealViewModel = new ViewModelProvider(getActivity()).get(MealViewModel.class);
        categoryViewModel = new ViewModelProvider(getActivity()).get(CategoryViewModel.class);

        setUpSearchTextListeners();
        setUpLatestMealsRecyclerView();
        setUpCategoriesRecyclerView();

        handleSearchedMealsData();
        handleLatestMealsData();
        handleCategoriesData();

        requestData();

        if (isTablet) {
            refreshMealsWhenItemToggled();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpSearchTextListeners() {
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchedDrinkTemp = charSequence;
                mealViewModel.searchForMealsByName(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.editTextSearch.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= (binding.editTextSearch.getRight() - binding.editTextSearch.getCompoundDrawables()[2].getBounds().width())) {
                    Disposable v0 = mealViewModel.cancelSearchByName()
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnComplete(() -> {
                                binding.editTextSearch.getText().clear();
                                binding.editTextSearch.clearFocus();
                            }).subscribe();

                    mealViewModel.compositeDisposable.add(v0);
                    return true;
                }
            }

            return false;
        });
    }

    private void setUpLatestMealsRecyclerView() {
        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();

        Disposable v0 = mealRecyclerViewAdapter.toggleFavorite
                .concatMapCompletable(mealModel -> mealViewModel.toggleFavoriteOfAMeal(mealModel)
                        .observeOn(AndroidSchedulers.mainThread())
                        .andThen(mealRecyclerViewAdapter.toggleFavoriteOfADrink(mealModel))
                        .doOnComplete(() -> {
                            if (isTablet) {
                                // Sync data on both screen
                                mealViewModel.setMealIdToBeSearched(mealModel.getIdMeal());
                            }
                        }))
                .subscribe();

        Disposable v1 = mealRecyclerViewAdapter.expandDetail.subscribe(mealModel -> {
            mealViewModel.setMealIdToBeSearched(mealModel.getIdMeal());
            navigateToMealDetail(R.id.action_mealListFragment_to_mealDetailFragment,
                    null,
                    findNavController(this), isTablet);
        });

        mealViewModel.compositeDisposable.add(v0);
        mealViewModel.compositeDisposable.add(v1);

        binding.recyclerViewLatestMeals.setAdapter(mealRecyclerViewAdapter);
        binding.recyclerViewLatestMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpCategoriesRecyclerView() {
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter();

        Disposable v0 = categoryRecyclerViewAdapter.expandCategoryDetail
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryModel -> {
                    categoryViewModel.setCategory(categoryModel);
                    findNavController(this).navigate(R.id.action_mealListFragment_to_categoryDetailFragment);
                });

        categoryViewModel.compositeDisposable.add(v0);

        binding.recyclerViewCategories.setAdapter(categoryRecyclerViewAdapter);
        binding.recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void handleLatestMealsData() {
        mealViewModel.latestMeals().observe(getViewLifecycleOwner(), mealModels -> {
            Timber.e("mealModels : " + mealModels.getViewState());
            if (mealModels.getViewState().equals(ViewState.LOADING)) {
                requestingForMealsUiSetup(mealModels.getViewState());
            } else if (mealModels.getViewState().equals(ViewState.SUCCESS)) {
                mealsRequestedUiSetup(mealModels.getResult());
            } else {
                errorRequestingMealsUiSetup();
            }
        });
    }

    private void handleSearchedMealsData() {
        mealViewModel.searchedMeals().observe(getViewLifecycleOwner(), mealModels -> {
            Timber.e("mealModels : " + mealModels.getViewState());
            if (mealModels.getViewState().equals(ViewState.LOADING)) {
                requestingForMealsUiSetup(mealModels.getViewState());
            } else if (mealModels.getViewState().equals(ViewState.SUCCESS)) {
                mealsRequestedUiSetup(mealModels.getResult());
            } else {
                errorRequestingMealsUiSetup();
            }
        });
    }

    private void requestingForMealsUiSetup(ViewState p0) {
        processLoadingState(p0, binding.imageViewLoading);
        binding.imageViewLoading.setVisibility(View.VISIBLE);
        binding.recyclerViewLatestMeals.setVisibility(View.GONE);
        binding.textViewEmptyMeals.setVisibility(View.GONE);
        binding.textViewErrorForMeals.setVisibility(View.GONE);
    }

    private void mealsRequestedUiSetup(List<MealModel> p0) {
        binding.recyclerViewLatestMeals.setVisibility(View.VISIBLE);
        binding.imageViewLoading.setVisibility(View.GONE);
        binding.textViewEmptyMeals.setVisibility(View.GONE);
        binding.textViewErrorForMeals.setVisibility(View.GONE);

        mealRecyclerViewAdapter.meals.submitList(p0);

        if (p0 == null || p0.isEmpty()) {
            binding.textViewEmptyMeals.setVisibility(View.VISIBLE);
            return;
        }
    }

    private void errorRequestingMealsUiSetup() {
        binding.textViewErrorForMeals.setVisibility(View.VISIBLE);
        binding.imageViewLoading.setVisibility(View.GONE);
        binding.recyclerViewLatestMeals.setVisibility(View.GONE);
        binding.textViewEmptyMeals.setVisibility(View.GONE);
    }

    private void handleCategoriesData() {
        categoryViewModel.categories().observe(getViewLifecycleOwner(), listResultWrapper -> {
            if (listResultWrapper.getViewState().equals(ViewState.SUCCESS)) {
                categoryRecyclerViewAdapter.categories.submitList(listResultWrapper.getResult());
                binding.textViewCategories.setVisibility(View.VISIBLE);
                binding.recyclerViewCategories.setVisibility(View.VISIBLE);
            } else {
                binding.textViewCategories.setVisibility(View.GONE);
                binding.recyclerViewCategories.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void refreshMealsWhenItemToggled() {
        mealViewModel.mealThatIsToggled.subscribe(mealModel -> mealViewModel.requestForLatestMeal());
    }

    @Override
    public void requestData() {
        if (mealViewModel.latestMeals().getValue().noResultYet()) {
            mealViewModel.requestForLatestMeal();
        }

        if (categoryViewModel.categories().getValue().noResultYet()) {
            categoryViewModel.requestForCategories();
        }
    }
}
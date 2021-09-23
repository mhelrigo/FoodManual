package com.mhelrigo.foodmanual.ui.meal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentMealListBinding;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.BaseFragment;

import javax.inject.Singleton;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@Singleton
@AndroidEntryPoint
public class MealListFragment extends BaseFragment {
    private FragmentMealListBinding binding;
    private MealViewModel mealViewModel;

    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    public MealListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMealListBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealViewModel = new ViewModelProvider(getActivity()).get(MealViewModel.class);

        setUpRecyclerView();

        handleLatestMealsData();
        handleSearchedMealsData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpSearchTextListeners() {
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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

    private void setUpRecyclerView() {
        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();

        Disposable v0 = mealRecyclerViewAdapter.toggleFavorite
                .concatMapCompletable(mealModel -> mealViewModel.toggleFavoriteOfAMeal(mealModel)
                        .observeOn(AndroidSchedulers.mainThread())
                        .andThen(mealRecyclerViewAdapter.toggleFavoriteOfADrink(mealModel))).subscribe();

        Disposable v1 = mealRecyclerViewAdapter.expandDetail.subscribe(mealModel -> {
            Bundle v2 = new Bundle();
            v2.putString(MealModel.ID, mealModel.getIdMeal());
            findNavController(this).navigate(R.id.action_mealListFragment_to_mealDetailFragment, v2);
        });

        mealViewModel.compositeDisposable.add(v0);
        mealViewModel.compositeDisposable.add(v1);

        binding.recyclerViewLatestMeals.setAdapter(mealRecyclerViewAdapter);
        binding.recyclerViewLatestMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void handleLatestMealsData() {
        mealViewModel.requestForLatestMeal();
        mealViewModel.latestMeals().observe(getViewLifecycleOwner(), mealModels -> {
            mealRecyclerViewAdapter.meals.submitList(mealModels);
        });
    }

    private void handleSearchedMealsData() {
        mealViewModel.searchedMeals().observe(getViewLifecycleOwner(), mealModels -> {
            mealRecyclerViewAdapter.meals.submitList(mealModels);
        });
    }
}
package com.mhelrigo.foodmanual.ui.ingredient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentIngredientListBinding;
import com.mhelrigo.foodmanual.model.ingredient.IngredientModel;
import com.mhelrigo.foodmanual.ui.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.base.ViewState;

import javax.inject.Singleton;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

@Singleton
@AndroidEntryPoint
public class IngredientListFragment extends BaseFragment<FragmentIngredientListBinding> {
    private IngredientViewModel ingredientViewModel;

    private IngredientRecyclerViewAdapter ingredientRecyclerViewAdapter;

    public IngredientListFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_ingredient_list;
    }

    @Override
    public FragmentIngredientListBinding inflateLayout(@NonNull LayoutInflater inflater) {
        return FragmentIngredientListBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientViewModel = new ViewModelProvider(getActivity()).get(IngredientViewModel.class);

        setUpRecyclerView();

        ingredientViewModel.requestForAllIngredient();
        handleIngredientsData();
    }

    private void setUpRecyclerView() {
        ingredientRecyclerViewAdapter = new IngredientRecyclerViewAdapter();

        Disposable v0 = ingredientRecyclerViewAdapter.expandIngredientDetails.subscribe(ingredientModel -> {
            ingredientViewModel.setIngredient(ingredientModel);
            findNavController(this).navigate(R.id.action_ingredientListFragment_to_ingredientDetailFragment);
        });

        ingredientViewModel.compositeDisposable.add(v0);

        binding.recyclerViewIngredients.setAdapter(ingredientRecyclerViewAdapter);
        binding.recyclerViewIngredients.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void handleIngredientsData() {
        ingredientViewModel.ingredients().observe(getViewLifecycleOwner(), listResultWrapper -> {
            if (listResultWrapper.getViewState().equals(ViewState.LOADING)) {
                processLoadingState(listResultWrapper.getViewState(), binding.imageViewLoading);

                binding.imageViewLoading.setVisibility(View.VISIBLE);
                binding.textViewEmptyIngredients.setVisibility(View.GONE);
                binding.textViewErrorForIngredients.setVisibility(View.GONE);
                binding.recyclerViewIngredients.setVisibility(View.GONE);
            } else if (listResultWrapper.getViewState().equals(ViewState.SUCCESS)) {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.textViewEmptyIngredients.setVisibility(View.GONE);
                binding.textViewErrorForIngredients.setVisibility(View.GONE);
                binding.recyclerViewIngredients.setVisibility(View.VISIBLE);

                if (listResultWrapper.getResult() == null && listResultWrapper.getResult().isEmpty()) {
                    binding.textViewEmptyIngredients.setVisibility(View.VISIBLE);
                    return;
                }

                ingredientRecyclerViewAdapter.ingredients.submitList(listResultWrapper.getResult());
            } else {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.textViewEmptyIngredients.setVisibility(View.GONE);
                binding.textViewErrorForIngredients.setVisibility(View.VISIBLE);
                binding.recyclerViewIngredients.setVisibility(View.GONE);
            }
        });
    }
}
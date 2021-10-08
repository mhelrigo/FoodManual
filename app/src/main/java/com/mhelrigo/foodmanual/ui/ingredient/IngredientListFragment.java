package com.mhelrigo.foodmanual.ui.ingredient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentIngredientListBinding;
import com.mhelrigo.foodmanual.ui.commons.base.BaseFragment;
import com.mhelrigo.foodmanual.ui.commons.base.ViewState;

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

        handleIngredientsData();

        requestData();
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
            processLoadingState(listResultWrapper.getViewState(), binding.imageViewLoading);
            if (listResultWrapper.getViewState().equals(ViewState.LOADING)) {
                binding.imageViewLoading.setVisibility(View.VISIBLE);
                binding.textViewEmptyIngredients.setVisibility(View.GONE);
                binding.textViewErrorForIngredients.setVisibility(View.GONE);
                binding.recyclerViewIngredients.setVisibility(View.GONE);
            } else if (listResultWrapper.getViewState().equals(ViewState.SUCCESS)) {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.textViewEmptyIngredients.setVisibility(View.GONE);
                binding.textViewErrorForIngredients.setVisibility(View.GONE);
                binding.recyclerViewIngredients.setVisibility(View.VISIBLE);

                ingredientRecyclerViewAdapter.ingredients.submitList(listResultWrapper.getResult());

                if (listResultWrapper.getResult() == null || listResultWrapper.getResult().isEmpty()) {
                    binding.textViewEmptyIngredients.setVisibility(View.VISIBLE);
                    return;
                }
            } else {
                binding.imageViewLoading.setVisibility(View.GONE);
                binding.textViewEmptyIngredients.setVisibility(View.GONE);
                binding.textViewErrorForIngredients.setVisibility(View.VISIBLE);
                binding.recyclerViewIngredients.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void requestData() {
        if (ingredientViewModel.ingredients().getValue().noResultYet()) {
            ingredientViewModel.requestForAllIngredient();
        }
    }
}
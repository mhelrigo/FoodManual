package com.mhelrigo.foodmanual.ui.ingredient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentIngredientListBinding;
import com.mhelrigo.foodmanual.model.ingredient.IngredientModel;
import com.mhelrigo.foodmanual.ui.BaseFragment;

import javax.inject.Singleton;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Consumer;

@Singleton
@AndroidEntryPoint
public class IngredientListFragment extends BaseFragment {
    private FragmentIngredientListBinding binding;
    private IngredientViewModel ingredientViewModel;

    private IngredientRecyclerViewAdapter ingredientRecyclerViewAdapter;

    public IngredientListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIngredientListBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        setUpRecyclerView();

        ingredientViewModel.requestForAllIngredient();
        handleIngredientsData();
    }

    private void setUpRecyclerView() {
        ingredientRecyclerViewAdapter = new IngredientRecyclerViewAdapter();

        ingredientRecyclerViewAdapter.expandIngredientDetails.subscribe(ingredientModel -> {
            Bundle v0 = new Bundle();
            v0.putString(IngredientModel.NAME, ingredientModel.getStrIngredient());
            v0.putString(IngredientModel.DESCRIPTION, ingredientModel.getStrDescription());
            v0.putString(IngredientModel.THUMBNAIL, ingredientModel.thumbnail());
            NavHostFragment.findNavController(this).navigate(R.id.action_ingredientListFragment_to_ingredientDetailFragment, v0);
        });

        binding.recyclerViewIngredients.setAdapter(ingredientRecyclerViewAdapter);
        binding.recyclerViewIngredients.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void handleIngredientsData() {
        ingredientViewModel.ingredients().observe(getViewLifecycleOwner(), ingredientModels -> {
            ingredientRecyclerViewAdapter.ingredients.submitList(ingredientModels);
        });
    }
}
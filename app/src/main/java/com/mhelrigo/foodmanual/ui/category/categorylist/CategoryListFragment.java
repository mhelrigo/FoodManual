package com.mhelrigo.foodmanual.ui.category.categorylist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentCategoryListBinding;
import com.mhelrigo.foodmanual.ui.category.CategoriesViewModel;
import com.mhelrigo.foodmanual.ui.category.CategoryRecyclerViewAdapter;
import com.mhelrigo.foodmanual.ui.category.categorydetails.CategoryDetailFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 */
@AndroidEntryPoint
public class CategoryListFragment extends Fragment {
    private static final String TAG = "CategoryListFragment";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    private FragmentCategoryListBinding binding;

    CategoriesViewModel mCategoriesViewModel;

    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;

    private boolean isTablet = false;

    @Inject
    public CategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        /*mCategoriesViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(CategoriesViewModel.class);*/
        mCategoriesViewModel.fetchCategories();

        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =
                DataBindingUtil
                        .inflate(inflater, R.layout.fragment_category_list, container, false);

        CategoryDetailFragment categoryDetailFragment = new CategoryDetailFragment();

        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(category -> {
            mCategoriesViewModel.setSelectedCategory(category);

            if (!isTablet){
                getFragmentManager().beginTransaction()
                        .add(R.id.fragmentCategory, categoryDetailFragment)
                        .addToBackStack(CategoryDetailFragment.class.getSimpleName())
                        .commit();
            }else {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentCategoryDetails, new CategoryDetailFragment(), CategoryDetailFragment.class.getSimpleName())
                        .commit();
            }
        });

        binding.setView(this);
        binding.setAdapter(categoryRecyclerViewAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.shimmerFrameLayoutCategories.startShimmer();
        binding.shimmerFrameLayoutCategories.setVisibility(View.VISIBLE);
        mCategoriesViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (isTablet){
                mCategoriesViewModel.setSelectedCategory(mCategoriesViewModel.getCategories().getValue().getCategories().get(0));
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentCategoryDetails, new CategoryDetailFragment(), CategoryDetailFragment.class.getSimpleName())
                        .commit();
            }
            categoryRecyclerViewAdapter.setData(categories.getCategories());
            binding.shimmerFrameLayoutCategories.stopShimmer();
            binding.shimmerFrameLayoutCategories.setVisibility(View.GONE);
        });
    }

    public void goBack(){
        if (isTablet){
            getActivity().finish();
        }else {
            getActivity().onBackPressed();
        }
    }
}

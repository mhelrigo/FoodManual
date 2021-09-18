package com.mhelrigo.foodmanual.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.mhelrigo.foodmanual.BaseActivity;
import com.mhelrigo.foodmanual.BaseApplication;
import com.mhelrigo.foodmanual.OldMealModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.ActivityCategoryBinding;
import com.mhelrigo.foodmanual.ui.category.categorylist.CategoryListFragment;
import com.mhelrigo.foodmanual.ui.connectionreceiver.ConnectivityReceiver;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesActivity extends AppCompatActivity {
    private static final String TAG = "CategoriesActivity";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    CategoriesViewModel mCategoriesViewModel;
    OldMealModel mOldMealModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_category);

        mCategoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        binding.setViewModel(mCategoriesViewModel);

        mOldMealModel = new ViewModelProvider(this).get(OldMealModel.class);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentCategory, new CategoryListFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCategoriesViewModel.setIsNetworkConnected(ConnectivityReceiver.isConnected(this));
        BaseApplication.getInstance().setConnectionListener(isConnected -> {
            mCategoriesViewModel.setIsNetworkConnected(isConnected);
            mCategoriesViewModel.onRetryNetworkRequests();

            MealDetailsFragment mealDetailsFragment = (MealDetailsFragment) getSupportFragmentManager().findFragmentByTag(MealDetailsFragment.class.getSimpleName());
            if (mealDetailsFragment != null && mealDetailsFragment.isVisible()){
                mOldMealModel.setIsNetworkConnected(isConnected);
                mOldMealModel.onRetryNetworkRequests();
            }
        });
    }
}

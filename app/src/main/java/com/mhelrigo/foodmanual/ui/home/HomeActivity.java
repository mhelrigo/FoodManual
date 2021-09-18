package com.mhelrigo.foodmanual.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.BaseActivity;
import com.mhelrigo.foodmanual.BaseApplication;
import com.mhelrigo.foodmanual.MealViewModel;
import com.mhelrigo.foodmanual.OldMealModel;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.ActivityHomeBinding;
import com.mhelrigo.foodmanual.databinding.NavigationMenuBinding;
import com.mhelrigo.foodmanual.ui.category.CategoriesActivity;
import com.mhelrigo.foodmanual.ui.connectionreceiver.ConnectivityReceiver;
import com.mhelrigo.foodmanual.ui.home.favoritemeals.FavoriteMealFragment;
import com.mhelrigo.foodmanual.ui.home.latestmeals.LatestMealFragment;
import com.mhelrigo.foodmanual.ui.home.randommeals.RandomMealsFragment;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;
import com.mhelrigo.foodmanual.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity implements NavigationDrawer {
    private static final String TAG = "HomeActivity";

    /*@Inject
    ViewModelProviderFactory viewModelProviderFactory;*/

    private HomeViewModel mHomeViewModel;
    private OldMealModel mOldMealModel;
    private MealViewModel mealViewModel;

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        NavigationMenuBinding navBinding =
                DataBindingUtil
                        .inflate(getLayoutInflater(), R.layout.navigation_menu, binding.navigationView, false);

        FirebaseAnalytics.getInstance(this);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setView(this);
        binding.setViewModel(mHomeViewModel);
        binding.navigationView.addHeaderView(navBinding.getRoot());
        navBinding.setView(this);

        mOldMealModel = new ViewModelProvider(this).get(OldMealModel.class);
        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentHome, new LatestMealFragment(), LatestMealFragment.class.getSimpleName())
                    .commit();
        }

        if (getResources().getBoolean(R.bool.isTablet)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentMealDetails, new MealDetailsFragment(), MealDetailsFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHomeViewModel.setIsNetworkConnected(ConnectivityReceiver.isConnected(this));
        BaseApplication.getInstance().setConnectionListener(isConnected -> {
            mHomeViewModel.setIsNetworkConnected(isConnected);
            mHomeViewModel.onRetryNetworkRequests();

            MealDetailsFragment mealDetailsFragment = (MealDetailsFragment) getSupportFragmentManager().findFragmentByTag(MealDetailsFragment.class.getSimpleName());
            if (mealDetailsFragment != null && mealDetailsFragment.isVisible()) {
                mOldMealModel.setIsNetworkConnected(isConnected);
                mOldMealModel.onRetryNetworkRequests();
            }
        });
    }

    @Override
    public void openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void goToCategory() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, CategoriesActivity.class));
    }

    @Override
    public void goToRandomMeals() {
        resetRandomMeals();
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentHome, new RandomMealsFragment())
                .addToBackStack(RandomMealsFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void goToFavoriteMeals() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentHome, new FavoriteMealFragment())
                .addToBackStack(FavoriteMealFragment.class.getSimpleName())
                .commit();
    }

    public String versionName() {
        try {
            return "v" + getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "v0.0.0";
        }
    }

    private void resetRandomMeals() {
        mHomeViewModel.setRandomMeals(null);
        mOldMealModel.setSelectedMeal(null);
    }
}

package com.mhelrigo.foodmanual.ui;

import static com.mhelrigo.foodmanual.di.AppModule.IS_TABLET;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.ActivityHomeBinding;
import com.mhelrigo.foodmanual.ui.meal.MealDetailFragment;
import com.mhelrigo.foodmanual.ui.meal.MealViewModel;
import com.mhelrigo.foodmanual.ui.settings.SettingsViewModel;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private SettingsViewModel settingsViewModel;
    private MealViewModel mealViewModel;

    @Inject
    @Named(IS_TABLET)
    public Boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);

        setUpBottomNavigation();
        handleNightModeSwitchChanges();

        setUpViewForFragment(savedInstanceState);
    }

    private void setUpViewForFragment(Bundle p0) {
        if (isTablet) {
            if (p0 == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameLayoutMealDetail, new MealDetailFragment(), MealDetailFragment.class.getSimpleName())
                        .commit();
            }
        }
    }

    private void handleNightModeSwitchChanges() {
        settingsViewModel.nightMode().observe(this, isNightMode -> {
            if (isNightMode) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    private void setUpBottomNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentContainerViewHome.getId());
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }
}
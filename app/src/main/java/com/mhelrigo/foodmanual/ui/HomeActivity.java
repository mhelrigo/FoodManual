package com.mhelrigo.foodmanual.ui;

import static com.mhelrigo.foodmanual.di.AppModule.IS_TABLET;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.manager.ConnectivityMonitor;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.ActivityHomeBinding;
import com.mhelrigo.foodmanual.ui.meal.MealDetailFragment;
import com.mhelrigo.foodmanual.ui.meal.MealViewModel;
import com.mhelrigo.foodmanual.ui.settings.SettingsViewModel;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private SettingsViewModel settingsViewModel;

    @Inject
    @Named(IS_TABLET)
    public Boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        setUpBottomNavigation();
        handleNightModeSwitchChanges();
        handleInternetConnectionChanges();

        setUpViewForFragment(savedInstanceState);

        settingsViewModel.requestForSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectivityListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterConnectivityListener();
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

    private void handleInternetConnectionChanges() {
        settingsViewModel.isNetworkAvailable().observe(this, isNetworkAvailable -> {
            if (isNetworkAvailable) {
                binding.linearLayoutNoInternetConnection.setVisibility(View.GONE);
            } else {
                binding.linearLayoutNoInternetConnection.setVisibility(View.VISIBLE);
            }
        });
    }

    private void registerConnectivityListener() {
        registerReceiver(connectivityListener, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void unRegisterConnectivityListener() {
        unregisterReceiver(connectivityListener);
    }

    private BroadcastReceiver connectivityListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            settingsViewModel.setIsNetworkAvailable(networkInfo != null && networkInfo.isConnectedOrConnecting());
        }
    };
}
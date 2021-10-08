package com.mhelrigo.foodmanual.ui.commons.base;

import static com.mhelrigo.foodmanual.di.AppModule.IS_TABLET;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.mhelrigo.foodmanual.ui.settings.SettingsViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public abstract class BaseFragment<B extends ViewBinding> extends Fragment implements DataRequestManager {
    protected B binding;

    @LayoutRes
    public abstract int layoutId();

    public abstract B inflateLayout(@NonNull LayoutInflater inflater);

    @Inject
    @Named(IS_TABLET)
    public Boolean isTablet;

    private SettingsViewModel settingsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = inflateLayout(inflater);

        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setUpDeviceBackNavigation(this);
        handleInternetConnectivityChanges();
    }

    public NavController findNavController(@NonNull Fragment fragment) {
        return NavHostFragment.findNavController(fragment);
    }

    public void setUpDeviceBackNavigation(@NonNull Fragment fragment) {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                findNavController(fragment).popBackStack();
            }
        });
    }

    public void processLoadingState(ViewState viewState, View view) {
        ValueAnimator objectAnimator =
                new ObjectAnimator().ofFloat(view, View.ROTATION, -360f, 0f);
        objectAnimator.setDuration(2500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        if (viewState.equals(ViewState.LOADING)) {
            objectAnimator.start();
        } else {
            objectAnimator.cancel();
        }
    }

    private void handleInternetConnectivityChanges() {
        settingsViewModel.isNetworkAvailable().observe(getViewLifecycleOwner(), isNetworkAvailable -> {
            if (isNetworkAvailable) {
                requestData();
            }
        });
    }
}

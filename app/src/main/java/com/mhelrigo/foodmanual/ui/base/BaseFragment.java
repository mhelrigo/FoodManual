package com.mhelrigo.foodmanual.ui.base;

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
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;

public abstract class BaseFragment<B extends ViewBinding> extends Fragment {
    protected B binding;

    @LayoutRes
    public abstract int layoutId();

    public abstract B inflateLayout(@NonNull LayoutInflater inflater);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = inflateLayout(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpDeviceBackNavigation(this);
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
}

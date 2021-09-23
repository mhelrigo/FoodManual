package com.mhelrigo.foodmanual.ui;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class BaseFragment extends Fragment {
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
}

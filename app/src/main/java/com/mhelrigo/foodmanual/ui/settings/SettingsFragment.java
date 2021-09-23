package com.mhelrigo.foodmanual.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhelrigo.foodmanual.databinding.FragmentSettingsBinding;
import com.mhelrigo.foodmanual.ui.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

import android.content.res.Configuration;

import javax.inject.Singleton;

@Singleton
@AndroidEntryPoint
public class SettingsFragment extends BaseFragment {
    private FragmentSettingsBinding binding;
    private SettingsViewModel settingsViewModel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);

        setUpNightModeSwitch();
        handleNightModeSwitchChanges();

        settingsViewModel.setNightMode(isNightMode(getResources().getConfiguration()));
    }

    private void setUpNightModeSwitch() {
        binding.switchNightMode.setOnCheckedChangeListener((compoundButton, b) -> {
            settingsViewModel.setNightMode(b);
        });
    }

    private void handleNightModeSwitchChanges() {
        settingsViewModel.nightMode().observe(getViewLifecycleOwner(), isNightMode -> {
            binding.switchNightMode.setChecked(isNightMode);
        });
    }

    private Boolean isNightMode(Configuration configuration) {
        switch (configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES: {
                return true;
            }
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED: {
                return false;
            }
            default:
                return false;
        }
    }
}
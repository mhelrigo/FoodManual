package com.mhelrigo.foodmanual.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;

import com.mhelrigo.foodmanual.BuildConfig;
import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.FragmentSettingsBinding;
import com.mhelrigo.foodmanual.ui.commons.base.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

import android.content.res.Configuration;

import javax.inject.Singleton;

@Singleton
@AndroidEntryPoint
public class SettingsFragment extends BaseFragment<FragmentSettingsBinding> {
    private SettingsViewModel settingsViewModel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_meal_list;
    }

    @Override
    public FragmentSettingsBinding inflateLayout(@NonNull LayoutInflater inflater) {
        return FragmentSettingsBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);

        setUpNightModeSwitch();
        handleNightModeSwitchChanges();
        displayApplicationVersion();

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

    private void displayApplicationVersion() {
        binding.textViewVersion.setText("version: " + BuildConfig.VERSION_NAME);
    }

    /**
     * Method that check if the system is in Dark Mode
     * */
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
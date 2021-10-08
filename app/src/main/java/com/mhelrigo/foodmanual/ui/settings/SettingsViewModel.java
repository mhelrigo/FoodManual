package com.mhelrigo.foodmanual.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private MutableLiveData<Boolean> nightMode;

    public LiveData<Boolean> nightMode() {
        return nightMode;
    }

    private MutableLiveData<Boolean> isNetworkAvailable;

    public LiveData<Boolean> isNetworkAvailable() {
        return isNetworkAvailable;
    }

    @Inject
    public SettingsViewModel() {
        nightMode = new MutableLiveData<>();
        isNetworkAvailable = new MutableLiveData<>();
    }

    public void setNightMode(Boolean p0) {
        nightMode.postValue(p0);
    }

    public void setIsNetworkAvailable(Boolean p0) {
        isNetworkAvailable.postValue(p0);
    }
}

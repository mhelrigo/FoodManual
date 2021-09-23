package com.mhelrigo.foodmanual.ui.settings;

import android.util.MutableBoolean;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private MutableLiveData<Boolean> nightMode;

    public LiveData<Boolean> nightMode() {
        return nightMode;
    }

    public void setNightMode(Boolean p0) {
        nightMode.postValue(p0);
    }

    @Inject
    public SettingsViewModel() {
        nightMode = new MutableLiveData<>();
    }
}

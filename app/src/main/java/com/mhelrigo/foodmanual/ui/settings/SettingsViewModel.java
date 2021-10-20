package com.mhelrigo.foodmanual.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.disposables.CompositeDisposable;
import mhelrigo.foodmanual.domain.entity.settings.SettingEntity;
import mhelrigo.foodmanual.domain.usecase.settings.GetSettings;
import mhelrigo.foodmanual.domain.usecase.settings.ModifySettings;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private GetSettings getSettings;
    private ModifySettings modifySettings;

    private MutableLiveData<Boolean> nightMode;

    public LiveData<Boolean> nightMode() {
        return nightMode;
    }

    private MutableLiveData<Boolean> isNetworkAvailable;

    public LiveData<Boolean> isNetworkAvailable() {
        return isNetworkAvailable;
    }

    private CompositeDisposable compositeDisposable;

    @Inject
    public SettingsViewModel(GetSettings getSettings, ModifySettings modifySettings) {
        this.getSettings = getSettings;
        this.modifySettings = modifySettings;

        nightMode = new MutableLiveData<>();
        isNetworkAvailable = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    public void requestForSettings() {
        compositeDisposable.add(getSettings.execute(null).subscribe(settingEntity -> {
            nightMode.postValue(settingEntity.getNightMode());
        }));
    }

    public void setNightMode(Boolean p0) {
        SettingEntity v0 = new SettingEntity();
        v0.setNightMode(p0);
        compositeDisposable.add(modifySettings.execute(ModifySettings.Params.params(v0)).doOnComplete(() -> {
            nightMode.postValue(p0);
        }).subscribe());
    }

    public void setIsNetworkAvailable(Boolean p0) {
        isNetworkAvailable.postValue(p0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}

package com.mhelrigo.foodmanual.ui.home;

import androidx.lifecycle.ViewModel;

import com.mhelrigo.foodmanual.di.ViewModelKey;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HomeModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel homeViewModel(HomeViewModel homeViewModel);
}
